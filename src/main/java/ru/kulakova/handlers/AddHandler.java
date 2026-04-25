package ru.kulakova.handlers;

import ru.kulakova.commands.AddCommand;
import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public class AddHandler extends BaseHandler {

    private static final String COMMAND_NAME = "ADD";

    public AddHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args){
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler.handle(command, args);
        }

        return new AddCommand(_controller, args);
    }
}
