import java.util.LinkedList;
import java.util.Queue;



public class Runway {
			private int secondsForLanding;
			private int secondsForTakeOff;
			private int runwayTimeLeft;
				
			
			public Runway(int L, int D) {
				secondsForLanding = L;
				secondsForTakeOff = D;
				runwayTimeLeft = 0;
						
			}
			
			public boolean isBusy() {
				return (runwayTimeLeft > 0);
			}
			
			public void reduceRemainingTime() {
				if (runwayTimeLeft > 0)
					runwayTimeLeft--;
			}
			
			public void startLanding() {
				if (runwayTimeLeft > 0)
					throw new IllegalStateException("Runway is already busy.");
				runwayTimeLeft = secondsForLanding;
			}
			
			public void startDepart() {
				if (runwayTimeLeft > 0)
					throw new IllegalStateException("Runway is already busy.");
				runwayTimeLeft = secondsForTakeOff;
			}

			
			public static String runwaySimulate(int LandingTime, int DepartTime, int amtFuel, double departProb, double arrivalProb, int totalTime) {
				 Queue<Integer> arrivalPlanes = new LinkedList<Integer>();
				 Queue<Integer> departPlanes = new LinkedList<Integer>(); 
				 Queue<Integer> FuelRemaining = new LinkedList<Integer>();
				
				  
				 int next;
				 int i;
				 AirBooleanSource arrival = new AirBooleanSource(arrivalProb);
				 AirBooleanSource depart = new AirBooleanSource(departProb);
				 Runway runway = new Runway(LandingTime, DepartTime);
				 AirAverager arriveWaitTimes = new AirAverager();
				 AirAverager departWaitTimes = new AirAverager();
				 int crashes = 0;
				 int currentMinute;
				 int thisFuel = 0;
				 
					String temp = "";
					temp += "Time to Land: " + LandingTime + "\n";
					temp += "Arrival rate: " + arrivalProb + "\n";
					temp += "Time to takeoff: " + DepartTime + "\n";
					temp += "Departure rate: " + departProb + "\n";
					temp += "Simulation Time: " + totalTime + "\n";		
				 
				 //Check Precondition
				 if (LandingTime <= 0 || arrivalProb < 0 || arrivalProb > 1 || totalTime < 0 || DepartTime <= 0 || departProb < 0 || departProb > 1) 
					 throw new IllegalArgumentException("Values out of range.");				
				 //Simulate the passage of Time
				 for (currentMinute = 0; currentMinute < totalTime; currentMinute++) {		
	
					 			
					 //Check whether an airplane has arrived for takeoff or departure
					 if (arrival.query()) 
						 arrivalPlanes.add(currentMinute);	
					 	 FuelRemaining.add(amtFuel);
					 	 
					 if (depart.query()) 
						 departPlanes.add(currentMinute);
					 
					 //Check whether we can start using the runway for Arrivals
					 if((!runway.isBusy() && !arrivalPlanes.isEmpty() && (FuelRemaining.peek() > 0))) {	
						 next = arrivalPlanes.remove();
						 FuelRemaining.remove();
						 arriveWaitTimes.addNumber(currentMinute - next);
						 runway.startLanding();
					 }
					 
					 if ((!runway.isBusy()) && (!departPlanes.isEmpty()) && arrivalPlanes.isEmpty()) {
							 next = departPlanes.remove();
							 departWaitTimes.addNumber(currentMinute - next);
							 runway.startDepart();
					
					 }
					 
					 if((!arrivalPlanes.isEmpty()) && (FuelRemaining.peek() <= 0)) {
							arrivalPlanes.remove();
						 	FuelRemaining.remove(amtFuel);
						 	crashes++;	 
					 }
					 
					if (runway.isBusy() && !arrivalPlanes.isEmpty() && !FuelRemaining.isEmpty()) { 
						 for (i = 0; i < FuelRemaining.size(); i++)  {
					 			thisFuel = ((LinkedList<Integer>) FuelRemaining).get(i);
						 		thisFuel =  thisFuel -1;
					 			((LinkedList<Integer>) FuelRemaining).set(i, thisFuel);
						 }
					}
				 
					 
					 runway.reduceRemainingTime();
					 
		}
 	 
				 
				
				 temp += "Airplanes Landed: " + arriveWaitTimes.howManyNumbers() + "\n";
				 temp += "Airplanes Departed: " + departWaitTimes.howManyNumbers() + "\n";
				 temp += "Number of crashes: " + crashes + "\n";
				 
				 if(arriveWaitTimes.howManyNumbers() > 0)
					 temp += "Average wait for Land: " + arriveWaitTimes.average() + " mins\n";
				 if(departWaitTimes.howManyNumbers() > 0)
					 temp+= "Average wait for Depart: " + departWaitTimes.average() + " mins\n";
				 
				return temp + " \n**************************************************\n"; 
			
	
	
				
			}
}	
		 


