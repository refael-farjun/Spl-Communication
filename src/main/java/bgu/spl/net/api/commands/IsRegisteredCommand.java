package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
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
        if (protocol.getCurUserName() == null && protocol.getCurPassword() == null) { // if no one logged in
            return new ErrorCommand(this.opcode); // err
        }

        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err admin cant send this command
        }
        if (!database.getCourses().containsKey(this.courseNumber))
            return new ErrorCommand(this.opcode); // err - no such course
        if (((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName()))
                .getRegisteredCourses().contains(this.courseNumber)) // thanks to dibil yanay
            return new AckCommand(this.opcode, "REGISTERED");
        else
            return new AckCommand(this.opcode, "NOT REGISTERED");

    }
}
