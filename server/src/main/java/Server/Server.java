package Server;

import Server.util.FileManager;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;

/**
 * Главный класс, запускающий приложение сервера.
 * Содержит метод main, отвечающий за запуск приложения.
 */
public final class Server {
    /**
     * Имя файла, в котором хранится коллекция.
     */
    static String fileName;
    /**
     * Порт по умолчанию
     */
    private static int PORT;

    /**
     * Хост, к которому пытается подключиться клиент.
     */
    private static String HOST = "localhost";

    /**
     * Максимально возможный номер порта.
     */
    private static final int maxPort = 65535;
    /**
     * Объект, который используется для прослушивания входящих клиентских подключений на сервере.
     */
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            fileName = FileManager.checkFileName(args[0]);
            checkPort(args[1]);
            ServerApp.fileManager.readCollection();
            ConsoleThread consoleThread = new ConsoleThread();
            consoleThread.start();
            ServerApp.startServer(serverSocket);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Не введено название файла и/или порт");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Метод checkPort пытается установить соединение
     */

    private static void checkPort(String port) {
        try {
            int portInt = Integer.parseInt(port);
            if (portInt > 0 && portInt <= maxPort) {
                PORT = portInt;
                serverSocket = new ServerSocket(portInt);
            } else {
                System.err.println("Порт не входит в возможный диапазон");
                System.exit(1);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Порт введен некорректно");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.err.println("Принудительное завершение работы");
            System.exit(1);
        } catch (BindException e) {
            System.err.println("Введенный порт недоступен. Введите другой");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Возникли непредвиденные ошибки: " + e.getMessage());
            System.exit(1);
        }
    }
}
