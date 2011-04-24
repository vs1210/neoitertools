package net.ericaro.neoitertools.generators;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/**
 * Make a Generator returning elements from the Generator and saving a copy of
 * each. When the Generator is exhausted, return elements from the saved copy.
 * Repeats indefinitely.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class CycleGenerator<T> implements Generator<T> {

	private Generator<T> generator;
	private List<T> list;
	private boolean isEmpty;
	private boolean firstPass;

	public CycleGenerator(Generator<T> generator) {
		this.generator = generator;
		this.list = new LinkedList<T>();
		firstPass = true;
	}


	public T next() {
		if (firstPass)
			return firstNexts();
		else
			return otherNexts();
	}

	/**
	 * until the "source" is not exhausted, "next" goes this way
	 * 
	 * @return
	 */
	private T firstNexts() {
		try{
			T t = generator.next();
			list.add(t);
			return t;
		}catch( NoSuchElementException e){
			// we reached the end of the first nexts, calling back the top level
			firstPass = false;
			// method to go to the right method now
			return next();
		}
	}

	/**
	 * the initial iterator is exhausted, use the list one for now on.
	 * 
	 * @return
	 */
	private T otherNexts() {
		// the field "iterator" for now on, will be an iterator over the list
		try{
			return generator.next();
		}catch( NoSuchElementException e){
			generator = new IteratorGenerator<T>(list.iterator());
			if (list.isEmpty()) throw new NoSuchElementException() ;//avoid infinite recursion
			return next();
		}
	}

}
