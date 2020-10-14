import java.util.List;


public interface SimpleOrderedList<T> {


	// Diese 3 Methoden müssen thread-safe sein
	 public boolean add( T item );// thread-safe !
	 public boolean remove( T item ); // thread-safe !
	 public boolean contains( T item ); // thread-safe !

	 // Weiterhin (primär um das Testen zu erleichtern und) nicht thread-safe, da dies vermutlich zu schwierig wird
	 public List<T> toList();
	 public String toString(); // sinnvoll implementiert !

	}//interface


