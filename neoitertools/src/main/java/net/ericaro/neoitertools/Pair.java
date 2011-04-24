package net.ericaro.neoitertools;


/** A simple Immutable couple of two types.
 * 
 */
public class Pair<T0, T1> {

	
	public final T0 f0;
	public final T1 f1;
	public Pair(T0 f0, T1 f1) {
		super();
		this.f0 = f0;
		this.f1 = f1;
	}
	
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f0 == null) ? 0 : f0.hashCode());
		result = prime * result + ((f1 == null) ? 0 : f1.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (f0 == null) {
			if (other.f0 != null)
				return false;
		} else if (!f0.equals(other.f0))
			return false;
		if (f1 == null) {
			if (other.f1 != null)
				return false;
		} else if (!f1.equals(other.f1))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "("+ f0 + ", " + f1 + ")";
	}

	
	
	
}
