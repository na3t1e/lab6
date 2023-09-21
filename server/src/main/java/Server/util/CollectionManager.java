package Server.util;

import Common.entity.City;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс-менеджер коллекции организаций.
 * Содержит методы для работы с коллекцией, такие как добавление, удаление,
 * поиск по id, замена по id, сортировка, перемешивание, очистка и т.д.
 * Также возвращает информацию о коллекции.
 */
public class CollectionManager {


    public CollectionManager() {
        cityCollection = new LinkedList<>();
        creationDate = ZonedDateTime.now();
    }

    private LinkedList<City> cityCollection;

    /**
     * Возвращает коллекцию организаций
     * @return коллекция организаций
     */
    public List<City> getCollection() {
        return cityCollection;
    }

    /**
     * Дата инициализации коллекции
     */
    private final ZonedDateTime creationDate;

    /**
     * Возвращает дату инициализации коллекции
     * @return дата инициализации коллекции
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }


    public void setCityCollection(LinkedList<City> cityCollection) {
        this.cityCollection = cityCollection;
    }
    public Long generateNextId() {
        if (cityCollection.isEmpty()) return 1L;
        List<City> sortedList = new ArrayList<>(cityCollection);
        sortedList.sort(Comparator.comparing(City::getId));
        return sortedList.get(sortedList.size() - 1).getId() + 1;
    }

    /**
     * Возвращает первый объект из коллекции.
     * Если коллекция пуста, возвращается null.
     * @return первый объект из коллекции или null, если коллекция пуста.
     */
    public City getFirst() {
        if (cityCollection.isEmpty()) return null;
        return cityCollection.getFirst();
    }

    /**
     * Метод удаляет первый элемент из коллекции
     */
    public void removeFirst(){ cityCollection.poll(); }


    public City getById(Long id) {
        try {
            return (City) cityCollection.stream()
                    .filter(s -> s.getId().equals(id)) // Фильтруются объекты, у которых id равен переданному в метод
                    .toArray()[0]; // Результат фильтрации преобразуется в массив, из которого выбирается первый элемент и возвращается в качестве результата метода
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void removeById(Long id) {
        cityCollection.removeIf(p -> p.getId().equals(id));
    }


    public void addToCollection(City city){
        cityCollection.add(city);
        cityCollection.sort(Comparator.comparing(City::getName, String.CASE_INSENSITIVE_ORDER));
    }


    public void clearCollection() {
        cityCollection.clear();
    }


    public void shuffleCollection(){
        Collections.shuffle(cityCollection);
    }

    /**
     * Сортирует коллекцию по умолчанию (по возрастанию id)
     */
    public void sortCollection(){
        Collections.sort(cityCollection);
    }


    public Set<Integer> getCarCodes() {
        return cityCollection.stream()
                .map(City::getCarCode)
                .collect(Collectors.toSet());
    }


    public List<Double> getListArea(){
        return cityCollection.stream() // создание потока данных
                .map(City::getArea)
                .sorted(Collections.reverseOrder()) // сортировка в обратном порядке
                .collect(Collectors.toList()); // собирает все значения в List
    }


    public String getInfo() {
        return "Тип коллекции: " + cityCollection.getClass() +
                "\nДата инициализации: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.y H:mm:ss")) +
                "\nКоличество элементов: " + cityCollection.size();
    }

    /**
     * Возвращает все объекты коллекции.
     * Если коллекция пуста, возвращается строка "Коллекция пуста".
     * @return все объекты коллекции.
     */
    public String showCollection() {
        if (cityCollection.isEmpty()) return "Коллекция пуста";
        return cityCollection.stream()
                .map(city -> city.toString() + "\n")
                .collect(Collectors.joining());
    }

    /**
     * Возвращает все объекты коллекции в обратном порядке.
     * Если коллекция пуста, возвращается строка "Коллекция пуста".
     * @return все объекты коллекции в обратном порядке.
     */
    public String printDescending() {
        if (cityCollection.isEmpty()) return "Коллекция пуста";
        List<City> reversedCollection = new ArrayList<>(cityCollection);
        Collections.reverse(reversedCollection);
        return reversedCollection.stream()
                .map(city -> city.toString() + "\n")
                .collect(Collectors.joining());
    }
}
