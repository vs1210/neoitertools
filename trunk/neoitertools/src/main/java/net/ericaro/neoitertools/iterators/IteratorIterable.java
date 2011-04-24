package net.ericaro.neoitertools.iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/** Thread safe class that provides iterators that takes values from the same single source iterator.
 * 
 * It is possible to get as many iterators as needed. They are all thread safe, and as memory efficient as possible. It means that
 * every Iterator has a buffer, and that this buffer is kept as small as possible. Of course reading all the values from one iterator will
 * cause all the others buffer to be filled. 
 * 
 * If the source iterator as already been consumed by previously return iterators, the creating new iterators will *not* start at the beginning
 * but at the current position.
 * 
 * @author eric
 *
 */
public class IteratorIterable<T> implements Iterable<T>{

	public class TeeIterator implements Iterator<T> {
		
		Deque<T> buffer = new LinkedList<T>();

		public boolean hasNext() {
			synchronized (lock) {
				return !buffer.isEmpty() || source.hasNext();
			}
		}

		public T next() {
			synchronized (lock) {
				if (buffer.isEmpty())
					tee(); // tee guarantee that all the buffer will gain at least one value
				return buffer.pollLast();
			}
		}

		public void remove() { throw new UnsupportedOperationException();		}

	}

	private Iterator<T> source;
	private Deque<TeeIterator> tees = new LinkedList<TeeIterator>();
	Object lock;

	public IteratorIterable(Iterator<T> source) {
		this.source = source;
		lock = new Object();
	}

	/** peek one value from the source, and push it in every buffer.
	 * 
	 */
	protected void tee(){
		synchronized (lock) {
			T t = source.next();
			for (TeeIterator tee : tees)
				tee.buffer.push(t);
		}
	}
	
	/** every iterator returned will start iterating over the source at its current position, this depend on the current state of every iterator.
	 * 
	 */
	public Iterator<T> iterator() {
		synchronized (lock) {
			TeeIterator i = new TeeIterator();
			tees.push(i);
			return i;
		}
	}
}

