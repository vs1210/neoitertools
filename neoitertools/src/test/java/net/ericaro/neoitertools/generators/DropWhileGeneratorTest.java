package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Lambda;

import org.junit.Test;

public class DropWhileGeneratorTest {

	//# dropwhile(lambda x: x<5, [1,4,6,4,1]) --> 6 4 1
	@Test
	public void testDropWhileGenerator() {
		List<Integer> xlist = Arrays.asList(6,4,1);
		
		List<Integer> list = list(new DropWhileGenerator<Integer>(new Lambda<Integer, Boolean>() {

			public Boolean map(Integer x) {
				return x<5;
			}
		}, iter(Arrays.asList(1,4,6,4,1))));
		assert xlist.equals(list);
	}

}
