package fr.sertelon.fp.funchelper.function;

import java.util.function.Function;

public interface F0<O> {
	O apply();
	
	default public <A> F0<A> then(final Function<O,A> f) {
		return () -> f.apply(F0.this.apply());
	}
}
