import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	private int size;
	private int height;
	private IntervalNode<T> root;
	
	
	//No arg constuctors
	public IntervalTree() {
		this.root = null;
		this.size = 0;
		this.height = 0;
	}
	
	//Constructor that initializes root
	public IntervalTree(IntervalNode<T> root) {
		this.root = root;
		this.size = 1;
		this.height = 1;
	}

	@Override
	public IntervalNode<T> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	/**
	 *<p>Each <code>Interval</code> is stored as the data item of an
	 * <code>IntervalNode</code>.  The position of the new IntervalNode 
	 * will be the position found using the binary search algorithm.
	 * This is the same algorithm presented in BST readings and lecture
	 * examples. 
	 * 
	 * <p>Tip: Call a recursive helper function with root node.
	 * In that call, traverse the tree using the binary search algorithm.
	 * Use the comparator defined in <code>Interval</code> and create a new
	 * IntervalNode to store the new <i>interval</i> item when you reach 
	 * the end of the tree.</p>
	 * 
	 * <p>This method must also check and possibly update the maxEnd 
	 * in the IntervalNode. Recall, that <b>maxEnd</b> of a node represents 
	 * the maximum end of current node and all descendant nodes.</p>
	 * 
	 * <p>Note: the key for comparison here will be the compareTo method defined
	 *  in interval class. You will use this for the interval stored in the node to
	 *  compare it with the input interval.s</p>
	 * 
	 * <p>If the start and end of the given interval match an existing 
	 * interval, throw an IllegalArgumentException.</p>
	 *  
	 * @param interval the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException if interval is null or is found 
	 * to be a duplicate of an existing interval in this tree.            
	 */
	@Override
	public void insert(IntervalADT<T> interval) //////
					throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		root = insert(interval, this.root);
		T temp = UpdateMaxEnd(root);

	}
	

	private IntervalNode<T> insert(IntervalADT<T> interval, 
			IntervalNode<T> node) {		
		
		if (node == null) {
			this.size++;
			//this.height++;
			node =  new IntervalNode<T>(interval);
			return node;
		}
		//Throw IllegalArgumentException if intervals same
		if (node.getInterval().compareTo(interval) == 0) {
			throw new IllegalArgumentException();
		}
		
		//Add to right sub-tree if interval greater than node's interval
		if (node.getInterval().compareTo(interval) < 0) {
			node.setRightNode(insert(interval, node.getRightNode()));
			if (node.getInterval().getEnd().compareTo(
					node.getRightNode().getInterval().getEnd()) < 0) {
				node.setMaxEnd(node.getRightNode().getInterval().getEnd());
			}
			return node;
		}
		
		//Else add to left sub-tree if interval comes before node's interval
		else {
			node.setLeftNode(insert(interval, node.getLeftNode()));
			if (node.getInterval().getEnd().compareTo(
					node.getLeftNode().getInterval().getEnd()) < 0) {
				node.setMaxEnd(node.getLeftNode().getInterval().getEnd());
			}
			return node;
		}
		
		
	}

	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		this.deleteHelper(root, interval);

	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		if(null == interval){
			throw new IllegalArgumentException();
		}
		else if(null == node){
			throw new IntervalNotFoundException(interval.toString());
		}
		
		
		int comp = node.getInterval().compareTo(interval);
		if(0 > comp){
			return this.delLeft(node.getLeftNode(), interval);
		}
		else if(0 < comp){ 
			return this.delRight(node.getRightNode(), interval);
		}
		else{
			return node;
		}
	
	}
	

	/**
	 * This method is meant to help the deleteHelper() method. It is called when
	 * the desired interval is greater than the current node's. It handles the
	 * deleting and replacing of the desired node and also updates the remaining
	 * nodes's maxEnd when necessary.
	 * 
	 * @param node
	 * @param interval
	 * @return del; The deleted node
	 * @throws IntervalNotFoundException
	 */
	private IntervalNode<T> delRight(IntervalNode<T> node, //DO This
			IntervalADT<T> interval) throws IntervalNotFoundException{
		
		IntervalNode<T> parent = node;
		IntervalNode<T> del = this.deleteHelper(node.getRightNode(), interval);
		
		if(node.getRightNode().equals(del)){
			IntervalNode<T> replace = del.getSuccessor();
			if(null == replace && null == del.getLeftNode()){
				parent.setRightNode(null);
				parent.setMaxEnd(parent.getInterval().getEnd());
			}
			else if(null == replace){
				parent.setRightNode(del.getLeftNode());
				parent.setMaxEnd(parent.getRightNode().getMaxEnd());
			}
			else{
				this.deleteHelper(del, replace.getInterval());
				replace.setLeftNode(del.getLeftNode());
				replace.setRightNode(del.getRightNode());
				replace.setMaxEnd(del.getMaxEnd());
				parent.setRightNode(replace);
			}
		}
		else{
			parent.setMaxEnd(parent.getRightNode().getMaxEnd());
		}
		return del;
	}
	
	/**
	 * This method is meant to help the deleteHelper() method. It is called when
	 * the desired interval is less than the current node's. It handles the
	 * deleting and replacing of the desired node and also updates the remaining
	 * nodes's maxEnd when necessary.
	 * 
	 * @param node
	 * @param interval
	 * @return del; The deleted node
	 * @throws IntervalNotFoundException
	 */
	private IntervalNode<T> delLeft(IntervalNode<T> node, //DO This
			IntervalADT<T> interval) throws IntervalNotFoundException{
		
		IntervalNode<T> parent = node;
		IntervalNode<T> del = this.deleteHelper(node.getLeftNode(), interval);
		
		if(node.getLeftNode().equals(del)){
			IntervalNode<T> replace = del.getSuccessor();
			if(null == replace && null == del.getLeftNode()){
				parent.setLeftNode(null);
			}
			else if(null == replace){
				parent.setRightNode(del.getLeftNode());
			}
			else{
				this.deleteHelper(del, replace.getInterval());
				replace.setLeftNode(del.getLeftNode());
				replace.setRightNode(del.getRightNode());
				replace.setMaxEnd(del.getMaxEnd());
				parent.setRightNode(replace);
			}
		}
		return del;
}


	@Override
	
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
		ArrayList<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
				
		
		return Overlap(interval, list, root);
	}
	
	private List<IntervalADT<T>> Overlap(IntervalADT<T> interval,
					List<IntervalADT<T>> list, IntervalNode<T> node) {
	
		if (node == null) {
			return list;
		}
		if (node.getInterval().overlaps(interval)) {
			list.add(node.getInterval());
		}	
		
		list = Overlap(interval, list, node.getLeftNode());
		list = Overlap(interval, list, node.getRightNode());
		
		return list;
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		// TODO Auto-generated method stub
		return this.searchPointHelper(point, root);
	}
	
	/**
	 * This method helps the searchPoint method. It handles searching all nodes
	 * for intervals containing the point making a list of intervals of the 
	 * ones that do.
	 * 
	 * @param point
	 * @param node
	 * @return list; A list of all intervals containing the point.
	 */
	private List<IntervalADT<T>> searchPointHelper(T point,
			IntervalNode<T> node){
		
		List<IntervalADT<T>> list = new LinkedList<IntervalADT<T>>();
		if(null == node){
			return list;
		}
		IntervalADT<T> interval = node.getInterval();
		if(interval.contains(point)){
			list.add(interval);
		}
		list.addAll(this.searchPointHelper(point, node.getLeftNode()));
		list.addAll(this.searchPointHelper(point, node.getLeftNode()));
		
		return list;
}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		
		
		this.height = heightHelper(root);
		return this.height;
	}
	
	private int heightHelper(IntervalNode<T> node) {
		
		if (node == null) {
			return 0;
			
		}
		else {
			//Path of right node larger
			if (heightHelper(node.getLeftNode()) < 
					heightHelper(node.getRightNode())) {
				return 1 + heightHelper(node.getRightNode());
			}
			else {
				return 1 + heightHelper(node.getLeftNode());
			}
		}
	
	}
	
	
	private T UpdateMaxEnd(IntervalNode<T> node) {
		if (node == null) {
			return null;
		}
		int compare = 0;
		int compareNew = 0;
		
		T tempL = UpdateMaxEnd(node.getLeftNode());
		T tempR = UpdateMaxEnd(node.getRightNode());
		
		//No kids
		if (tempL == null && tempR == null) {
			return node.getMaxEnd();
		}
		
		//Only has left child
		else if (tempR == null) { 
			compare = node.getMaxEnd().compareTo(tempL);
			if (compare < 0) {
				node.setMaxEnd(tempL);
				return tempL;
			}
		}
		
		//Only has right child
		else if (tempL == null) {
			compare = node.getMaxEnd().compareTo(tempR);
			if (compare < 0) {
				node.setMaxEnd(tempR);
				return tempR;
			}
		}
		
		//Two children
		else {
			compareNew = tempL.compareTo(tempR);
			if (compareNew <= 0) {
				compare = node.getMaxEnd().compareTo(tempR);
				if (compare < 0) {
					node.setMaxEnd(tempR);
					return tempR;
				}
			}
			else {
				compare = node.getMaxEnd().compareTo(tempL);
				if (compare < 0) {
					node.setMaxEnd(tempL);
					return tempL;
				}
			}
		}
		return node.getMaxEnd();
	}

	@Override
	/**
	 * Returns true if the tree contains an exact match for the start and end of the given interval.
	 * The label is not considered for this operation.
	 *  
	 * <p>Tip: Define and call a recursive helper function to call with root node 
	 * and the target interval.</p>
	 * 
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             	if interval is null.
	 * 
	 */
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
		return containsHelper(this.root, interval);
	
	}
	
	private boolean containsHelper(IntervalNode<T> node, 
			IntervalADT<T> interval) {
		if (interval == null) {
			throw new IllegalArgumentException();
		}
		
		if (node == null) {
			return false;
		}
		if (node.getInterval().compareTo(interval) < 0) {
			return containsHelper(node.getRightNode(), interval);
		}
		else if (node.getInterval().compareTo(interval) == 0) {
			return true;
		}
		else {
			return containsHelper(node.getRightNode(), interval);
		}
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + this.size);
		System.out.println("-----------------------------------------");

	}

}
