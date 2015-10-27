package project4;

public class Male extends Critter {
	
	public String toString() {
		return "M";
	}

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		int dir = Critter.getRandomInt(8);
		run(dir);
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		if (oponent.equals("F")) {
			walk(Critter.getRandomInt(8));
		}
		return true;
	}
	
	public static void runStats(java.util.List<Critter> men) {
		System.out.println("There are " + men.size() + " men alive");
	}

}
