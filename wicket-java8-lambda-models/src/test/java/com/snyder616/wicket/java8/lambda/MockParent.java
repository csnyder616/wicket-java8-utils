package com.snyder616.wicket.java8.lambda;

import java.io.Serializable;

class MockParent implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Object childObject;
	
	MockParent() {
		this.childObject = null;
	}
	
	MockParent(Object childObject) {
		this.childObject = childObject;
	}
	
	public Object getChildObject() {
		return childObject;
	}
}
