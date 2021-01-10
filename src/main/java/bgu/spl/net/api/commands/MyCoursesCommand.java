package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MyCoursesCommand extends Command {

    public MyCoursesCommand(){
        this.opcode = 11;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (protocol.getCurUserName() == null && protocol.getCurPassword() == null) { // if no one logged in
            return new ErrorCommand(this.opcode); // err
        }

        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err admin cant register to courses
        }
        if (((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName()))
                .getRegisteredCourses().isEmpty())
            return new AckCommand(this.opcode, "[]");
        ConcurrentLinkedQueue<Short> registeredCourses =
                ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses();

        String myCourses = "[";
        for (int i = 0; i < database.getCourses().size(); i++){
            if (registeredCourses.contains(database.getCoursesNum().get(i)))
                myCourses += database.getCoursesNum().get(i) + ",";
        }
        myCourses = myCourses.substring(0, myCourses.length() - 1);
        myCourses += "]";
        return new AckCommand(this.opcode, myCourses);
    }
}
