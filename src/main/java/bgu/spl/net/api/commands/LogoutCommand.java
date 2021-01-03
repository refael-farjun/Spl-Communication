package bgu.spl.net.api.commands;

import bgu.spl.net.api.Command;

public class LogoutCommand extends Command {
    String userName;
    String password;
    public LogoutCommand(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.opcode = 4;
    }


    @Override
    public Command react() {
        return null;
    }
}
