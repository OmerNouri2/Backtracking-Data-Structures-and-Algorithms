public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;		// in each step we push to the stack an 'Action' object that we created in another class
    private int[] arr;
	private int arrayEnd;		// the index of the last number we inserted to the array - pointer to the end of the array
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        arrayEnd=-1;		// if the array is empty, 'arrayEnd' = -1
    }
    @Override
    public Integer get(int index){
    	if(index>arrayEnd)
    		return null;
    	return arr[index];
    }

    @Override
    public Integer search(int x) {		// binary search
    	int high = arr.length-1;
        int low = 0;
        while (high>=low) {        	
        	int mid = (high+low)/2;
        	if(arr[mid]==x)
        		return mid;
        	else if(arr[mid]<x)
        		low=mid+1;
        	else //if(arr[mid]>x)
        		high=mid-1;
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
    	int i = arrayEnd+1;
    	while (i > 0 && arr[i-1] > x){		// we start from the end of the array and move each value to the next index until we find the right index for 'x' in order that the array will stay sorted
    		arr[i] = arr[i-1];
    		i = i-1;
    		}
    	arr[i]=x;
    	stack.push(new Action<Integer>(x,i,true));		// we save in the 'Action' object the inserted number, it's index & a boolean which means the last operation was insertion
    	arrayEnd=arrayEnd+1;
    }
    

    @Override
    public void delete(Integer index) {
    	stack.push(new Action<Integer>(arr[index],index,false));		// 'false' means the last operation was insertion
    	for(int i=index;i<arrayEnd;i=i+1)
    	{
    		arr[i]=arr[i+1];
    	}
    	arrayEnd=arrayEnd-1;
    }

    @Override
    public Integer minimum() {		// in a sorted array the minimum is the first index
        if(arrayEnd==-1)	//if the array is empty
        	return -1;
    	return 0;
    }

    @Override
    public Integer maximum() {		// the maximum is the last index
        return arrayEnd;
    }

    @Override
    public Integer successor(Integer index) {		// in a sorted array the successor is 'arr[index+1]'
        if(index>=arrayEnd | arrayEnd==-1)		// if there is no successor or the array is empty
        	return -1;
    	return index+1;
    }

    @Override
    public Integer predecessor(Integer index) {		// in a sorted array the successor is 'arr[index-1]'
    	if(index==0 | index>arrayEnd)		// if there is no predecessor or the array is empty
        	return -1;
    	return index-1;
    }

    @Override
    public void backtrack() {
       Action<Integer> lastAction = (Action)stack.pop();
       if(lastAction.getAction()) {		// if the last action was insertion
    	   delete(lastAction.getIndex());
    	   stack.pop();		//when 'delete' is called a new action is pushed to the stack. When we use backtracking we don't want any trace to the last action
       }
       else {		// the last action was deletion
    	   insert(lastAction.getValue());
    	   stack.pop();		//when 'insert' is called a new action is pushed to the stack. When we use backtracking we don't want any trace to the last action
       }
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
    	for(int i=0;i<=arrayEnd;i=i+1)
        	System.out.print(arr[i]+" ");
    }
}
