package dataStructures1;

import java.util.ArrayList;

public class ArrayUnionFind {
	
	static int find(int[] array, int in1) {
		ArrayList<Integer> list = new ArrayList<>();
		while (array[in1] != -1) {
			list.add(in1);
			in1 = array[in1];
		}
		for (int i = 0; i < (list.size()-1); i++) {
			array[list.get(i)] = in1;
		}
		return in1;
	}
	
	static int union(int[] array, int in1, int in2) {
		int root1 = find(array,in1);
		int root2 = find(array,in2);
		
		array[root2] = root1;
		
		return root1;
	}
	
	static void simpleUnion(int[] array, int in1, int in2) {
		array[in2] = in1;
	}
}
