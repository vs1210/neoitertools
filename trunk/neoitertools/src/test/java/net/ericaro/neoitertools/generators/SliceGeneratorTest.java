package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class SliceGeneratorTest {

	/*
	 * # islice('ABCDEFG', 2) --> A B
    # islice('ABCDEFG', 2, 4) --> C D
    # islice('ABCDEFG', 2, None) --> C D E F G
    # islice('ABCDEFG', 0, None, 2) --> A C E G
	 */
	
	@Test public void testEnd(){
		
		List<Character> xlist = Arrays.asList('A', 'B');
		
		List<Character> list = list( new SliceGenerator<Character>(iter("ABCDEFG"), 0, 2, 1));
		assert xlist.equals(list);
		
	}
	
	@Test public void testStartEnd(){
		
		List<Character> xlist = Arrays.asList('C', 'D');
		
		List<Character> list = list( new SliceGenerator<Character>(iter("ABCDEFG"), 2, 4, 1));
		assert xlist.equals(list);
		
	}
	
	@Test public void testStartInfinity(){
		
		List<Character> xlist = Arrays.asList('C', 'D', 'E','F','G');
		
		List<Character> list = list( new SliceGenerator<Character>(iter("ABCDEFG"), 2, Integer.MAX_VALUE, 1));
		assert xlist.equals(list);
	}

	@Test public void testStartStepInfinity(){
		
		List<Character> xlist = Arrays.asList('A', 'C', 'E' ,'G');
		
		List<Character> list = list( new SliceGenerator<Character>(iter("ABCDEFG"), 0, Integer.MAX_VALUE, 2));
		assert xlist.equals(list);
	}
	
	
	
	
}
