package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class KdamCheckCommand extends Command {
    short courseNumber;
    public KdamCheckCommand(){
        this.courseNumber = courseNumber;
        this.opcode = 6;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
