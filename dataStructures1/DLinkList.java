package dataStructures1;

import java.util.Iterator;

public class DLinkList<E> implements Iterable<E>
{

	private DLinkList<E> next; // head
	private DLinkList<E> prev; // tail
	private int count;
	
	public DLinkList()
	{
		next = this;
		prev = this;
	}

	private void addNode(DLinkList<E> node, E value)
	{
		//private caller for public input methods, new node is added after input node
		node.next.prev = new DLinkListNode(node.next, node, value);
		node.next = node.next.prev;
		count++;
	}
	
	public void addEnd(E value)
	{
		//public add node at end of list
		addNode(this.prev, value);
	}

	public void addStart(E value)
	{
		//public add node at start of list
		addNode(this,value);
	}
	
	public void addIndex(E value, int index)
	{
		//public add node at selected index
		DLinkList<E> node = findNode(index);
		addNode(node.prev,value);
	}
	
	public void replace(E value, int index)
	{
		//public replace node at index
		DLinkList<E> node = findNode(index);
		node.prev.next = new DLinkListNode(node.next,node.prev,value);
		node.next.prev = node.prev.next;
	}
	
	public void remIndex(int index)
	{
		if (count == 0)
		{
			throw new IllegalArgumentException("There are no nodes to remove.");
		}

		DLinkList<E> node = findNode(index);
		node.prev.next = node.next;
		node.next.prev = node.prev;
		count--;
			
	}
	
	public E getIndex(int index)
	{
		if (count == 0)
		{
			return null;
		}

		DLinkListNode node = (DLinkListNode) findNode(index);
		return node.value;
	}
	
	public int getLength()
	{
		return count;
	}
	
	private DLinkList<E> findNode(int index)
	{
		//if count is zero this returns the sentinel
		if (count == 0)
		{
			return this;
		}
		
		if (index >= count || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		
		DLinkList<E> node = this;
		//search from the front or rear, whichever is shortest
		if (index == 0 || count/index >= 2)
		{
			for (int i = 0; i < (index+1); i++)
			{
				node = node.next;
			}
		}
		else
		{
			for (int i = 0; i < (count-index); i++)
			{
				node = node.prev;
			}
		}

		return node;
	}
	
	private class DLinkListNode extends DLinkList<E>
	{
		private E value;

		private DLinkListNode(DLinkList<E> next, DLinkList<E> prev, E value)
		{
			DLinkList<E> temp = this;
			temp.next = next;
			temp.prev = prev;
			this.value = value;
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		return new DLinkIterator(this);
	}

	protected class DLinkIterator implements Iterator<E>
	{
		private final int COUNT;
		private int position;
		
		private DLinkIterator(DLinkList<E> obj) {
			COUNT = obj.count;
			position = 0;
		}
		
		@Override
		public boolean hasNext() {
			return COUNT>position ? true:false;
		}
		
		@Override
		public E next() {
			E value = getIndex(position);
			position++;
			return value;
		}
	}
	
	public static <T extends Comparable<T>> void insertionSort(DLinkList<T> obj)
	{
		final int LESSTHAN = -1;
		int L = obj.getLength();
		obj = obj.next;
		
		for (int i = 1; i < L; i++)
		{
			obj = obj.next;
			T value = ((DLinkList<T>.DLinkListNode) obj).value;
			DLinkList<T> nextNode = obj;
			
			for (int j = i; j > 0; j--)
			{
				nextNode = nextNode.prev;
				T nextValue = ((DLinkList<T>.DLinkListNode) nextNode).value;
				
				if (value.compareTo(nextValue) != LESSTHAN)
				{
					break;
				}
				
				if (j == 1)
				{//if we get to the last position, increment an additional time so that insertion happens at the front
					nextNode = nextNode.prev;
				}
			}
			
			DLinkList<T> temp = obj.next;//store current iteration point
			
			obj.prev.next = obj.next;
			obj.next.prev = obj.prev;
			
			obj.next = nextNode.next;
			obj.prev = nextNode;
			
			nextNode.next.prev = obj;
			nextNode.next = obj;
			
			obj = temp.prev;
		}
	}
}