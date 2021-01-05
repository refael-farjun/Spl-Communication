package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class UnRegisteredCommand extends Command {
    short courseNumber;

    public UnRegisteredCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 10;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        // check if there is any courses that the student is registered to
        if (((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses().isEmpty())
            return new ErrorCommand(this.opcode);
        if (!((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses().
                contains(this.courseNumber))
            return new ErrorCommand(this.opcode);

        // remove from the data structures
        ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses().
                remove(this.courseNumber);
        database.getStudentInCourses().get(this.courseNumber).remove(protocol.getCurUserName());
        return new AckCommand(this.opcode, null);
    }
}
