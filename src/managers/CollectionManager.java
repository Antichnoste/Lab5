package managers;

import models.Movie;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс менеджера коллекции
 */
public class CollectionManager {
    private int currentId = 1;
    private LocalDateTime LastSaveTime = null;
    private LocalDateTime LastInitTime = null;
    private Map<Integer, Movie> movies = new HashMap<>();
    private LinkedList<Movie> collection = new LinkedList<>();
    private final DumpManager dumpManager;

    /**
     * Конструктор
     * @param dumpManager
     */
    public CollectionManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
        LastSaveTime = null;
        LastInitTime = null;
    }

    /**
     * @return ID последнего добавленного элемента
     */
    public int getCurrentId() {
        return currentId;
    }

    /**
     * @return Время последнего сохранения коллекции
     */
    public LocalDateTime getLastSaveTime() {
        return LastSaveTime;
    }

    /**
     * @return Время последней инициалтзации коллекции
     */
    public LocalDateTime getLastInitTime() {
        return LastInitTime;
    }

    /**
     * @return коллекцию
     */
    public LinkedList<Movie> getCollection() {
        return collection;
    }

    /**
     * @param id индекс, по которому надо вернуть объект класса Movie
     * @return объект класса Movie по индексу id
     */
    public Movie getById(int id) {
        return movies.get(id);
    }

    /**
     * Проверяет находиться ли фильм в коллекции
     * @param movie объект класса, который мы проверяем
     * @return True если находится в коллекции, False - не находится
     */
    public boolean isContains(Movie movie) {
        return getById(movie.getId()) != null;
    }

    /**
     * @return свободный ID коллекции
     */
    public int getFreeId(){
        while (getById(++currentId) != null);
        return currentId;
    }

    /**
     * Обеспечивает наличие объекта класса Movie в коллекции
     * @param movie объект класса Movie, который надо добавить в коллекцию
     * @return True - объекта не было и время обновлено, False - объект был и время не обновлено
     */
    public boolean add(Movie movie) {
        if (isContains(movie)) {
            return false;
        }

        movies.put(movie.getId(), movie);
        collection.add(movie);
        sort();
        return true;
    }

    /**
     * Сортировать коллекцию
     */
    public void sort() {
        Collections.sort(collection, (m1, m2) -> Integer.compare(m1.getOscarsCount(), m2.getOscarsCount()));
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection(){
        dumpManager.writeCollection(collection);
        LastSaveTime = LocalDateTime.now();
    }

    /**
     * Обеспечивает отсутствие элемента в коллекции по ID
     * @param id уникалтный номер элемента в коллекции
     * @return True - объект был и его не стало, False - объекта не было
     */
    public boolean remove(int id) {
        Movie movie = getById(id);
        if (movie == null) { return false;}

        movies.remove(movie.getId());
        collection.remove(movie);
        sort();
        return true;
    }

    /**
     * Загрузка коллекции из файла (инициализация)
     * @return True - успешно всё загрузилось, False - произошла обшибка
     */
    public boolean loadCollection(){
        movies.clear();
        dumpManager.readCollection(collection);
        LastInitTime = LocalDateTime.now();

        for (Movie movie : collection) {
            if (getById(movie.getId()) != null) {
                collection.clear();
                movies.clear();
                return false;
                // Очищаем коллекцию так как мы встретили элемент, ID которого уже есть в коллекции
            } else{
                if (movie.getId() > currentId) {
                    currentId = movie.getId();
                }
                movies.put(movie.getId(), movie);
            }
        }
        sort();
        return true;
    }

    /**
     * @return возвращает коллекцию, переведённую в строку
     */
    @Override
    public String toString()    {
        if (collection.isEmpty()) {return "Коллекция пуста!";}

        StringBuilder sb = new StringBuilder();
        for(Movie movie : collection) {
            sb.append(movie).append("\n");
        }
        return sb.toString().trim();
    }
}
