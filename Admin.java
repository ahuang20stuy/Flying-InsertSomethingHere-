public class Admin extends User {

    public Admin(String u, String p) {
        super(u, p);
    }

    public void generateSchedules(int id) {
	if (id > LStudent.biggestID)
	    break;
	else {
	    for (Course c:LStudent.getStudent(id).getWants()) {
		
		
		
	
    }

     public Course addCourse(Course c) {
         System.out.println(LCourse.addCourse(c));
         return c;
     }

     public void removeCourse(int i) {
         System.out.println(LCourse.removeCourse(i));
     }


     public void addStudent(Student s) {
         LStudent.addStudent(s);
     }


     public Student removeStudent(int id) {
         return LStudent.removeStudent(id);
     }

     public Student getStudent(int id) {
         return LStudent.getStudent(id);
     }

    public  boolean addStudentInfo(String info) {
        return true;
    }
}
