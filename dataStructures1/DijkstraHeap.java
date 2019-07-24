package dataStructures1;

/**
 * A binary heap for specific use with Dijkstra's shortest path.
 * @author Brooks
 */
public class DijkstraHeap {
	
	private int[] array;
	private int[] position;
	private int[] distance;
	private int size;
	
	/**
	 * See static factory method {@link initializeHeap}
	 */
	private DijkstraHeap() {}
	
	/**
	 * Static factory method for constructing new heap objects.
	 * @param _size Size of heap.
	 * @param start Starting vertex for Dijkstra's
	 * @return New heap object.
	 */
	static DijkstraHeap initializeHeap(int _size, int start){
		DijkstraHeap heap = new DijkstraHeap();
		heap.size = _size;
		heap.array = new int[_size+1];
		heap.position = new int[_size+1];
		heap.distance = new int[_size+1];
		for (int i = 0; i < _size; i++) {
			heap.distance[i] = Integer.MAX_VALUE;
		}
		for (int i = 1; i <= _size; i++) {
			heap.array[i] = i;
		}
		heap.distance[start] = 0;
		heap.array[1] = start;
		heap.position[start] = 1;
		return heap;
	}
	
	/**
	 * Heapify method that is used only for updating a new vertex weight.
	 * This method assumes distances can only decrease.
	 * @param _position
	 */
	void heapify(int _position) {
		int target = array[_position];
		int parent = array[_position/2];
		int current = target;
		while (current != 1 && distance[target] < distance[parent]) {
			array[current] = parent;
			position[parent] = current;
			current /= 2;
			parent = array[current/2];
		}
		array[current] = target;
		position[target] = current;
	}
	
	
		
}
