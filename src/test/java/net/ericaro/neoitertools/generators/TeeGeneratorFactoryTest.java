package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

import org.junit.Test;

public class TeeGeneratorFactoryTest {

	boolean failed = false;
	int M = 100;
	int t=0;
	class Runner extends Thread{

		Generator<Integer> generator;
		
		public Runner(Generator<Integer> generator) {
			super("id"+(t++));
			this.generator = generator;
		}

		@Override
		public void run() {
			try {
				for (int i=0;i<M;i++) {
					//System.out.println(getName()+" -> "+ i); 
					if ( i != generator.next() ) failed = true;
				}
			} catch (NoSuchElementException e) {
				failed = true;
			}
				
		}
		
	}
	
	@Test
	public void testTeeGeneratorFactory() throws InterruptedException {
		ArrayList<Integer> source = new ArrayList<Integer>();
		for (int i=0;i<M;i++) source.add(i);
		
		TeeGeneratorFactory<Integer> factory = new TeeGeneratorFactory<Integer>(iter(source));
		
		Runner[] threads = new Runner[10];
		for(int i=0;i<threads.length;i++)
			threads[i] = new Runner(factory.newInstance());
		
		for(int i=0;i<threads.length;i++)
			threads[i].start();
		for(int i=0;i<threads.length;i++)
			threads[i].join();
		
		assert !failed ;
	}

}
