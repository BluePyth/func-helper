package fr.bluepyth.funchelper.trylike;

import fr.bluepyth.funchelper.function.F0;

public abstract class FTry0<O> extends F0<Try<O>> {
	
	public <A> FTry0<A> then(final FTry1<O, A> f) {
		return new FTry0<A>() {
			@Override
			public Try<A> apply() {
				return FTry0.this.apply().flatMap(f);
			}
		};
	}
}
