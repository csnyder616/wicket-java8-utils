package com.snyder616.wicket.java8.lambda;

import static org.junit.Assert.*;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

public class TestNullSafeReadOnlyLambdaModels {
	/**
	 * Preconditions: Parent is non-null
	 * Expected outcome: The getObject() method of the model returns the child object
	 */
	@Test
	public void testCreateModel() {
		Object child = new String("This is the child.");
		MockParent parent = new MockParent(child);
		IModel<MockParent> parentModel = Model.of(parent);
		IModel<Object> childModel = NullSafeReadOnlyLambdaModels.createModel(parentModel, p -> p.getChildObject());
		assertEquals(child, childModel.getObject());
	}
	
	/**
	 * Preconditions: Parent is null
	 * Expected outcome: The getObject() method of the model returns null
	 */
	@Test
	public void testCreateModelWithNullParent() {
		IModel<MockParent> parentModel = Model.of();
		IModel<Object> childModel = NullSafeReadOnlyLambdaModels.createModel(parentModel, p -> p.getChildObject());
		assertNull(childModel.getObject());
	}
}
