package ru.kulakova.commands;

import ru.kulakova.controllers.LibraryController;

public class AddCommand implements Commanding{

    private final LibraryController _controller;
    private final String _title;
    private final String _author;
    private final Integer _year;

    public AddCommand(LibraryController controller, String title, String author, Integer year) {
        _controller = controller;
        _title = title;
        _author = author;
        _year = year;
    }

    @Override
    public void execute() {
        _controller.handleAdd(_title, _author, _year);
    }
}
