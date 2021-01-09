package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class IsRegisteredCommand extends Command {
    short courseNumber;

    public IsRegisteredCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 9;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName()))
                .getRegisteredCourses().contains(this.courseNumber)) // thanks to dibil yanay
            return new AckCommand(this.opcode, "REGISTERED");
        else
            return new AckCommand(this.opcode, "NOT REGISTERED");

    }
}
