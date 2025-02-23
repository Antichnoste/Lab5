package command;

import managers.Ask;
import utility.ExecutionResponse;

/**
 * Интерфейс для исполняемых команд
 */
public interface Executable {
    /**
     * @param arguments массив с аргументами
     * @return возвращает результат о выполнении команды
     */
    ExecutionResponse apply(String[] arguments);
}
