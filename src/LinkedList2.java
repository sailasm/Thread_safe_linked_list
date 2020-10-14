import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedList2<T> implements SimpleOrderedList<T> {

	Node<T> head = new Node<T>(null);
	protected Comparator<T> comparator = null;

	public LinkedList2(final Comparator<T> comp) {
		comparator = comp;
	}

	public boolean add(T item) {
		if (item == null) {  // Don't add nulls
			System.out.printf(item + " adding of item failed because item is null \n");
			return false;
		}
		Node<T> newnode = new Node<T>(item); //get the new node
		Node<T> current = head; // current is head in start
		current.lock.lock(); // lock the current node

		while (current.next != null && comparator.compare(current.next.data, item) >= 0) { 
			Node<T> last = current; // last is current
			current.next.lock.lock(); //lock the newly chosen node
			current = current.next; // get the value of the new node
			last.lock.unlock(); //unlock the last so other threads can start to work on it
		}
		if (current.next != null) { 
			newnode.next = current.next; //value is not in the end 

		} //or value has to be inserted into the end of the list 
		current.next = newnode;
		current.lock.unlock();
		System.out.printf(item + " was added \n");
		return true;
	}

	public boolean remove(T item) {
		if (item == null) {
			return false; //no nulls allowed here 
		}
		Node<T> prev = head; //Get head so we start at the beginning
		prev.lock.lock(); //lock the current 
		Node<T> next = prev.next; //get the next for iterations purposes
		while (next != null) { 
			next.lock.lock(); //always lock the new node
			if (next.data == item) { //if we get a match
				prev.next = next.next; //delete the old node 
				next.lock.unlock(); //unlocks
				prev.lock.unlock();
				System.out.printf(item + " was removed \n");
				return true;
			} else {
				prev.lock.unlock(); //if not found continue going through the list 
				prev = next;
				next = prev.next;
			}
		}
		prev.lock.unlock(); //if we end here it means we did not found a node to remove
		return false;
	}

	public boolean contains(T item) {
		Node<T> next = head; // start from the head
		while (next != null) { //continue while we are not in the end
			next.lock.lock(); //lock the current at the start of every loop 
			if (next.data == item) { //item found 
				next.lock.unlock();  //always unlock before leaving 
				System.out.printf(item + " is in the list  \n");
				return true;
			}
			next.lock.unlock(); //unlock the old one 
			next = next.next; //get a new node 
		}
		System.out.printf(item + " is not in the list  \n");
		return false; //if we are here we found nothing
	}

	public synchronized String toString() {
		String res = "[ ";
		Node<T> next = head;
		while (next != null) {
			res = res + next.data.toString() + " ";
			next = next.next;
		}
		res = res + " ]";
		return res;
	}

	public synchronized List<T> toList() {
		List<T> list = new ArrayList<T>();
		Node<T> next = head;
		while (next != null) {
			list.add((T) next.data);
			next = next.next;
		}

		return list;
	}

	private class Node<T> {
		final private Lock lock;
		T data = null;
		Node<T> next = null;

		Node(T o) {
			data = o;
			this.lock = new ReentrantLock();
		}

		Node(T o, Node<T> n) {
			data = o;
			next = n;
			this.lock = new ReentrantLock();
		}
	}

}
