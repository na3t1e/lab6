package Server.util;

import Common.util.Request;
import Common.util.Response;
import Server.commands.AbstractCommand;
import Server.ServerApp;
import Common.exception.DisconnectInitException;

/**
 * Класс для построения ответа на запрос клиента, используя переданную команду и запрос.
 */
public class RequestBuilder {

    public static Response build(AbstractCommand command, Request request) throws DisconnectInitException {
        if (request.getCity() != null)
            request.getCity().setId(ServerApp.collectionManager.generateNextId());
        return command.execute(request);
    }
}
