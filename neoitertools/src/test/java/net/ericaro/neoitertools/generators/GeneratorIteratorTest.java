package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.in;
import static net.ericaro.neoitertools.Itertools.range;
import static org.junit.Assert.*;

import org.junit.Test;

public class GeneratorIteratorTest {

	@Test
	public void testGeneratorIterator() {
		int i=0;
		for (int k: in(range(10) ))
			i++;
		assert i == 10;
		
	}

}
