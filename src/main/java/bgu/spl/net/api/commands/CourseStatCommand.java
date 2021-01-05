package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class CourseStatCommand extends Command {
    short courseNumber;

    public CourseStatCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 7;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err - not an Admin
        }
        String courseStat = "Course: (" + this.courseNumber + ") " + database.getCourses().get(this.courseNumber).get(0) + "\n";
        courseStat += "Seat Available: " + database.getStudentInCourses().get(this.courseNumber).size() + "/";
        courseStat += database.getCourses().get(this.courseNumber).get(2) + "\n";
        courseStat += "Students Registered: [" ;
        for (String userName : database.getStudentInCourses().get(this.courseNumber)){
            courseStat += userName + ", ";
        }
        courseStat = courseStat.substring(0, courseStat.length() - 1);
        courseStat += "]";

        return new AckCommand(this.opcode, courseStat);
    }
}
