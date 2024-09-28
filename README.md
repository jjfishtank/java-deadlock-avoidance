# java-deadlock-avoidance
 Small threaded application to practice deadlock avoidance.
 
 Teacher thread is started which waits for and answers questions as they are received. Simulates random time taken to answer a question.
 
 Student thread(s) is/are started (number specified by command line argument.) Students continuously ask the teacher questions and receive answers. Sleeps for a random amount of time before asking another question.
# Usage
 `java -jar teacherdeadlock.jar <numStudents>`
