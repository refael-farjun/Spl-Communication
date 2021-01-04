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
            return null; // err

        }
        if (!this.password.equals(database.getUserConcurrentHashMap().get(this.userName).getPassword())){ // wrong password
            return null; // err
        }
        if (database.getUserConcurrentHashMap().get(this.userName).isLoggedIn()){
            return null; // err
        }
        database.getUserConcurrentHashMap().get(this.userName).logIn();

        return null; // ack
    }
}
