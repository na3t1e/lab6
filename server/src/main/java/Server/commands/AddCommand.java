package Server.commands;

import Common.util.Request;
import Common.util.Response;
import Server.util.CollectionManager;


public class AddCommand extends AbstractCommand {
    /**
     Менеджер коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Создает новый объект команды.
     * @param collectionManager менеджер коллекции
     */
    public AddCommand(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду добавления элемента в коллекцию.
     * @param request объект запроса
     * @return объект ответа
     */
    @Override
    public Response execute(Request request) {
        collectionManager.addToCollection(request.getCity());
        return new Response("Город добавлен в коллекцию");
    }
}
