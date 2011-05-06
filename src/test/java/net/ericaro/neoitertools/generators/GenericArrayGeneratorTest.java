package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GenericArrayGeneratorTest {

	@Test
	public void testGenericArrayGenerator() {
		
		List<Integer> xlist = Arrays.asList(1,2,3);

		GenericArrayGenerator<Integer> g = new GenericArrayGenerator<Integer>(1,2,3);
		List<Integer> list = list(g);
		System.out.println(xlist);
		System.out.println(list);
		assert xlist.equals(list);
	}

	

}
