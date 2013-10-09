package fr.bluepyth.java.funchelper.immutable;

import fr.bluepyth.java.funchelper.function.F1;


public abstract class IList<T> {
	
	public static <U> IList<U> nil() {
		return new Nil<U>();
	}
	
	public static <U> IList<U> list(U... elements) {
		IList<U> list = nil();
		for(int i = elements.length - 1; i >= 0; i--) {
			list = list.prepend(elements[i]);
		}
		return list;
	}
	
	public abstract T head();
	public abstract IList<T> tail();
	public abstract boolean isEmpty();
	public abstract T get(int i);
	
	public IList<T> reverse() {
		return reverseAcc(IList.<T>nil());
	}
	protected abstract IList<T> reverseAcc(IList<T> acc);
	
	public IList<T> filter(F1<T, Boolean> predicate) {
		return filterAcc(predicate, IList.<T>nil());
	}
	protected abstract IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc);
	
	public <U> IList<U> map(F1<T,U> f) {
		return mapAcc(f, IList.<U>nil());
	}
	protected abstract <U> IList<U> mapAcc(F1<T,U> f, IList<U> acc);
	
	// foreach?, mkString?
	public IList<T> prepend(T element) {
		return new Cons<T>(element, this);
	}
	
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
		public IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc) {
			return tail.filterAcc(p, p.apply(head) ? acc.prepend(head) : acc);
		}

		@Override
		protected <U> IList<U> mapAcc(F1<T, U> f, IList<U> acc) {
			return tail.mapAcc(f, acc.prepend(f.apply(head)));
		}
		
	}
	
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
		public IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc) {
			return acc.reverse();
		}

		@Override
		protected <U> IList<U> mapAcc(F1<T, U> f, IList<U> acc) {
			return acc.reverse();
		}
		
	}
	
}
