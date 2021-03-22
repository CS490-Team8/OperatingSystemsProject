package OperatingSystemsProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Producer object which will "produce" jobs once the arrive to the system
 */
public class Producer extends Thread {
    //Class members
    //private static final int MAX_SIZE = 3;
    private ObservableList<Process> processQueue = FXCollections.observableArrayList(); //Array list representing the waiting process queue

    /**
     * Override run function
     */
    @Override
    public void run() {
        try {
            while (true) {
                //produce();
            }
        } catch (Exception exp) {
        }
    }

    /**
     * produce function adds a process to the queue
     * @param p process object to be produced
     * @throws Exception
     */
    public synchronized void produce(Process p) throws Exception {
        /*while (processQueue.size() == MAX_SIZE) {
            System.out.println("Queue limit reached. Waiting for consumer");
            wait();
            System.out.println("Producer got notification from consumer");
        }*/
        processQueue.add(p); //produce a process
        System.out.println("Producer added data"); //debug line
        notify();
    }

    /**
     * consume function
     * @return process
     * @throws Exception
     */
    public synchronized Process consume() throws Exception {
        notify();

        //wait while queue is empty
        while (processQueue.isEmpty()) {
            wait();
        }

        //if queue is not empty grab a process and consume it
        Process data = processQueue.get(0);
        processQueue.remove(data);
        return data;
    }
}