package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

import java.util.concurrent.ConcurrentLinkedQueue;

public class StudentStatCommand extends Command {
    String studentUserName;

    public StudentStatCommand(String studentUserName){
        this.studentUserName = studentUserName;
        this.opcode = 8;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (protocol.getCurUserName() == null && protocol.getCurPassword() == null) { // if no one logged in
            return new ErrorCommand(this.opcode); // err
        }
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Student){
            return new ErrorCommand(this.opcode); // err - not an Admin
        }
        if (!database.getUserConcurrentHashMap().containsKey(this.studentUserName))
            return new ErrorCommand(this.opcode); // not had any student with this user name
        String studentStat = "Student: " + this.studentUserName + "\n";
        studentStat += "Courses: [";
        ConcurrentLinkedQueue<Short> registeredCourses =
                ((Student) database.getUserConcurrentHashMap().get(this.studentUserName)).getRegisteredCourses();
        if (registeredCourses.isEmpty())
            studentStat += "]";
        else {
            for (short courseNum : database.getCourses().keySet()) {
                if (registeredCourses.contains(courseNum)) {
                    studentStat += courseNum + ",";
                }
            }
            studentStat = studentStat.substring(0, studentStat.length() - 1);
            studentStat += "]";
        }

        return new AckCommand(this.opcode, studentStat);
    }
}
