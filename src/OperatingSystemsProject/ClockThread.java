package OperatingSystemsProject;

/**
 * Clock Thread will run a thread to simulate the system clock
 * Original code written by Professor Beth
 * @author mea0010
 */
public class ClockThread implements Runnable{

    private SharedCounter myCounter;   // each thread will have its own message counter
    private String myName;   // used to distinguish threads
    private String myPrefix; // leading spaces to distinguish between threads visually
    private int    myTime;
    private Controller myController;

    /**
     * Class Constructor
     * @param name
     * @param c
     * @param prefix
     * @param timems
     * @param controller
     */
    public ClockThread(String name, SharedCounter c, String prefix, int timems, Controller controller) {
        // To do, any construction details that need to be performed before this code runs in its thread
        myCounter = c;  myTime = timems;
        myName = name;  myPrefix = prefix;
        myController = controller;
    }

    public void run( ){
        int tempvalue;
        for (int i = 1; i <= 20; i++) {  // just run 10 times
            if (!myCounter.isDoRun()){
                break;
            }
            try {
                Thread.sleep(myTime);  // 1000 milliseconds is 1 second
            } catch (InterruptedException ex) {
                // TBD catch and deal with exception ere
            }
            tempvalue = myCounter.updateCount();  // Treat the ++ and retrieve as a single operation
            System.out.println(myPrefix + myName + " has woken up and this is message number " + tempvalue);
            myController.increaseClockCycle();
        }
    }
}
