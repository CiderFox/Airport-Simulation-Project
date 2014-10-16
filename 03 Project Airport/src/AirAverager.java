
public class AirAverager {
	private int count;
	private double sum;
	//private int crashes;
	
	public AirAverager() {
		count = 0;
		sum = 0;
		//crashes = 0;
	}
	
	public void addNumber(double value) {
		if (count == Integer.MAX_VALUE)
			throw new IllegalStateException("Too many numbers.");
		count ++;
		sum += value;
	}
	public double average() {
		if(count == 0) 
			return Double.NaN;
		else
			return sum/count;
	}
	
	public int howManyNumbers() {
		return count;
	}
	

	
//	public void noFuel() {
//		crashes++;
//	}
//	
//	public int crashes() {
//		return crashes;
//	}
}

