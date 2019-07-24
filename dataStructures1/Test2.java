package dataStructures1;

import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Test2 {
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
		
		/*
		 * Initialize heap with node for each vertex
		 * nodes should contain distance and vertex number
		 * heap should have a vector translating vertex number to heap position
		 * initial heap positions are arbitrary, use 1 to n
		 * starting vertex should be set to position 1 in heap and weight set to 0
		 * 
		 * while heap.size > 0
		 * 	pop top element v
		 * 	iterate over edges of element (v,u)
		 * 		if u == v error(not a simple graph)
		 * 		
		 * 		if distance[u] > distance[v] + w
		 * 			distance[u] = distance[v] + w
		 * 			heapify(heap, u)
		 * 
		 * heapify(heap, k, distance)
		 * 	parent = heap.a[heap.p[k]/2]
		 * 	current = heap.a[heap.p[k]]
		 * 	while distance[current] < distance[parent] && heap.p[k] != 1
		 * 		heap.a[k] = parent
		 * 		heap.p[parent] = heap.p[k]
		 * 		k /= 2
		 * 		parent = heap.a[k/2]
		 * 	heap.a[k] = current
		 * 	heap.p[current] = k
		 * 		
		 * heappop(heap, distance)
		 * 	last = heap.a[heap.size]
		 * 	heap.size--
		 * 	k = 1
		 * 	first = heap.a[k]
		 * 	heap.a[k] = last
		 * 	while 2*k <= heap.size && distance[last] > distance[heap.a[2*k]]
		 * 		heap.a[k] = heap.a[2*k]
		 * 		heap.p[heap.a[k].n] = k
		 * 		k *= 2
		 * 	heap.a[k] = last
		 * 	heap.p[last.n] = k
		 * 	return first
		 * 
		 * 	class dijkstraHeap
		 * 		int[] a = heap array 0, 1...n containing vertex indices
		 * 		p = node positions
		 * 		size
		 * 		initializeHeap(n)
		 * 			size = n
		 * 			a = new dijkstraNode[size+1]
		 * 			for i 2..a.length
		 * 				a[i] = MAX_INT
		 * 			a[0] = null
		 * 			a[1] = 0
		 * 			p = new int[size]
		 * 		
		 * 		
		 */
		
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
