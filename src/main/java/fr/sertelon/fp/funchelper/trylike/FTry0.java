package fr.sertelon.fp.funchelper.trylike;

import fr.sertelon.fp.funchelper.function.F0;

public interface FTry0<O> extends F0<Try<O>> {
	
	public default <A> FTry0<A> then(final FTry1<O, A> f) {
		return () -> FTry0.this.apply().flatMap(f);
	}
	
}
