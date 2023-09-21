package Server.commands;

import Common.entity.City;
import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;

/**
 * Удаление первой организации из коллекции
 */
public class RemoveFirstCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет удаление первого элемента коллекции.
     *
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response("Коллекция пуста");
        else {
            City cityToRemove = collectionManager.getFirst();
            if (cityToRemove == null)
                return new Response("Организации с таким id не существует.");
            else {
                collectionManager.removeFirst();
                return new Response("Организация была удалена.");
            }
        }
    }
}
