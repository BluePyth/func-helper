package fr.sertelon.fp.funchelper.immutable;

import static fr.sertelon.fp.funchelper.Nothing.nothing;
import static fr.sertelon.fp.funchelper.option.Opt.none;
import static fr.sertelon.fp.funchelper.option.Opt.toOpt;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;

import fr.sertelon.fp.funchelper.Nothing;
import fr.sertelon.fp.funchelper.option.Opt;

/**
 * Immutable linked list. It resembles Scala's List.
 *
 * As of now, this class is NOT optimized at all. I've started to implement it from scratch
 * for fun, and as an exercise to apply my functional knowledge.
 * 
 * Thus, it is suitable for small lists.
 *
 * @param <T> The type of the elements of the list
 */
public abstract class IList<T> {
	
	/**
	 * @return the empty list
	 */
	public static <U> IList<U> nil() {
		return new Nil<U>();
	}
	
	/**
	 * Builds a list from a list of elements.
	 * @param elements the elements that should be added to the list
	 * @return the expected list
	 */
	@SafeVarargs
	public static <U> IList<U> list(U... elements) {
		IList<U> list = nil();
		for(int i = elements.length - 1; i >= 0; i--) {
			list = list.prepend(elements[i]);
		}
		return list;
	}
	
	public static <U> IList<U> list(Iterable<U> c) {
		return revList(c).reverse();
	}
	
	@SafeVarargs
	public static <U> IList<U> revList(U... elements) {
		IList<U> list = nil();
		for(int i = 0; i < elements.length; i++) {
			list = list.prepend(elements[i]);
		}
		return list;
	}
	
	public static <U> IList<U> revList(Iterable<U> c) {
		IList<U> list = nil();
		if(c != null) {
			for(U e : c) {
				list = list.prepend(e);
			}
		}
		return list;
	}
	
	/**
	 * Returns a list containing numbers from 'from' to 'to-1'
	 * 
	 * @param from the lower bound
	 * @param to the upper bound (excluded)
	 * @return the expected list
	 */
	public static IList<Integer> range(int from, int to) {
		return rangeIncl(from, to - 1);
	}
	
	/**
	 * Returns a list containing numbers from 'from' to 'to' included
	 * @param from the lower bound
	 * @param to the upper bound
	 * @return the expected list
	 */
	public static IList<Integer> rangeIncl(int from, int to) {
		IList<Integer> list = nil();
		for(int i = to; i >= from; i--) {
			list = list.prepend(i);
		}
		return list;
	}
	
	/**
	 * @return the first element of this list
	 */
	public abstract T head();

	public Opt<T> headOpt() {
		if(isEmpty())
			return none();
		else
			return toOpt(head());
	}
	
	/**
	 * @return the tail of this list
	 */
	public abstract IList<T> tail();
	
	/**
	 * @return whether this list is empty (nil) or not
	 */
	public abstract boolean isEmpty();
	
	/**
	 * @param i the index of the wanted element
	 * @return the i-th element of the list
	 */
	public abstract T get(int i);
	
	/**
	 * @return this list, reversed
	 */
	public IList<T> reverse() {
		return reverseAcc(IList.<T>nil());
	}
	protected abstract IList<T> reverseAcc(IList<T> acc);
	
	/**
	 * @param predicate the filtering function
	 * @return this list, only with filtered elements
	 */
	public IList<T> filter(Function<T, Boolean> predicate) {
		return filterAcc(predicate, IList.<T>nil());
	}
	protected abstract IList<T> filterAcc(Function<T, Boolean> p, IList<T> acc);
	
	/**
	 * @param f the function that will transform each element
	 * @return this list, with each element transformed by f
	 */
	public <U> IList<U> map(Function<T,U> f) {
		return mapAcc(f, IList.<U>nil());
	}
	protected abstract <U> IList<U> mapAcc(Function<T,U> f, IList<U> acc);

	/**
	 * @param sep the separator
	 * @return a string with all elements separated by sep
	 */
	public String mkString(String sep) {
		return mkString("", sep, "");
	}
	
