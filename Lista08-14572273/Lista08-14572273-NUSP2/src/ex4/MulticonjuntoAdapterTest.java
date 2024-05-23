package ex4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ex3.MulticonjuntoAbstrato;
import ex3.MulticonjuntoAbstrato.Wrapper;
import ex3.MulticonjuntoArrayList;
import ex3.MulticonjuntoLinkedList;
import ex3.MulticonjuntoSet;
import ex3.MulticonjuntoStack;

public class MulticonjuntoAdapterTest {
	@ParameterizedTest
	@MethodSource("adapterProvider")
	public void testWorkingAsSet(MulticonjuntoAbstrato<Integer> c) {
		assertTrue(c.add(5, 20));
		assertFalse(c.add(5));
		assertTrue(c.add(8));
		
		Iterator<MulticonjuntoAbstrato<Integer>.Wrapper> i = c.iterator();
		while(i.hasNext()) {
			Wrapper w = i.next();
			if (w.getMultiplicity() != 1) fail();
		}
	}
	
	@ParameterizedTest
	@MethodSource("equalsProvider")
	public void testEquals(MulticonjuntoAbstrato<Integer> c1, MulticonjuntoAbstrato<Integer> c2) {
		assertEquals(c1, c2);
	}
	
	@ParameterizedTest
	@MethodSource("addAllProvider")
	public void testAddAll(MulticonjuntoAbstrato<Integer> c1, MulticonjuntoAbstrato<Integer> c2, MulticonjuntoAbstrato<Integer> expected) {
		c1.addAll(c2);
		
		assertEquals(expected, c1);
	}
	
	private static Stream<MulticonjuntoAbstrato<Integer>> adapterProvider() {
	    return Stream.of(
	    		new MulticonjuntoAdapter<MulticonjuntoSet<Integer>, Integer>(new MulticonjuntoSet<Integer>()),
	    		new MulticonjuntoAdapter<MulticonjuntoStack<Integer>, Integer>(new MulticonjuntoStack<Integer>()),
	    		new MulticonjuntoAdapter<MulticonjuntoArrayList<Integer>, Integer>(new MulticonjuntoArrayList<Integer>()),
	    		new MulticonjuntoAdapter<MulticonjuntoLinkedList<Integer>, Integer>(new MulticonjuntoLinkedList<Integer>())
	    );
	}
	
	private static Stream<Arguments> equalsProvider() {
		ArrayList<Arguments> args = new ArrayList<Arguments>();
		ArrayList<MulticonjuntoAbstrato<Integer>> c1 = new ArrayList<MulticonjuntoAbstrato<Integer>>();
		c1.add(new MulticonjuntoAdapter<MulticonjuntoSet<Integer>, Integer>(new MulticonjuntoSet<Integer>()));
		c1.add(new MulticonjuntoAdapter<MulticonjuntoStack<Integer>, Integer>(new MulticonjuntoStack<Integer>()));
		c1.add(new MulticonjuntoAdapter<MulticonjuntoArrayList<Integer>, Integer>(new MulticonjuntoArrayList<Integer>()));
		c1.add(new MulticonjuntoAdapter<MulticonjuntoLinkedList<Integer>, Integer>(new MulticonjuntoLinkedList<Integer>()));
		ArrayList<MulticonjuntoAbstrato<Integer>> c2 = new ArrayList<MulticonjuntoAbstrato<Integer>>();
		c2.add(new MulticonjuntoAdapter<MulticonjuntoSet<Integer>, Integer>(new MulticonjuntoSet<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoStack<Integer>, Integer>(new MulticonjuntoStack<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoArrayList<Integer>, Integer>(new MulticonjuntoArrayList<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoLinkedList<Integer>, Integer>(new MulticonjuntoLinkedList<Integer>()));
		
		for (MulticonjuntoAbstrato<Integer> c : c1) {
			c.add(1, 1);
			c.add(2, 2);
			c.add(3, 3);
			c.add(4, 4);
			c.add(5, 5);
		}
		for (MulticonjuntoAbstrato<Integer> c : c2) {
			c.add(1, 1);
			c.add(2, 2);
			c.add(3, 3);
			c.add(4, 4);
			c.add(5, 5);
		}
		
		for (MulticonjuntoAbstrato<Integer> ca : c1) {
			for (MulticonjuntoAbstrato<Integer> cb : c2) {
				args.add(Arguments.of(ca, cb));
			}
		}
		
		return args.stream();
	}
	
	private static Stream<Arguments> addAllProvider() {
		ArrayList<Arguments> args = new ArrayList<Arguments>();
		
		ArrayList<MulticonjuntoAbstrato<Integer>> c2 = new ArrayList<MulticonjuntoAbstrato<Integer>>();
		c2.add(new MulticonjuntoAdapter<MulticonjuntoSet<Integer>, Integer>(new MulticonjuntoSet<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoStack<Integer>, Integer>(new MulticonjuntoStack<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoArrayList<Integer>, Integer>(new MulticonjuntoArrayList<Integer>()));
		c2.add(new MulticonjuntoAdapter<MulticonjuntoLinkedList<Integer>, Integer>(new MulticonjuntoLinkedList<Integer>()));
		
		MulticonjuntoAbstrato<Integer> expected = new MulticonjuntoArrayList<Integer>();
		expected.add(1, 1);
		expected.add(2, 1);
		expected.add(3, 1);
		expected.add(4, 1);
		expected.add(5, 1);
	
		for (MulticonjuntoAbstrato<Integer> c : c2) {
			c.add(1, 5);
			c.add(2, 4);
			c.add(3, 3);
			c.add(4, 2);
			c.add(5, 1);
		}
		
		for (MulticonjuntoAbstrato<Integer> cb : c2) {
			// Need to create a new instace for each time, otherwise one test will interfeer with the next one
			// Precisa criar uma nova instancia para cada teste, se não a soma feita em um teste interfere no próximo;
			ArrayList<MulticonjuntoAbstrato<Integer>> c1 = new ArrayList<MulticonjuntoAbstrato<Integer>>();
			c1.add(new MulticonjuntoAdapter<MulticonjuntoSet<Integer>, Integer>(new MulticonjuntoSet<Integer>()));
			c1.add(new MulticonjuntoAdapter<MulticonjuntoStack<Integer>, Integer>(new MulticonjuntoStack<Integer>()));
			c1.add(new MulticonjuntoAdapter<MulticonjuntoArrayList<Integer>, Integer>(new MulticonjuntoArrayList<Integer>()));
			c1.add(new MulticonjuntoAdapter<MulticonjuntoLinkedList<Integer>, Integer>(new MulticonjuntoLinkedList<Integer>()));
			
			for (MulticonjuntoAbstrato<Integer> c : c1) {
				c.add(1, 1);
				c.add(2, 2);
				c.add(3, 3);
				c.add(4, 4);
				c.add(5, 5);
				
				args.add(Arguments.of(c, cb, expected));
			}
		}
		
		return args.stream();
	}
}
