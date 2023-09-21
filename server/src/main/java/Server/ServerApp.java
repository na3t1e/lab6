package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import Common.exception.DisconnectInitException;
import Common.util.Request;
import Common.util.Response;
import Server.commands.*;
import Server.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс для установки соединения с клиентами.
 */
public class ServerApp {
    /**
     * Сканер для чтения ввода.
     */
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Файловый менеджер.
     */
    public static final FileManager fileManager = new FileManager(Server.fileName);

    /**
     * Менеджер коллекции.
     */
    public static CollectionManager collectionManager = new CollectionManager();

    /**
     * Состояние сервера.
     */
    public static boolean running = true;

    /**
     * Командный менеджер, содержащий список команд для обработки запросов клиентов.
     */
    public static CommandManager commandManager = new CommandManager(
            new AddCommand(collectionManager),
            new ClearCommand(collectionManager),
            new ExecuteScriptCommand(),
            new ExitCommand(),
            new HelpCommand(),
            new InfoCommand(collectionManager),
            new PrintDescendingCommand(collectionManager),
            new PrintFieldDescendingAreaCommand(collectionManager),
            new PrintUniqueCarCodeCommand(collectionManager),
            new RemoveByIdCommand(collectionManager),
            new RemoveFirstCommand(collectionManager),
            new ShowCommand(collectionManager),
            new ShuffleCommand(collectionManager),
            new SortCommand(collectionManager),
            new UpdateByIdCommand(collectionManager)
    );

    /**
     * Метод для запуска сервера.
     *
     * @param serverSocket объект ServerSocket, прослушивающий порт.
     * @throws IOException если возникли проблемы с вводом-выводом.
     */
    static void startServer(ServerSocket serverSocket) throws IOException {
        try {
            System.out.println("Сервер запущен");
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (running) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        System.out.println("Подключение клиента c порта: " + clientSocket.getPort());
                        startSelectorLoop(clientSocket, serverSocket);
                    } catch (SocketException e) {
                        if (e.getMessage().contains("Connection reset")) ;
                        else {
                            System.err.println("Проблема подключения клиента:" + e.getMessage());
                        }
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        System.err.println("Проблема подключения клиента:" + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Не удается принять клиентское соединение: " + e.getMessage());
        }
    }

    /**
     * Метод, который запускает бесконечный цикл обработки запросов клиента.
     *
     * @param socket       сокет для подключения клиента.
     * @param serverSocket серверный сокет для принятия клиентских подключений.
     * @throws IOException            если возникает ошибка ввода/вывода при работе с сокетами.
     * @throws ClassNotFoundException если класс не был найден при чтении объекта из потока.
     * @throws InterruptedException   если поток был прерван во время ожидания ответа от клиента.
     */
    private static void startSelectorLoop(Socket socket, ServerSocket serverSocket) throws IOException, ClassNotFoundException, InterruptedException {
        while (socket.isConnected()) {
            startIteratorLoop(socket, serverSocket);
        }
    }

    /**
     * Метод, который обрабатывает запросы клиента.
     *
     * @param socket       сокет для подключения клиента.
     * @param serverSocket серверный сокет для принятия клиентских подключений.
     * @throws IOException            если возникает ошибка ввода/вывода при работе с сокетами.
     * @throws ClassNotFoundException если класс не был найден при чтении объекта из потока.
     */
    private static void startIteratorLoop(Socket socket, ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        boolean isClientDisconnected = false;
        try {
            ServerSocketIO socketIO = new ServerSocketIO(socket);
            Request request = (Request) socketIO.receive();
            AbstractCommand command = ServerApp.commandManager.initCommand(request);
            if (request.getCommandName().equals("exit")) {
                System.out.println("Отключение клиента c порта: " + socket.getPort());
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
                }
            } else {
                Response response = RequestBuilder.build(command, request);
                socketIO.send(response);
            }
        } catch (DisconnectInitException e) {
            socket.close();
            serverSocket.close();
            System.err.println("Работа сервера завершена");
            System.exit(1);
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset")) {
                isClientDisconnected = true;
                System.err.println("Клиент c порта: " + socket.getPort() + " внезапно отключился");
            }
        } finally {
            if (isClientDisconnected) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
                }
            }
        }
    }
}
