package ru.kulakova.handlers.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.lists.ListCommand;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.handlers.BaseHandler;
import ru.kulakova.handlers.Handleable;
import ru.kulakova.handlers.NestedHandleable;

public class ListHandler extends BaseHandler implements NestedHandleable {

    private static final String COMMAND_NAME = "LIST";

    private Handleable _childHandlers;

    public ListHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler != null ? _nextHandler.handle(command, args) : null;
        }

        if (args == null || args.isBlank()) {
            return new ListCommand(_controller);
        }

        String[] parts = args.trim().split("\\s+", 2);
        String keyword = parts[0];
        String tail = parts.length > 1 ? parts[1] : "";

        return handleNested(keyword, tail);
    }

    @Override
    public NestedHandleable addChild(Handleable child) {
        if (_childHandlers == null) {
            _childHandlers = child;
        } else {
            _childHandlers.addNext(child);
        }
        return this;
    }

    @Override
    public Commanding handleNested(String command, String args) {
        if (_childHandlers == null) {
            return null;
        }
        return _childHandlers.handle(command, args);
    }
}
