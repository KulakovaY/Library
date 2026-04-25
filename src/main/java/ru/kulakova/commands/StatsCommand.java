package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

public class StatsCommand implements Commanding{

    private final LibraryController _controller;

    public StatsCommand(LibraryController controller) {
        _controller = controller;
    }

    @Override
    public void execute() {
        _controller.handleStats();
    }
}
