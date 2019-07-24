package dataStructures1;

import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Test {
	Graph graph;
	ArrayList<EdgeNode> eVect;
	PriorityQueue<EdgeNode> que;
	
	public static void main(String[] args) {
		Test myTest = new Test();
		File file = new File("C:\\Users\\Brooks\\Documents\\PROGRAMMING\\Eclipse\\GenFiles\\bin\\dataStructures1\\graph_in.txt");
		myTest.graph = new Graph(file);
		myTest.edgeVector();
		myTest.que = myTest.sortEdges();
		/*for (int i = 0; i < myTest.eVect.size(); i++) {
			System.out.println(myTest.que.poll().weight);
		}*/
		
		int count = 0;
		int[] setArray = new int[myTest.graph.nverts];
		int[] parentArray = new int[myTest.graph.nverts];
		for (int i = 0; i < setArray.length; i++) {
			setArray[i] = -1;
			parentArray[i] = -1;
		}
		System.out.println(myTest.que);
		while (count < myTest.graph.nverts-1) {
			EdgeNode peek = myTest.que.poll();
			//System.out.println(peek);
			int set1 = ArrayUnionFind.find(setArray,peek.parent);
			int set2 = ArrayUnionFind.find(setArray,peek.child);
			if (set1 != set2) {
				parentArray[peek.child] = peek.parent; 
				System.out.println(peek);
				ArrayUnionFind.simpleUnion(setArray, set1, set2);
				count++;
			}
		}
		for (int i = 0; i < parentArray.length; i++) {
			System.out.print("p: "+parentArray[i]+" ");
		}
		System.out.println();
		
	//pick smallest edge where each end is in different connected component
		//peek smallest edge
		//check if both ends are in the same set
			//if not connect the edges and union the sets
			//pop the edge off the que
		//repeat until edge count is nvert-1
		//note this requires checking that the graph is actually connected
		//also note that each undirected edge is currently having calcs run twice which is inefficient
		
	}
	
	void edgeVector() {
		eVect = new ArrayList<>(graph.nedges);
		for (int i = 0; i < graph.nverts; i++) {
			EdgeNode cur = graph.edges[i];
			while (cur != null) {
				//System.out.println("p "+cur.parent+" c "+cur.child+" w "+cur.weight);
				eVect.add(cur);
				cur = cur.next;
			}
		}
	}
	
	PriorityQueue<EdgeNode> sortEdges() {
		Comparator<EdgeNode> comp = new Comparator<EdgeNode>() {
			public int compare(EdgeNode en1, EdgeNode en2){
				//System.out.println("edge1 "+en1+" edge2 "+en2);
				//System.out.println("w1 "+en1.weight+" w2 "+en2.weight);
				if (en1.weight > en2.weight) {
					return 1;
				}else if (en1.weight < en2.weight) {
					return -1;
				}else {
					return 0;
				}
			}
		};
		PriorityQueue<EdgeNode> result = new PriorityQueue<EdgeNode>(comp);
		result.addAll(eVect);
		return result;
	}
}
