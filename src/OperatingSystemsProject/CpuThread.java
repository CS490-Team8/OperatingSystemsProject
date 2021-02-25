package OperatingSystemsProject;

public class CpuThread extends Thread {
    //Class Members
    private SharedCounter myCounter;   // each thread will have its own message counter
    private String myName;   // used to distinguish threads
    private String myPrefix; // leading spaces to distinguish between threads visually
    private int    myTime;
    private Controller myController;
    private boolean stopped = false;


    private Producer producer;

    public CpuThread(Producer producer, SharedCounter c) {
        this.producer = producer;
        this.myCounter = c;
    }

    @Override
    public void run() {
        int timeIn;
        int timeOut;
        try {
            while (true) {
                Process data = producer.consume();
                System.out.println("Consumed by: " + Thread.currentThread().getName() + " data: " + data);
                timeIn = myCounter.getCount();
                timeOut = timeIn + data.getServiceTime();
                System.out.println("TimeIN: " + timeIn + " timeOUT: " +timeOut + " clock: " + myCounter.getCount() );
                this.stopped=true;
                while(this.stopped){
                    System.out.println("Entered wait queue. Clock: " + myCounter.getCount() + " :: " + timeOut);
                    if(timeOut >= myCounter.getCount()){
                        this.stopped=false;
                    }
                    Thread.sleep(1);
                }
                System.out.println("Finished");
            }
        } catch (Exception exp) {
        }
    }
}

