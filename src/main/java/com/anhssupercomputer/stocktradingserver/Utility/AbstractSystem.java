package com.anhssupercomputer.stocktradingserver.Utility;

/**
 * Iterative control structure to be used on all systems that run in parallel
 */
public abstract class AbstractSystem extends Thread {

    private final int periodMS;
    private boolean initialized = false;
    private boolean terminated = false;

    public AbstractSystem(int period) {
        this.periodMS = period;
    }

    /**
     * Signals that initialization is complete
     */
    protected void setInitialized() {
        initialized = true;
    }

    /**
     * Gets the iteration period of the system
     */
    public int getPeriod() {
        return periodMS;
    }

    /**
     * Returns initialization state
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Stops Looping actions for controlLoop, resulting in the thread dying
     */
    public void terminate() {
        terminated = true;
    }

    /**
     * Includes loop control when being run by a thread
     * Will stop loop and end thread when wishing to terminate
     */
    @Override
    public void run() {
        long startTimeMS;

        while (!terminated) {

            startTimeMS = System.currentTimeMillis();

            // Main code to be executed
            if (initialized)
                controlLoop();

            long executionTimeMS = System.currentTimeMillis() - startTimeMS;

            // Checks that code execution does not run over
            if (executionTimeMS > periodMS) {
                System.out.println("WARNING: " + this + " has exceeded allowed iteration period.");
            } else {
                try {
                    // Sleep for the remaining time in the iteration
                    sleep((long) periodMS - executionTimeMS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This code block will run on a specified period
     */
    protected abstract void controlLoop();
}
