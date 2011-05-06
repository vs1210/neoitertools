package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Pair;

import org.junit.Test;

public class ZipPairGeneratorTest {

	/*x = [1, 2, 3]
>>> y = [4, 5, 6]
>>> zipped = zip(x, y)
>>> zipped
[(1, 4), (2, 5), (3, 6)]
	 * 
	 */
	@Test
	public void testZipPairGenerator() {
		
		List<Integer> x = Arrays.asList(1,2,3);
		List<Integer> y = Arrays.asList(4,5,6);
		List<Pair<Integer, Integer>> xlist = Arrays.asList(new Pair<Integer,Integer>(1,4) ,new Pair<Integer,Integer>(2,5) ,new Pair<Integer,Integer>(3,6)  );
		
		List<Pair<Integer, Integer>> list = list(new ZipPairGenerator<Integer, Integer>(iter(x), iter(y) ));
		assert xlist.equals(list);
	}

}
