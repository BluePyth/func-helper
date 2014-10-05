package fr.sertelon.fp.funchelper.function;

public interface F0<O> {
	O apply();
	
	default public <A> F0<A> then(final F1<O,A> f) {
		return () -> f.apply(F0.this.apply());
	}
}
