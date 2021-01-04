package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class LogOutCommand extends Command {

    public LogOutCommand(){
        this.opcode = 4;
    }


    @Override
    public Command react(BGRSProtocol protocol) {
        if (protocol.getCurUserName() == null && protocol.getCurPassword() == null) { // if no one logged in
            return null; // err
        }
        return null; //ack

    }
}