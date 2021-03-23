package OperatingSystemsProject;

import java.util.ArrayList;

/**
 * Shared counter class will represent the system clock on the OS.
 * Original code written by Professor Beth
 * @author mea0010
 */
public class SharedCounter {
    //class members
    private int count; //count representing clock cycle
    private boolean doRun; //flag representing if the clock should be running
    private ArrayList <Process> finishedJobs = new ArrayList<Process>();


    /**
     * Class Constructor
     */
    public SharedCounter() {
        this.count = -1; //initialize clock at -1. This ensures we do not miss any jobs that come in at time 0
        this.doRun = false; //initialize flag to false, don't want to start running until start button is selected
    }

    /**
     * Getter for the clock running flag
     * @return boolean value
     */
    synchronized public boolean isDoRun() {
        return doRun;
    }

    /**
     * Setter for the clock running flag
     * @param doRun boolean value
     */
    synchronized public void setDoRun(boolean doRun) {
        this.doRun = doRun;
    }

    /**
     * Getter for system clock count
     * @return integer
     */
    synchronized public int  getCount() {
        return count;
    }

    /**
     * Update system clock count
     * @return integer
     */
    synchronized public int updateCount() {
        count++;
        return count;
    }

    /**
     * add finished job to the finished list
     * @param p Process object representing the finished job
     */
    synchronized public void addFinishedProcess(Process p){
        this.finishedJobs.add(p);
    }

    /**
     * Function that outputs a string containing the information of all
     * finished jobs
     * @return String
     */
    synchronized public String printFinishedJobs(){
        String jobStatus = "\n"; //start with a new line

        //loop entire list and add information for each process in list
        for (Process p: finishedJobs) {
            jobStatus += p.getProcessId() +"\t"+p.getArrivalTime()+"\t"+p.getServiceTime()+"\t"+p.getFinishTime()+"\t"+ p.getTurnaround()+"\t"+p.getnTATTime()+"\n";
        }

        //Add throughput information
        jobStatus +="\n";
        if(count > 0) { //make sure we do not divide by zero
            jobStatus += "Current Throughput: " + ((double)finishedJobs.size() / (double)count) + " process/unit of time";
        }else{
            jobStatus += "Current Throughput: N/A";
        }

        return jobStatus; //return finalized screen
    }
}
