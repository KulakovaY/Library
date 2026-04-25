package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;

public interface Handleable {

    Handleable addNext(Handleable next);

    Commanding handle(String command, String args);
}
