package OperatingSystemsProject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller spins up threads and controls the GUI
 */
public class Controller implements Initializable {
    //Scene Components
    @FXML private Label systemState;
    @FXML private Button start;
    @FXML private Button stepButton;
    @FXML private Button pauseButton;
    @FXML private TextField timeInput;
    @FXML private TextArea systemReportDisplay;
    @FXML private TableView<Process> pTable;
    @FXML public TableColumn<Process, SimpleStringProperty> colName;
    @FXML public TableColumn<Process, SimpleIntegerProperty> colTime;

    //class members
    private SharedCounter globalCounter; //this SharedCounter will keep track of clock cycle time.
    private ClockThread systemClock;
    private int timeUnit; //variable that holds the user value of time unit from the GUI
    private Thread thread1;
    public ArrayList<Process> processesReceived = new ArrayList<Process>(); //List of all incoming processes read in from input file
    private ObservableList<Process> processQueue = FXCollections.observableArrayList(); //Array list representing the waiting process queue

    /**
     * Override for abstract method initialize
     * This function is called when the GUI launches, it is responsible for initializing the GUI and necessary variables.
     * @param location part of the abstract method
     * @param resources part of the abstract method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Scene Initialized"); //debug line
        //------------------------------------------------------------
        //Read inputs from file
        //------------------------------------------------------------
        //ProcessParser readInput = new ProcessParser(); //use this line to let the user enter the file path
        ProcessParser readInput = new ProcessParser("src/OperatingSystemsProject/input.txt"); //use this line if file path is known

        this.processesReceived = readInput.getProcesses(); //store processes

        //------------------------------------------------------------
        //initialize variables
        //------------------------------------------------------------
        this.timeUnit= Integer.parseInt(this.timeInput.getText());
        globalCounter = new SharedCounter(); //system wide clock/counter
        this.systemReportDisplay.setText(getSystemReport());
        //------------------------------------------------------------
        //initialize table values
        //------------------------------------------------------------
        colName.setCellValueFactory(new PropertyValueFactory<Process, SimpleStringProperty>("ProcessId"));
        colTime.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("ServiceTime"));
        pTable.setItems(processQueue);

        this.add2queue(); //check to see if any new items need to be added to the queue

        //------------------------------------------------------------
        //spin up threads
        //------------------------------------------------------------
        systemClock = new ClockThread("M1", globalCounter, "",this.timeUnit, this); // create the threads
        this.thread1 = new Thread(systemClock);  // create a thread that can run the threads
        thread1.start();
    }

    /**
     * This function is generates the system report. This will be displayed on the GUI.
     * @return String containing the system report status
     */
    private String getSystemReport(){
        String report = "Clock Cycle: " + this.globalCounter.getCount();
        return report;
    }

    /**
     * This function starts the system clock once the user presses start
     * @param actionEvent mouse click event from the user
     */
    public void start(ActionEvent actionEvent)  {
        //System.out.println("start");//debugging line
        this.globalCounter.setDoRun(true); //make sure running flag is set to true
        this.systemState.setText("System Running"); //update system state text

       // msgThread1 = new ClockThread("M1", globalCounter, "",this.timeUnit, this); // create the threads
      //  this.thread1 = new Thread(msgThread1);  // create a thread that can run the threads
        //thread1.start();
    }

    /**
     * Pause function will pause the system clock when the user hits the pause button
     * @param actionEvent mouse click event from the user
     */
    public void pause(ActionEvent actionEvent) {
        System.out.println("pausing"); //debug line
        this.globalCounter.setDoRun(false); //set the flag in the shared counter to indicate to stop the system clock
        this.systemState.setText("System Paused"); //update text on GUI
    }

    /**
     * This function steps through the clock cycles manually everytime the user hits the button.
     * @param actionEvent mouse click event from the user
     */
    public void step(ActionEvent actionEvent) {
        System.out.println("stepping"); //debug line
        increaseClockCycle();
    }

    /**
     * add2queue compares process entry time to clock cycle time to see if a job as "arrived" at the OS
     */
    private void add2queue(){
        for(Process p: this.processesReceived){
            //check process arrival time vs. system clock time
            if (this.globalCounter.getCount() == p.getArrivalTime()){
                System.out.println(p); //debug line
                processQueue.add(p); //add process to queue
            }
        }
    }

    /**
     * Increase Clock Cycle will perform the necessary operations each clock cycle
     */
    public void increaseClockCycle(){
        this.systemReportDisplay.setText(getSystemReport()); //update status report text
        this.add2queue(); //check to see if any new items need to be added to the queue
    }
}