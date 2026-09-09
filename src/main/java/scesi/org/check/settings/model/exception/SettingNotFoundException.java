package scesi.org.check.settings.model.exception;

public class SettingNotFoundException extends RuntimeException {
    public static final String DEfAULT_MESSAGE = "Setting not found";

    public SettingNotFoundException(){
        super(DEfAULT_MESSAGE);
    }
}
