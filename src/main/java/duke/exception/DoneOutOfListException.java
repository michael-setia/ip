package duke.exception;

public class DoneOutOfListException extends DukeException {

    @Override
    public String getMessage() {
        return " Oh no! Task number cannot be zero or negative"
                + "\n" + " Please refer to your task list to find the"
                + "\n" + " appropriate task number :)";
    }
}