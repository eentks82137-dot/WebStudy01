package kr.or.ddit.hw04.exception;

public class UnitConversionException extends IllegalArgumentException {

    public UnitConversionException() {
    }

    public UnitConversionException(String s) {
        super(s);
    }

    public UnitConversionException(Throwable cause) {
        super(cause);
    }

    public UnitConversionException(String message, Throwable cause) {
        super(message, cause);
    }

}
