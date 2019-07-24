package dataStructures1;

public class DlinkTest {

	public static void main(String[] args) 
	{
		DLinkList<Integer> A = new DLinkList<Integer>();
		
		A.addStart(8);
		A.addStart(3);
		A.addEnd(0);
		A.addEnd(51);
		A.addEnd(-20);
		//A.remIndex(1);
		//A.remIndex(7);
		A.addIndex(2, 1);
		A.addStart(100);
		A.addStart(-5);
		A.addEnd(500);
		A.addEnd(0);
		A.addEnd(-20);
		A.addEnd(17);
		
		DLinkList.insertionSort(A);
		
		for (Integer i : A)
		{
			System.out.println(i);
		}
	}

}
