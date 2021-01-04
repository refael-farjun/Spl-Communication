package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class ErrorCommand extends Command {
    short messageOpCode;

    public ErrorCommand(short messageOpCode){
        this.messageOpCode = messageOpCode;
        this.opcode = 13;
    }

    public short getMessageOpCode() {
        return messageOpCode;
    }

    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
