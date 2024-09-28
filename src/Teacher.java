import java.util.Random;
import java.util.concurrent.locks.*;

public class Teacher implements Runnable {
    private final Lock lock = new ReentrantLock();
    private final Condition questionAsked = lock.newCondition();
    private final Condition answerGiven = lock.newCondition();
    private boolean hasQuestion = false;
    
	public Teacher() {}
	
    /**
     * Waits for a question to be asked, then simulates the time to answer the question.
     */
    public void AnswerStart() throws InterruptedException {
        lock.lock();
        
        try {
             while (!hasQuestion) {
                 // If no question is asked after 1 second of waiting, the teacher falls asleep..
                 if (!questionAsked.await(1, java.util.concurrent.TimeUnit.SECONDS)) {
                     System.out.println("Teacher is sleeping...zZz...");
                 }
                 
             }
        } finally {
            lock.unlock();
            
            // Simulate time to answer question.
            Random random = new Random();
            System.out.println("Teacher is answering a question...");
            Thread.sleep(random.nextInt(15000) + 5000); // Between 5 and 20 seconds.
        }
    }
    
    /**
     * Sets hasQuestion to false. Signals to all waiting threads that a question has been answered.
     */
    public void AnswerDone() {
        lock.lock();
        try {
            hasQuestion = false;
            answerGiven.signalAll(); // Signal to all waiting Students that the Teacher has answered a question
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Ask the teacher a question. Blocks until any previous question has been answered.
     */
    public void QuestionStart() throws InterruptedException {
        lock.lock();
        try {
            // Wait for any current question the teacher has to resolve.
            while (hasQuestion) {
                answerGiven.await();
            }
            
            // Ask the question.
            hasQuestion = true;
            System.out.println(Thread.currentThread().getName() + " asks a question.");
            questionAsked.signal();
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Blocks until an answerGiven signal, printing that an answer has been received.
     */
    public void QuestionDone() throws InterruptedException {
        lock.lock();
        try {
            while (hasQuestion) {
                answerGiven.await();
            }
            System.out.println(Thread.currentThread().getName() + " recieved an answer.");
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Teacher thread continuously waits for questions and answers them as they are asked.
     */
    @Override
    public void run() {
        try {
            while (true) {
                AnswerStart();
                AnswerDone();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
