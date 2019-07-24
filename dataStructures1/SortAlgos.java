package dataStructures1;

interface SortAlgos<E>
{
	static final int LESSER = -1;
	static final int GREATER = +1;
	static final int EQUALTO = 0;
	
	static <T extends Comparable<T>> void insertionSort(DLinkList<T> input)
	{//slow version of insertion sort for DLink lists see DLinkList class for faster method
		for (int i = 0; i < input.getLength(); i++)
		{
			int j = i;
			while (j > 0)
			{
				int result = input.getIndex(j-1).compareTo(input.getIndex(i));
				if (result == LESSER || result == EQUALTO)
				{
					break;
				}
				j--;
			}
			
			if (j == i)
			{
				continue;
			}
			
			input.addIndex(input.getIndex(i), j);
			input.remIndex(i+1);
		}
	}
	
	void addIndex(E value, int index);
	//this should place value in index shifting other values right
	
	void remIndex(int index);
	//this should remove the value at the index shifting values left
	
	void replace(E value, int index);
	//this should replace the value at the index
	
	E getIndex(int index);
	
	int getLength();
}

