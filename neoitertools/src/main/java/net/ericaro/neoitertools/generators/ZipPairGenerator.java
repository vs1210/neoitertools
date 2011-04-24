package net.ericaro.neoitertools.generators;

import net.ericaro.neoitertools.Generator;
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
public class ZipPairGenerator<T1, T2> implements Generator<Pair<T1, T2>> {
	private final Generator<T1> generator1;
	private final Generator<T2> generator2;

	public ZipPairGenerator(Generator<T1> iterator1, Generator<T2> iterator2) {
		this.generator1 = iterator1;
		this.generator2 = iterator2;
	}

	public Pair<T1, T2> next() {
		return new Pair<T1, T2>(generator1.next(), generator2.next());
	}
}