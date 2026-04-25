package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

import java.util.UUID;

public class RemoveCommand implements Commanding{

    private final LibraryController _controller;
    private final String _id;

    public RemoveCommand(LibraryController controller, String id) {
        _controller = controller;
        _id = id;
    }

    @Override
    public void execute() {
        UUID id = null;

        if (_id != null && !_id.isBlank()) {
            try {
                id = UUID.fromString(_id.trim());
            } catch (IllegalArgumentException e) {
                id = null;
            }
        }

        _controller.handleRemove(id);
    }
}
