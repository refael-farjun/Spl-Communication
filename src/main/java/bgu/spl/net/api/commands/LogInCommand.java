package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class LogInCommand extends Command {
    String userName;
    String password;

    public LogInCommand(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.opcode = 1;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public Command react(BGRSProtocol protocol) {
        if (!database.getUserConcurrentHashMap().containsKey(this.userName)){ // if nor register
            return new ErrorCommand(this.opcode); // err

        }
        if (!this.password.equals(database.getUserConcurrentHashMap().get(this.userName).getPassword())){ // wrong password
            return new ErrorCommand(this.opcode); // err
        }
        if (database.getUserConcurrentHashMap().get(this.userName).isLoggedIn()){
            return new ErrorCommand(this.opcode); // err
        }
        database.getUserConcurrentHashMap().get(this.userName).logIn();

        return new AckCommand(this.opcode, null); // ack
    }
}
