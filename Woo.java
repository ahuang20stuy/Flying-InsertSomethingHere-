import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.*;
import java.util.PriorityQueue;

public class Woo {// driver

	private User user;
	private boolean studentAccount;
	
	String adminPass = "12345";

	public void loginPrompt() throws IOException, FileNotFoundException {
          IOTools.initStudents();
          IOTools.initCourses();
          for (Student s : LStudent.getStudents())
            s.setSchedule();
          System.out.println("Hi there! Welcome to Flying Bob's Programming Program!");
		if (!IOTools.checkSave()) { // if save file doesn't exist, there's no way for a user to be returning
			makeAcct();
		} else {
			if (hasAccount())
				login();
			else
				makeAcct();
		}
		//IOTools.initStudents();
		//IOTools.initCourses();
	}

	public void mainMenu2() {
		System.out.println("------------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1. Add a Course");
		System.out.println("2. Remove a Course");
		System.out.println("3. Add a Student");
		System.out.println("4. Remove a Student");
		System.out.println("5. Get a student");
		System.out.println("6. Generate Schedules");
		System.out.println("7. Logout");
		System.out.println("------------------------------");

		int option = Integer.parseInt(IOTools.readLine());
		if (option == 1) {
			System.out.println("Teacher?");
			String teacher = IOTools.readLine();
			System.out.println("Subject?");
			String subject = IOTools.readLine();
			System.out.println("Period?");
			int period = Integer.parseInt(IOTools.readLine());
			Course c = new Course(teacher, subject, period);
			((Admin) user).addCourse(c);
			mainMenu2();
		}
		if (option == 2) {
			ArrayList<Course> courses = LCourse.getCourses();
			for (int i = 1; i <= courses.size(); i++) {
				System.out.println(i + ". " + courses.get(i - 1));
			}
			System.out.println((courses.size() + 1) + ". Go back");
			int op = Integer.parseInt(IOTools.readLine());
			if (op > courses.size())
				mainMenu2();
			else {
				((Admin) user).removeCourse(op - 1);
				mainMenu2();
			}
		}
		if (option == 3) {
			System.out.println("Username?");
			String u = IOTools.readLine();
			System.out.println("Pass?");
			String p = IOTools.readLine();
			System.out.println("Grade?");
			int g = Integer.parseInt(IOTools.readLine());
			System.out.println("GPA?");
			double a = Double.parseDouble(IOTools.readLine());
			Student s = new Student(u, p, g, a);
			((Admin) user).addStudent(s);
			System.out.println("added student");
			mainMenu2();
		}
		if (option == 4) {
			LStudent.printStudents();
			System.out.println("type the id of the student you want to remove");
			int id = Integer.parseInt(IOTools.readLine());
			((Admin) user).removeStudent(id);
			mainMenu2();
		}
		if (option == 5) {
			System.out.println("type the id of the student you want to retrieve");
			int id = Integer.parseInt(IOTools.readLine());

			Student s = ((Admin) user).getStudent(id);
			System.out.println(s);
			if (s != null) {
				s.printSchedule();
			}
			mainMenu2();
		}
		if (option == 6) {
			((Admin) user).generateSchedules();
			mainMenu2();
		}
		if (option == 7) {
			System.out.println("Logging out...");
			return;
		}
	}

	public boolean hasAccount() {
		System.out.println("\nAre you...");
		System.out.println("1: A returning student?");
		System.out.println("2: A new student?");
		System.out.println("3: An administator?");

		int option = Integer.parseInt(IOTools.readLine());
		switch (option) { // decided to try something new
		case 1:
			studentAccount = true;
			return true;
		case 2:
			studentAccount = true;
			return false;
		case 3:
			studentAccount = false;
			return true;
		default:
			System.out.println("Please input a valid option.");
			hasAccount();
			return false;
		}
	}

	public void login() throws IOException, FileNotFoundException{
		System.out.println("Username?");
		String u = IOTools.readLine();
		System.out.println("Pass?");
		String p = IOTools.readLine();
		if (studentAccount) {
			int id = LStudent.checkStudent(u, p);
			if (id != -1) {
				user = LStudent.getStudent(id);
                                mainMenu();
                                return;
			} else {
				System.out.println("Incorrect combination. Try again.");
				login();
				return;
			}
		}
		// add a way to check and return bool whether this set of data exists in csv
		if (u.equals("admin") && p.equals("12345")) {
			user = new Admin(u, p);
			mainMenu2();
		} else {
			System.out.println("Incorrect combination. Try again.");
			login();
			return;
		}
	}

