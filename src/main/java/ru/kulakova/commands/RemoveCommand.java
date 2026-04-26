package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

import java.util.UUID;

public class RemoveCommand implements Commanding{

    private final LibraryController _controller;
    private final UUID _id;

    public RemoveCommand(LibraryController controller, UUID id) {
        _controller = controller;
        _id = id;
    }

    @Override
    public void execute() {
        _controller.handleRemove(_id);
    }
}
