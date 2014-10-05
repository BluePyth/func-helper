package fr.sertelon.fp.funchelper.trylike;

import static fr.sertelon.fp.funchelper.Nothing.nothing;

import java.util.function.Function;

import fr.sertelon.fp.funchelper.Nothing;
import fr.sertelon.fp.funchelper.immutable.IList;

public abstract class Try<T> {
	
	public abstract boolean isSuccess();
	public abstract boolean isFailure();
	
	public abstract T getPayload();
	public abstract Exception getException();
	
	public static <T> Success<T> success(T payload) {
		return new Success<T>(payload);
	}
	
	public static Success<Nothing> emptySuccess() {
		return new Success<Nothing>(nothing);
	}
	
	public static <E extends Exception, T> Failure<E, T> failure(E exception) {
		return new Failure<E, T>(exception);
	}
	
	public static <T> Try<IList<T>> sequence(IList<Try<T>> list) {
		return list //
			.foldLeft(success(IList.<T>nil()), (Try<IList<T>> acc, Try<T> listElem) -> //
				acc.flatMap(l -> //
					listElem.map(element -> l.prepend(element)) //
				) //
			) //
			.map(input -> input.reverse());
	}
	
	public static <A> Try<A> flatten(Try<Try<A>> in) {
		if(in.isSuccess())
			return in.getPayload();
		else
			return in.fail();
	}

	public <A> Try<A> flatMap(FTry1<T, A> lambda) {
		if(isSuccess()) {
			return lambda.apply(getPayload());
		} else {
			return this.fail();
		}
	}
	
	public <A> Try<A> map(Function<T,A> lambda) {
		if(isSuccess())
			return success(lambda.apply(getPayload()));
		else
			return this.fail();
	}

	public <E> Try<E> fail() {
		return new Failure<Exception, E>(isFailure() ? getException() : new Exception());
	}
	
	public <U> Try<U> transform(FTry1<T, U> s, FTry1<Exception, U> f) {
		if(isSuccess())
			return s.apply(getPayload());
		else
			return f.apply(getException());
	}

	public static class Success<T> extends Try<T> {
		private T payload;

		public Success(T payload) {
			this.payload = payload;
		}
		
		public boolean isSuccess() {
			return true;
		}

		public boolean isFailure() {
			return false;
		}

		public T getPayload() {
			return payload;
		}

		public Exception getException() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public String toString() {
			return new StringBuilder("Success(").append(payload).append(")").toString();
		}
		
	}
	
	public static class Failure<E extends Exception, A> extends Try<A> {

		private E exception;
		
		public Failure(E exception) {
			this.exception = exception;
		}
		
		@Override
		public boolean isSuccess() {
			return false;
		}

		@Override
		public boolean isFailure() {
			return true;
		}

		@Override
		public A getPayload() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E getException() {
			return exception;
		}

		@Override
		public String toString() {
			return new StringBuilder("Failure(").append(exception).append(")").toString();
		}
	}
	
}
