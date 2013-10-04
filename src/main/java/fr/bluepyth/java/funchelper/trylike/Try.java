package fr.bluepyth.java.funchelper.trylike;

import fr.bluepyth.java.funchelper.Nothing;
import fr.bluepyth.java.funchelper.function.F1;

public abstract class Try<T> {
	
	public abstract boolean isSuccess();
	public abstract boolean isFailure();
	
	public abstract T getPayload();
	public abstract Exception getException();
	
	public static <T> Success<T> success(T payload) {
		return new Success<T>(payload);
	}
	
	public static Success<Nothing> emptySuccess() {
		return new Success<Nothing>(new Nothing());
	}
	
	public static <E extends Exception, T> Failure<E, T> failure(E exception) {
		return new Failure<E, T>(exception);
	}
	
	public <A> Try<A> map(F1<T, Try<A>> lambda) {
		if(isSuccess()) {
			return lambda.apply(getPayload());
		} else {
			return new Failure<Exception, A>(this.getException());
		}
	}
	
	public <E> Try<E> fail() {
		return new Failure<Exception, E>(isFailure() ? getException() : new Exception());
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
			return new StringBuilder("[Success payload=").append(payload).append("]").toString();
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
			return new StringBuilder("[Failure exception=").append(exception).append("]").toString();
		}
	}
	
}
