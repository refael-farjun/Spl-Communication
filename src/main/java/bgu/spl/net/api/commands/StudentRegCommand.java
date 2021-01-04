package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class StudentRegCommand extends Command {
    String userName;
    String password;

    public StudentRegCommand(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.opcode = 1;
    }


        @Override
    public Command react(BGRSProtocol protocol) {
            if (database.getUserConcurrentHashMap().containsKey(this.userName)){
                return new ErrorCommand(this.opcode); // err already registered
            }
            if (database.getUserConcurrentHashMap().get(this.userName) instanceof Admin){
                return new ErrorCommand(this.opcode); // err already registered
            }
            database.addUser(new Student(this.userName, this.password));

            return new AckCommand(this.opcode, null);
    }
}
