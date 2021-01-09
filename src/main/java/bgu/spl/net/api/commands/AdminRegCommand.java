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
        if (protocol.getCurUserName() != null && protocol.getCurPassword() != null) { // if no one logged in
            return new ErrorCommand(this.opcode); // err - someone already connected
        }
        if (database.getUserConcurrentHashMap().containsKey(this.userName)){
            return new ErrorCommand(this.opcode); // err
        }
        if (database.getUserConcurrentHashMap().get(this.userName) instanceof Student){
            return new ErrorCommand(this.opcode); // err
        }
        database.addUser(new Admin(this.userName, this.password));

        return new AckCommand(this.opcode, null); // ack
    }
}
