import java.util.LinkedList;
import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	IntervalNode<T> root;
	int numNodes;
	int treeHeight;
	
	public IntervalTree(){
		this.root = null;
		this.numNodes = 0;
		this.treeHeight = 0;
	}
	
	public IntervalTree(IntervalNode<T> root){
		this.root = root;
		this.numNodes = 1;
		this.treeHeight = 1;
	}
	
	@Override
	public IntervalNode<T> getRoot() {
		// TODO Auto-generated method stub
		return this.root;
	}

	/*
	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
	}
	
	*/

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

	/*
	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	@Override
	public List<IntervalADT<T>> searchPoint(T point) { //Do this
		
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
		
		List<IntervalADT<T>> list = new LinkedList();
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

	/*
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub

	}

	*/
}
