package utility;

public class ExecutionResponse {
    private boolean exitCode;
    private String message;

    public ExecutionResponse(boolean exitCode, String message) {
        this.exitCode = exitCode;
        this.message = message;
    }

    public ExecutionResponse(String s) {
        this(true, s);
    }

    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

    public String toString(){
        return String.valueOf(exitCode) + ";" + message;
    }
}
