# wicket-java8-lambda-models #

Models for Wicket which utilize lambda functions to retrieve data.

## Classes ##

### NullSafeReadOnlyLambdaModels ###

This class constructs models that provide appropriate behaviors for when the parent's model object is null. Each method defines
what should be returned by the child model when the parent object is null.