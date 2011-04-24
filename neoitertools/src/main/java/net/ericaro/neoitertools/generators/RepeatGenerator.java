package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/**
 * a Generator that returns object over and over again. Runs indefinitely Used
 * as argument to imap() for invariant function parameters. Also used with
 * izip() to create constant fields in a tuple record.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class RepeatGenerator<T> implements Generator<T> {

	private T object;
	private int times;

	public RepeatGenerator(T object) {
		this.object = object;
		this.times = Integer.MAX_VALUE;
	}

	public RepeatGenerator(T object, int times) {
		this(object);
		assert times >= 0 : "times must be >=0";
		this.times = times;
	}


	public T next() {
		if (times <= 0)  throw new NoSuchElementException();
		if (times != Integer.MAX_VALUE) // max value is interpreted as infinity
			times--;
		return object;
	}

}
