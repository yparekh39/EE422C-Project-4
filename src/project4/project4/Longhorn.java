package project4;

public class Longhorn extends Critter {
	private String identity;
	private int lastDir;
	private int age;
	
	
	public Longhorn(){
		int isBevo = getRandomInt(10);
		if(isBevo == 9)
			identity = "I am Bevo";
		lastDir = 0;
		age = 0;
	}
	
	//Graze
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		if(lastDir == 0)
			lastDir = 4;
		else
			lastDir = 0;
		
		walk(lastDir);
		//there can only be one! or a few...
		if(identity != "I am Bevo"){
			if(age == 20 || age == 40){
				Longhorn offspring = new Longhorn();
				reproduce(offspring, 0);
			}
			
		}
		
		age++;

	}

	//Bevo never runs from a fight
	@Override
	public boolean fight(String oponent) {
		if(identity == "I am Bevo")
			return true;
		return false;
	}
	
	public String toString(){
		if(identity == "I am Bevo")
			return "B";
		else
			return "L";
	}
	
	public static void runStats(java.util.List<Critter> longhorns){
		System.out.println("There are " + longhorns.size() + " longhorns.");
		
		for(Critter c: longhorns){
			Longhorn current = (Longhorn) c;
			if(current.identity == "I am Bevo")
			System.out.println("At least one of them is Bevo-worthy!");
		}		
	}

}
