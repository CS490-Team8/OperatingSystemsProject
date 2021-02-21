package OperatingSystemsProject;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Process class will store the details of individual processes
 */
public class Process {
    private  SimpleIntegerProperty arrivalTime;
    private  SimpleStringProperty processId;
    private  SimpleIntegerProperty serviceTime;
    private  SimpleIntegerProperty processPriority;

    /**
     * Constructor that takes in inputs for all the Process fields and sets them to equal the inputs.
     * @param a_time int - arrival time
     * @param p_id String - process ID
     * @param s_time int - service time
     * @param priority int - process priority
     */
    public Process (Integer a_time, String p_id, Integer s_time, Integer priority){
        this.arrivalTime = new SimpleIntegerProperty(a_time);
        this.processId =  new SimpleStringProperty(p_id);
        this.serviceTime = new SimpleIntegerProperty(s_time);
        this.processPriority = new SimpleIntegerProperty(priority);
    }

    /**
     * Getter for arrival time
     * @return int
     */
    public int getArrivalTime(){
        return arrivalTime.get();
    }

    /**
     * Setter for arrival time
     * @param new_time int
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
}
