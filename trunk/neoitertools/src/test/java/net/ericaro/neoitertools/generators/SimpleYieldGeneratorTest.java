package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.count;
import static net.ericaro.neoitertools.Itertools.in;
import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.range;
import static net.ericaro.neoitertools.Itertools.yield;
import static org.junit.Assert.*;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Yield;

import org.junit.Test;

public class SimpleYieldGeneratorTest {

	@Test
	public void testSimpleYieldGenerator() {
		
		Yield<Void, Integer> yieldFunction = new Yield<Void, Integer>() {

			public void generate() {
				int i=0;
				while(true) 
					yield(i++);
			}
		};
		
		Generator<Integer> xg = count();
		Generator<Integer> g = iter(yieldFunction);
		for(int i: in( range(100) )){
			System.out.println("i="+i);
			assert xg.next() == g.next() ;
		}
		
	}

}
