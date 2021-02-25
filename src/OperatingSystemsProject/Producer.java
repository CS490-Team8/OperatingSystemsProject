package OperatingSystemsProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Producer extends Thread {

    private static final int MAX_SIZE = 3;
    private ObservableList<Process> processQueue = FXCollections.observableArrayList(); //Array list representing the waiting process queue

    @Override
    public void run() {
        try {
            while (true) {
                //produce();
            }
        } catch (Exception exp) {
        }
    }

    public synchronized void produce(Process p) throws Exception {
        /*while (processQueue.size() == MAX_SIZE) {
            System.out.println("Queue limit reached. Waiting for consumer");
            wait();
            System.out.println("Producer got notification from consumer");
        }*/
        processQueue.add(p);
        System.out.println("Producer added data");
        notify();
    }

    public synchronized Process consume() throws Exception {
        notify();
        while (processQueue.isEmpty()) {
            wait();
        }
        Process data = processQueue.get(0);
        processQueue.remove(data);
        return data;
    }
}