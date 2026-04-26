package ru.kulakova.commands.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public class ListCommand implements Commanding {

    private final LibraryController _controller;

    public ListCommand(LibraryController controller) {
        _controller = controller;
    }

    @Override
    public void execute() {
        _controller.handleList();
    }
}
