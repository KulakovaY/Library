package ru.kulakova.handlers.lists;

import ru.kulakova.commands.Commanding;
import ru.kulakova.commands.lists.ListCommand;
import ru.kulakova.controllers.LibraryController;
import ru.kulakova.handlers.BaseHandler;
import ru.kulakova.handlers.Handleable;
import ru.kulakova.handlers.NestedHandleable;

public class ListHandler extends BaseHandler implements NestedHandleable {

    private static final String COMMAND_NAME = "LIST";

    private Handleable _children;

    public ListHandler(LibraryController controller) {
        super(controller);
    }

    @Override
    public Commanding handle(String command, String args) {
        if (!command.equals(COMMAND_NAME)) {
            return _nextHandler.handle(command, args);
        }

        if (args == null || args.isBlank()) {
            return new ListCommand(_controller);
        }

        return handleNested(command, args);
    }

    @Override
    public NestedHandleable addChild(Handleable child) {
        if (_children == null) {
            _children = child;
        } else {
            _children.addNext(child);
        }
        return this;
    }

    @Override
    public Commanding handleNested(String command, String args) {
        if (_children == null) {
            return null;
        }
        return _children.handle(command, args);
    }
}
