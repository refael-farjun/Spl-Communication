package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class LogOutCommand extends Command {

    public LogOutCommand(){
        this.opcode = 4;
    }


    @Override
    public synchronized Command react(BGRSProtocol protocol) {
        if (protocol.getCurUserName() == null && protocol.getCurPassword() == null) { // if no one logged in
            return new ErrorCommand(this.opcode); // err
        }
        database.getUserConcurrentHashMap().get(protocol.getCurUserName()).logOut();

//        database.setSoneoneIsLogIn(false);

        protocol.setCurUserName(null);
        protocol.setCurPassword(null);
        protocol.setShouldTerminate(true);

        return new AckCommand(this.opcode, null); //ack

    }
}
