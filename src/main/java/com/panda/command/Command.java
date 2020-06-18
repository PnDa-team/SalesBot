package com.panda.command;

/**
 * @author carbon
 * class created 15.06.2020
 */
public enum Command {
    NONE, NOTFORME,

    NOTIFY,
    START, HELP, ID, CUSTOMER , ADMIN,
    STICKER;

    public static Command getCommandByNumber(int number) {
        return Command.values()[number];
    }

    public static Command setCommandByGroup(String group) {
        Command result = getCommandByNumber(0);
        for (Command command : Command.values()) {
            if (group.equalsIgnoreCase(String.valueOf(command))) {
                result = command;
                return result;
            }
        }
        return result;
    }
}
