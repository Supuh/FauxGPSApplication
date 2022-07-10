package GPS;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
* The PriorityQueue class implements a PriorityQueue
* in the form of a MaxHeap.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class PriorityQueue<T extends Comparable<? super T>> {

	//====================================================================================== Properties
	private static final int DEFAULT_CAPACITY = 11;
	private T[] heap;
	private int size;
	
	//====================================================================================== Constructors
	public PriorityQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	public PriorityQueue(int capacity) {
		if (capacity <= 0) { capacity = DEFAULT_CAPACITY; }
		
		heap = (T[])new Comparable[capacity];
		clear();
	}
	
	public PriorityQueue(T[] entries) {
		this(entries.length + 1);
		size = entries.length;
		for (int index = 0; index < entries.length; index++) {
			heap[index + 1] = entries[index];
		}
		for (int rIndex = size / 2; rIndex > 0; rIndex--) {
			reheapDown(rIndex);
		}
	}
	
	//====================================================================================== Helper Methods
	private void verifyCapacity() {
		if (size == heap.length - 1) heap = Arrays.copyOf(heap, 2 * heap.length);
	}
	
	private void reheapUp(int index) {
		if (index < 2) return;
		int parentIndex = index / 2;
		if (getCompare(index, parentIndex) > 0) {
			swap(index, parentIndex);
		}
		reheapUp(parentIndex);
	}
	
	private void reheapDown(int index) {
		if (isLeaf(index)) {
			return;
		}
		int child = maxChild(index);
		if (heap[index].compareTo(heap[child]) < 0) {
			swap(index, child);
		}

		reheapDown(child);
	}
	
	private boolean isLeaf(int pos) {
		if (pos * 2 > size) {
			return true;
		} else if (heap[pos * 2] == null && heap[(pos * 2) + 1] == null) {
			return true;
		}
		return false;
	}
	
	private int maxChild(int num) {
		if (heap[num * 2] == null) {
			return (num * 2) + 1;
		}
		if ((num * 2) + 1 >= size || heap[(num * 2) + 1] == null) {
			return (num * 2);
		}
		return (heap[num * 2].compareTo(heap[(num * 2) + 1]) > 0) ? num * 2 : num * 2 + 1;
	}
	
	private int getCompare(int i, int j) {
		return heap[i].compareTo(heap[j]);
	}
	
	private void swap(int i, int j) {
		T tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < heap.length; i++) {
			ret += ", " + heap[i];
		}
		
		return "[" + ret.substring(2) + "]";
	}
	
	//====================================================================================== Methods
	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == heap.length;
	}

	public void clear() {
		Arrays.fill(heap, 0, (size + 1), null);
		size = 0;
	}

	public int size() {
		return size;
	}

	public void add(T newEntry) {
		verifyCapacity();
		heap[++size] = newEntry;
		reheapUp(size);
	}

	public T peek() {
		return (isEmpty() ? null : heap[1]);
	}

	public T remove() {
		if (isEmpty()) throw new NoSuchElementException();
		T ret = heap[1];
		heap[1] = heap[size];
		heap[size--] = null;
		reheapDown(1);
		return ret;
	}
	
}
