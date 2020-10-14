import java.util.concurrent.locks.ReentrantLock;


public class ThreadSafeNode<T> {
	private ThreadSafeNode<T> nextNode;
	private T value;
	private ReentrantLock nodeLock;
	
	
	public ThreadSafeNode() {
		nodeLock = new ReentrantLock();
	}
	
	public void setNext(ThreadSafeNode<T> node) {
		this.nextNode = node;
	}

	public ThreadSafeNode<T> getNext() {
		return nextNode;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean hasNext() {
		return nextNode != null;
	}
	
	
	/**
	 * get exclusive access to this node
	 */
	public void acquireNode() {
		nodeLock.lock();
	}
	
	/**
	 * give up the exclusive access on this node
	 */
	public void releaseNode() {
		nodeLock.unlock();
	}
}