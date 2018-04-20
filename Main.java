package oppHomeWork3;

import java.io.File;
import java.io.IOException;

public class Main {
	private static char Pedro;

	public static void main(String[] args) {

		Group gr = new Group("PN-121");
		try {
			gr.addStudent(new Student("Alexander", "Ts", 19, true, 1234, "PN-121"));
			gr.addStudent(new Student("Ekaterina", "Rez", 18, false, 1253, "PN-121"));
			gr.addStudent(new Student("Alexey", "St", 20, true, 14523, "PN-121"));
			gr.addStudent(new Student("Irina", "Iv", 23, false, 12213, "PN-121"));
			gr.addStudent(new Student("Olga", "Cher", 17, false, 1243, "PN-121"));
			gr.addStudent(new Student("Andrey", "Pop", 18, true, 12123, "PN-121"));

		} catch (MyException e) {
			System.out.println(e.getMessage());
		}

		System.out.println(gr);
		gr.sortByParametr(2, false);
		System.out.println(gr);

		Student[] recruterArray = gr.getRecruter();

		System.out.println();

		System.out.println();

		File file = new File(gr.getGroupName() + ".txt");

		try {
			Group.saveobjet(file, gr);

		} catch (IOException e) {
			e.printStackTrace();

		}
		Group newstud = null;
		File fileTwo = new File("PN-121.txt");

		try {
			newstud = (Group) Group.loadObject(fileTwo);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();

		}

		System.out.println(newstud);
	}
}