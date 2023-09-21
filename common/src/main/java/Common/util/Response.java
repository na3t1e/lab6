package Common.util;

import Common.entity.City;
import Common.interfaces.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Класс Response - класс, содержащий информацию для ответа на запрос.
 */
public class Response implements Serializable, Data {

    /**
     * Сообщение, отправляемое в ответ на запрос.
     */
    private String messageToResponse;

    private City cityToResponse;

    /**
     * Данные коллекции, отправляемые в ответ на запрос.
     */
    private List<City> collectionToResponse;

    /**
     * Конструктор класса Response, принимающий сообщение для ответа.
     *
     * @param messageToResponse сообщение для ответа
     */
    public Response(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }


    public Response(String messageToResponse, City cityToResponse) {
        this.messageToResponse = messageToResponse;
        this.cityToResponse = cityToResponse;
    }


    public Response(String messageToResponse, List<City> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }


    public Response(City cityToResponse) {
        this.cityToResponse = cityToResponse;
    }


    public Response(List<City> cityToResponse) {
        this.collectionToResponse = collectionToResponse;
    }

    /**
     * Метод, возвращающий сообщение для ответа.
     *
     * @return сообщение для ответа
     */
    public String getMessageToResponse() {
        return messageToResponse;
    }


    public City getCityToResponse() {
        return cityToResponse;
    }

    public List<City> getCollectionToResponse() {
        return collectionToResponse;
    }

    /**
     * Метод, возвращающий информацию для отправки.
     *
     * @return информация для отправки
     */
    @Override
    public String getData() {
        return (messageToResponse == null ? "" : (getMessageToResponse()))
                + (cityToResponse == null ? "" : ("\nДанные города:\n" + getCityToResponse().toString()))
                + (collectionToResponse == null ? "" : ("\nКоллекция:\n" + getCollectionToResponse()));
    }

    /**
     * Представляет ответ, полученный от сервера, в формате, удобном для чтения.
     */
    @Override
    public String toString() {
        return "Ответ[" + messageToResponse + "]";
    }
}