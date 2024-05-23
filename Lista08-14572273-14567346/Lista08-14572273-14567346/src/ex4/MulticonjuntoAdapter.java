package ex4;

import java.util.Iterator;

import ex3.MulticonjuntoAbstrato;

public class MulticonjuntoAdapter<TMulticonjunto extends MulticonjuntoAbstrato<TElement>, TElement> extends MulticonjuntoAbstrato<TElement>{
	
	public TMulticonjunto multiConjunto;

	public MulticonjuntoAdapter(TMulticonjunto c) {
		multiConjunto = c;
	}
	
	@Override
	public Iterator<MulticonjuntoAbstrato<TElement>.Wrapper> iterator() {
		return multiConjunto.iterator();
	}

	@Override
	public int getMultiplicity(TElement element) {
		return multiConjunto.getMultiplicity(element) > 0 ? 1 : 0;
	}

	@Override
	public boolean add(TElement e) {
		if (getMultiplicity(e) <= 0)
			return multiConjunto.add(e, 1);
		
		return false;
	}

	@Override
	public boolean add(TElement e, int m) {
		return add(e);
	}
	
	

}
