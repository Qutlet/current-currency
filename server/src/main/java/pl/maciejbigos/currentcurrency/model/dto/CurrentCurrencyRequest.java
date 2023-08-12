package pl.maciejbigos.currentcurrency.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCurrencyRequest {
    private String currency;
    private String name;
}
