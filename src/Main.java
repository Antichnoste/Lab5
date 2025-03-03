import command.*;
import models.*;
import utility.ExecutionResponse;
import utility.Runner;
import utility.StandartConsole;
import managers.*;

import java.io.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Ask.AskBreak, IOException {
        var console = new StandartConsole();
        //var fileName = "D:\\All\\Огузки\\ex.csv";

        var fileName = System.getenv("FILE_NAME");

        if (fileName == null) {
            System.out.println("Переменное окружение FILE_NAME не установленна");
            System.exit(1);
        }

        var dumpManager = new DumpManager(fileName, console);
        var collectionManager = new CollectionManager(dumpManager);

        if (!collectionManager.loadCollection()){
            System.exit(1);
        }

        var commandManager = new CommandManager() {
            {
            register("add", new Add(console, collectionManager));
            register("help", new Help(console, this));
            register("exit", new Exit(console));
            register("average_of_oscars_count", new AverageOfOscarsCount(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("filter_contains_name", new FilterContainsName(console, collectionManager));
            register("history", new History(console, this));
            register("filter_starts_with_tagline", new FilterStartsWithTagline(console, collectionManager));
            register("info", new Info(console, collectionManager));
            register("remove_by_id", new RemoveById(console,collectionManager ));
            register("remove_first", new RemoveFirst(console, collectionManager));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("update", new UpdateID(console, collectionManager));
        }
        };

        new Runner(console, commandManager).interactiveMode();
    }
}