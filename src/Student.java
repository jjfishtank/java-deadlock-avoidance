import java.util.Random;

public class Student implements Runnable{
    private final Teacher teacher;
    private final Random random = new Random();
    
	public Student(Teacher teacher) {
	    this.teacher = teacher;
	}
	
    /**
     * Student thread continuously asks teacher questions and receives answers. 
     * Simulates time in-between asking questions.
     */
	@Override
    public void run() {
    	try {
    	    while(true) {
    	        Thread.sleep(random.nextInt(89000) + 1000); // Between 1 and 90 seconds
    	        teacher.QuestionStart();
    	        teacher.QuestionDone();
    	    }
    	} catch(InterruptedException e) {
    	    Thread.currentThread().interrupt();
    	}
    	
    }
}
