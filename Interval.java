
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

    // TODO declare any needed data members
	private T start; 
	private T end;
	String label;

    public Interval(T start, T end, String label) {
        // TODO Auto-generated constructor stub
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    @Override
    public T getStart() {
        // TODO Auto-generated method stub
    	return start;
    }

    @Override
    public T getEnd() {
        // TODO Auto-generated method stub
    	return end;
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
    	return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	
    	//Other's start within interval
    	if (start.compareTo(other.getStart()) <= 0 &&
    			end.compareTo(other.getStart()) >= 0) {
    		return true;
    	}
    	//Other's end within interval
    	else if (start.compareTo(other.getEnd()) <= 0 &&
    			end.compareTo(other.getEnd()) >= 0) {
    		return true;
    	}
    	//Other interval contains this interval
    	else if (start.compareTo(other.getStart()) > 0 &&
    			end.compareTo(other.getEnd()) < 0) {
    		return true;
    	}
    	else {
    		return false;
    	}
  
    }

    @Override
    public boolean contains(T point) {
        // TODO Auto-generated method stub
    	
    	if (start.compareTo(point) <= 0 && end.compareTo(point) >=0 ) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    @Override
    public int compareTo(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	
    	if (other == null) {
    		return 0;
    	}
    	
    	int compare = start.compareTo(other.getStart());
    	if (compare == 0) {
    		return end.compareTo(other.getEnd());
    	}
    	else {
    		return compare;
    	}
    }

}
