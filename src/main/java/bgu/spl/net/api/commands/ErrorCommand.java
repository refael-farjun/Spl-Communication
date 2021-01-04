package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class ErrorCommand extends Command {

    public ErrorCommand(){
        this.opcode = 13;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
