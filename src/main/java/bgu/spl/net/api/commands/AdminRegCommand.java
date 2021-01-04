package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class AdminRegCommand extends Command {
    String userName;
    String password;

    public AdminRegCommand(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.opcode = 1;

    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (database.getUserConcurrentHashMap().containsKey(this.userName)){
            return null; // err
        }
        if (database.getUserConcurrentHashMap().get(this.userName) instanceof Student){
            return null; // err
        }
        database.addUser(new Admin(this.userName, this.password));

        return null; // ack
    }
}
