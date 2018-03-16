package UDPTCP;

public class StopWatch {

    private long elapsedTime;
    private long startTime;
    private boolean isRunning;

    /**
     * Constructor
     */

    public StopWatch(){
        reset();
    }

    /**
     * Starts the timer
     */

    public void start(){
        if(isRunning){return;}
        isRunning = true;
        startTime = System.nanoTime();
    }

    /**
     * Stops the timer
     */

    public void stop(){
        if(isRunning){
            return;
        }
        isRunning = false;
        long endTime = System.nanoTime();
        elapsedTime = elapsedTime + endTime - startTime;
    }

    /**
     *
     * @return elapsed time
     */

    public long getElapsedTime(){
        if(isRunning){
            long endTime = System.nanoTime();
            return elapsedTime + endTime - startTime;
        }
        else{
            return elapsedTime;
        }
    }

    /**
     * Resets the timer
     */

    public void reset(){
        elapsedTime = 0;
        isRunning = false;
    }
}
