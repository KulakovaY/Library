package ru.kulakova.runners;

import ru.kulakova.commands.Commanding;
import ru.kulakova.handlers.Handleable;

import java.util.Scanner;

public class ConsoleRunner implements Runnable{
    private static final String EXIT_COMMAND = "EXIT";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    private final Handleable _handlerChain;
    private final Scanner _scanner;

    public ConsoleRunner(Handleable handlerChain) {
        _handlerChain = handlerChain;
        _scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Доступные команды: ADD, REMOVE, LIST, FIND, STATS, EXIT");

        while (true) {
            System.out.print("> ");
            String input = _scanner.nextLine().trim();

            if (input.isEmpty()) continue;
            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                System.out.println("Программа завершена!");
                break;
            }

            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toUpperCase();
            String args = parts.length > 1 ? parts[1] : "";

            Commanding cmd = _handlerChain.handle(command, args);

            if (cmd != null) {
                cmd.execute();
            } else {
                System.out.println(ANSI_YELLOW + "Неизвестная команда. Доступные: ADD, REMOVE, LIST, FIND, STATS, EXIT" + ANSI_RESET);
            }
        }

        _scanner.close();
    }
}
