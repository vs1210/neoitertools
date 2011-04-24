package net.ericaro.neoitertools.generators;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/**
 * This is a versatile Generator containing arithmetic progressions. It is most
 * often used in for loops. The full form returns an iterator over Integers
 * [start, start + step, start + 2 * step, ...].
 * <ul>
 * <li>If step is positive, the last element is the largest start + i * step
 * less than stop;</li>
 * <li>if step is negative, the last element is the smallest start + i * step
 * greater than stop.</li>
 * <li>step must not be zero (or else InvalidParameterException is raised).</li>
 * Example:
 * 
 * @author eric
 * 
 */
public class RangeGenerator implements Generator<Integer> {
	private final int step;
	private final int end;
	int i;

	public RangeGenerator(int start, int end) {
		this(start, end, 1);
	}

	public RangeGenerator(int end) {
		this(0,  end, 1);
	}

	public RangeGenerator(int start, int end, int step) {
		if (step == 0)
			throw new InvalidParameterException("step must be != 0");
		this.step = step;
		this.end = end;
		i = start;
	}

	public Integer next() {
		if (step > 0 ? i >= end : i <= end)
			throw new NoSuchElementException();
		int j = i;
		i += step;
		return j;
	}

}