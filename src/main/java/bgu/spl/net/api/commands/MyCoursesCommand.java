package bgu.spl.net.api.commands;

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
        if (((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses().isEmpty())
            return new AckCommand(this.opcode, "[]");
        ConcurrentLinkedQueue<Short> registeredCourses =
                ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).getRegisteredCourses();

        String myCourses = "[";
        for (short courseNum : registeredCourses){
            myCourses += courseNum + ",";
        }
        myCourses = myCourses.substring(0, myCourses.length() - 1);
        myCourses += "]";
        return new AckCommand(this.opcode, myCourses);
    }
}
