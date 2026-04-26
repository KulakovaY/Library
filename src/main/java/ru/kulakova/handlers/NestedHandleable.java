package ru.kulakova.handlers;

import ru.kulakova.commands.Commanding;

public interface NestedHandleable extends Handleable{

    NestedHandleable addChild(Handleable child);

    Commanding handleNested(String commandName, String args);
}
