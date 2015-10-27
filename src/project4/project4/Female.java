package project4;

public class Female extends Critter {

	public String toString() { return "F"; }
	
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		Critter child;
		if (Critter.getRandomInt(2) == 0) { child = new Male(); }
		else { child = new Female(); }
		
		if (oponent.equals("M")) {
			reproduce(child, Critter.getRandomInt(8));
		}
		return true;
	}
	
	public static void runStats(java.util.List<Critter> females) {
		System.out.println("There are " + females.size() + " females alive.");
	}

}
