package net.ericaro.neoitertools;

import static net.ericaro.neoitertools.Itertools.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ItertoolsTest {

	public static <T> void assertList(Generator<T> generator, T... expected) {
		for (T x : expected) {
			T c = generator.next();
			System.out.print(c + " , ");
			assert x.equals(c);
		}
		System.out.println("...");
	}

	public static Lambda<Integer, Boolean> lt(final int bound) {
		return new Lambda<Integer, Boolean>() {
			public Boolean map(Integer x) {
				return x < bound;
			}
		};
	}

	Lambda<? super List<Character>, String> str = new Lambda<List<Character>, String>() {

		public String map(List<Character> arg) {
			return string(iter(arg));
		}
	};

	Lambda<Integer, Boolean> pair = new Lambda<Integer, Boolean>() {

		public Boolean map(Integer arg) {
			return (arg % 2) == 0;
		}
	};

	@Test
	public void testAll() {
		System.out.println(">>> all(range(0, 10, 2);");
		boolean res = all(range(0, 10, 2), pair);
		System.out.println(res);
		assert res;
	}

	@Test
	public void testAny() {

		System.out.println(">>> any(range(0, 10);");
		boolean res = any(range(0, 10), pair);
		System.out.println(res);
		assert res;
	}

	@Test
	public void testChainGeneratorOfGeneratorOfT() {
		Generator<Generator<Integer>> generators = iter(Arrays.asList(range(3), range(3, 5)));
		List<Integer> all = list(chain(generators));
		assert Arrays.asList(0, 1, 2, 3, 4).equals(all);
	}

	@Test
	public void testChainGeneratorOfTArray() {
		System.out.println(">>> chain(range(3), range(3,5));");
		assertList(chain(range(3), range(3, 5)), 0, 1, 2, 3, 4);
	}

	@Test
	public void testCombinations() {
		// combinations('ABCD', 2) --> AB AC AD BC BD CD
		// combinations(range(4), 3) --> 012 013 023 123

		System.out.println(">>> map(str, combinations(iter(\"ABCD\"), 2));");
		assertList(map(str, combinations(iter("ABCD"), 2)), "AB", "AC", "AD", "BC", "BD", "CD");
	}

	@Test
	public void testCount() {
		System.out.println(">>> count();");
		assertList(count(), 0, 1, 2, 3, 4);
	}

	@Test
	public void testCountInt() {
		System.out.println(">>> count(6);");
		assertList(count(6), 6, 7, 8, 9);
	}

	@Test
	public void testCycle() {
		// cycle('ABCD') --> A B C D A B C D ...
		System.out.println(">>> cycle(iter(\"ABCD\");");
		assertList(cycle(iter("ABCD")), 'A', 'B', 'C', 'D', 'A', 'B', 'C', 'D');
	}

	@Test
	public void testDropwhile() {
		// dropwhile(lambda x: x<5, [1,4,6,4,1]) --> 6 4 1
		System.out.println(">>> dropwhile(lt(5), iter(binomial) )");
		int[] binomial = new int[] { 1, 4, 6, 4, 1 };

		assertList(dropwhile(lt(5), iter(binomial)), 6, 4, 1);
	}

	@Test
	public void testEnumerate() {
		// >>> for i, season in enumerate(['Spring', 'Summer', 'Fall', 'Winter']):
		// ... print i, season
		// 0 Spring
		// 1 Summer
		// 2 Fall
		// 3 Winter
		System.out.println(">>> for(Index<Character>  pair: in( enumerate(iter(\"ABCD\")))){\n" + "...     System.out.println(pair.i+\" \"+pair.value);");
		for (Index<Character> pair : in(enumerate(iter("ABCD")))) {
			System.out.println(pair.i + " " + pair.value);
		}
	}

	@Test
	public void testFilter() {
		System.out.println(">>> filter(pair, range(10))");
		// ifilter(lambda x: x%2, range(10)) --> 1 3 5 7 9
		assertList(filter(pair, range(10)), 1, 3, 5, 7, 9);
	}

	@Test
	public void testFilterfalse() {
		System.out.println(">>> filterfalse(pair, range(10))");
		// ifilter(lambda x: x%2, range(10)) --> 1 3 5 7 9
		assertList(filterfalse(pair, range(10)), 0, 2, 4, 6, 8);
	}

	@Test
	public void testGroupby() {
		// [k for k, g in groupby('AAAABBBCCDAABBB')] --> A B C D A B
		Lambda<Pair<Character, Generator<Character>>, Character> key = new Lambda<Pair<Character, Generator<Character>>, Character>() {
			public Character map(Pair<Character, Generator<Character>> arg) {
				return arg.f0;
			}
		};
		Lambda<Character, Character> id = identity();
		System.out.println(">>> map(key, groupby(iter(\"AAABBBCCDAABBB\"), id))");

		assertList(map(key, groupby(iter("AAABBBCCDAABBB"), id)), 'A', 'B', 'C', 'D', 'A', 'B');
	}

	@Test
	public void testIdentity() {
		Lambda<Object, Object> id = identity();
		assert id == id.map(id);
	}

	@Test
	public void testIn() {

		System.out.println(">>> for(int i : in(range(3)))\n" + "...			System.out.println(\"i\");");
		for (int i : in(range(3)))
			System.out.println(i);
	}

	@Test
	public void testIterBooleanArray() {
		boolean[] val = new boolean[] { true, false };
		List<Boolean> list = list(iter(val));
		//System.out.println(list);
		for (Index<Boolean> pair : in(enumerate(iter(val))))
			assert val[pair.i] == pair.value;
	}

	@Test
	public void testIterByteArray() {
		byte[] val = new byte[] { 1, 2 };
		List<Byte> list = list(iter(val));
		System.out.println(list);
		for (Index<Byte> pair : in(enumerate(iter(val))))
			assert val[pair.i] == pair.value;
	}

	@Test
	public void testIterCharSequence() {
		System.out.println(">>> for(Character c: in( iter(\"ABCD\")))\n" + "...		System.out.println(c);");
		for (Character c : in(iter("ABCD")))
			System.out.println(c);

	}

	@Test
	public void testIterYieldOfVoidT() {
		// equivalent to count
		Yield<Void, Integer> yieldFunction = new Yield<Void, Integer>() {
			public void generate() {
				int i = 0;
				while (true)
					yield(i++);
			}
		};
		System.out.println(">>> iter(yieldFunction);");
		assertList(iter(yieldFunction), 0, 1, 2, 3, 4, 5);

	}

	@Test
	public void testListGeneratorOfT() {
		System.out.println(">>> list(range(3)) ;");
		System.out.println(list(range(3)));
	}

	@Test
	public void testListGeneratorOfTInt() {
		System.out.println(">>> list(count(),3) ;");
		System.out.println(list(count(), 3));
	}

	@Test
	public void testMap() {
		System.out.println(">>> map(pair,range(10) );");
		assertList(map(pair, range(10)), true, false, true, false);

	}

	// @Test
	// public void testPermutationsGeneratorOfT() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testPermutationsGeneratorOfTInt() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testProductGeneratorOfGeneratorOfT() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testProductGeneratorOfGeneratorOfTInt() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	@Test
	public void testRangeInt() {
		System.out.println(">>> range(3)");
		assertList(range(3), 0, 1, 2);
	}

	@Test
	public void testRangeIntInt() {
		System.out.println(">>> range(1,3)");
		assertList(range(1, 3), 1, 2);
	}

	@Test
	public void testRangeIntIntInt() {
		System.out.println(">>> range(1,10,5)");
		assertList(range(1, 10, 5), 1, 6);
	}

	// @Test
	// public void testReduceOperatorOfTGeneratorOfT() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testReduceOperatorOfTGeneratorOfTT() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testRepeatT() {
		System.out.println(">>> slice(repeat(\"Hello\"), 5)");
		assertList(slice(repeat("hello"), 5), "hello", "hello", "hello", "hello", "hello");
	}

	@Test
	public void testRepeatTInt() {
		System.out.println(">>> repeat(\"Hello\", 5)");
		assertList(repeat("hello", 5), "hello", "hello", "hello", "hello", "hello");
	}

	@Test
	public void testReversed() {
		System.out.println(">>> reversed(range(5))");
		assertList(reversed(range(5)), 4, 3, 2, 1, 0);
	}

	@Test
	public void testSliceGeneratorOfTInt() {
		System.out.println(">>> slice(count(),5)");
		assertList(slice(count(), 5), 0, 1, 2, 3, 4);
	}

	@Test
	public void testSliceGeneratorOfTIntInt() {
		System.out.println(">>> slice(count(),3,5)");
		assertList(slice(count(), 3, 5), 3, 4);
	}

	@Test
	public void testSliceGeneratorOfTIntIntInt() {
		System.out.println(">>> slice(count(),1,6,2)");
		assertList(slice(count(), 1, 6, 2), 1, 3, 5);
	}

	@Test
	public void testSortedGeneratorOfT() {
		System.out.println(">>> sorted(iter(\"ACBED\"))");
		assertList(sorted(iter("ACBED")), 'A', 'B', 'C', 'D', 'E');
	}

	// @Test
	// public void testSortedGeneratorOfTComparatorOfQsuperKLambdaOfQsuperTKBoolean() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSortedGeneratorOfTComparatorOfQsuperT() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testSortedGeneratorOfTLambdaOfTKBoolean() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	// @Test
	// public void testString() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	// @Test
	// public void testStringBuilder() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	@Test
	public void testTakewhile() {
		System.out.println(">>> takewhile(count(), lt(5))");
		assertList(takewhile(count(), lt(5)), 0, 1, 2, 3, 4);
	}

	// @Test
	// public void testTee() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	// @Test
	// public void testTuple() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	// @Test
	// public void testZipGeneratorOfGeneratorOfT() {
	// System.out.println(">>> ");
	// fail("Not yet implemented");
	// }

	@Test
	public void testZipGeneratorOfT1GeneratorOfT2() {
		System.out.println(">>> for( Pair<Integer, Character>  p: in( zip(range(4), iter(\"ABCDE\") )))\n" + "...		System.out.println(p.f0+\" -> \"+ p.f1);");
		for (Pair<Integer, Character> p : in(zip(range(4), iter("ABCDE"))))
			System.out.println(p.f0 + " -> " + p.f1);
	}

}
