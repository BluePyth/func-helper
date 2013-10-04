package fr.bluepyth.java.funchelper.option;

import fr.bluepyth.java.funchelper.function.F1;

public abstract class Option<T> {
	
	public static <A> Option<A> from(A a) {
		return a == null ? new None<A>() : new Some<A>(a);
	}
	
	public static <A> None<A> none() {
		return new None<A>();
	}
	
	public abstract boolean isDefined();
	
	public abstract T get();
	
	public <U> Option<U> map(F1<T, U> f) {
		if(isDefined())
			return from(f.apply(get()));
		else
			return none();
	}
	
	public Option<T> or(Option<T> other) {
		if(isDefined())
			return this;
		else
			return other;
	}
	
	public T getOrElse(T defaultValue) {
		if(isDefined())
			return get();
		else
			return defaultValue;
	}
	
	public static class Some<T> extends Option<T> {

		private final T value;
		
		public Some(T value) {
			this.value = value;
		}
		
		@Override
		public boolean isDefined() {
			return true;
		}

		@Override
		public T get() {
			return value;
		}
		
	}
	
	public static class None<T> extends Option<T> {

		@Override
		public boolean isDefined() {
			return false;
		}

		@Override
		public T get() {
			throw new UnsupportedOperationException();
		}
		
	}
}

