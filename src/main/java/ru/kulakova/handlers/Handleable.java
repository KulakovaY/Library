package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;

public interface Handleable {
    Commanding handle(String command, String args);
}
