package hufs.ces.cirbuf.gui;

import java.math.BigInteger;
import java.util.Iterator;

public class BigFibonacciIterator implements Iterator<BigInteger> {

	private BigInteger num1 = BigInteger.ZERO;
	private BigInteger num2 = BigInteger.ONE;	
	private int maxCount = Integer.MAX_VALUE;
	private int curCount = 0;
	
	public BigFibonacciIterator(){}
	public BigFibonacciIterator(int mcount){
		this.maxCount = mcount;
	}
	public boolean hasNext() {
		if (curCount < maxCount)
			return true;
		else
			return false;
	}

	public BigInteger next() {
		BigInteger retValue = num2;
		num2 = num1.add(num2);
		num1 = retValue;
		curCount++;
		return retValue;
	}

	public void remove() {
		throw new UnsupportedOperationException();

	}

}
