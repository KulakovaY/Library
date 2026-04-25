package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public class ListHandler extends BaseHandler{

    private static final String COMMAND_NAME = "LIST";

    public ListHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        return null;
    }
}
