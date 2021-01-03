package bgu.spl.net.api;

import bgu.spl.net.api.commands.LogoutCommand;

public class BGRSProtocol implements MessagingProtocol<Command> {
    private boolean shouldTerminate = false;
    @Override
    public Command process(Command msg) {
        if (msg instanceof LogoutCommand)
            shouldTerminate = true;
        return msg.react();
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
