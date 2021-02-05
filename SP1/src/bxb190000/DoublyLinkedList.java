package bxb190000;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;

		Entry(E x, Entry<E> next, Entry<E> prev) {
			super(x, next);
			this.prev = prev;
		}
	}

	public DoublyLinkedList() {
		head = new Entry<>(null, null, null);
		tail = head;
		size = 0;
	}

	public void add(T x) {
		add(new Entry<>(x, null, null));
	}

	public void add(Entry<T> ent) {
		Entry<T> currTail = (Entry<T>) tail;
		super.add(ent);
		((Entry<T>) tail).prev = currTail;
	}

	public DLLIterator<T> iterator() {
		return new DLLIterator<T>();
	}

	protected class DLLIterator<X> extends SLLIterator {
		DLLIterator() {
			super();
		}

		public boolean hasPrev() {
			return cursor != head && ((Entry<T>) cursor).prev != head;
		}

		public T prev() {
			if (!hasPrev()) {
				throw new NoSuchElementException();
			}
			cursor = ((Entry<T>) cursor).prev;
			return cursor.element;
		}

		public void add(T t) {
			Entry<T> ent = new Entry<>(t, null, null);
			if (hasPrev()) {
				ent.prev = ((Entry<T>) cursor).prev;
				ent.next = cursor;
				((Entry<T>) cursor).prev.next = ent;
				((Entry<T>) cursor).prev = ent;
			} else {
				ent.next = head.next;
				if (((Entry<T>) head.next) != null) {
					((Entry<T>) head.next).prev = ent;
				} else {
					tail = ent;
				}
				ent.prev = (Entry<T>) head;
				head.next = ent;
			}
			size++;
		}

		public void remove() {
			super.remove();
			((Entry<T>) cursor.next).prev = (Entry<T>) cursor;
		}
	}

	public static void main(String[] args) {
		generalTest();
		emptyListInsertionTest();
	}
	
	private static void emptyListInsertionTest(){
		System.out.println();
		System.out.println("emptyListInsertionTest Output");
		DoublyLinkedList<Integer> lst1 = new DoublyLinkedList<>();
		DoublyLinkedList<Integer>.DLLIterator<Integer> dit = lst1.iterator();
		lst1.printList();
		System.out.println("hasNext: "+dit.hasNext() + ", hasPrevious: " + dit.hasPrev());
		dit.add(10);
		dit.add(20);
		lst1.printList();
		System.out.println("hasNext: "+dit.hasNext() + ", hasPrevious: " + dit.hasPrev());
		System.out.println("next: "+dit.next() + ", hasNext: "+dit.hasNext() + ", hasPrevious: " + dit.hasPrev());
		System.out.println("next: "+dit.next() + ", hasNext: "+dit.hasNext() + ", hasPrevious: " + dit.hasPrev());
		System.out.println("previous: "+dit.prev() + ", hasNext: "+dit.hasNext() + ", hasPrevious: " + dit.hasPrev());
	}
	
	private static void generalTest(){
		int n = 10;

		DoublyLinkedList<Integer> lst1 = new DoublyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst1.add(i);
		}

		lst1.printList();

		DoublyLinkedList<Integer>.DLLIterator<Integer> dit = lst1.iterator();
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1:
				if (dit.hasNext()) {
					System.out.println(dit.next());
				} else {
					break whileloop;
				}
				break;
			case 2:
				dit.remove();
				lst1.printList();
				break;
			case 3:
				if (dit.hasPrev()) {
					System.out.println(dit.prev());
				} else {
					break whileloop;
				}
				break;
			case 4:
				dit.add(in.nextInt());
				lst1.printList();
				break;
			default:
				break whileloop;
			}
		}
		lst1.printList();
	}

}

/*
 *
 * Sample Input: 1 1 4 20 1 1 4 50 3 1 1 2 0
 *
 *
Sample Output:
1
2
11: 1 20 2 3 4 5 6 7 8 9 10
3
4
12: 1 20 2 3 50 4 5 6 7 8 9 10
50
4
5
11: 1 20 2 3 50 4 6 7 8 9 10
11: 1 20 2 3 50 4 6 7 8 9 10
0:
hasNext: false, hasPrevious: false
2: 20 10
hasNext: true, hasPrevious: false
next: 20, hasNext: true, hasPrevious: false
next: 10, hasNext: false, hasPrevious: true
previous: 20, hasNext: true, hasPrevious: false
 */