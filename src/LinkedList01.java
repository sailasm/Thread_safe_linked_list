import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LinkedList01<T> implements SimpleOrderedList<T> {

	private LinkedList<T> list = new LinkedList<T>();

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public LinkedList01(final Comparator<T> comp) {

	}

	public boolean add(T item) {
		if (lock.isWriteLocked() || item.equals(null)) { //
			System.out.printf(item + " adding of item failed \n");
			return false;
		}

		lock.writeLock().lock();
		list.add(item);
		lock.writeLock().unlock();
		System.out.printf(item + " was added \n");
		return true;
	}

	public boolean remove(T item) {
		if (lock.isWriteLocked() || !list.contains(item)) {
			System.out.printf(item + " removing of item failed \n");
			return false;
		}

		lock.writeLock().lock();
		list.remove(item);

		lock.writeLock().unlock();
		System.out.printf(item + " was removed \n");
		return true;
	}

	public boolean contains(T item) {
		if (lock.getReadLockCount() > 0) {
		System.out.printf(item + " someone is already accessing \n");
		return false;
	}
		boolean lol = false;
		lock.readLock().lock();
		lol =  contains(item);
		lock.readLock().unlock();
		return lol;
//		if (lock.getReadLockCount() > 0) {
//			System.out.printf(item + " someone is already accessing \n");
//			return false;
//		}
//		//lock.writeLock().lock();
//		lock.readLock().lock();
//		if (!list.contains(item)) {
//			System.out.printf(item + " is not in the list  \n");
//			lock.readLock().unlock();
//			lock.writeLock().unlock();
//			return false;
//			
//		}else {
//			lock.readLock().unlock();
//			//lock.writeLock().unlock();
//			return true;
//			
//		}

	}

	public String toString() {

		Iterator<T> it = list.iterator();

		if (!it.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			T item = it.next();
			sb.append(item);
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(", ");
		}
	}

	@Override
	public List<T> toList() {
		// TODO Auto-generated method stub
		return null;
	}

}
