package bgu.spl.net.api;

public class BGRSProtocol implements MessagingProtocol<Command> {
    @Override
    public Command process(Command msg) {
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
