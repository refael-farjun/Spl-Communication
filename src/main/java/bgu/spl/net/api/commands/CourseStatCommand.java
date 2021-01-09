package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class CourseStatCommand extends Command {
    short courseNumber;

    public CourseStatCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 7;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Student){
            return new ErrorCommand(this.opcode); // err - not an Admin
        }
        if (!database.getCourses().containsKey(this.courseNumber))
            return new ErrorCommand(this.opcode); // err - no such course
        String courseStat = "Course: (" + this.courseNumber + ") " + database.getCourses().get(this.courseNumber).get(0) + "\n";
        if (database.getStudentInCourses().containsKey(this.courseNumber))
            courseStat += "Seat Available: " + database.getStudentInCourses().get(this.courseNumber).size() + "/";
        else
            courseStat += "Seat Available: " + "0" + "/";
        courseStat += database.getCourses().get(this.courseNumber).get(2) + "\n";
        courseStat += "Students Registered: [" ;
        if (database.getStudentInCourses().containsKey(this.courseNumber)){
            for (String userName : database.getStudentInCourses().get(this.courseNumber)){
                courseStat += userName + ", ";
            }
            courseStat = courseStat.substring(0, courseStat.length() - 2);
        }

        courseStat += "]";

        return new AckCommand(this.opcode, courseStat);
    }
}
