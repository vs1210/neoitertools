package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;
import net.ericaro.neoitertools.Pair;

/**
 *  <b>import</b> there is a difference in implementation from the original python:
 *  in Python the generator returned in the Pair is dependent on the next() state, meaning that when you call next() the generator is no longer available.
 *  In java, Iterator have an extra "hasNext" method that <b>cannot</b> be implemented without calling the next method, hence, the python groupBy won't work at all.
 *  Therefore we store values in the pair
 * @author eric
 *
 * @param <K>
 * @param <T>
 */
public class GroupByGenerator<K, T> implements Generator<Pair<K, Generator<T>>> {

	
	private Generator<T> generator; // the source generator
	private Lambda<T, K> keyMapper; // the mapper
	K currentKey;
	T currentValue;
	boolean itsOver = false;

	public GroupByGenerator(Generator<T> generator, Lambda<T, K> keyMapper) {
		this.generator = generator;
		this.keyMapper = keyMapper;
		try {
			next();
		} catch (NoSuchElementException e) {}
	}


	public Pair<K, Generator<T>> next() throws NoSuchElementException {
		if (itsOver) throw new NoSuchElementException();
		List<T> values = new LinkedList<T>();
		K targetKey = currentKey;
		try {
		while (currentKey == targetKey || currentKey.equals(targetKey)) { // consume all keys
				values.add(currentValue);
				currentValue = generator.next();// Exit on NSEE
				currentKey = keyMapper.map(currentValue);
		}
		} catch (NoSuchElementException e) {
			itsOver = true;
		}
		return new Pair<K, Generator<T>>(targetKey, iter(values));
	}

}
