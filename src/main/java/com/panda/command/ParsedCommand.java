package com.panda.command;

/**
 * @author carbon
 * class created 15.06.2020
 *
 * сама команда и весь текст, который идет после команды
 */
public class ParsedCommand {
    Command command = Command.NONE;
    String text="";

    public ParsedCommand(Command command, String text) {
        this.command = command;
        this.text = text;
    }

    public ParsedCommand() {
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
