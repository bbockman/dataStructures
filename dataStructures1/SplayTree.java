package dataStructures1;

import java.util.ArrayList;

public class SplayTree<E extends Comparable<E>> {
	SplayTreeNode<E> root;
	SplayTreeNode<E> min;
	SplayTreeNode<E> max;
	int count;
	
	public SplayTreeNode<E> insert(E _value) {
		SplayTreeNode<E> node;
		
		if (root != null) {
			node = root.findHelper(_value);
			node = node.insertHelper(_value);
		}else {
			root = new SplayTreeNode<>(_value,root,this);
			root.parent = root;
			node = root; max = root; min = root;
		}
		if (node.value.compareTo(max.value) == 1) {
			max = node;
		}else if (node.value.compareTo(min.value) == -1) {
			min = node;
		}
		count++;
		return node;
	}

	/**
	 * Removes the node by replacing with smallest right descendant.
	 */
	void delete(SplayTreeNode<E> _node) {
		SplayTreeNode<E> temp = _node;
		if (temp.right != null) {
			temp = temp.right;
			int steps = 0;
			while (temp.left != null) {
				temp = temp.left;
				steps++;
			}
			if (steps != 0) {
				temp.parent.left = null;
			}else {
				temp.parent.right = null;
			}
			_node.value = temp.value;
		}else {
			//right node was null so we need to find which descendant we are
			if (_node == root) {
				root = _node.left;
				if (root != null) {root.parent = root;}
			}
			if (_node.parent.value.compareTo(_node.value) == -1) {
				_node.parent.right = _node.left;
			}else {
				_node.parent.left = _node.left;
			}
			if (_node.left != null) {_node.left.parent = _node.parent;}
		}
		if (_node == max || _node == min) {
			max = root; min = root;
			updateMinMax();
		}
	}
	
	void updateMinMax(){
		if (root == null){max = null; min = null; return;}
		TraversalFunction<E> updateFnc = (SplayTreeNode<E> tree) -> {
			if(tree.value.compareTo(tree.header.max.value) == 1) {
				tree.header.max = tree;
			}else if(tree.value.compareTo(tree.header.min.value) == -1) {
				tree.header.min = tree;
			}};
			
		root.inOrderTrav(updateFnc);
	}
	
	public SplayTreeNode<E> getMax(){
		return max;
	}
	public SplayTreeNode<E> getMin(){
		return min;
	}
	public SplayTreeNode<E> find(E _value){
		SplayTreeNode<E> temp = root.findHelper(_value);
		if (temp.value.compareTo(_value) == -1) {
			if (temp.right != null) {
				return temp.right;
			}
		}else if (temp.value.compareTo(_value) == 1) {
			if (temp.left != null) {
				return temp.left;
			}
		}else {
			return temp;
		}
		
		return null; //not equal but path is null
	}
	
	/**
	 * Function to pass to tree traversal method.
	 * @author Brooks
	 *
	 * @param <L>
	 */
	interface TraversalFunction<L extends Comparable<L>>{
		void travFunc(SplayTreeNode<L> tree);
	}
	
	@Override
	public String toString() {
		ArrayList<E> myList = new ArrayList<>();
		TraversalFunction<E> addToList = (SplayTreeNode<E> tree) -> {myList.add(tree.value);};
		root.inOrderTrav(addToList);
		return myList.toString(); 
	}
}


class SplayTreeNode<T extends Comparable<T>> {
	T value;
	SplayTreeNode<T> right;
	SplayTreeNode<T> left;
	SplayTreeNode<T> parent;
	SplayTree<T> header;
	
	SplayTreeNode(T _value, SplayTreeNode<T> _parent, SplayTree<T> _header){
		value = _value;
		parent = _parent;
		header = _header;
	}

	/** 
	 * Finds parent of node with specific value or finds 
	 * where it would be in the tree if it existed.  This is
	 * a helper function that can be used for both insert and
	 * standard find.
	 * @param _value value to search for
	 * @return resulting node
	 */
	SplayTreeNode<T> findHelper(T _value){
		if (this.value.compareTo(_value) == -1) {
			if (this.right != null) {
				return this.right.findHelper(_value);
			}
		}else if (this.value.compareTo(_value) == 1) {
			if (this.left != null) {
				return this.left.findHelper(_value);
			}
		}else {
			return this.parent;
		}
		return this; //not equal, but path is null
	}
	
	/**
	 * Create and insert a new node with _value under the provided
	 * node. If values match, insert on left.
	 * @param _node to be parent node
	 * @param _value to be node value
	 */
	SplayTreeNode<T> insertHelper(T _value) {
		SplayTreeNode<T> temp = new SplayTreeNode<>(_value, this,this.header);
		if (this.value.compareTo(_value) == -1) {
			temp.right = this.right;
			this.right = temp;
		}else {	
			temp.left = this.left;
			this.left = temp;
		}
		return temp;
	}
	
	/**
	 * In order tree traversal method with arbitrary process function.
	 * @param fnc Process function lambda expression.
	 */
	void inOrderTrav(SplayTree.TraversalFunction<T> fnc) {
		if (this.left != null) {
			this.left.inOrderTrav(fnc);
		}
		fnc.travFunc(this);
		if (this.right != null) {
			this.right.inOrderTrav(fnc);
		}
	}
}

