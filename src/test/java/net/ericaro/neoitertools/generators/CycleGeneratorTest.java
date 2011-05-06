package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CycleGeneratorTest {

	@Test
	public void testCycleGenerator() {
		List<Character> xlist = Arrays.asList('A','B','C','D','A','B','C','D','A','B','C','D','A');
		
		CycleGenerator<Character> g = new CycleGenerator<Character>(new CharSequenceGenerator("ABCD"));
		List<Character> list = list(g, xlist.size());
		System.out.println(list);
		assert xlist.equals(list);
		
	}

}
