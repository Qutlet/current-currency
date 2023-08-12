package pl.maciejbigos.currentcurrency.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentCurrencyResponse {
    private String currency;
    private double value;
}
