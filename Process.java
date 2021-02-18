
import java.util.*;

public class Process {
    protected int arrival_time;
    protected String process_id;
    protected int service_time;
    protected int process_priority;


    // Constructor that takes in inputs for all the Process fields and sets them to equal the inputs.
    public Process (int a_time, String p_id, int s_time, int priority){
        arrival_time = a_time;
        process_id = p_id;
        service_time = s_time;
        process_priority = priority;
    }

    // Get and Set for the above fields.
    public int getArrival_Time(){
        return arrival_time;
    }

    public void setArrival_Time(int new_time){
        arrival_time = new_time;
    }

    public String getProcess_Id(){
        return process_id;
    }

    public void setProcess_Id(String new_id){
        process_id = new_id;
    }

    public int getService_Time(){
        return service_time;
    }

    public void setService_Time(int new_time){
        service_time = new_time;
    }

    public int getProcess_Priority(){
        return process_priority;
    }

    public void setProcess_Priority(int new_priority){
        process_priority = new_priority;
    }

    @Override
    public String toString(){
        return ("Arrival Time: " + arrival_time + ", Process Id: " + process_id + ", Service Time: " + service_time + ", Process Priority: " + process_priority + "\n");
    }
}

