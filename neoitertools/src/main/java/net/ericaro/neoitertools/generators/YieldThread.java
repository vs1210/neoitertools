package net.ericaro.neoitertools.generators;

import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Yield;

/** Yield protocol implementation class. A thread that :
 * <ul>
 * <li>handle the synchronization between the thread calling the next method, and this thread running the generator method</li>
 * <li>stop itself when the generator is garbage collected</li>
 * </ul>
 * 
 * @author eric
 *
 * @see <a href="http://code.google.com/p/neoitertools/wiki/YieldThread">YieldThread's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class YieldThread<U, V> extends Thread {

	private V nextValue; // place to store in an out value for exchange between
	// threads
	private U nextInput;
	boolean itsGeneratorTurn = true;

	private Object monitor = new Object(); // kept private so that there can be
											// only one thread at a time
											// monitoring this object

	private boolean running = true;
	private boolean started = false;
	private WeakReference<Object> generatorReference;
	private Yield<U,V> generable;

	public YieldThread(Object generator, Yield<U, V> generable) {
		super("yield-thread");
		generatorReference = new WeakReference<Object>(generator); // a
																			// weak
																			// ref
																			// is
		// kept to the
		// generator so
		// that the
		// thread can
		// stop when the
		// generator is
		// no longer
		// used
		this.generable = generable;
	}

	public void run() {
		try {
			started = true;
			generable.generate();

		} finally {
			synchronized (monitor) {
				// the yield thread has ended wake up the calling one (if needed
				running = false;
				monitor.notifyAll(); // in case
			}
		}
	}

	/**
	 * actual next method, pause the calling thread, and wake up the yielding
	 * thread
	 * 
	 * @param nextInput
	 * @return the value passed by the generator to the yield function
	 */
	V next(U nextInput) {

		synchronized (monitor) {
			if (!running)
				throw new NoSuchElementException();

			this.nextInput = nextInput; // push the value that will be returned
										// by yield statement

			itsGeneratorTurn = true;

			if (!started) {
				start();
				started = true;
			}

			monitor.notify();
			waitForGenerator(); // exchange zone with yield thread

			return nextValue;
		}

	}

	/**
	 * This method is tighly linked with the <code>next</code> method. the
	 * <code>next</code> method will return <code>o</code>. On the other hand,
	 * this method returns the input parameter of the <code>next</code> method.
	 * 
	 * @param nextValue
	 * @return
	 */
	public U yield(V nextValue) {

		synchronized (monitor) {
			// put the Out in its place
			this.nextValue = nextValue;
			// notify the callee
			monitor.notify();
			// wait in the calling thread, if I'm still running, if it's not my
			// turn, and if the generator object is still in use.
			itsGeneratorTurn = false; // looks weird but in fact stuff happens
										// while waiting, and in particular this
										// boolean changes
			waitForNext();
			return nextInput;
		}
	}

	private void waitForNext() throws ThreadDeath {
		while (running && !itsGeneratorTurn && generatorReference.get() != null )
			try {
				monitor.wait(100);
			} catch (InterruptedException e) {
				throw new ThreadDeath();
			}
		if (!running || generatorReference.get() == null)
			throw new ThreadDeath();
	}

	/**
	 * called wait the callee thread for the yield thread to return. threads
	 * 
	 */
	private void waitForGenerator() {
		while (running && itsGeneratorTurn)
			try {
				// wait in the yield thread, every second check in case it's
				// dead
				monitor.wait(1000);
			} catch (InterruptedException e) {
				running = false;
				monitor.notifyAll();
			}
		if (!running)
			throw new NoSuchElementException();
	}

	
}
