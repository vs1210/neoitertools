package net.ericaro.neoitertools;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.yield;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class YieldTest  {

	@Test
	public void testGenerate() {
		
		
		Generator<Integer> g = iter(new Yield<Void, Integer>() {

			public void generate() {
				int i=1;
				int j=1;
				yield(j);
				yield(i);
				while(true){
					i=i+j;
					j=i-j;
				    yield(i);
				}
				
			}
		});
		List<Integer> list = list(g,10);
		System.out.println(  list  );
		
		
		List<Integer> xlist = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
		assert xlist.equals(list);
		
	}

}
