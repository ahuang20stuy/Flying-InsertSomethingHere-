import java.util.ArrayList;

public class LCourse {
    //instance vars
    private ArrayList<Course> courses;

    public LCourse() {
	courses = new ArrayList();
    }
    
    //methods
    public void printCourses() {
	String retVal = "Current List of Courses Available: \n";
	
	for (Course c : courses)
	    retVal += c + "/n"; //make a toString for Course w/ subject, teacher

	System.out.println(retVal);
    }

    public String addCourse(Course c) {
	courses.add(c);
	return "Added course " + c.getSubject() + "!";
    }

    public String removeCourse(Course c) {
	//need to implement
	return "Removed Course " + c.getSubject() + "!";
    }
}


