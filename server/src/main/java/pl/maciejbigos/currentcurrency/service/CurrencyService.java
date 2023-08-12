package pl.maciejbigos.currentcurrency.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.maciejbigos.currentcurrency.exceptions.CurrencyCodeNotFoundException;
import pl.maciejbigos.currentcurrency.exceptions.NotEnoughDataException;
import pl.maciejbigos.currentcurrency.model.CurrencyRequest;
import pl.maciejbigos.currentcurrency.model.dto.CurrentCurrencyRequest;
import pl.maciejbigos.currentcurrency.model.dto.CurrentCurrencyResponse;
import pl.maciejbigos.currentcurrency.model.ExchangeRate;
import pl.maciejbigos.currentcurrency.repository.CurrencyRequestRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CurrencyService {

    public static final String NPB_API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/%s?format=json";

    private final RestTemplate restTemplate;
    private final CurrencyRequestRepository repository;

    public CurrentCurrencyResponse getCurrentCurrencyValue(CurrentCurrencyRequest request) throws NotEnoughDataException, CurrencyCodeNotFoundException {
        try {
            ResponseEntity<ExchangeRate> response = restTemplate.getForEntity(String.format(NPB_API_URL, request.getCurrency()), ExchangeRate.class);

            ExchangeRate exchangeRate = Optional.ofNullable(response.getBody()).orElseThrow(NotEnoughDataException::new);
            log.info("Received data from NPB API: {}", response.getBody());

            CurrentCurrencyResponse currentCurrencyResponse = CurrentCurrencyResponse.builder()
                    .currency(exchangeRate.getCurrency())
                    .value(exchangeRate.getRates().stream().findFirst().orElseThrow(NotEnoughDataException::new).getMid())
                    .build();

            saveRequest(currentCurrencyResponse.getCurrency(), currentCurrencyResponse.getValue(), true, request.getName());

            return currentCurrencyResponse;
        } catch (HttpClientErrorException e) {
            log.warn("404 Error received while reaching nbp api");
            saveRequest(request.getCurrency(), null, false, request.getName());
            throw new CurrencyCodeNotFoundException(request.getCurrency());
        }
    }

    private void saveRequest(String currency, Double value, boolean success, String name) {
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .currency(currency)
                .value(value)
                .date(OffsetDateTime.now())
                .success(success)
                .name(name)
                .build();
        CurrencyRequest saved = repository.save(currencyRequest);
        log.info("Saved object {}", saved);
    }

    public List<CurrencyRequest> getCurrencyRequests() {
        List<CurrencyRequest> currencyRequests = repository.findAll();
        log.info("Found {} elements", currencyRequests.size());
        return currencyRequests;
    }

}
