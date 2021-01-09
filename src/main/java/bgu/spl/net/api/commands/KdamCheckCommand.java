package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class KdamCheckCommand extends Command {
    short courseNumber;
    public KdamCheckCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 6;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin) {
            return new ErrorCommand(this.opcode); // err admin cant send this command
        }
        if (!database.getUserConcurrentHashMap().containsKey(protocol.getCurUserName()))
            return new ErrorCommand(this.opcode);
        if (!database.getCourses().containsKey(this.courseNumber))
            return new ErrorCommand(this.opcode);

        String kdamCourse = database.getCourses().get(this.courseNumber).get(1);

        if (kdamCourse.equals("[]")){
            kdamCourse = null;
        }
        return new AckCommand(this.opcode, kdamCourse);
    }
}
