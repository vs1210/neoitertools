package net.ericaro.neoitertools.generators;

import java.util.Deque;
import java.util.LinkedList;

import net.ericaro.neoitertools.Generator;

/** Creates n independent iterators from a single source sequence.
 * 
 * @author eric
 *
 * @see <a href="http://code.google.com/p/neoitertools/wiki/TeeGeneratorFactory">TeeGeneratorFactory's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class TeeGeneratorFactory<T>  {

	public class TeeGenerator implements Generator<T> {
		
		Deque<T> buffer = new LinkedList<T>();

		
		public T next() {
			synchronized (lock) {
				if (buffer.isEmpty())
					tee(); // tee guarantee that all the buffer will gain at least one value
				return buffer.pollLast();
			}
		}
	}

	private Generator<T> source;
	private Deque<TeeGenerator> tees = new LinkedList<TeeGenerator>();
	Object lock;
	boolean started = false;

	public TeeGeneratorFactory(Generator<T> source) {
		this.source = source;
		lock = new Object();
	}

	/** peek one value from the source, and push it in every buffer.
	 * 
	 */
	protected void tee(){
		synchronized (lock) {
			started = true;
			T t = source.next();
			for (TeeGenerator tee : tees)
				tee.buffer.push(t);
		}
	}
	
	/** every iterator returned will start iterating over the source at its current position, this depend on the current state of every iterator.
	 * 
	 */
	public Generator<T> newInstance() {
		if (started) throw new IllegalStateException("the factory cannot be reused once the Generators have started to generate");
		synchronized (lock) {
			TeeGenerator i = new TeeGenerator();
			tees.push(i);
			return i;
		}
	}
}
