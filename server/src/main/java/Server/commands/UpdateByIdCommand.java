package Server.commands;

import Common.entity.City;
import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;

/**
 * Команда для обновления значения элемента коллекции, id которого равен заданному.
 */
public class UpdateByIdCommand extends AbstractCommand {

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public UpdateByIdCommand(CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, который выполняет команду.
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        City cityToUpdate = collectionManager.getById(id);
        if (cityToUpdate == null)
            return new Response("Организации с таким id не существует");
        else {
            City updatedCity = request.getCity();
            updatedCity.setId(id);
            collectionManager.removeById(id);
            collectionManager.addToCollection(updatedCity);
            return new Response("Данные организации были обновлены");
        }
    }
}
