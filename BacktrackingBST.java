public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;		// used for backtracking
    private Stack redoStack;	// used for retracking
    BacktrackingBST.Node root = null;
    private boolean backtracked;		// indicates if the last action/s were 'backtrack'
    
    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }
	
    public Node search(int x) {
        return search(root,x);
    }

    private Node search(Node curr, int x) {		//assisting recursive function for finding the Node with 'x' key
    	if(curr==null || curr.getKey()==x)
    		return curr;
    	else if(curr.getKey()>x)
    		return search(curr.left,x);
    	else
    		return search(curr.right,x);
    }
    
    public void insert(BacktrackingBST.Node z) {
    	backtracked = false;		// the last action is not backtrack
    	BacktrackingBST.Node x = root;		// x is used for the current node
    	BacktrackingBST.Node y = null;		// y represents the parent of x
    	while (x!=null) {		// we haven't reached a leaf
    		y=x;
    		if(z.getKey()>x.getKey())
    			x=x.right;
    		else if(z.getKey()<x.getKey())
    			x=x.left;
    	}
    	z.setParent(y);
    	if(y==null)		//the tree is empty
    		root=z;
    	else if(z.getKey()<y.getKey())	
    		y.left=z;		// z is the left child of y
    	else
    		y.right=z;
    	stack.push(new Action<BacktrackingBST.Node>(z,z.getKey(),z.getParent(),null,true));		// we add to the stack an Action object that saves the node we inserted, it's key, it's parent node & 'true'-for insertion action
    }
    
    public void delete(Node x) {
    	backtracked = false;		// the last action is not backtrack
    	BacktrackingBST.Node splice;	// the node we remove from it's current place
    	if (x.right==null || x.left==null) {		// x has 1/0 children		
    		splice=x;
    	}
    	else		
    		splice = successor(x);		// if 'x' has 2 children we put it's successor instead of 'x'
    	BacktrackingBST.Node spliceChild;		// we put 'splice's child instead of 'splice'
    	BacktrackingBST.Node prevFather = splice.getParent();		// the father of the node that we should remove from it's place
    	int prevKey = x.getKey();		// the key of the node we delete
    	if(splice.left!=null)
    		spliceChild = splice.left;
    	else
    		spliceChild = splice.right;
    	if(spliceChild!=null)	
    		spliceChild.setParent(splice.getParent());
    	else		// 'spliceChild' = null
    		prevFather = splice.getParent();		// if 'splice' has no children we set 'prevFather' as the parent of 'splice' so we know where to put it back
    	if(splice.getParent()==null) {		// if we delete a root that has 0/1 children 
    		root=spliceChild;
    		prevFather = null;
    	}
    	else { 
    		if(splice == splice.getParent().left) {		// check whether 'splice' is the left child or right child before splicing
    			splice.getParent().left=spliceChild;		// we set 'spliceChild' to be the left child of 'splice's parent
    		}
    		else
    			splice.getParent().right=spliceChild;		// we set 'spliceChild' to be the right child of 'splice's parent
    	}
    	if(splice!=x) {		//deleting a node with two children
    		prevFather = splice.getParent();		// 'splice' is the successor so we set 'prevFather' as the parent of the successor so we know where to put him back
    		x.setKey(splice.getKey());		// we set 'x's key to be the successor's key    		
    	}
    	stack.push(new Action<BacktrackingBST.Node>(splice,prevKey,prevFather,x,false));		// we add to the stack an Action object that saves the node we deleted, it's key, the previous parentNode of the node that took 'x's place & 'false'-for deletion action
    }

    public Node minimum() {		// in BST the smallest value is in the leftmost node
    	if(root==null)		// tree is empty
    		return null;
    	BacktrackingBST.Node x = root;	
    	while(x.left!=null)
    		x=x.left;
    	return x;
    }

    public Node maximum() {		// in BST the biggest value is in the rightmost node
    	if(root==null)		// tree is empty
    		return null;
    	BacktrackingBST.Node x = root;	
    	while(x.right!=null)
    		x=x.right;
    	return x;
    }

    public Node successor(Node x) {			
    	if(x==maximum())		// if the node doesn't have a successor
    		return null;
    	if (x.right!=null)		// the following number after 'x' is the minimum number in 'x.right' subtree.
        	return x.right.minimum();		// We call the 'minimum' function from 'Node'
        BacktrackingBST.Node parent = x.getParent();
        while (parent!=null && parent.right==x) {		// if x.right = null then the successor = the right most ancestor of 'x'
        	x=parent;
        	parent = parent.parent;
        }
        return parent;
    }

    public Node predecessor(Node x) {
    	if(x==minimum())		// if the node doesn't have a predecessor
    		return null;
    	if(x.left!=null)		// the previous number before 'x' is the maximum number in 'x.left' subtree.
        	return x.left.maximum();		// We call the 'maximum' function from 'Node'
        BacktrackingBST.Node parent = x.getParent();
        while (parent!=null && parent.left==x) {		// if x.left = null then the predecessor = the left most ancestor of 'x'
        	x=parent;
        	parent = parent.parent;
        }
        return parent;
    }
    
    // A function we added to place a node with the key 'key' in a specific place in the tree
    private void insertHere(BacktrackingBST.Node addNode, BacktrackingBST.Node parentNode) {		
    	//BacktrackingBST.Node addNode = new Node(key, null);
    	addNode.right=null;
    	addNode.left=null;
    	addNode.setParent(parentNode);
    	if(parentNode.getKey()>addNode.getKey()) {		// in the case 'addNode' should be the left child of 'parentNode'
    		if(parentNode.left!=null) {		// set left child of 'parentNode' to be the child of 'addNode'
    			if(parentNode.left.getKey()>addNode.getKey()) 
    				addNode.right = parentNode.left;		
    			else 
    				addNode.left = parentNode.left;
    			parentNode.left.setParent(addNode);
    		}
    		parentNode.left = addNode;
    	}
    	else {				// in the case 'addNode' should be the right child of 'parentNode'
    		if(parentNode.right!=null) {
    			if(parentNode.right.getKey()>addNode.getKey()) 
    				addNode.right = parentNode.right;		
    			else 
    				addNode.left = parentNode.right;
    			parentNode.right.setParent(addNode);
    		}
    		parentNode.right = addNode;
    	}
    	
    }

    @Override
    public void backtrack() {
    	Action<BacktrackingBST.Node> lastAction = (Action<BacktrackingBST.Node>)stack.pop();
        BacktrackingBST.Node value = lastAction.getValue();		// the previous action was done on this node 
        if(lastAction.getAction()) {		// the previous action was 'insert'
        	delete(value);
        	stack.pop();		// we don't want to save the backtrack action
        }
        else {			// the previous action was 'delete'
        	if(lastAction.getIndex()==null) {		// if we deleted a root that had 1/0 children
        		root = value;
        		if(root.left!=null)
        			root.left.setParent(root);
        		else if(root.right!=null) 
        			root.right.setParent(root);
        	}
        	else {
        		insertHere(value, lastAction.getIndex());		// a function we added to return the key that replaced the deleted node to its previous place
        		lastAction.getDeleted().setKey(lastAction.getKey()); 	//in case the deleted node has two children we change the key back to it's original key
        	}
        }
        backtracked = true;		// the last action is backtrack
        redoStack.push(lastAction);		// we save the action we backtracked in case we want to use 'retrack'
    }

    @Override
    public void retrack() {
    	if(!backtracked)		// if last action was not backtrack
    		redoStack.clear();		// no 'retrack' can be done 
        if(!redoStack.isEmpty()) {
        	Action<BacktrackingBST.Node> lastAction = (Action<BacktrackingBST.Node>)redoStack.pop();
        	if(lastAction.getAction()) {		// if the last action was backtracking an insert action
        		insert(lastAction.getValue());
        	}
        	else {		// if the last action was backtracking a delete action
        		delete(lastAction.getDeleted());
        	}
        }
        if(!redoStack.isEmpty())	// 'retrack' function can still be done
        	backtracked=true;
    }

    public void printPreOrder(){
    	root.printPreOrder();		// we use an assisting recursive function from Node class
    }

    @Override
    public void print() {	
    	root.printPreOrder();		
    }

    public static class Node{
    	//These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }
        
        public int getKey() {
            return key;
        }
        
        public Object getValue() {
            return value;
        }
     //----------------------------------Assisting Function we added------------------------------------------------------------------------------
        
        private void setParent(BacktrackingBST.Node parent) {	//function for changing the private field 'parent'
        	this.parent=parent;
        }
        
        private void setKey(int key) {
        	this.key=key;
        }
        
        private Node getParent() {
        	return parent;
        }

        private Node minimum() {
        	BacktrackingBST.Node min = this;
        	while(min.left!=null)
        		min=min.left;
        	return min;
        }
        
        private Node maximum() {
        	BacktrackingBST.Node max = this;	
        	while(max.right!=null)
        		max=max.right;
        	return max;
        }

        private void printPreOrder(){
        	System.out.print(key+" ");
        	if(left != null)
        		left.printPreOrder();
        	if(right != null)
        		right.printPreOrder();
        }
    }

}
