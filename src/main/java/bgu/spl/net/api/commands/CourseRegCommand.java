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
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Admin){
            return new ErrorCommand(this.opcode); // err admin cant register to courses
        }
        //TODO data structure or decrement the max student - for seat open
        if (database.getStudentInCourses().get(this.courseNumber).size() >= getMaxSeat()){
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
            int[] kadamCourses = parseIntArray(tempArr);
            for (int kdamCourse : kadamCourses){
                if (!studentCourses.contains(kdamCourse)){
                    return new ErrorCommand(this.opcode); // err - not have all kdam courses
                }
            }
            ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).registerToCourse(this.courseNumber);
            checkAddCourse(protocol.getCurUserName()); // check if there is student that rgisterd if not put new
            return new AckCommand(this.opcode, null); // ack OK have all the kdam course

        }

    }
    public int[] parseIntArray(String[] arr) {
        int[] ints = new int[arr.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(arr[i]);
        }
        return ints;
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
