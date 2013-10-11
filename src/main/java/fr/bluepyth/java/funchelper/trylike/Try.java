package fr.bluepyth.java.funchelper.trylike;

import static fr.bluepyth.java.funchelper.Nothing.nothing;
import fr.bluepyth.java.funchelper.Nothing;
import fr.bluepyth.java.funchelper.function.F1;
import fr.bluepyth.java.funchelper.function.F2;
import fr.bluepyth.java.funchelper.immutable.IList;
import fr.bluepyth.java.funchelper.option.Opt;

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
	
	public static <T> Try<IList<T>> trySeq(IList<Try<T>> list) {
		return list.foldLeft(success(IList.<T>nil()), new F2<Try<IList<T>>, Try<T>, Try<IList<T>>>() {
			public Try<IList<T>> apply(Try<IList<T>> acc, final Try<T> listElem) {
				
				return acc.map(new FTry<IList<T>, IList<T>>() {
					public Try<IList<T>> apply(final IList<T> list) {
						
						return listElem.map(new FTry<T, IList<T>>() {
							public Try<IList<T>> apply(T element) {
								
								return success(list.prepend(element));
							}
						});
					}
				});
			}
		}).map(new F1<IList<T>, Try<IList<T>>>() {
			public Try<IList<T>> apply(IList<T> input) {
				return success(input.reverse());
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <U,V extends Exception> Try<Opt<U>> toTry(Opt<Try<U>> opt, Class<V> exceptionClass) {
		if(opt.isDefined())
			return (Try) success(opt);
		else
			try {
				return failure(exceptionClass.newInstance());
			} catch (InstantiationException e) {
				return failure(e);
			} catch (IllegalAccessException e) {
				return failure(e);
			}
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
	
	public <U> Try<U> transform(FTry<T, U> s, FTry<Exception, U> f) {
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
