package bgu.spl.net.api;

public abstract class Command {
    protected short opcode;
    protected Database database = Database.getInstance();


    public abstract Command react();
}
