package OperatingSystemsProject;

/**
 * CpuThread will be used to simulate a single CPU
 * Multiple CpuThread objects can be used to add additional CPUs
 */
public class CpuThread extends Thread {
    //Class Members
    private SharedCounter myCounter;   // each thread will have its own message counter
    private String myName;   // used to distinguish threads
    private String myPrefix; // leading spaces to distinguish between threads visually
    private int    myTime; //time input from the user, determines wait time between cycles
    private Controller myController; //link back to the original controller
    private boolean stopped = false; //boolean value used to determine when the thread should "pause" while waiting for the service time of a process to complete
    private String processName = null; //current name of process that CPU is executing, changes for each object the CPU consumes
    private int timeOut; //int value representing the time that the CPU consumed the job
    private Process data; //process to be executed on CPU
    private Producer producer; //producer from the producer/consumer pattern

    /**
     * Class constructor
     * @param producer the producer from the producer/consumer pattern
     * @param c SharedCounter object representing the system clock, this ensures the whole system is using the same clock count
     * @param controller controller object
     */
    public CpuThread(Producer producer, SharedCounter c, Controller controller) {
        //assign class members
        this.producer = producer;
        this.myCounter = c;
        this.myController = controller;

    }

    /**
     * Override for run function. This is what simulates CPU execution
     */
    @Override
    public void run() {
        int timeIn;

        try {
            while (true) {
                //-------------------------------------------------------------
                //consume next job and set current values
                //-------------------------------------------------------------
                data = producer.consume(); //process to be executed on CPU
                this.processName = data.getProcessId(); //set processName to be the same as the process that was just consumed
                System.out.println("Consumed by: " + Thread.currentThread().getName() + " data: " + data); //debug line
                timeIn = myCounter.getCount(); //represents the time that the CPU consumed the job
                this.timeOut = timeIn + data.getServiceTime(); //calculate the time that the CPU

                myController.removeFromQueue(data); //remove job from wait queue
                System.out.println("TimeIN: " + timeIn + " timeOUT: " + this.timeOut + " clock: " + myCounter.getCount() ); //debug line

                //-------------------------------------------------------------
                //Pause CPU an equal time as the service time
                //-------------------------------------------------------------
                this.stopped=true; //set flag

                while(this.stopped){
                    //System.out.println("Entered wait queue. Clock: " + myCounter.getCount() + " :: " + timeOut); //debug line

                    if(timeOut <= myCounter.getCount()){ //Check to see
                        //System.out.println("CPU has paused for the service time"); //debug line
                        this.stopped=false; //change flag
                    }
                    //Thread.sleep(75); //used to not flood console output with debugging information. Recommend only uncommenting when trying to debug
                }
                //System.out.println("Job Finished"); //debug line

                //-------------------------------------------------------------
                //Make a new process with identical data for storing finished jobs
                //-------------------------------------------------------------
                Process copyData = new Process(data.getArrivalTime(),data.getProcessId(),data.getServiceTime(),data.getProcessPriority(),this.timeOut); //create object
                myCounter.addFinishedProcess(copyData); //store object

                //Remove process name from this CPU now that it is complete
                this.processName = null;
            }
        } catch (Exception exp) {
            //TBD
        }
    }

    /**
     * Getter for process name
     * @return String representing the name of the current process being executed
     */
    public String getProcessName(){
        return this.processName;
    }

    /**
     * Getter for time remaining
     * @return integer value representing time left until CPU finishes current job
     */
    public int getTimeRemaining(){
        return this.timeOut - myCounter.getCount();
    }
}

