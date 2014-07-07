package fr.bluepyth.funchelper.function;

public interface F1<I, O> {
	O apply(I input);
	
	default public <A> F1<I, A> then(final F1<O, A> next) {
		return input -> next.apply(F1.this.apply(input));
	}
}