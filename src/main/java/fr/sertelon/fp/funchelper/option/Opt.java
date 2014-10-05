package fr.sertelon.fp.funchelper.option;

import static fr.sertelon.fp.funchelper.trylike.Try.success;

import java.util.NoSuchElementException;

import fr.sertelon.fp.funchelper.function.F1;
import fr.sertelon.fp.funchelper.trylike.Try;

public abstract class Opt<T> {
	// Helpers
	
	public static <A> Opt<A> toOpt(A a) {
		return a == null ? new None<A>() : new Some<A>(a);
	}
	
	public static <A> Opt<A> none() {
		return new None<A>();
	}
	
	// Methods
	
	public abstract boolean isDefined();
	
	public abstract T get();
	
	public T getOrElse(T defaultValue) {
		if(isDefined())
			return get();
		else
			return defaultValue;
	}
	
	public <U> Opt<U> map(F1<T, U> f) {
		if(isDefined())
			return toOpt(f.apply(get()));
		else
			return none();
	}
	
	public Opt<T> filter(F1<T, Boolean> p) {
		return toOpt(isDefined() && p.apply(get()) ? get() : null);
	}
	
	public <U> Opt<U> asOpt(Class<U> clazz) {
		if(isDefined() && clazz.isInstance(get()))
			return toOpt(clazz.cast(get()));
		else
			return none();
	}

	@SuppressWarnings("unchecked")
	public <A extends T> Opt<T> or(Opt<A> other) {
		if(isDefined())
			return this;
		else
			return (Opt<T>) other;
	}

	public Try<Opt<Object>> toTry() {
		return toTry(Object.class);
	}
	
	public <A> Try<Opt<A>> toTry(final Class<A> cls) {
		if(isDefined() && get() instanceof Try) {
			Try<?> i = (Try<?>) get();
			if(i.isSuccess() && cls.isInstance(i.getPayload())) {
				return success(toOpt(cls.cast(i.getPayload())));
			} else {
				return i.fail();
			}
		} else {
			return success(Opt.<A>none());
		}
	}
	
	
	public static class Some<T> extends Opt<T> {

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
		
		@Override
		public String toString() {
			return new StringBuilder().append("Some(").append(get()).append(")").toString();
		}
		
	}
	
	public static class None<T> extends Opt<T> {

		@Override
		public boolean isDefined() {
			return false;
		}

		@Override
		public T get() {
			throw new NoSuchElementException();
		}
		
		@Override
		public String toString() {
			return "None";
		}
		
	}
}

