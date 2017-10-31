//Name: Casey Au
//class : cs241
//date: 10-28-16

/**
*	TreeMap implemented as Binary Search Tree
*/

import java.util.TreeSet;

public class MyTreeMap<K extends Comparable<K>,V> implements MyMap<K,V> {
	private BinaryTree<Element> map;
	java.util.Set<K> keys;  // to return keys in order
	private int size;
	
	public boolean containsKey(K key){
		Element k = new Element(key, null);//makes Element object with empty value. when checks map, it only looks at key
		if (map == null){
			return false;
		} else if(search(k, map) != null){
			return true;
		}
		return false;
	}
	
	public V put(K key, V value){
		insert(new Element(key, value));
		size++;
		return value;
	}
	
	//returns value associated with key
	public V get(K key){
		Element k = new Element(key, null);
		if (search(k, map) != null){//finds element that key is in 
			return search(k, map).value;//returns value of key 
		} else{
			return null;
		}
	}
	
	public V remove(K key){
		if (containsKey(key) == false){
			return null;
		}
		Element k = new Element(key, null);//creates element with key value and no 
		Element temp = search(k, map);
		Element r = delete(map, temp, null);
		size--;
		return r.value;
		}
	
	public int size(){
		return size;
	}
	
	public int height(){
		if (map == null){
			return -1;
		}
		return height(map);
	}
	
	public String toString(){
		return map.toString();
	}
	
	public java.util.Set<K> keySet(){
		if (map == null){
			return null;
		}
		keys = new TreeSet<K>();//initializes keys
		inorder(map);
		return keys;
	}
	

//Element class
	private class Element{
	K key; V value;
	public Element(K key, V value){
		this.key = key;
		this.value = value;
	}
	public int compareTo(Element that){
		if (key.compareTo(that.key) == 0){
			return 0;
		}else if (key.compareTo(that.key) < 0){
			return -1;
		} else{
			return 1;
		}
	}
	public String toString(){
		return key+"";
	
	}
  }
	
//private methods implementing BST operations search, insert, delete, inorder
//reference: Wikipedia article on Binary Search Tree
//
	private Element search(Element element, BinaryTree<Element> tree){//return element if found or else null
		/*
		1. if tree is empty, return null
		2. let r be the element at root
		3. if e = r return r
		4. else if e < r return search(e, lef t)
		5. else return search(e, right)
		*/
		if (tree == null){
			return null;
		}
		if (element.compareTo(tree.getRoot()) == 0){
			return tree.getRoot();
		}else if (element.compareTo(tree.getRoot()) < 0){
			return search(element, tree.getLeft());
		} else{
			return search(element,tree.getRight());
		}

	}
	
	private Element insert(Element element){//return old element if key exists, else return null 
		/*
		1. if tree is empty, create new tree with e at root and return null
		2. else return insert(e, tree)
		*/
		if (map == null){
			map = new BinaryTree<Element>(element);
			return null;
		} else{
			return insert(element, map);
		}
	}
	
	private Element insert(Element element, BinaryTree<Element> tree){
		/*
		1. let r be element at root
		2. e = r set root to e and return r
		3. else if e < r
			3.1. if lef t = null attach e to left and return null
			3.2. else return insert(e, lef t)
		4. else {e > r}
			4.1. if right = null attach e to right and return null
			4.2. else return insert(e, right)
		*/
		if (element.compareTo(tree.getRoot()) == 0){
			size--;//decrement for the case that it finds the same key there before cause whenever insert, it adds one
			tree.setRoot(element);
			return tree.getRoot();
		} else if(element.compareTo(tree.getRoot()) < 0){
			if (tree.getLeft() == null){
				BinaryTree<Element> tempTree = new BinaryTree<Element>(element);
				tree.setLeft(tempTree);
				return null;
			} else{
				
				return insert(element, tree.getLeft());
			}
		} else{
			if(tree.getRight() == null){
				
				BinaryTree<Element> tempTree = new BinaryTree<Element>(element);
				tree.setRight(tempTree);
				return null;
			} else{
				
				return insert(element, tree.getRight());
			}
		}
	}
	
	private Element delete(BinaryTree<Element> tree, Element element, BinaryTree<Element> parent){//return element deleted, or null if not found
		/*
		1. if tree is empty, return null
		2. let r be the element at root x
		3. if e < r return delete(e, lef t)
		4. else if e > r return delete(e, right)
		5. else {e = r}
			5.1. if node x is a leaf, delete it
			5.2. else if x has only one child, delete x and promote child
			5.3. else {x has two children}
				5.3.1. find inorder successor node s of x with element f
				5.3.2. set element at x to f
				5.3.3. delete s and promote its only child
			5.4. return r
		*/
		if (tree == null){
			return null;
		}
		if (element.compareTo(tree.getRoot()) < 0){
			return delete(tree.getLeft(), element, tree);
		} else if(element.compareTo(tree.getRoot()) > 0){
			return delete(tree.getRight(), element, tree);
		} else{
			if (tree.isLeaf() == true){//leaf
				if (element.compareTo(parent.getLeft().getRoot()) == 0){
					parent.setLeft(null);
				} else{
					parent.setRight(null);
				}
			} else if(tree.getRight() == null || tree.getLeft() == null){//x has one child
				if (tree.getRight() == null){
					promote(tree, parent, tree.getLeft());
				} else{
					promote(tree, parent, tree.getRight());
				}
			} else{//x has 2 children
				//left most element of right subtree
				BinaryTree<Element> temp = tree.getRight();
				BinaryTree<Element> tempParent = tree;
				while(temp.getLeft() != null){
					tempParent = temp;
					temp = temp.getLeft();
				}
				tree.setRoot(temp.getRoot());//deleting the replaced element from the original place
				delete(temp,temp.getRoot(),tempParent);		
			}
			return element;
		}
	}
	
//make newChild the appropriate (left or right) child of parent, if parent exists
	private void promote(BinaryTree<Element> tree, BinaryTree<Element> parent, BinaryTree<Element> newChild){
		if (tree.getLeft() == null){
			if (parent == null){
				map.setRoot(newChild.getRoot());
			} else{
				parent.setLeft(newChild);
			}
		} else{
			if (parent == null){
				map.setRoot(newChild.getRoot());
			} else{
				parent.setRight(newChild);
			}
		}
		
	}
	
	private void inorder(BinaryTree<Element> tree){
		if (tree.getLeft() != null){
			inorder(tree.getLeft());
		}
		keys.add(tree.getRoot().key);
		if (tree.getRight() != null){
			inorder(tree.getRight());
		}
	}
	
	private int height(BinaryTree<Element> tree){
		if (tree == null){
			return 0;
		} else if (tree.isLeaf()){
			return 0;
		} else {
			return 1 + Math.max(height(tree.getLeft()), height(tree.getRight()));
		}
		
	}
}	



	



