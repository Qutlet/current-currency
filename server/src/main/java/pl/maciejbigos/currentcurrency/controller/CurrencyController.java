package pl.maciejbigos.currentcurrency.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maciejbigos.currentcurrency.exceptions.CurrencyCodeNotFoundException;
import pl.maciejbigos.currentcurrency.exceptions.NotEnoughDataException;
import pl.maciejbigos.currentcurrency.model.dto.CurrentCurrencyRequest;
import pl.maciejbigos.currentcurrency.service.CurrencyService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/currencies")
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping(path = "/get-current-currency-value-command")
    public ResponseEntity getCurrentCurrencyValue(@RequestBody CurrentCurrencyRequest request) {
        try {
            return ResponseEntity.ok(currencyService.getCurrentCurrencyValue(request));
        } catch (CurrencyCodeNotFoundException | NotEnoughDataException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(path = "/requests")
    public ResponseEntity getCurrencyRequests() {
        return ResponseEntity.ok().body(currencyService.getCurrencyRequests());
    }

}
