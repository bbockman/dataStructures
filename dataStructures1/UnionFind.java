package dataStructures1;

public class UnionFind<E> {
	E self;
	UnionFind<E> parent;
	int size;
	
	UnionFind(E val, UnionFind<E> par){
		self = val;
		parent = par;
		size = 1;
	}
	
	UnionFind(E val){
		this(val,null);
		size = 1;
	}
	
	UnionFind<E> find() {
		UnionFind<E> cur = this;
		while (cur.parent != null) {
			cur = cur.parent;
		}
		return cur;
	}
	
	static <T> UnionFind<T> union(UnionFind<T> obj1, UnionFind<T> obj2) {
		UnionFind<T> root1 = obj1.find();
		UnionFind<T> root2 = obj2.find();
		
		if (root1.size > root2.size) {
			root2.parent = root1;
			root1.size += root2.size;
			return root1;
		}else {
			root1.parent = root2;
			root2.size += root1.size;
			return root2;
		}
	}
}
