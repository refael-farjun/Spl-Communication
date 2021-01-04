package bgu.spl.net.api.commands;

import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;

public class UnRegisteredCommand extends Command {
    @Override
    public Command react(BGRSProtocol protocol) {
        return null;
    }
}
