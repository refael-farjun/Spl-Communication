package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class IsRegisteredCommand extends Command {
    short courseNumber;

    public IsRegisteredCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 5;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
