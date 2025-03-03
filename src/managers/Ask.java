package managers;

import utility.*;
import models.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

// Реализовать если надо вывод ошибок всяких (типа "Введён не enum", "Введена строка, а не цифра" и т.д.)

/**
 * Класс менеджера по считыванию данных из консоли
 */
public class Ask {
    /**
     * Исключение для выхода из цикла опроса
     */
    public static class AskBreak extends Exception {}

    /**
     * Функция считывания объекта класса Movie из консоли
     *
     * @param console консоль
     * @param id уникальный номер учебной группы
     * @return возвращает считанный объект класса Movie
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static Movie askMovie (Console console, int id) throws AskBreak{
        try {
            String name;
            while(true){
                console.print("Movie's name: ");
                name = console.readln().trim();
                if (name.equals("exit")) {throw new AskBreak();}
                if (!name.isEmpty()) {break;}
            }

            var coordinates = askCoordinate(console);

            ZonedDateTime creationDate = ZonedDateTime.now();
            creationDate = ZonedDateTime.parse(creationDate.format(DateTimeFormatter.ISO_DATE_TIME));

            int oscarsCount;
            while (true){
                    console.print("oscarsCount: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) {throw new AskBreak();}
                    if (!line.isEmpty()){
                        try{
                            oscarsCount = Integer.parseInt(line);
                            if (oscarsCount > 0) break;
                        } catch (NumberFormatException e) {}
                    }
            }

            String tagline;
            while (true){
                console.print("tagline: ");
                tagline = console.readln().trim();
                if (tagline.equals("exit")) {throw new AskBreak();}
                if (!tagline.isEmpty()){
                    break;
                }
            }

            MovieGenre genre = askMovieGenre(console);

            MpaaRating mpaaRatin = askMpaaRating(console);

            Person person = askPerson(console);

            return new Movie(id, name, coordinates, creationDate,oscarsCount, tagline, genre, mpaaRatin, person);

        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Функция считывания объекта класса Coordinates из консоли
     *
     * @param console консоль
     * @return возвращает считанный объект класса Coordinates
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static Coordinates askCoordinate (Console console) throws AskBreak {
        try {
            Double x;
            while (true) {
                console.print("coordinates x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (!line.isEmpty()) {
                    try {
                        x = Double.parseDouble(line);
                        break;
                    } catch (NumberFormatException e) {}
                }
            }

            double y;
            while (true) {
                console.print("coordinates y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) { throw new AskBreak();}
                if (!line.isEmpty()) {
                    try {
                        y = Double.parseDouble(line);
                        if (y > -708) break;
                    } catch (NumberFormatException e) {}
                }
            }
            return new Coordinates(x, y);

        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Функция считывания объекта класса Person из консоли
     *
     * @param console консоль
     * @return возвращает считанный объект класса Person
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static Person askPerson (Console console) throws AskBreak {
        try{
            String name;
            while(true){
                console.print("Screenwriter's name: ");
                name = console.readln().trim();
                if (name.equals("exit")) {throw new AskBreak();}
                if (!name.isEmpty()) {break;}
            }

            Float height;
            while (true){
                console.print("height: ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (line.isEmpty()){
                    height = null;
                    break;
                }
                try{
                    height = Float.parseFloat(line);
                    if (height > 0) break;
                } catch (NumberFormatException e) {}
            }

            Float weight;
            while (true){
                console.print("weight: ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (!line.isEmpty()){
                    try{
                        weight = Float.parseFloat(line);
                        if (weight > 0) break;
                    } catch (NumberFormatException e) {}
                }
            }

            Color eyeColor = askColor(console);

            return new Person(name, height, weight, eyeColor);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }

    }

    /**
     * Функция считывания объекта класса MovieGenre из консоли
     *
     * @param console консоль
     * @return возвращает считанный объект класса MovieGenre
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static MovieGenre askMovieGenre (Console console) throws AskBreak {
        try{
            MovieGenre genre;
            while (true){
                console.print("genre ("+ MovieGenre.names() +"): ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (!line.isEmpty()) {
                    try{
                        genre = MovieGenre.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException  e) {}
                } else {
                    genre = null;
                }
            }
            return genre;
        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }


    }

    /**
     * Функция считывания объекта класса MpaaRating из консоли
     *
     * @param console консоль
     * @return возвращает считанный объект класса MpaaRating
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static MpaaRating  askMpaaRating  (Console console) throws AskBreak {
        try{
            MpaaRating rating;
            while (true){
                console.print("rating ("+ MpaaRating.names() +"): ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (!line.isEmpty()) {
                    try{
                        rating = MpaaRating.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException  e) {}
                }
            }
            return rating;
        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Функция считывания объекта класса Color из консоли
     *
     * @param console консоль
     * @return возвращает считанный объект класса Color
     * @throws AskBreak исключение, которые происходит при принудительной остановки обработки
     */
    public static Color askColor (Console console) throws AskBreak{
        try{
            Color color;
            while (true){
                console.print("Eye's color ("+ Color.names() +"): ");
                var line = console.readln().trim();
                if (line.equals("exit")) {throw new AskBreak();}
                if (!line.isEmpty()) {
                    try{
                        color = Color.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException  e) {}
                } else {
                    color = null;
                }
            }
            return color;
        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }
}