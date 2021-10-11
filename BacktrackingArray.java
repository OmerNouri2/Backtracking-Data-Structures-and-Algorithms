public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;		// in each step we push to the stack an 'Action' object that we created in another class
    private int[] arr;
    private int arrayEnd;		// the index of the last number we inserted to the array - pointer to the end of the array
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.arrayEnd = -1;		// if the array is empty, 'arrayEnd' = -1
    }

    @Override
    public Integer get(int index){
    	if(index>arrayEnd)
    		return null;
    	return arr[index];
    }

    @Override
    public Integer search(int x) {
        for (int i=0;i<=arrayEnd;i=i+1)		// for loop until the end of the inserted numbers in the array
        {
        	if (arr[i]==x)
        		return i;
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
        arr[arrayEnd+1]=x;		
        stack.push(new Action<Integer>(x,arrayEnd+1,true));		// we save in the 'Action' object the inserted number, it's index & a boolean which means the last operation was an 'insert'
        arrayEnd=arrayEnd+1;		// when a number is inserted we should increase 'arrayEnd' by 1
    }

    @Override
    
    public void delete(Integer index) {
    	stack.push(new Action<Integer>(arr[index],index,false));	// false means the operation was 'delete'. We also save the number we deleted and it's previous index
    	arr[index] = arr[arrayEnd];		// switch between the deleted value and the last value
    	arrayEnd = arrayEnd-1;
    }

    @Override
    public Integer minimum() {
        if (arrayEnd==-1)		// array is empty
        	return -1;
    	int min=0;
        for (int i=1;i<=arrayEnd;i=i+1) {
        	if(arr[i]<arr[min])
        		min=i;
        }
        return min;
    }

    @Override
    public Integer maximum() {
    	 if (arrayEnd==-1)		// array is empty
         	return -1;
    	int max=0;
        for (int i=1;i<=arrayEnd;i=i+1) {
        	if(arr[i]>arr[max])
        		max=i;
        }
        return max;
    }

    @Override
    public Integer successor(Integer index) {
    	if(index>arrayEnd)
    		return -1;
    	int successor=maximum();
        for (int i=0;i<=arrayEnd;i=i+1) {		// we start at the maximum value and we search for the smallest value that is bigger than 'arr[index]'
        	if(arr[i]>arr[index]&&arr[i]<arr[successor])
        		successor=i;
        }
        if (successor==index)		// arr[index] is the biggest value in the array therefore there is no successor
        	return -1;		
        return successor;
    }

    @Override
    public Integer predecessor(Integer index) {
    	if(index>arrayEnd)
    		return -1;
    	int predecessor=minimum();
        for (int i=0;i<=arrayEnd;i=i+1) {// we start at the minimum value and we search for the biggest value that is smaller than 'arr[index]'
        	if(arr[i]<arr[index]&&arr[i]>arr[predecessor])
        		predecessor=i;
        }
        if (predecessor==index)		// arr[index] is the smallest value in the array therefore there is no predecessor
        	return -1;
        return predecessor;
    }

    @Override
    public void backtrack() {
       Action<Integer> lastAction = (Action<Integer>) stack.pop();
       if(lastAction.getAction())		// the last action was 'insert'
    	   arrayEnd=arrayEnd-1;
       else {		// lastAction was delete
    	   arrayEnd = arrayEnd+1;
    	   arr[arrayEnd] = arr[lastAction.getIndex()];
    	   arr[lastAction.getIndex()]  = lastAction.getValue();
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
