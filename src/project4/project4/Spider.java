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

public class Spider extends Critter {
	private int lastDir;
	private boolean movedLastStep;
	private int age;
	private String ageAppearance;
	
	public Spider(){
		lastDir = 0;
		movedLastStep = false;
		age = 0;
		ageAppearance = "X";
	}
	
	
	@Override
	public void doTimeStep() {
		//if a newborn, offset 2 to the right
		if(age == 0)
			run(lastDir);

		//if it moved last step, sit still this step
		//movement pattern is a square that reminds me of a web (though not a spiral that changes size)
		if(!movedLastStep){
			lastDir = (lastDir + 2) %8;
			walk(lastDir);
		}
		else
			movedLastStep = false;
		
		//reproduce every 4 steps
		if(age % 20 == 0){
			Critter offspring = new Spider();
			this.reproduce(offspring, 0);
		}
		
		
		age++;
	}

	//Gotta defend the "web"
	@Override
	public boolean fight(String oponent) {
		return true;
	}
	
	public String toString(){
		return ageAppearance;
	}
	
	public static void runStats(java.util.List<Critter> spiders){
		System.out.println("There are " + spiders.size() + " spiders.");
		int ageCount = 0;
		for(Critter c: spiders){
			Spider current = (Spider) c;
			if(current.age >= 20)
				ageCount++;
		}
		
		System.out.println("" + ageCount + " spiders are old and " + (spiders.size() - ageCount) + " are young." );
	}

}
