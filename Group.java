package oppHomeWork3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Group implements Voencom, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student[] studentArray = new Student[10];
	private String groupName;

	public Group(String string, String string2, int i, int j, String string3) {
		super();
		this.groupName = "unknow";
	}

	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + Arrays.hashCode(studentArray);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (!Arrays.equals(studentArray, other.studentArray))
			return false;
		return true;
	}

	public void addStudentInteractive() throws MyException {
		try {
			String name = getName("Input student name");
			String lastName = getName("Input student lastname");
			int age = getAge();
			boolean sex = getSex("Input sex -> man or women");
			long zach = getZach("Input zach number");
			String group = this.groupName;
			Student st = new Student(name, lastName, age, sex, zach, group);
			this.addStudent(st);
		} catch (NullPointerException e) {
			System.out.println("Canceled");
			return;
		}
	}

	public void addStudent(Student student) throws MyException {
		if (student == null) {
			throw new IllegalArgumentException("Null student");
		}
		for (int i = 0; i < studentArray.length; i++) {
			if (studentArray[i] == null) {
				student.setGroup(this.groupName);
				studentArray[i] = student;
				return;
			}
		}
		throw new MyException();
	}

	public Student search(String lastName) {
		for (Student student : studentArray) {
			if (student != null && student.getLastname().equals(lastName)) {
				return student;
			}
		}
		return null;
	}

	public void deleteStudent(int n) {
		if (!(n >= 0 && n < studentArray.length)) {
			System.out.println("Error index");
			return;
		}
		studentArray[n] = null;
	}

	private void sort() {
		for (int i = 0; i < studentArray.length - 1; i++) {
			for (int j = i + 1; j < studentArray.length; j++) {
				if (compareStudent(studentArray[i], studentArray[j]) > 0) {
					Student temp = studentArray[i];
					studentArray[i] = studentArray[j];
					studentArray[j] = temp;
				}
			}
		}
	}

	private int compareStudent(Student a, Student b) {
		if (a != null && b == null) {
			return 1;
		}
		if (a == null && b != null) {
			return -1;
		}
		if (a == null && b == null) {
			return 0;
		}
		return a.getLastname().compareTo(b.getLastname());

	}

	private int getAge() throws NullPointerException {
		boolean done = false;
		int age = 0;
		for (; !done;) {
			try {
				age = Integer.valueOf(JOptionPane.showInputDialog("Input student age"));
				done = true;
			} catch (NumberFormatException e) {
				JOptionPane.showInternalMessageDialog(null, "Invalid ");
			}
		}
		return age;
	}

	private String getName(String message) throws NullPointerException {
		boolean done = false;
		String name = "";
		for (; !done;) {
			try {
				name = JOptionPane.showInputDialog(message);
				done = true;
			} catch (NumberFormatException e) {
				JOptionPane.showInternalMessageDialog(null, "Invalid format");
			}
		}
		return name;
	}

	private boolean getSex(String message) throws NullPointerException {
		boolean done = false;
		boolean name = false;
		for (; !done;) {
			try {
				name = JOptionPane.showInputDialog(message).equals("man");
				done = true;
			} catch (NumberFormatException e) {
				JOptionPane.showInternalMessageDialog(null, "Invalid format");
			}
		}
		return name;
	}

	private long getZach(String message) throws NullPointerException {
		boolean done = false;
		long name = 0;
		for (; !done;) {
			try {
				name = Long.valueOf(JOptionPane.showInputDialog(message));
				done = true;
			} catch (NumberFormatException e) {
				JOptionPane.showInternalMessageDialog(null, "Invalid format");
			}
		}
		return name;
	}

	public void sortByParametr(int i) {
		Arrays.sort(this.studentArray, new StudentComparator(i));
	}

	public void sortByParametr(int i, boolean forward) {
		Arrays.sort(this.studentArray, new StudentComparator(i, forward));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Group: " + this.groupName).append(System.lineSeparator());
		int i = 0;
		// sort();
		for (Student student : studentArray) {
			if (student != null) {
				sb.append((++i) + ") ").append(student);
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

	@Override
	public Student[] getRecruter() {
		int n = 0;
		for (Student student : studentArray) {
			if (student != null && student.isSex() && student.getAge() >= 18) {
				n += 1;
			}
		}
		Student[] studentArray = new Student[n];
		int i = 0;
		for (Student student : studentArray) {
			if (student != null && student.isSex() && student.getAge() >= 18) {
				studentArray[i++] = student;
			}
		}
		return studentArray;
	}

	public static void saveobjet(File file, Object object) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException();
		}
		try (ObjectOutput oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(object);

		} catch (IOException e) {

			throw e;
		}
	}

	public static Object loadObject(File file) throws IOException, ClassNotFoundException {

		if (file == null) {
			throw new IllegalArgumentException();
		}
		try (ObjectInput ois = new ObjectInputStream(new FileInputStream(file))) {

			return ois.readObject();

		} catch (IOException e) {
			throw e;
		}
	}

	public Field getDeclaredField(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}