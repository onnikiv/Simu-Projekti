package simu.framework;

/**
 * The Trace class provides methods to output trace messages.
 */

public class Trace {

	/**
	 * The Level enum represents the trace levels.
	 */

	public enum Level{INFO, WAR, ERR}

	/**
	 * The traceLevel variable holds the current trace level.
	 */

	private static Level traceLevel;

	/**
	 * Sets the trace level.
	 *
	 * @param lvl the trace level
	 */

	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}

	/**
	 * Outputs a trace message.
	 *
	 * @param lvl the trace level
	 * @param txt the message to output
	 */

	public static void out(Level lvl, String txt){
		if (lvl.ordinal() >= traceLevel.ordinal()){
			System.out.println(txt);
		}
	}



}