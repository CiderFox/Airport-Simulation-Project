
public class AirBooleanSource {
	private double probability;
	
	public AirBooleanSource(double p) {
		if((p < 0) || (1 < p)) 
			throw new IllegalArgumentException("Illegal p: " + p);
		probability = p;
	}
	
	public boolean query() {
		return(Math.random() < probability);
	}
	

}
