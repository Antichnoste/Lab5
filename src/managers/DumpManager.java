package managers;

import au.com.bytecode.opencsv.*;
import models.Movie;
import utility.Console;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс файлового менеджера по сериализации/десереализации из коллекции в CSV
 */
public class DumpManager {
    private final String fileName;
    private final Console console;

    /**
     * Конструктор
     * @param fileName имя файла
     * @param console консоль
     */
    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Переводит массив объектов класса Movie в CSV-строку
     * @param collection массив объектов класса Movie
     * @return строка
     */
    private String collection2CSV(Collection <Movie> collection){
        try{
            StringWriter sw = new StringWriter();
            CSVWriter writer = new CSVWriter(sw, ';');
            for (Movie movie: collection){
                writer.writeNext(Movie.toArray(movie));
            }
            String csv = sw.toString();
            return csv;
        } catch (Exception e){
            console.printError("Ошибка сериализации");
            return null;
        }
    }

    /**
     * Записывает массив объектов класса Movie в CSV-файл
     * @param collection массив объектов класса Movie
     */
    public void writeCollection(Collection <Movie> collection){
        OutputStreamWriter writer = null;
        try{
            String csv = collection2CSV(collection);
            if (csv == null) return;
            writer = new OutputStreamWriter(new FileOutputStream(fileName));
            try{
                writer.write(csv);
                writer.flush();
                console.println("Коллекция успешно сохранена в файл!");
            } catch (IOException e) {
                console.printError("Ошибка сохранения");
            }
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                console.printError("Ошибка закрытия файла");
            }
        }
    }

    /**
     * Переводит CSV-строку в коллекцию
     * @param s строка
     * @return CSV-строка
     */
    private LinkedList<Movie> CSV2collection (String s){
        try{
            StringReader sr = new StringReader(s);
            CSVReader reader = new CSVReader(sr, ';');
            LinkedList<Movie> collection = new LinkedList<>();
            String[] record;

            while((record = reader.readNext()) != null){
                Movie movie = Movie.fromArray(record);
                if (movie.isValid()){
                    collection.add(movie);
                } else {
                    console.printError("Файл содержит некорректные данные");
                }
            }

            reader.close();
            return collection;
        } catch (IOException e) {
            console.println("Ошибка десериализации");
            return null;
        }
    }

    /**
     * Функция чтения из файла
     * @param collection коллекция, в которую будут записаны данные файла
     */
    public void readCollection(Collection <Movie> collection){
        if (fileName != null && !fileName.isEmpty()){
            try (Scanner fileReader = new Scanner(new File(fileName))) {
                StringBuilder s = new StringBuilder();

                while (fileReader.hasNextLine()){
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
                collection.clear();

                for (Movie e : CSV2collection(s.toString())){
                    collection.add(e);
                }

                if (!collection.isEmpty()){
                    console.println("Файл успешно считан, коллекция загружена");
                    return;
                } else {
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
                }
            } catch (FileNotFoundException e) {
                console.printError("Ошибка нахождения файла");
            } catch (IllegalStateException e){
                console.printError("Неправильный формат данных");
                System.exit(0);
            }
        } else {
            console.printError("Переменная окружения с загрузочным файлом не найдена!");
        }
        collection = new LinkedList<Movie>();
    }
}
