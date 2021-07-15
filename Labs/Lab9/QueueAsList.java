package lab9;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
@SuppressWarnings("rawtypes")
public class QueueAsList implements Queue{
	private LinkedList<Object> lst;
	public QueueAsList() { lst = new LinkedList<Object>(); } 
	public boolean isEmpty() { return lst.size() == 0; }
	public Object dequeue() throws NoSuchElementException { 
		if(lst.size()>0) {
			Object x=lst.get(0);
			lst.remove(0);
			return x;
		}
		return null;
	}
	public void enqueue(Object o) { lst.add(o); } 
	public void clear() { LinkedList<Object> empty = new LinkedList<Object>(); lst = empty; } 
	public boolean contains(Object o) { return lst.contains(o); } 
	public boolean remove(Object o) { return lst.remove(o); }
	public int size() { return lst.size(); }
	public Object[] toArray() { return lst.toArray(); } 
	public Object[] toArray(Object[] a) {  
		LinkedList<Object> temp = new LinkedList<Object>();
		temp.add(a);
		return temp.toArray();
	}
	public boolean add(Object arg0) { return lst.add(arg0); } 
	public Object element() { return lst.get(0); } 
	public boolean offer(Object arg0) { return lst.add(arg0); } 
	public Object peek() { return lst.get(0); } 
	public Object poll() { return lst.get(0); } 
	public Object remove() { return dequeue(); }
	public Iterator<Object> iterator() { return lst.iterator(); }
	@SuppressWarnings("unchecked")
	public boolean addAll(Collection c) { return lst.addAll(c); }
	@Override
	public boolean containsAll(Collection c) { return lst.containsAll(c); }
	@Override
	public boolean removeAll(Collection c) { return lst.removeAll(c); }
	@Override
	public boolean retainAll(Collection c) { return lst.retainAll(c); }
}
