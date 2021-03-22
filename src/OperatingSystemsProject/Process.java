package OperatingSystemsProject;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Process class will store the details of individual processes
 */
public class Process {
    private  SimpleIntegerProperty arrivalTime; //arrival time
    private  SimpleStringProperty processId; //unique name of process
    private  SimpleIntegerProperty serviceTime; //duration of service time
    private  SimpleIntegerProperty processPriority; //priority in queue
    private  SimpleIntegerProperty finishTime; //clock cycle when process finished executing
    private  SimpleIntegerProperty turnaroundTime; //turnaround time: the elapsed time from when a process arrives in the system to when it finished
    private SimpleDoubleProperty normalizedTATTime; //normalized turnaround time: the TAT (turnaround time) divided by the service time.


    /**
     * Constructor that takes in inputs for all the Process fields and sets them to equal the inputs.
     * @param a_time int - arrival time
     * @param p_id String - process ID
     * @param s_time int - service time
     * @param priority int - process priority
     */
    public Process (Integer a_time, String p_id, Integer s_time, Integer priority){
        //set class members from arguments
        this.arrivalTime = new SimpleIntegerProperty(a_time);
        this.processId =  new SimpleStringProperty(p_id);
        this.serviceTime = new SimpleIntegerProperty(s_time);
        this.processPriority = new SimpleIntegerProperty(priority);
    }

    /**
     * Overload constructor for a finished process that includes statistical data
     * @param a_time int - arrival time
     * @param p_id String - process ID
     * @param s_time int - service time
     * @param priority int - process priority
     * @param finishTime int - clock cycle when process was completed
     */
    public Process (Integer a_time, String p_id, Integer s_time, Integer priority, Integer finishTime){
        //set class members from arguments
        this.arrivalTime = new SimpleIntegerProperty(a_time);
        this.processId =  new SimpleStringProperty(p_id);
        this.serviceTime = new SimpleIntegerProperty(s_time);
        this.processPriority = new SimpleIntegerProperty(priority);
        this.finishTime = new SimpleIntegerProperty(finishTime);

        //calculate additional values
        this.turnaroundTime = new SimpleIntegerProperty(finishTime-a_time);
        this.normalizedTATTime = new SimpleDoubleProperty((finishTime-a_time)/s_time);
    }

    /**
     * Getter for arrival time
     * @return integer of arrival time
     */
    public int getArrivalTime(){
        return arrivalTime.get();
    }

    /**
     * Setter for arrival time
     * @param new_time int to set the time
     */
    public void setArrivalTime(int new_time){
        this.arrivalTime = new SimpleIntegerProperty(new_time);
    }

    /**
     * Getter for process id
     * @return String
     */
    public String getProcessId(){
        return processId.get();
    }

    /**
     * Setter for process ID
     * @param new_id String
     */
    public void setProcessId(String new_id){
        this.processId = new SimpleStringProperty(new_id);
    }

    /**
     * Getter for service time
     * @return int
     */
    public int getServiceTime(){
        return serviceTime.get();
    }

    /**
     * Setter for service time
     * @param new_time int
     */
    public void setServiceTime(int new_time){
        this.serviceTime = new SimpleIntegerProperty(new_time);
    }

    /**
     * Getter for process priority
     * @return int
     */
    public int getProcessPriority(){
        return processPriority.get();
    }

    /**
     * Setter for process priority
     * @param new_priority integer
     */
    public void setProcessPriority(int new_priority){
        this.processPriority = new SimpleIntegerProperty(new_priority);
    }

    /**
     * Override function for debugging
     * @return String containing the contents of the process
     */
    @Override
    public String toString(){
        return ("Arrival Time: " + this.arrivalTime + ", Process Id: " + this.processId + ", Service Time: " + this.serviceTime + ", Process Priority: " + this.processPriority + "\n");
    }

    /**
     * getter for finish time
     * @return int
     */
    public int getFinishTime() {
        return finishTime.get();
    }

    /**
     * getter for TAT (turnaround time)
     * @return int
     */
    public int getTurnaround(){
        return turnaroundTime.get();
    }

    /**
     * getter for normalized turnaround time
     * @return double
     */
    public double getnTATTime(){
        return normalizedTATTime.get();
    }

    //Don't believe we need these functions
    /*
    public SimpleIntegerProperty finishTimeProperty() {
        return finishTime;
    }


    public void setFinishTime(int finishTime) {
        this.finishTime.set(finishTime);
    }*/
}
