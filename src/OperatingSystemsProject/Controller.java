package OperatingSystemsProject;

import javafx.beans.property.SimpleDoubleProperty;
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
 * Controller that spins up threads and controls the GUI
 */
public class Controller implements Initializable {
    //Scene Components
    @FXML    private Label systemState;
    @FXML    private Button start;
    @FXML    private Button stepButton;
    @FXML    private Button pauseButton;
    @FXML    private TextField timeInput;
    @FXML    private TextArea systemReportDisplay;
    @FXML    private TextArea CPUDisplay;
    @FXML    private TableView<Process> pTable;
    @FXML    public TableColumn<Process, SimpleStringProperty> colName;
    @FXML    public TableColumn<Process, SimpleIntegerProperty> colTime;
    @FXML    private TableView<Process> reportTable;
    @FXML    private TableColumn<Process, SimpleStringProperty> processName;
    @FXML    private TableColumn<Process, SimpleIntegerProperty> arrivalTime;
    @FXML    private TableColumn<Process, SimpleIntegerProperty> serviceTime;
    @FXML    private TableColumn<Process, SimpleIntegerProperty> finishTime;
    @FXML    private TableColumn<Process, SimpleIntegerProperty> tat;
    @FXML    private TableColumn<Process, SimpleDoubleProperty> ntat;


    //class members
    private SharedCounter globalCounter; //this SharedCounter will keep track of clock cycle time.
    private ClockThread systemClock; //Clock thread object that will be used to simulate the system clock
    private int timeUnit; //variable that holds the user value of time unit from the GUI
    private Thread thread1; //thread to run the other threads
    public ArrayList<Process> processesReceived = new ArrayList<Process>();
    private ObservableList<Process> processQueue = FXCollections.observableArrayList(); //Array list representing the waiting process queue
    private ObservableList<Process> finishedJobs = FXCollections.observableArrayList(); //list of finished jobs

    /**
     * Override for abstract method initialize
     * This function is called when the GUI launches, it is responsible for initializing the GUI and necessary variables.
     *
     * @param location  part of the abstract method
     * @param resources part of the abstract method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Scene Initialized"); //debug line
        //------------------------------------------------------------
        //Read inputs from file
        // **Able to choose to let the user declare a file path or use
        //   the default one provided in the project structure
        //------------------------------------------------------------
        //ProcessParser readInput = new ProcessParser(); //use this line to let the user enter the file path
        ProcessParser readInput = new ProcessParser("src/OperatingSystemsProject/input.txt"); //use this line if file path is known

        this.processesReceived = readInput.getProcesses(); //store processes
        this.stepButton.setVisible(false); //not using this button, was for initial debugging use only

        //------------------------------------------------------------
        //initialize variables
        //------------------------------------------------------------
        this.timeUnit = Integer.parseInt(this.timeInput.getText());
        globalCounter = new SharedCounter(); //system wide clock/counter
        this.systemReportDisplay.setText(getSystemReport());

        this.finishedJobs = FXCollections.observableArrayList(globalCounter.getFinishedJobs()); //store processes
        this.pauseButton.setDisable(true); //disable pause button before program has started
        //------------------------------------------------------------
        //initialize table values
        //------------------------------------------------------------
        colName.setCellValueFactory(new PropertyValueFactory<Process, SimpleStringProperty>("ProcessId"));
        colTime.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("ServiceTime"));
        pTable.setItems(processQueue);

        processName.setCellValueFactory(new PropertyValueFactory<Process, SimpleStringProperty>("ProcessId"));
        arrivalTime.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("ArrivalTime"));
        serviceTime.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("ServiceTime"));
        finishTime.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("FinishTime"));
        tat.setCellValueFactory(new PropertyValueFactory<Process, SimpleIntegerProperty>("TurnaroundTime"));
        ntat.setCellValueFactory(new PropertyValueFactory<Process, SimpleDoubleProperty>("NormalizedTATTime"));
        reportTable.setItems(this.finishedJobs);

        this.add2queue(); //check to see if any new items need to be added to the queue

        //------------------------------------------------------------
        //spin up threads
        //------------------------------------------------------------
        systemClock = new ClockThread("M1", globalCounter, "", this.timeUnit, this); // create the system clock thread

        this.CPUDisplay.setText(systemClock.getCPUReport()); //display the initial CPU report

        this.thread1 = new Thread(systemClock);  // create a thread that can run the other threads
        thread1.start();
    }

    /**
     * This function is generates the system report. This will be displayed on the GUI.
     * @return String containing the system report status
     */
    private String getSystemReport() {
        String report = "Clock Cycle: " + this.globalCounter.getCount();
        report += "\nName\tArrival Time\tService Time\tFinish Time\n";
        report += globalCounter.printFinishedJobs();
        return report;
    }

    /**
     * This function starts the system clock once the user presses start
     * @param actionEvent mouse click event from the user
     */
    public void start(ActionEvent actionEvent) {
        //System.out.println("start");//debugging line
        this.systemClock.setMyTime(Integer.parseInt(this.timeInput.getText()));
        this.globalCounter.setDoRun(true); //make sure running flag is set to true
        this.systemState.setText("System Running"); //update system state text

        //Disable or enable certain input fields
        this.timeInput.setDisable(true); //disable time input
        this.start.setDisable(true); //disable start button while program is running
        this.pauseButton.setDisable(false); //enable pause button while program is running
    }

    /**
     * Pause function will pause the system clock when the user hits the pause button
     * @param actionEvent mouse click event from the user
     */
    public void pause(ActionEvent actionEvent) {
        //System.out.println("pausing"); //debug line
        this.timeInput.setDisable(false); //enable time input
        this.start.setDisable(false); //enable start button while program is running
        this.pauseButton.setDisable(true); //disable pause button while program is running

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
    private void add2queue() {
        for (Process p : this.processesReceived) {
            //check process arrival time vs. system clock time
            if (this.globalCounter.getCount() == p.getArrivalTime()) {
                System.out.println(p); //debug line
                processQueue.add(p); //add process to queue
            }
        }
    }

    /**
     * removeFromQueue will removed a specified process from the waiting queue
     * @param removalProcess Process object representing the process to be removed
     */
    public void removeFromQueue(Process removalProcess){
        //loop through stored processes and remove if fount
        for (Process p : this.processesReceived) {
            if (removalProcess.getProcessId().equals(p.getProcessId())){
                processQueue.remove(p);
            }
        }
    }

    /**
     * Increase Clock Cycle will perform the necessary operations each clock cycle
     */
    public void increaseClockCycle() {
        this.systemReportDisplay.setText(getSystemReport()); //update status report text
        this.CPUDisplay.setText(systemClock.getCPUReport()); //update CPU report text
        this.add2queue(); //check to see if any new items need to be added to the queue
        this.finishedJobs = FXCollections.observableArrayList(globalCounter.getFinishedJobs()); //store processes
        reportTable.setItems(this.finishedJobs);
        //System.out.println("Updated finished log" + finishedJobs); //debug line

    }
}

