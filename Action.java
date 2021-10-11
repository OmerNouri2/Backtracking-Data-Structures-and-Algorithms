
public class  Action<T>{
	private T value;	// in BST 'value' indicates the node that the action was done on him. In array value is inserted/deleted number	
	private T index;	// in BST 'index' indicates the father of the node that the action was done on him
	private T deleted;	// in BST, 'deleted' indicates the deleted node and is used in case that this node has 2 children.
	private int key;	// only used in BST and indicates the key of the node that the action was done on
	private boolean action; //true= insert, false = delete
	
	public Action(T value, T index, boolean action) {		// constructor for both array & sorted array
		this.value = value;
		this.index = index;
		this.action = action;
		this.deleted = null;
		this.key = -1;
	}
	
	public Action(T value, int key, T index, T deleted, boolean action) {		// constructor for both array & sorted array
		this.value = value;
		this.index = index;
		this.deleted=deleted;
		this.action = action;
		this.key = key;		
	}

	public T getValue(){	
		return value;
	}
	
	public T getIndex(){	// in BST 'index' indicates the father of the node that the action was done on him
		return index;
	}
	
	public boolean getAction(){
		return action;
	}
	
	public void setAction(boolean action) {
		this.action=action;
	}
	
	public int getKey() {
		return key;
	}
	
	public T getDeleted() {
		return deleted;
	}
}
