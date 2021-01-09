package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class CourseRegCommand extends Command {
    short courseNumber;

    public CourseRegCommand(short courseNumber){
        this.courseNumber = courseNumber;
        this.opcode = 5;
    }
    @Override
    public Command react(BGRSProtocol protocol) {
        if (!database.getCourses().containsKey(this.courseNumber)){
            return new ErrorCommand(this.opcode); // err no such course
        }
        else if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err admin cant register to courses
        }
        //TODO data structure or decrement the max student - for seat open
        else if (database.getStudentInCourses().contains(this.courseNumber) &&
                database.getStudentInCourses().get(this.courseNumber).size() >= getMaxSeat()){
            return new ErrorCommand(this.opcode); // err there is no seat left
        }

        else {
        //if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Student){
            if (database.getCourses().get(this.courseNumber).get(1).equals("[]")){
                ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).registerToCourse(this.courseNumber);
                checkAddCourse(protocol.getCurUserName()); // check if there is student that rgisterd if not put new

                return new AckCommand(this.opcode, null); // ack there is no kdam courses
            }
            ConcurrentLinkedQueue<Short> studentCourses = ((Student) database.getUserConcurrentHashMap().
                    get(protocol.getCurUserName())).getRegisteredCourses();
            String str = database.getCourses().get(this.courseNumber).get(1);
            String[] tempArr = str.substring(1, str.length()-1).split(",");

            short[] kadamCourses = parseShortArray(tempArr);
            for (short kdamCourse : kadamCourses){
                if (!studentCourses.contains(kdamCourse)){
                    return new ErrorCommand(this.opcode); // err - not have all kdam courses
                }
            }
            ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).registerToCourse(this.courseNumber);
            checkAddCourse(protocol.getCurUserName()); // check if there is student that rgisterd if not put new
            return new AckCommand(this.opcode, null); // ack OK have all the kdam course

        }

    }
    public short[] parseShortArray(String[] arr) {
        short[] kadamCourses = new short[arr.length];
        for (int i = 0; i < kadamCourses.length; i++){
            kadamCourses[i] = Short.parseShort(arr[i]);
        }
        return kadamCourses;
    }

    public void checkAddCourse(String userName){
        if (database.getStudentInCourses().containsKey(this.courseNumber)){
            database.getStudentInCourses().get(this.courseNumber).add(userName); // adding user name to the courses list
        }
        else
            database.getStudentInCourses().put(this.courseNumber, new ArrayList<String>(Collections.singletonList(userName)));
    }

    public int getMaxSeat(){
        return Integer.parseInt(database.getCourses().get(this.courseNumber).get(2));
    }
}
