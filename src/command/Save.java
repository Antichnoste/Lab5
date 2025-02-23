package command;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class Save extends Command {
    private final Console consle;
    private final CollectionManager manager;

    public Save(Console consle, CollectionManager manager) {
        super("save", "сохранить коллекцию в файл");
        this.consle = consle;
        this.manager = manager;
    }


    @Override
    public ExecutionResponse apply(String[] arguments) {
        return null;
    }
}
