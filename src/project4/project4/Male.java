package project4;
/* CRITTERS <MyClass.java>
 * EE422C Project 4 submission by
 * Matt Owens
 * mo8755
 * 16340
 * Yash Parekh
 * yjp246
 * 16345
 * Slip days used: 1
 * Fall 2015
 */

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
