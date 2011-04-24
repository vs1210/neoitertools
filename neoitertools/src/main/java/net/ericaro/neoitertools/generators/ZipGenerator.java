package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.map;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;
import net.ericaro.neoitertools.Pair;

/**
 * an {@link Generator} of Pairs, where the i-th couple contains the i-th
 * element from each of the argument Generator. The returned Generator is
 * truncated in length to the length of the shortest argument sequence.
 * 
 * Due to static typing of java, it is not possible to provide a generic length
 * of Generator and at the same time provide mixed-type tuples.
 * 
 * @author eric
 * 
 */
public class ZipGenerator<T> implements Generator<List<T>> {
	private List<Generator<T>> generators;
	private Lambda<Generator<T>, T> next;

	public ZipGenerator(List<Generator<T>> generators) {
		this.generators = generators;
		next = new Lambda<Generator<T>, T>() {

			public T map(Generator<T> arg) {
				return arg.next();
			}
		};
	}

	public List<T> next() {
		List<T> list = list( map(next, iter(generators)) );
		if (list.size() != generators.size()) throw new NoSuchElementException() ;
		return list;
	}
}