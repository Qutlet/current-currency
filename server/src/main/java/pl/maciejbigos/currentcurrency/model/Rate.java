package pl.maciejbigos.currentcurrency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private String no;
    private String effectiveDate;
    private double mid;

    public Rate(double v) {
        this.mid = v;
    }
}
