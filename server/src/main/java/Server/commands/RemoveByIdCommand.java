package Server.commands;

import Common.entity.City;
import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;

/**
 * Команда для удаления элемента коллекции по его id.
 */
public class RemoveByIdCommand extends AbstractCommand {

    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет удаление элемента коллекции по его id.
     * @param request запрос, содержащий id элемента для удаления
     * @return ответ с информацией об успешности выполнения операции
     */
    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        City cityToRemove = collectionManager.getById(id);
        if (cityToRemove == null)
            return new Response("Организации с таким id не существует");
        else {
            collectionManager.removeById(id);
            return new Response("Организация была удалена");
        }
    }
}