	public void makeAcct() throws FileNotFoundException, IOException {
		System.out.println("Username?");
		String u = IOTools.readLine();
		System.out.println("Pass?");
		String p = IOTools.readLine();
		System.out.println("Grade?");
		int g = Integer.parseInt(IOTools.readLine());
		System.out.println("GPA?");
		double a = Double.parseDouble(IOTools.readLine());

		user = new Student(u, p, g, a);
		LStudent.addStudent((Student) user);
		System.out.println("Great job, we've made you an account and logged you in!");
		mainMenu();
	}

	public void mainMenu() throws FileNotFoundException, IOException {
		System.out.println("------------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1. Print schedule");
		System.out.println("2. Print schedule in reverse");
		System.out.println("3. Choose classes");
		System.out.println("4. View stats");
		System.out.println("5. Try to crack the admin password");
		System.out.println("6. Logout");
		System.out.println("------------------------------");

		int option = Integer.parseInt(IOTools.readLine());
		if (option == 1) {
			((Student) user).printSchedule();
			mainMenu();
		}
		if (option == 2) {
			((Student) user).printScheduleReverse();
			mainMenu();
		}
		if (option == 3) {
			((Student) user).chooseClasses(1);
			mainMenu();
		}
		if (option == 4) {
			System.out.println("gpa: " + ((Student) user).getGPA());
			System.out.println("grade: " + ((Student) user).getGrade());
			mainMenu();
		}
		if (option == 5) {
			System.out.println("Cracking results: " + crackPass(adminPass));
			System.out.println("You've successfully cracked the admin password!");
			System.out.println("You have been duly reported.");
			mainMenu();
		}
		if (option == 6) {
			logout();
			System.out.println("Logging out...");
			return;
		}
	}

	public void logout() throws FileNotFoundException, IOException {
		String studentDat = "Username,Password,Grade,GPA\n";
		String courseDat = "Teacher,Subject,Period,Waitlist|Roster\n";

		for (Student s : LStudent.getStudents()) {
                  String sched = "";
                  for (Course c: s.getSchedule()){
                    sched += LCourse.searchCourse(c) + ",";
                  }
			String temp = s.getUser() + "," + s.getPass() + "," + s.getGrade() + "," + s.getGPA() + sched +  "\n";
			studentDat += temp;
		}
		for (Course c : LCourse.getCourses()) {
			String temp2 = "";
			String temp3 = "";
			for (Student s : c.getWaitList()) {
				temp2 += s.getId() + ",";
			}
			for (Student s : c.getRoster()) {
				temp3 += s.getId() + ",";
			}
			String temp = c.getTeacher() + "," + c.getSubject() + "," + c.getPeriod() + "," + temp2 + "|" + temp3 + "\n";
			courseDat += temp;
		}

		IOTools.writeStudent(studentDat, false);
		IOTools.writeCourse(courseDat, false);
	}

	public static void main(String[] args) throws IOException, FileNotFoundException {
		Woo n = new Woo();
		n.loginPrompt();
	}

	// Recursive Backtracking Password Cracker (RBPC) (patent-pending) by Bob
	public static String crackPass(String p) {
		return crackPass(p, "");
	}

	public static String crackPass(String pass, String guess) {
		// Base case: password and guess have same length
		if (pass.length() == guess.length()) {
			if (pass.equals(guess.toString())) {
				return guess.toString();
			} else {
				return "";
			}
		}

		String s = "";

		for (int i = 0; i < 9; i++) {
			// Adds a new int to guess
			guess += String.valueOf(i);
			System.out.println("Current guess: " + guess);
			// Try to find pass with current guess
			s = crackPass(pass, guess);

			if (!s.equals("")) {
				return s;
			}

			// Bad guess, so remove the int and try again with the next
			String tmp = guess.substring(0, guess.length() - 1);
			System.out.println(guess + " failed, backtracking to " + tmp);
			guess = tmp;

		}
		// None of the ints have worked, so go up one level
		return "";
	}

}
