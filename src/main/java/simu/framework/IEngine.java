package simu.framework;

/**
 * The IEngine interface provides methods to set and get the simulation time and delay.
 */

public interface IEngine {

    /**
     * Sets the simulation time of the engine.
     *
     * @param time the new simulation time
     */

    public void setSimulationTime(double time);

    /**
     * Sets the delay between simulation steps.
     *
     * @param time the delay in milliseconds
     */

    public void setDelay(long time);

    /**
     * Gets the delay between simulation steps.
     *
     * @return the delay in milliseconds
     */

    public long getDelay();

}
