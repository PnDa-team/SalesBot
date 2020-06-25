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
        String regex = "(\\/)(?<=\\/{1})(\\S+)(\\s+)(.+)|(\\/)(?<=\\/{1})(\\S+)";

        Pattern p = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(text);
        while (m.find()) {
            if (m.group(2) == null && m.group(4) == null) {
                command = Command.setCommandByGroup(m.group(6));
                log.info("Command without arguments --> " + command);
            } else if (!m.group(2).isEmpty() && !m.group(4).isEmpty()) {
                command = Command.setCommandByGroup(m.group(2));
                textForCommand = m.group(4);
                log.info("Command with arguments");
                log.info("Command --> " + command);
                log.info("TextForCommand --> " + textForCommand);
            }
        }


        return new ParsedCommand(command, textForCommand);
    }
}