package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

public class LogInCommand extends Command {
    String userName;
    String password;

    public LogInCommand(String userName, String password){
        synchronized (this){
            this.userName = userName;
            this.password = password;
        }
        this.opcode = 3;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized String getUserName() {
        return userName;
    }

    @Override
    public synchronized Command react(BGRSProtocol protocol) {
//        if (database.isSoneoneIsLogIn())
//            return new ErrorCommand(this.opcode); // err - someone ALREADY LOGIN

        if (!database.getUserConcurrentHashMap().containsKey(this.userName)){ // if nor register
            return new ErrorCommand(this.opcode); // err

        }
        if (!this.password.equals(database.getUserConcurrentHashMap().get(this.userName).getPassword())){ // wrong password
            return new ErrorCommand(this.opcode); // err - WRONG PASSWORD
        }
        if (database.getUserConcurrentHashMap().get(this.userName).isLoggedIn()){
            return new ErrorCommand(this.opcode); // err - ALREADY LOGIN
        }
        database.getUserConcurrentHashMap().get(this.userName).logIn();
//        database.getIsLogInConcurrentHashMap().put(this.userName, true);

//        database.setSoneoneIsLogIn(true);


        return new AckCommand(this.opcode, null); // ack
    }
}
