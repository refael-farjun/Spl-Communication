package bgu.spl.net.api;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Student extends User{
    private ConcurrentLinkedQueue<Short> registeredCourses;
    public Student(String userName, String password) {
        super(userName, password);
        this.registeredCourses = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueue<Short> getRegisteredCourses() {
        return registeredCourses;
    }
    public synchronized void registerToCourse(Short course){
        this.registeredCourses.add(course);
    }
}
