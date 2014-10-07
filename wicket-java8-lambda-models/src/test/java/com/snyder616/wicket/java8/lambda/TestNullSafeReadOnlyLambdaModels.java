package com.snyder616.wicket.java8.lambda;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

public class TestNullSafeReadOnlyLambdaModels {
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createModel(IModel, java.util.function.Function)} with a non-null parent
	 * 
	 * Expected outcome: The getObject() method of the model returns the parent's child object
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
	 * Test {@link NullSafeReadOnlyLambdaModels#createModel(IModel, java.util.function.Function)} with a null parent
	 * 
	 * Expected outcome: The getObject() method of the model returns null
	 */
	@Test
	public void testCreateModelWithNullParent() {
		IModel<MockParent> parentModel = Model.of();
		IModel<Object> childModel = NullSafeReadOnlyLambdaModels.createModel(parentModel, p -> p.getChildObject());
		assertNull(childModel.getObject());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createCollectionModel(IModel, java.util.function.Function)} with a non-null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns the parent's child collection
	 */
	@Test
	public void testCreateCollectionModel() {
		List<String> childList = new ArrayList<String>();
		MockParent parent = new MockParent(childList);
		IModel<MockParent> parentModel = Model.of(parent);
		IModel<Collection<? extends Serializable>> childModel = NullSafeReadOnlyLambdaModels.createCollectionModel(
			parentModel,
			p -> p.getChildList());
		assertSame(childList, childModel.getObject());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createCollectionModel(IModel, java.util.function.Function)} with a null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns an empty collection
	 */
	@Test
	public void testCreateCollectionModelWithNullParent() {
		IModel<MockParent> parentModel = Model.of();
		IModel<Collection<? extends Serializable>> childModel = NullSafeReadOnlyLambdaModels.createCollectionModel(
			parentModel,
			p -> p.getChildList());
		assertEquals(0, childModel.getObject().size());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createListModel(IModel, java.util.function.Function)} with a non-null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns the parent's child list
	 */
	@Test
	public void testCreateListModel() {
		List<String> childList = new ArrayList<String>();
		MockParent parent = new MockParent(childList);
		IModel<MockParent> parentModel = Model.of(parent);
		IModel<List<? extends Serializable>> childModel = NullSafeReadOnlyLambdaModels.createListModel(parentModel, p -> p.getChildList());
		assertSame(childList, childModel.getObject());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createListModel(IModel, java.util.function.Function)} with a null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns an empty list
	 */
	@Test
	public void testCreateListModelWithNullParent() {
		IModel<MockParent> parentModel = Model.of();
		IModel<List<? extends Serializable>> childModel = NullSafeReadOnlyLambdaModels.createListModel(parentModel, p -> p.getChildList());
		assertEquals(0, childModel.getObject().size());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createBooleanModel(IModel, java.util.function.Function)} with a non-null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns the parent's child boolean
	 */
	@Test
	public void testCreateBooleanModel() {
		Boolean childBoolean = Boolean.TRUE;
		MockParent parent = new MockParent(childBoolean);
		IModel<MockParent> parentModel = Model.of(parent);
		IModel<Boolean> childModel = NullSafeReadOnlyLambdaModels.createBooleanModel(parentModel, p -> p.getChildBoolean());
		assertSame(childBoolean, childModel.getObject());
	}
	
	/**
	 * Test {@link NullSafeReadOnlyLambdaModels#createBooleanModel(IModel, java.util.function.Function)} with a null parent
	 * 
	 * Expected outcome: the getObject() method of the model returns Boolean.FALSE
	 */
	@Test
	public void testCreateBooleanModelWithNullParent() {
		IModel<MockParent> parentModel = Model.of();
		IModel<Boolean> childModel = NullSafeReadOnlyLambdaModels.createBooleanModel(parentModel, p -> p.getChildBoolean());
		assertEquals(Boolean.FALSE, childModel.getObject());
	}
}
