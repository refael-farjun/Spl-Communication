package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class IsRegisteredCommand extends Command {
    short courseNumber;

    public IsRegisteredCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 9;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().containsKey(protocol.getCurUserName()))
            return new AckCommand(this.opcode, "REGISTERED");
        else
            return new AckCommand(this.opcode, "NOT REGISTERED");

    }
}
