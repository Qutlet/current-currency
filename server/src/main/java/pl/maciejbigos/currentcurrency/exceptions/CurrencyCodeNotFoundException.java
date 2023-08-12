package pl.maciejbigos.currentcurrency.exceptions;

public class CurrencyCodeNotFoundException extends Exception {

    public CurrencyCodeNotFoundException(String cur) {
        super(String.format("Request unsuccessful, most probably 'Currency Code' %s do not exists.", cur));
    }

}