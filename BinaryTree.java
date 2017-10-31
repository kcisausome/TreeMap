//Name: Casey Au
//class : cs241
//date: 10-26-16

public class BinaryTree<E> {
  private E root;
  private BinaryTree<E> left, right;

// Constructors
  public BinaryTree(E root, BinaryTree<E> left, BinaryTree<E> right){
    this.root = root;
    this.left = left;
    this.right = right;
  }
  public BinaryTree(E root){
    this.root = root;
  }
    
// Methods
    public E getRoot(){
      return this.root;
    }

    public BinaryTree<E> getLeft(){
      return this.left;
    }
  
    public BinaryTree<E> getRight(){
      return this.right;
    }
    
    public E setRoot(E element){
      this.root = element;
      return this.root;
    }

  public BinaryTree<E> setLeft(BinaryTree<E> tree){ 
    if(tree == null){
      this.left = null;
      return this.left;
    }
    this.left = new BinaryTree<E>(tree.getRoot());
    return this.left;
  }
  
  public BinaryTree<E> setRight(BinaryTree<E> tree){
    if(tree == null){
      this.right = null;
      return this.right;
    }
    this.right = new BinaryTree<E>(tree.getRoot());
    return this.right;
  }
  
  public String toString(){
    StringBuilder buf = new StringBuilder("" + root);
    if(!isLeaf()){
      buf.append("(");
      if(left != null) buf.append(left);
      if(right != null) buf.append("," + right);
      buf.append(")");
    }
    return buf + "";
  }
    
    public boolean isLeaf(){
      if((getLeft()== null) && (getRight() == null)){
        return true;
      }else{
        return false;
      }
    }
}
  


