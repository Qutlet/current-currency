package pl.maciejbigos.currentcurrency.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currency;
    private String name;
    private boolean success;
    private OffsetDateTime date;
    private Double value;

    public CurrencyRequest(String currency, String name, boolean success, OffsetDateTime date, Double value) {
        this.currency = currency;
        this.name = name;
        this.success = success;
        this.date = date;
        this.value = value;
    }


}
