package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class StudentStatCommand extends Command {
    String studentUserName;

    public StudentStatCommand(String studentUserName){
        this.studentUserName = studentUserName;
        this.opcode = 8;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err - not an Admin
        }
        String studentStat = "Student: " + this.studentUserName + "\n":
        studentStat += "Courses: " +
        return null;
    }
}
