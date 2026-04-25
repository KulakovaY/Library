package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;
import ru.kulakova.controllers.LibraryController;

public abstract class BaseHandler implements Handleable {

    protected Handleable _nextHandler;
    protected final LibraryController _controller;

    public BaseHandler(LibraryController controller) {
        _controller = controller;
    }

    public final Handleable addNext(Handleable next) {
        if (_nextHandler == null) {
            _nextHandler = next;
        } else {
            _nextHandler.addNext(next);
        }

        return this;
    }

    @Override
    public abstract Commanding handle(String command, String args);
}
