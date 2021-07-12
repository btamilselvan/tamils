package com.success;

public abstract class TestAbstract {

	private int a;
	public TestAbstract(int a) {
		
	}
}


class TestAbstractChild extends TestAbstract{

	public TestAbstractChild(int a) {
		super(a);
	}

	public TestAbstractChild() {
		super(10);
	}	
}