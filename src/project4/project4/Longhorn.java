package project4;

public class Longhorn extends Critter {
	private String identity;
	private int lastDir;
	
	
	public Longhorn(){
		int isBevo = getRandomInt(10);
		if(isBevo == 9)
			identity = "I am Bevo";
		lastDir = 0;
	}

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		if(lastDir == 0)
			lastDir = 4;
		else
			lastDir = 0;
		walk(lastDir);

	}

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
