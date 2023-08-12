package pl.maciejbigos.currentcurrency.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.maciejbigos.currentcurrency.exceptions.CurrencyCodeNotFoundException;
import pl.maciejbigos.currentcurrency.exceptions.NotEnoughDataException;
import pl.maciejbigos.currentcurrency.model.CurrencyRequest;
import pl.maciejbigos.currentcurrency.model.Rate;
import pl.maciejbigos.currentcurrency.model.dto.CurrentCurrencyRequest;
import pl.maciejbigos.currentcurrency.model.dto.CurrentCurrencyResponse;
import pl.maciejbigos.currentcurrency.model.ExchangeRate;
import pl.maciejbigos.currentcurrency.repository.CurrencyRequestRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyRequestRepository repository;

    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyService(restTemplate, repository);
    }

    @Test
    void testGetCurrentCurrencyValue_Success() throws NotEnoughDataException, CurrencyCodeNotFoundException {
        // Mocking the RestTemplate response
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency("USD");
        exchangeRate.setRates(Collections.singletonList(new Rate(1.23)));

        ResponseEntity<ExchangeRate> responseEntity = new ResponseEntity<>(exchangeRate, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(ExchangeRate.class))).thenReturn(responseEntity);

        CurrentCurrencyRequest request = new CurrentCurrencyRequest("USD", "Test");

        CurrentCurrencyResponse response = currencyService.getCurrentCurrencyValue(request);

        // Assertions
        assertEquals("USD", response.getCurrency());
        assertEquals(1.23, response.getValue());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetCurrentCurrencyValue_NotEnoughDataException() {
        // Mocking the RestTemplate response with an empty body
        ResponseEntity<ExchangeRate> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(ExchangeRate.class))).thenReturn(responseEntity);

        CurrentCurrencyRequest request = new CurrentCurrencyRequest("USD", "Test");

        // Assertions
        assertThrows(NotEnoughDataException.class, () -> currencyService.getCurrentCurrencyValue(request));
        verify(repository, never()).save(any());
    }

    @Test
    void testGetCurrentCurrencyValue_CurrencyCodeNotFoundException() {
        // Mocking the RestTemplate response with a 404 error
        when(restTemplate.getForEntity(anyString(), eq(ExchangeRate.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        CurrentCurrencyRequest request = new CurrentCurrencyRequest("USD", "Test");

        // Assertions
        assertThrows(CurrencyCodeNotFoundException.class, () -> currencyService.getCurrentCurrencyValue(request));
        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetCurrencyRequests() {
        // Mocking the repository response
        List<CurrencyRequest> mockCurrencyRequests = new ArrayList<>();
        mockCurrencyRequests.add(new CurrencyRequest("USD", "Test",true, OffsetDateTime.now(),1.23 ));
        mockCurrencyRequests.add(new CurrencyRequest("EUR", "Test",true, OffsetDateTime.now(),0.89));

        when(repository.findAll()).thenReturn(mockCurrencyRequests);

        List<CurrencyRequest> currencyRequests = currencyService.getCurrencyRequests();

        // Assertions
        assertEquals(2, currencyRequests.size());
        verify(repository, times(1)).findAll();
    }

}
