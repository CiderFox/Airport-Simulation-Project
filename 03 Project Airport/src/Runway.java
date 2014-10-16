
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;



public class Runway {
			private int secondsForLanding;
			private int secondsForTakeOff;
			private int runwayTimeLeft;
			//private int Fuel;
			
			public Runway(int L, int D) {
				secondsForLanding = L;
				secondsForTakeOff = D;
				runwayTimeLeft = 0;
				//this.Fuel = Fuel;
						
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

			
			

			
			public static String runwaySimulate(int LandingTime, int DepartTime, int amtFuel, double departProb, double arrivalProb, int totalTime, int numRunways, int LandingTime2, int DepartTime2) {
				 Queue<Integer> arrivalPlanes = new LinkedList<Integer>();
				 Queue<Integer> departPlanes = new LinkedList<Integer>(); 
				 Queue<Integer> FuelRemaining = new LinkedList<Integer>();
				  
				 int next;
				 int i;
				 int drop;
				 AirBooleanSource arrival = new AirBooleanSource(arrivalProb);
				 AirBooleanSource depart = new AirBooleanSource(departProb);
				 Runway runway  = new Runway(LandingTime, DepartTime);
				 Runway runway2 = null;
				 AirAverager arriveWaitTimes = new AirAverager();
				 AirAverager departWaitTimes = new AirAverager();
				 int crashes = 0;
				 int currentMinute;
				 int thisFuel = 0;
				
			
				 
				 if (numRunways == 2) {
					runway2 = new Runway(LandingTime2, DepartTime2);
				 }

					String temp = "";
					
					temp += "Time to Land: 	" + LandingTime + "\n";
					temp += "Arrival rate: 		" + arrivalProb + "\n";
					temp += "Time to takeoff:	" + DepartTime + "\n";
					temp += "Departure rate:	" + departProb + "\n";
					temp += "Simulation Time: 	" + totalTime + "\n";		
				 
				 //Check Precondition
				 if (LandingTime <= 0 || arrivalProb < 0 || arrivalProb > 1 || totalTime < 0 || DepartTime <= 0 || departProb < 0 || departProb > 1) 
					 JOptionPane.showMessageDialog(null, "Landing time must be greater than 0\n"
					 		+ "Probabilities must range from  0.0 to 0.99\n"
					 		+ "Simulation Time must be greater than 0", "You Entered Wrong Input", JOptionPane.PLAIN_MESSAGE);

				 //Simulate the passage of Time
				 for (currentMinute = 0; currentMinute < totalTime; currentMinute++) {	
		
					 //Check whether an airplane has arrived for takeoff or departure
					 if (arrival.query()) {
						 arrivalPlanes.add(currentMinute);	
					 	 FuelRemaining.add(amtFuel);
					 }
					 
					 if (depart.query()) {
						 departPlanes.add(currentMinute);
					 }
					 
					 //Check whether we can start using the runway for Arrivals
					 if(runway2 == null) {
						 
						 //Check whether we can start using the runway for Arrivals
						 if((!runway.isBusy() && !arrivalPlanes.isEmpty() && (FuelRemaining.peek() > 0))) {	
							 next = arrivalPlanes.remove();
							 drop = FuelRemaining.remove();
							 arriveWaitTimes.addNumber(currentMinute - next);
							 runway.startLanding();
							 
						 }
						 
						 if ((!runway.isBusy()) && (!departPlanes.isEmpty()) && arrivalPlanes.isEmpty()) {
								 next = departPlanes.remove();
								 departWaitTimes.addNumber(currentMinute - next);
								 runway.startDepart();
						
						 }
						 
						 if((!arrivalPlanes.isEmpty()) && (FuelRemaining.peek() <= 0)) {
								drop = arrivalPlanes.remove();
							 	drop = FuelRemaining.remove();
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
						 
					 	}else{
	
					 		
					 		if(!runway.isBusy() && !arrivalPlanes.isEmpty() && (FuelRemaining.peek() > 0)) {	
								 next = arrivalPlanes.remove();
								 drop = FuelRemaining.remove();
								 arriveWaitTimes.addNumber(currentMinute - next);
								 runway.startLanding();

							 }
					 		
						 	if(!runway2.isBusy() && !arrivalPlanes.isEmpty() && (FuelRemaining.peek() > 0) && runway.isBusy()) {	
								 next = arrivalPlanes.remove();
								 drop = FuelRemaining.remove();
								 arriveWaitTimes.addNumber(currentMinute - next);
								 runway2.startLanding();
							 }
						 	if (arrivalPlanes.isEmpty()) {
						 		
						 		if(!runway.isBusy() && !departPlanes.isEmpty()) {
						 			next = departPlanes.remove();
						 			departWaitTimes.addNumber(currentMinute - next);
						 			runway.startDepart();
						 		}
						 	
						 		if(runway.isBusy() && !departPlanes.isEmpty() && !runway2.isBusy()) {
						 			next = departPlanes.remove();
						 			departWaitTimes.addNumber(currentMinute - next);
						 			runway2.startDepart();
						 		}
						 	}

						 	
						 if(runway2.isBusy() && runway.isBusy() && !arrivalPlanes.isEmpty() && !FuelRemaining.isEmpty())  {
								 for (i = 0; i < FuelRemaining.size(); i++)  {
							 			thisFuel = ((LinkedList<Integer>) FuelRemaining).get(i);
								 		thisFuel =  thisFuel -1;
							 			((LinkedList<Integer>) FuelRemaining).set(i, thisFuel);
								}
						 	}
						 
						 	runway.reduceRemainingTime();
							runway2.reduceRemainingTime();
							if((!arrivalPlanes.isEmpty()) && (FuelRemaining.peek() <= 0)) {
								drop = arrivalPlanes.remove();
							 	drop = FuelRemaining.remove();
							 	crashes++;	 
						 	}
					 }
				 }
				 
				 if (!departPlanes.isEmpty()&& (currentMinute == totalTime)) {
					 for (i = 0; i < departPlanes.size(); i++)  {
					 		next = ((LinkedList<Integer>) departPlanes).remove(i);
					 		departWaitTimes.addNumber(next);
					 }	
				 }

			
				 temp += "Airplanes Landed: 	" + arriveWaitTimes.howManyNumbers() + "\n";
				 temp += "Airplanes Departed: 	" + departWaitTimes.howManyNumbers() + "\n";
				 temp += "Number of crashes: 	" + crashes + "\n";
				 
				 if(arriveWaitTimes.howManyNumbers() > 0)
					 temp += "Average wait for Land: 	" + arriveWaitTimes.average() + " mins\n";
				 if(departWaitTimes.howManyNumbers() > 0) 
					 temp+= "Average wait for Depart: 	" + departWaitTimes.average() + " mins\n";
				 	
				return temp; 
			}
			}	
		 


