public class Warmup {
	
	public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int fdChange = fd;		// variable for counting the search steps
    	for (int i=0;i<arr.length;i=i+1) {
        	myStack.push(i);		// we save the indexes we checked
        	if(arr[i]==x)
        		return i;
        	fdChange=fdChange-1;
        	if(fdChange==0) {		// check if we passed 'fd' steps in the array
        		fdChange=fd;
        		for(int j=0;j<=bk;j=j+1)
        			i=(int)myStack.pop();		// we go back to previous indexes
        	}
        }
    	return -1;
    }
    
    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	int high = arr.length-1;
        int low = 0;
        while (high>=low) {			        	
        	int isCon = isConsistent(arr);			
        	while (isCon!=0 && !myStack.isEmpty()) {		//invariant: the array is sorted at first	
        		isCon=isCon-1;
        		high = (int)myStack.pop();					// at each step of backtracking we pop the last 'low' & 'high' indexes to come back to previous ones		
        		low = (int)myStack.pop();
        	}
        	int mid = (high+low)/2;
        	myStack.push(low);				// at each step we push to the stack the low index and the high index
        	myStack.push(high);
        	if(arr[mid]==x)
        		return mid;
        	else if(arr[mid]<x)
        		low=mid+1;
        	else //if(arr[mid]>x)
        		high=mid-1;
        }
        return -1;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}
