package dataStructures1;

class BagTree extends SplayTree<Integer> {
	final Integer  BAG_SIZE;
	int remainder;
	SplayTreeNode<Integer> closest;

	BagTree(Integer size){
		BAG_SIZE = size;
		this.root = this.insert(BAG_SIZE);
	}

	void bagPlace(Integer _value){
		remainder = BAG_SIZE+1;
		closest = null;
		bagFindHelper(_value,root);
		if (closest == null) {closest = this.insert(BAG_SIZE);}
		this.delete(closest);
		closest.value -= _value;
		//System.out.println(closest);
		this.insert(closest.value);
	}

	void bagFindHelper(Integer _value, SplayTreeNode<Integer> _node){
		int difference = _node.value - _value;
		if (difference > 0) {
			if (difference < remainder) {remainder = difference; closest = _node;}
			if (_node.left != null) {bagFindHelper(_value,_node.left);}
		}
		if (difference < 0) {
			if (_node.right != null) {bagFindHelper(_value,_node.right);}
		}
	}
}
