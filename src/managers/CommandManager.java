package managers;

import command.Command;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс менеджера коллекции
 */
public class CommandManager {
    /**
     * Словарь для хранения команд и их названий
     */
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Функция регистрации команды
     * @param name имя команды
     * @param command команда
     */
    public void register(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Функия получения всех добеленных команд
     * @return команды, хранящиеся в менеджере
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
