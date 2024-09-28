
public class Driver {
    
    /**
     * Starts a Teacher thread followed by numStudents Student threads.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: teacherdeadlock.jar <numStudents>");
        }
        int numStudents = Integer.parseInt(args[0]);
        
        Student[] students = new Student[numStudents];
        Teacher teacher = new Teacher();
        
        Thread teacherThread = new Thread(teacher);
        teacherThread.start();
        
        Thread[] studentThreads = new Thread[numStudents];
        for (int i = 0; i < numStudents; i++) {
            students[i] = new Student(teacher);
        	studentThreads[i] = new Thread(students[i], "Student " + (i + 1)); // Name Student threads
        	studentThreads[i].start();
        }
        
        try {
        	teacherThread.join();
        	for (Thread t : studentThreads) {
        		t.join();
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
