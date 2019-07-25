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
	public static DijkstraHeap initializeHeap(int _size, int start){
		if (_size < 1) {throw new IllegalArgumentException("Zero size DijkstraHeap is not supported.");}
		DijkstraHeap heap = new DijkstraHeap();
		heap.size = _size;
		heap.array = new int[_size+1];
		heap.position = new int[_size];
		heap.distance = new int[_size];
		for (int i = 0; i < _size; i++) {
			heap.distance[i] = Integer.MAX_VALUE;
			heap.array[i+1] = i;
			heap.position[i] = i+1;
		}
		heap.distance[start] = 0;
		int temp = heap.array[1];
		heap.array[1] = start;
		heap.array[start+1] = temp;
		temp = heap.position[start];
		heap.position[start] = 1;
		heap.position[0] = temp;
		return heap;
	}
	
	/**
	 * Heapify method that is used only for updating a new vertex weight.
	 * This method assumes distances can only decrease.
	 * @param _position Position in the heap array where the distance was changed.
	 */
	public void heapify(int _position) {
		if (_position < 0 || _position > size) {throw new IllegalArgumentException("Heapify index is out of bounds.");}
		int target = array[_position];
		int parent = array[_position/2];
		int k = _position; //current heap index
		while (k != 1 && distance[target] < distance[parent]) {
			array[k] = parent;
			position[parent] = k;
			k /= 2;
			parent = array[k/2];
		}
		array[k] = target;
		position[target] = k;
	}
	
	/**
	 * Pop the top of the heap, position for popped item is set to -1.
	 * @return Vertex number for item on top of heap.
	 */
	public int pop() {
		int last = array[size];
		int first = array[1];
		int k = 1; //current heap index
		size--;
		while (2*k <= size && distance[last] > distance[array[2*k]]) {
			array[k] = array[2*k];
			position[array[k]] = k;
			k *= 2;
		}
		array[k] = last;
		position[last] = k;
		position[first] = -1;
		return first;
	}
	
	/*GETTER METHODS*/
	
	/**
	 * Get size of heap.
	 * @return Current number of nodes in heap.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Get position of a given vertex in the heap. 
	 * @param vertex Vertex number (from 0 to n-1).
	 * @return Returns position of vertex in heap array. Returns -1 if it is not in the heap.
	 */
	public int getPosition(int vertex) {
		return position[vertex];
	}
	
	/**
	 * Get current distance estimate for the given vertex. 
	 * @param vertex Vertex number (from 0 to n-1).
	 * @return Returns current distance estimate for given vertex.
	 */
	public int getDistance(int vertex) {
		return distance[vertex];
	}
	
	/*SETTER METHODS*/
	
	/**
	 * Set distance for given vertex.
	 * @param vertex Vertex number (from 0 to n-1).
	 * @param dist Distance for given vertex (negative weights are not allowed).
	 */
	public void setDistance(int vertex, int dist) {
		if (dist < 0) {throw new IllegalArgumentException("Negative weights are not allowed.");}
		distance[vertex] = dist;
	}
	
	/*UTILITIES*/
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		String newline = "\n";
		string.append("array:   ");
		appendArray(string, array, size+1);
		string.append(newline);
		string.append("position:   ");
		appendArray(string, position, position.length);
		string.append(newline);
		string.append("distance:   ");
		appendArray(string, distance, distance.length);
		return string.toString();
	}
	
	private static void appendArray(StringBuilder string, int[] _array, int size) {
		String space = " ";
		for (int i = 0; i < size; i++) {
			string.append(_array[i]);
			string.append(space);
		}
	}
}
