package OperatingSystemsProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private int    myTime; //amount of time between clock cycles. This is determined by an input from the GUI
    private Controller myController; //link back to the controller that created this clock thread
    private Producer producer; //producer for the producer/consumer pattern. Producer will populate jobs once they arrive (as determined in the input file)
    private CpuThread consumer1; //first consumer from the producer consumer pattern. This represents CPU #1
    private CpuThread consumer2; //first consumer from the producer consumer pattern. This represents CPU #2


    /**
     * Class Constructor
     * @param name name of thread. Used to distinguish between different threads
     * @param c SharedCounter object that will represent the system clock
     * @param prefix Used to distinguish between different threads
     * @param timems //time in milliseconds
     * @param controller //link back to controller that created this thread
     */
    public ClockThread(String name, SharedCounter c, String prefix, int timems, Controller controller) {
        //assign class members their values
        myCounter = c;  myTime = timems;
        myName = name;  myPrefix = prefix;
        myController = controller;

        //create and start the producer
        producer = new Producer();
        producer.setName("Producer-1");
        producer.start();

        //create and start CPU #1 (first consumer)
        consumer1 = new CpuThread(producer, myCounter, myController);
        consumer1.setName("CPU-1");
        consumer1.start();

        //create and start CPU #2 (second consumer)
        consumer2 = new CpuThread(producer, myCounter, myController);
        consumer2.setName("CPU-2");
        consumer2.start();
    }

    /**
     * Run function for Clock Thread
     */
    public void run( ){
        int tempvalue;
        while(true) {
            while (myCounter.isDoRun()) {
                try {
                    Thread.sleep(myTime);  // This puts the clock to sleep for time determined by user. Time value is in milliseconds
                } catch (InterruptedException ex) {
                    // TBD catch and deal with exception here
                }
                tempvalue = myCounter.updateCount();  // Treat the ++ and retrieve as a single operation
                System.out.println(myPrefix + myName + " has woken up and this is message number " + tempvalue); //debug line
                myController.increaseClockCycle(); //perform everything needed with a clock cycle increase

                try{
                    add2queue();
                }catch (Exception e){
                    // TBD catch and deal with exception here
                }
            }
        }
    }

    /**
     * add2queue adds items to the wait queue that is displayed on the GUI
     * These represent jobs that the CPUs have not grabbed yet
     * @throws Exception
     */
    private synchronized void add2queue() throws Exception{
        for(Process p: this.myController.processesReceived){
            //check process arrival time vs. system clock time
            if (this.myCounter.getCount() == p.getArrivalTime()){
                //System.out.println(p); //debug line
                this.producer.produce(p); //add process to queue
            }
        }
    }

    /**
     * getCPUReport generates the string that will be used
     * to display on the CPU box of the GUI. This string
     * will show what the CPUs are currently doing
     * @return String that contains current CPU data
     */
    public String getCPUReport(){
        String report; //string that will get returned

        //Info for CPU #1
        report = consumer1.getName() + "\nexec: ";
        if(consumer1.getProcessName() != null){
            report += consumer1.getProcessName();
            report += "\ntime remaining = " + consumer1.getTimeRemaining();
        }else{
            report += " N/A";
            report += "\ntime remaining = N/A";
        }

        //Info for CPU #2
        report += "\n\n";
        report += consumer2.getName() + "\nexec: ";
        if(consumer2.getProcessName() != null){
            report += consumer2.getProcessName();
            report += "\ntime remaining = " + consumer2.getTimeRemaining();
        }else{
            report += " N/A";
            report += "\ntime remaining = N/A";
        }

        return report;
    }

    /**
    public ObservableList<Process> getQueue() {
        return FXCollections.observableArrayList(producer.getProcessQueue());

    }*/
}
