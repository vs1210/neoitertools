package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RepeatGeneratorTest {

	@Test
	public void testRepeatGeneratorT() {
		List<Integer> xlist = Arrays.asList(1,1,1,1);
		List<Integer> list = list( new RepeatGenerator<Integer>(1), 4);
		assert xlist.equals(list);
		
	}

	@Test
	public void testRepeatGeneratorTInt() {
		List<Integer> xlist = Arrays.asList(1,1,1,1);
		List<Integer> list = list( new RepeatGenerator<Integer>(1,4));
		assert xlist.equals(list);
	}

	
	@Test
	public void testRepeatGenerator1() {
		List<Integer> xlist = Arrays.asList(1);
		List<Integer> list = list( new RepeatGenerator<Integer>(1,1));
		assert xlist.equals(list);
	}
	
	
	
}
