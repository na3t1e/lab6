package Common.util;

import Common.entity.City;
import Common.interfaces.Data;

import java.io.Serializable;

/**
 * Класс, представляющий объект-запрос.
 * Реализует интерфейс Serializable для возможности сериализации объектов.
 * Реализует интерфейс Data для отправки данных по сети.
 * Содержит информацию о команде и параметрах команды.
 */
public class Request implements Serializable, Data {

    /**
     * Имя команды.
     */
    private final String commandName;

    /**
     * Числовой аргумент команды.
     */
    private Long numericArgument;


    private City city;

    /**
     * Конструктор класса, принимающий имя команды.
     * @param commandName имя команды
     */
    public Request(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Конструктор класса, принимающий имя команды и числовой аргумент.
     * @param commandName имя команды
     * @param numericArgument числовой аргумент команды
     */
    public Request(String commandName, Long numericArgument) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
    }


    public Request(String commandName, City city) {
        this.commandName = commandName;
        this.city = city;
    }

    public Request(String commandName, Long numericArgument, City city) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
        this.city = city;
    }

    /**
     * Получает имя команды.
     * @return имя команды
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Получает числовой аргумент.
     * @return числовой аргумент команды
     */
    public Long getNumericArgument() {
        return numericArgument;
    }


    public City getCity() {
        return city;
    }

    /**
     * Метод getData() возвращает строковое представление данных объекта в виде имени команды и соответствующих аргументов.
     */
    @Override
    public String getData(){
        return "Имя команды для отправки: " + commandName
                + (city == null ? "" : ("\nИнформация о городе для отправки:\n " + city))
                + (numericArgument == null ? "" : ("\nЧисловой аргумент для отправки:\n " + numericArgument));
    }

    /**
     * Возвращает строковое представление объекта в формате "Ответ[имя команды]".
     */
    @Override
    public String toString() {
        return "Ответ[" + commandName + "]" ;
    }
}
