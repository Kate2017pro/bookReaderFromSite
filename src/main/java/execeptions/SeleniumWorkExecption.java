package execeptions;

public class SeleniumWorkExecption extends RuntimeException{
    public SeleniumWorkExecption() {
    }

    public SeleniumWorkExecption(String message) {
        super(message);
    }
}
