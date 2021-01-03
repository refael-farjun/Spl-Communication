package bgu.spl.net.api;

public interface Command {
//    protected short opcode;
//    protected Database data;
    public abstract Command react(); // maybe return String.. maybe instead of act -> sendBack
}
