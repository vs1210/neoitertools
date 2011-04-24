package net.ericaro.neoitertools.iterators;

import java.security.InvalidParameterException;
import java.util.Iterator;


/** This is a versatile Iterator containing arithmetic
	 * progressions. It is most often used in for loops. The full form returns
	 * an iterator over Integers [start, start + step, start + 2 * step, ...].
	 * <ul>
	 * <li>If step is positive, the last element is the largest start + i * step
	 * less than stop;</li>
	 * <li>if step is negative, the last element is the smallest start + i *
	 * step greater than stop.</li>
	 * <li>step must not be zero (or else InvalidParameterException is raised).</li>
	 * Example:
 * 
 * @author eric
 *
 */
public class RangeIterator implements Iterator<Integer> {
	private final int step;
	private final int end;
	int i;

	public RangeIterator(int start, int end) {
		this(start, 1, end);
	}
	public RangeIterator(int end) {
		this(0, 1, end);
	}
	public RangeIterator(int start, int step, int end) {
		if (step == 0)
			throw new InvalidParameterException("step must be != 0");
		this.step = step;
		this.end = end;
		i = start;
	}

	public boolean hasNext() {
		return step > 0 ? i < end : i > end;
	}

	public Integer next() {
		try {
			return i;
		} finally {
			i += step;
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}