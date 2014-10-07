package com.snyder616.wicket.java8.lambda;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * Provides utility methods to construct read-only null-safe models that consist of a parent model and a function to retrieve a
 * child of that model. If the object returned by the parent model is null, the model will return an empty value rather than
 * calling the function. The specific empty value returned varies depending on which type of model is used - each factory method
 * documents what "empty" means for its specific case.
 */
public final class NullSafeReadOnlyLambdaModels {
	/**
	 * Constructs a null-safe model to retrieve child data from the parent. If the parent object is null, this model will return
	 * null.
	 * 
	 * @param <P>
	 *            Parent type
	 * @param <C>
	 *            Child type
	 * @param parentModel
	 *            Model to access parent object
	 * @param childGetter
	 *            Function that returns the child data from the specified parent. May safely assume that parent is not null.
	 * @return Read-only model providing access to the child data.
	 */
	public static <P, C> IModel<C> createModel(IModel<P> parentModel, Function<P, C> childGetter) {
		return new NullSafeReadOnlyModel<P, C>(parentModel, childGetter);
	}
	
	/**
	 * Constructs a null-safe model to retrieve child collections from the parent. If the parent object is null, this model will
	 * return an immutable empty collection.
	 * 
	 * @param <P>
	 *            Parent type
	 * @param <C>
	 *            Child type
	 * @param parentModel
	 *            Model to access parent object
	 * @param childGetter
	 *            Function that returns the child collection from the specified parent. May safely assume that parent is not null.
	 * @return Read-only model providing access to the child collections.
	 */
	public static <P, C> IModel<Collection<? extends C>> createCollectionModel(IModel<P> parentModel,
		Function<P, Collection<? extends C>> childGetter) {
		return new CollectionModel<P, C>(parentModel, childGetter);
	}
	
	/**
	 * Constructs a null-safe model to retrieve child lists from the parent. If the parent object is null, this model will return
	 * an immutable empty list.
	 * 
	 * @param <P>
	 *            Parent type
	 * @param <C>
	 *            Child type
	 * @param parentModel
	 *            Model to access parent object
	 * @param childGetter
	 *            Function that returns the child lists from the specified parent. May safely assume that parent is not null.
	 * @return Read-only model providing access to the child lists.
	 */
	public static <P, C> IModel<List<? extends C>> createListModel(IModel<P> parentModel, Function<P, List<? extends C>> childGetter) {
		return new ListModel<P, C>(parentModel, childGetter);
	}
	
	/**
	 * Constructs a null-safe model to retrieve child Boolean data from the parent. If the parent object is null, this model will
	 * return Boolean.FALSE.
	 * 
	 * Caveat: In the absence of data actually leads to data (FALSE) being returned. Therefore, this method is only applicable to
	 * limited use-cases. In many cases, {@link ReadOnlyLambdaModels#of(IModel, Function)} is the more appropriate model to use.
	 * 
	 * @param <P>
	 *            Parent type
	 * @param parentModel
	 *            Model to access parent object
	 * @param childGetter
	 *            Function that returns the child Boolean from the specified parent. May safely assume that parent is not null.
	 * @return Read-only model providing access to the child Boolean.
	 */
	public static <P> IModel<Boolean> createBooleanModel(IModel<P> parentModel, Function<P, Boolean> childGetter) {
		return new BooleanModel<P>(parentModel, childGetter);
	}
	
	/**
	 * @param <P>
	 *            Parent data type
	 * @param <C>
	 *            Child data type
	 */
	private static class NullSafeReadOnlyModel<P, C> extends AbstractReadOnlyModel<C> {
		private static final long serialVersionUID = 1L;
		private final IModel<P> parent;
		private final Function<P, C> childGetter;
		
		private NullSafeReadOnlyModel(IModel<P> parent, Function<P, C> childGetter) {
			this.parent = parent;
			this.childGetter = childGetter;
		}
		
		@Override
		public C getObject() {
			P parent = this.parent.getObject();
			if (parent == null) {
				return nullValue();
			}
			return childGetter.apply(parent);
		}
		
		@Override
		public void detach() {
			super.detach();
			parent.detach();
		}
		
		protected C nullValue() {
			return null;
		}
	}
	
	private static class ListModel<P, C> extends NullSafeReadOnlyModel<P, List<? extends C>> {
		private static final long serialVersionUID = 1L;
		
		private ListModel(IModel<P> parent, Function<P, List<? extends C>> childGetter) {
			super(parent, childGetter);
		}
		
		@Override
		protected List<? extends C> nullValue() {
			return Collections.emptyList();
		}
	}
	
	private static class CollectionModel<P, C> extends NullSafeReadOnlyModel<P, Collection<? extends C>> {
		private static final long serialVersionUID = 1L;
		
		private CollectionModel(IModel<P> parent, Function<P, Collection<? extends C>> childGetter) {
			super(parent, childGetter);
		}
		
		@Override
		protected Collection<? extends C> nullValue() {
			return Collections.emptyList();
		}
	}
	
	private static class BooleanModel<P> extends NullSafeReadOnlyModel<P, Boolean> {
		private static final long serialVersionUID = 1L;
		
		private BooleanModel(IModel<P> parent, Function<P, Boolean> childGetter) {
			super(parent, childGetter);
		}
		
		@Override
		protected Boolean nullValue() {
			return Boolean.FALSE;
		}
	}
}