package bgu.spl.net.api.commands;

import bgu.spl.net.api.Admin;
import bgu.spl.net.api.BGRSProtocol;
import bgu.spl.net.api.Command;
import bgu.spl.net.api.Student;

import java.util.ArrayList;
import java.util.Arrays;
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
            return null; // err
        }
        //TODO data structure or decrement the max student - for seat open
        if (database.getUserConcurrentHashMap().get(protocol.getCurUserName()) instanceof Student){
            if (database.getCourses().get(this.courseNumber).get(1).equals("[]")){
                ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).registerToCourse(this.courseNumber);
                return null; // ack
            }
            ConcurrentLinkedQueue<Short> studentCourses = ((Student) database.getUserConcurrentHashMap().
                    get(protocol.getCurUserName())).getRegisteredCourses();
            String str = database.getCourses().get(this.courseNumber).get(1);
            String[] tempArr = str.substring(1, str.length()-1).split(",");
            int[] kadamCourses = parseIntArray(tempArr);
            for (int kdamCourse : kadamCourses){
                if (!studentCourses.contains(kdamCourse)){
                    return null; // err - not have all kdam courses
                }
            }
            ((Student) database.getUserConcurrentHashMap().get(protocol.getCurUserName())).registerToCourse(this.courseNumber);
            return null; // ack

        }

        return null; // err - user is admin
    }
    public int[] parseIntArray(String[] arr) {
        int[] ints = new int[arr.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(arr[i]);
        }
        return ints;
    }
}
