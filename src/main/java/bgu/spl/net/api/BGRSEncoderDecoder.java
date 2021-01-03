package bgu.spl.net.api;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command>{
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private String userName = null;
    private String password = null;
    private short opCodeNum = 0; // maybe in both need to be -1 .. saw in Dolav office hours
    private short courseNum = 0;

    @Override
    public Command decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Command message) {
        return new byte[0];
    }
}
