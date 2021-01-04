package bgu.spl.net.api;

import java.lang.String;

import java.nio.ByteBuffer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command>{
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private String userName = null;
    private String password = null;
    private short opCodeNum = -1; // maybe in both need to be -1 .. saw in Dolav office hours
    private short courseNum = -1;

    @Override
    public Command decodeNextByte(byte nextByte) {
        if (opCodeNum == -1) {
            bytes[len++] = nextByte;
            if (len == 2){
                opCodeNum = bytesToShort(bytes);
                len = 0;
            }
            return null;
        } //Opcode is int now
        else{
            Command commandToSend = null;
            if ( decodeByteByOpcodeNumber(nextByte)){
                commandToSend = getCommandByOpcode();
                reset();
            }
            return commandToSend;
        }
    }

    private boolean decodeByteByOpcodeNumber(byte nextByte){
        if (opCodeNum == 1 || opCodeNum == 2 || opCodeNum == 3){
            if (userName == null)
                decodeUserName(nextByte);
            else if (password == null)
                decodePassword(nextByte);
            return  userName != null && password != null;
        }


        else if (opCodeNum == 5 || opCodeNum == 6 || opCodeNum == 7 || opCodeNum == 9 || opCodeNum == 10) {
            if (courseNum == -1)
                decodeCourseNum(nextByte);
            return courseNum != -1;
        }

        else if (opCodeNum == 8){
            if (userName == null)
                decodeUserName(nextByte);
            return userName != null;
        }

        return true;


    }

    private void decodeUserName(byte nextByte){

        if (nextByte != '\0' ) {
            pushByte(nextByte);
        }
        else {
            userName = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
        }
    }

    private void decodePassword(byte nextByte){

        if (nextByte != '\0' ) {
            pushByte(nextByte);
        }
        else {
            password = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
        }
    }

    private short decodeCourseNum(byte nextByte){  //Not sure
        if (courseNum == -1) {
            bytes[len++] = nextByte;
            if (len == 2){
                len = 0;
                return bytesToShort(bytes);

            }
            return -1;
        }
    }



    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
    }


    private Command getCommandByOpcode(){
        if (opCodeNum == 1)
            return new AdminRegCommand(userName, password);
        else if (opCodeNum == 2)
            return new StudentRegCommand(userName, password);
        else if (opCodeNum == 3)
            return new LogInCommand(userName, password);
        else if (opCodeNum == 4)
            return new LogOutCommand(userName, password);
        else if (opCodeNum == 5)
            return new CourseRegCommand(courseNum);
        else if (opCodeNum == 6)
            return new KdamCheckCommand(courseNum);
        else if (opCodeNum == 7)
            return new CourseStatCommand(courseNum);
        else if (opCodeNum == 8)
            return new StudentStatCommand(userName);
        else if (opCodeNum == 9)
            return new IsRegisterCommand(courseNum);
        else if (opCodeNum == 10)
            return new MyCoursesCommand();
        else
            return null;

    }

    private void reset(){
        opCodeNum = -1;
        courseNum = -1; //mmm......
        len = 0;
        bytes = new byte[1 << 10];
        userName = null;
        password = null;
    }

    @Override
    public byte[] encode(Command message) {
        return new byte[0];
    }

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
