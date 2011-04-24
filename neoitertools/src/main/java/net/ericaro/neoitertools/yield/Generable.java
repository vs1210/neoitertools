package net.ericaro.neoitertools.yield;

public abstract class Generable<U,V> {
	
	private YieldThread<U,V> yieldThread;
	

	public abstract void generator();
	

	
	void setYieldThread(YieldThread<U, V> yieldThread) {
		this.yieldThread = yieldThread;
	}



	protected U yield(V value){
		return yieldThread.yield(value);
	}
	
}
