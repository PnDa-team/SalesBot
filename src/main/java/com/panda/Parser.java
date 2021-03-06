package com.panda;

import com.panda.command.Command;
import com.panda.command.ParsedCommand;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author carbon
 * class created 15.06.2020
 */
public class Parser {  //TODO
    private static final Logger log = Logger.getLogger(Parser.class);
    private final String PREFIX_FOR_COMMAND = "/";
    private final String DELIMITER_COMMAND_BOTNAME = "@";
    private String botName;

    public Parser(String botName) {
        this.botName = botName;
    }

    public Parser() {

    }


    public static ParsedCommand getParsedCommand(String text) {
        Command command = Command.getCommandByNumber(0);
        String textForCommand = "";
        if (text != null) textForCommand = text.trim();
//        String regex = "(\\/)(?<=\\/{1})(\\S+)(\\s+)(.+)";
        String regex = "(\\/)(?<=\\/{1})(\\S+)(\\s+)(.+)|(\\/)(?<=\\/{1})(\\S+)";

        Pattern p = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(text);
        while (m.find()) {
            if (m.group(2) == null && m.group(4) == null) {
                command = Command.setCommandByGroup(m.group(6));
                log.info("Command without arguments --> " + command);
            } else {
                command = Command.setCommandByGroup(m.group(2));
                textForCommand = m.group(4);
                log.info("Command with arguments");
                log.info("Command --> " + command);
                log.info("TextForCommand --> " + textForCommand);
            }
        }


        return new ParsedCommand(command, textForCommand);
    }

    private boolean isCommand(String text) { //TODO
        return text.startsWith(PREFIX_FOR_COMMAND);
    }


   /* public ParsedCommand getParsedCommand(String text) {
        String trimText = "";
        if (text != null) trimText = text.trim();
        ParsedCommand result = new ParsedCommand(Command.NONE, trimText);

        if ("".equals(trimText)) return result;
        Pair<String, String> commandAndText = getDelimitedCommandFromText(trimText);
        if (isCommand(commandAndText.getKey())) {
            if (isCommandForMe(commandAndText.getKey())) {
                String commandForParse = cutCommandFromFullText(commandAndText.getKey());
                Command commandFromText = getCommandFromText(commandForParse);
                result.setText(commandAndText.getValue());
                result.setCommand(commandFromText);
            } else {
                result.setCommand(Command.NOTFORME);
                result.setText(commandAndText.getValue());
            }

        }
        return result;
    }

    private String cutCommandFromFullText(String text) {
        return text.contains(DELIMITER_COMMAND_BOTNAME) ?
                text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME)) :
                text.substring(1);
    }

    private Command getCommandFromText(String text) {
        String upperCaseText = text.toUpperCase().trim();
        Command command = Command.NONE;
        try {
            command = Command.valueOf(upperCaseText);
        } catch (IllegalArgumentException e) {
            log.debug("Can't parse command: " + text);
        }
        return command;
    }

    private Pair<String, String> getDelimitedCommandFromText(String trimText) {
        Pair<String, String> commandText;

        if (trimText.contains(" ")) {
            int indexOfSpace = trimText.indexOf(" ");
            commandText = new Pair<>(trimText.substring(0, indexOfSpace), trimText.substring(indexOfSpace + 1));
        } else commandText = new Pair<>(trimText, "");
        return commandText;
    }

    private boolean isCommandForMe(String command) {
        if (command.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = command.substring(command.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    */
}