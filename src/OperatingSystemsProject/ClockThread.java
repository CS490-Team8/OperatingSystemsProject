package OperatingSystemsProject;

import java.util.ArrayList;

/**
 * Clock Thread will run a thread to simulate the system clock
 * This will act like a producer
 * Original code written by Professor Beth
 * @author mea0010
 */
public class ClockThread implements Runnable{

    private SharedCounter myCounter;   // each thread will have its own message counter
    private String myName;   // used to distinguish threads
    private String myPrefix; // leading spaces to distinguish between threads visually
    private int    myTime;
    private Controller myController;
    private Producer producer;


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

        producer = new Producer();
        producer.setName("Producer-1");
        producer.start();

        CpuThread consumer = new CpuThread(producer, myCounter);
        consumer.setName("CPU-1");
        consumer.start();
    }

    public void run( ){
        int tempvalue;
        while(true) {
            while (myCounter.isDoRun()) {
                try {
                    Thread.sleep(myTime);  // 1000 milliseconds is 1 second
                } catch (InterruptedException ex) {
                    // TBD catch and deal with exception ere
                }
                tempvalue = myCounter.updateCount();  // Treat the ++ and retrieve as a single operation
                System.out.println(myPrefix + myName + " has woken up and this is message number " + tempvalue);
                myController.increaseClockCycle();

                try{
                    add2queue();
                }catch (Exception e){

                }

            }
        }
    }

    private synchronized void add2queue() throws Exception{
        for(Process p: this.myController.processesReceived){
            //check process arrival time vs. system clock time
            if (this.myCounter.getCount() == p.getArrivalTime()){
                System.out.println(p); //debug line
                this.producer.produce(p); //add process to queue
            }
        }
    }
}
