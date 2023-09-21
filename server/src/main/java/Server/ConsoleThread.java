package Server;

import Common.entity.City;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, представляющий поток консоли сервера.
 */
public class ConsoleThread extends Thread {

    /**
     * Сканнер, считывающий ввод пользователя.
     */
    private static final Scanner scanner = ServerApp.scanner;

    /**
     * Состояние запущенного потока.
     */
    public volatile boolean running = true;

    /**
     * Запускает поток консоли сервера.
     */
    @Override
    public void run() {
        try {
            while (running) {
                String line = scanner.nextLine();
                if ("save".equalsIgnoreCase(line)) {
                    ServerApp.fileManager.writeCollection((LinkedList<City>) ServerApp.collectionManager.getCollection());
                } else if ("exit".equalsIgnoreCase(line)) {
                    ServerApp.fileManager.writeCollection((LinkedList<City>) ServerApp.collectionManager.getCollection());
                    System.out.println("Работа сервера завершена");
                    System.out.println("До свидания!");
                    System.exit(0);
                } else {
                    System.err.println("Введенной команды не существует. Доступны только 'save' или 'exit'");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Принудительное завершение работы");
            System.out.println("Работа сервера завершена");
            System.exit(1);
        }
    }
}
