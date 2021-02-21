package OperatingSystemsProject;

/**
 * Shared counter class will represent the system clock on the OS.
 * Original code written by Professor Beth
 * @author mea0010
 */
public class SharedCounter {
    //class members
    private int count; //count representing clock cycle
    private boolean doRun; //flag representing if the clock should be running

    /**
     * Class Constructor
     */
    public SharedCounter() {
        this.count = 0; //initialize clock at 0
        this.doRun = true; //initialize flag to true
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
        return count; }
}
