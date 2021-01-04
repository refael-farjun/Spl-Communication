package bgu.spl.net.api;

public abstract class Command {
    protected short opcode;
    protected Database database = Database.getInstance();

    public short getOpcode() {
        return opcode;
    }

    public abstract Command react(BGRSProtocol protocol);
}
