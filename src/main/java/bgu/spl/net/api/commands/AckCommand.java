package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class AckCommand extends Command {
    short messageOpCode;
    String optional;

    public AckCommand(short messageOpCode, String optional){
        this.messageOpCode = messageOpCode;
        this.optional = optional;
        this.opcode = 12;
    }

    public short getMessageOpCode() {
        return messageOpCode;
    }

    public String getOptional() {
        return optional;
    }


    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