	/**
	 * @param start the beginning of the resulting string
	 * @param sep the separator
	 * @param end the end of the resulting string
	 * @return a string with all elements like so: start+el+sep+...+sep+el+end
	 */
	public String mkString(String start, final String sep, String end) {
		final AtomicBoolean first = new AtomicBoolean(true); 
		final StringBuilder sb = new StringBuilder();
		
		sb.append(start);
		
		foreach((T input) -> {
			if(first.get()) {
				first.set(false);
			} else {
				sb.append(sep);
			}
			sb.append(input);
			return nothing;
		});
		
		sb.append(end);
		
		return sb.toString();
	}
	
	/**
	 * @param f the function to be applied on each element of the list
	 * @return nothing (function has only side effects)
	 */
	public abstract Nothing foreach(Function<T, Nothing> f);
	
	/**
	 * This function allows one to traverse the list while computing a value.
	 * For example it can be used to compute the sum of the elements of a list:
	 * 	 list(1,2).foldLeft(0, (a,b) -&gt; a+b)
	 * @param init the initial value
	 * @param f the function that will be applied
	 * @return a value of type U
	 */
	public <U> U foldLeft(U init, BiFunction<U, T, U> f) {
		return foldLeftAcc(f, init);
	}
	protected abstract <U> U foldLeftAcc(BiFunction<U,T,U> f, U acc);
	
	/**
	 * Adds an element to the beginning of this list
	 * @param element the element to be prepended
	 * @return a new list with element prepended
	 */
	public IList<T> prepend(T element) {
		return new Cons<T>(element, this);
	}
	
	/**
	 * Drops the n first elements of this list
	 * @param n the number of elements to drop
	 * @return the remaining list, nil if all elements have been dropped
	 */
	public abstract IList<T> drop(int n);
	
	@Override
	public String toString() {
		return mkString("List(", ",", ")");
	}
	
	public int size() {
		return foldLeft(0, (Integer i1, T i2) -> {
			return i1 + 1;
		});
	}
	
	/**
	 * This class represents a link of the list
	 * @param <T>
	 */
	public static class Cons<T> extends IList<T> {

		private final T head;
		private final IList<T> tail;
		
		public Cons(T head, IList<T> tail) {
			this.head = head;
			this.tail = tail;
		}
		
		@Override
		public T head() {
			return head;
		}

		@Override
		public IList<T> tail() {
			return tail;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public T get(int i) {
			return i == 0 ? head : tail.get(i - 1);
		}

		@Override
		public IList<T> reverseAcc(IList<T> acc) {
			return tail.reverseAcc(acc.prepend(head));
		}

		@Override
		public IList<T> filterAcc(Function<T, Boolean> p, IList<T> acc) {
			return tail.filterAcc(p, p.apply(head) ? acc.prepend(head) : acc);
		}

		@Override
		protected <U> IList<U> mapAcc(Function<T, U> f, IList<U> acc) {
			return tail.mapAcc(f, acc.prepend(f.apply(head)));
		}

		@Override
		protected <U> U foldLeftAcc(BiFunction<U, T, U> f, U acc) {
			return tail.foldLeftAcc(f, f.apply(acc, head));
		}

		@Override
		public Nothing foreach(Function<T, Nothing> f) {
			f.apply(head);
			return tail.foreach(f);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof IList))
				return false;
			
			IList<?> other = (IList<?>) obj;
			
			if(other.isEmpty())
				return false;
			
			if(head.equals(other.head()))
				return tail.equals(other.tail());
			else
				return false;
		}

		@Override
		public IList<T> drop(int n) {
			if(n == 0)
				return this;
			else
				return tail.drop(n - 1);
		}
		
	}
	
	/**
	 * This class represents the last element of an IList (the empty link)
	 * @param <T>
	 */
	public static class Nil<T> extends IList<T> {

		@Override
		public T head() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IList<T> tail() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public T get(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public IList<T> reverseAcc(IList<T> acc) {
			return acc;
		}

		@Override
		public IList<T> filterAcc(Function<T, Boolean> p, IList<T> acc) {
			return acc.reverse();
		}

		@Override
		protected <U> IList<U> mapAcc(Function<T, U> f, IList<U> acc) {
			return acc.reverse();
		}

		@Override
		protected <U> U foldLeftAcc(BiFunction<U, T, U> f, U acc) {
			return acc;
		}

		@Override
		public Nothing foreach(Function<T, Nothing> f) {
			return nothing;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof IList))
				return false;
			else 
				return ((IList<?>) obj).isEmpty();
		}

		@Override
		public IList<T> drop(int n) {
			return this;
		}
	}
	
}
