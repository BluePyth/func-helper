package fr.bluepyth.funchelper.trylike;

import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.function.F2;

public abstract class FTry2<I1, I2, O> extends F2<I1, I2, Try<O>> {
	public abstract Try<O> apply(I1 i1, I2 i2);
	
	public <A,B> FTry2<A,B,O> preComp(final F1<A, I1> f1, final F1<B,I2> f2) {
		return new FTry2<A, B, O>() {
			@Override
			public Try<O> apply(A i1, B i2) {
				return FTry2.this.apply(f1.apply(i1), f2.apply(i2));
			}
		};
	}
	
	public <A> FTry2<I1,I2,A> then(final FTry1<O, A> f) {
		return new FTry2<I1, I2, A>() {
			@Override
			public Try<A> apply(I1 i1, I2 i2) {
				return FTry2.this.apply(i1, i2).flatMap(f);
			}
		};
	}
}
