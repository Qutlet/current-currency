package pl.maciejbigos.currentcurrency.exceptions;

public class NotEnoughDataException extends Exception {

    public NotEnoughDataException() {
        super("Request was successful, but not enough data found.");
    }

}
