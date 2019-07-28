package dataStructures1;

public class EdgeNode {
	int weight;
	public int child;
	public int parent;
	public EdgeNode next;
	
	EdgeNode(){
		this(-1,-1,-1);
	}
	
	EdgeNode(int _parent, int _child){
		this(_parent,_child,-1);
	}
	
	EdgeNode(int _parent, int _child, int _weight){
		child = _child;
		parent = _parent;
		weight = _weight;
	}
	
	@Override
	public String toString() {
		return ("c["+child+"] p["+parent+"] w["+weight+"] ");
	}
}
