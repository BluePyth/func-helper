package fr.bluepyth.java.funchelper.immutable;


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
	// filter, map, foreach?
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
		
	}
	
}
