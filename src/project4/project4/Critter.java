/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Matt Owens
 * mo8755
 * 16340
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2015
 */
package project4;

import java.util.ArrayDeque;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
	private boolean hasMoved;
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		
		if(hasMoved)
			return;
			
		switch (direction) {
			case 0:
				this.x_coord = (x_coord+1) % Params.world_width;
				break;
			case 1:
				this.x_coord = (x_coord+1) % Params.world_width;
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord += Params.world_width;
				break;
			case 2:
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord += Params.world_width;
				break;
			case 3:
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord += Params.world_width;
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord += Params.world_width;
				break;
			case 4:
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord += Params.world_width;
				break;
			case 5:
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord += Params.world_width;
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 6:
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 7:
				this.x_coord = (x_coord+1) % Params.world_width;
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
		}
		hasMoved = true;
	}
	
	protected final void run(int direction) {
		if(hasMoved){
			this.energy -= Params.run_energy_cost;
			return;
		}
			
		walk(direction);
		hasMoved = false;
		walk(direction);
		this.energy += (2*Params.walk_energy_cost);
		this.energy -= Params.run_energy_cost;
		hasMoved = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = (this.energy/2);
		this.energy /= 2;
		switch (direction) {
			case 0:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				break;
			case 1:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				offspring.y_coord = (y_coord-1) % Params.world_height;
				break;
			case 2:
				offspring.y_coord = (y_coord-1) % Params.world_height;
				break;
			case 3:
				offspring.y_coord = (y_coord-1) % Params.world_height;
				offspring.x_coord = (x_coord-1) % Params.world_width;
				break;
			case 4:
				offspring.x_coord = (x_coord-1) % Params.world_width;
				break;
			case 5:
				offspring.x_coord = (x_coord-1) % Params.world_width;
				offspring.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 6:
				offspring.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 7:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				offspring.y_coord = (y_coord+1) % Params.world_height;
				break;
		}
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> critterClass;
		Object object;
		Constructor<?> constructor;
		try {
			critterClass = Class.forName(critter_class_name);
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			constructor = critterClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			object = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}
		if (!(object instanceof Critter)) {
			throw new InvalidCritterException(critter_class_name);
		}
		Critter newCritter = (Critter) object;
		newCritter.energy = Params.start_energy;
		newCritter.x_coord = rand.nextInt(Params.world_width);
		newCritter.y_coord = rand.nextInt(Params.world_height);
		population.add(newCritter);
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> critterClass;
		try {
			critterClass = Class.forName(critter_class_name);
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		for (Critter c : population) {
			if (critterClass.isInstance(c)) {
				result.add(c);
			}
		}
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
		
	public static void worldTimeStep() {
		/*call doTimeStep for each critter*/
		Iterator<Critter> iterator = population.iterator();
		while(iterator.hasNext()) {
			iterator.next().doTimeStep();
		}		

		/*Resolve Encounters and runaways*/
/*		System.out.println("Pre-encounter Step: pop size: " + population.size());
		for(int a = 0; a < population.size()-1; a++){
			Critter firstOccupier = population.get(a);
			for(int b = a+1; b < population.size(); b++){
				Critter secondOccupier = population.get(b);
				//ENCOUNTER HANDLING
				if(firstOccupier.x_coord == secondOccupier.x_coord && firstOccupier.y_coord == secondOccupier.y_coord){
					
					//firstOccupier still here and wants to fight?
					int oldX = firstOccupier.x_coord; int oldY = firstOccupier.y_coord;			
					boolean firstWantFight = firstOccupier.fight(secondOccupier.toString());					
					boolean firstStillHere = (oldX == firstOccupier.x_coord) && (oldY == firstOccupier.y_coord);
					
					//firstOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!firstStillHere){
						for(Critter current : population){
							if(firstOccupier.x_coord == current.x_coord && firstOccupier.y_coord == current.y_coord && firstOccupier != current){
								firstOccupier.x_coord = oldX;
								firstOccupier.y_coord = oldY;
								firstStillHere = true;
							}
						}
					}
						
					//secondOccupier still here and wants to fight?
					oldX = secondOccupier.x_coord; oldY = firstOccupier.y_coord;
					boolean secondWantFight = secondOccupier.fight(firstOccupier.toString());
					boolean secondStillHere = (oldX == secondOccupier.x_coord) && (oldY == secondOccupier.y_coord);
					
					//secondOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!secondStillHere){
						for(Critter current: population){
							if(secondOccupier.x_coord == current.x_coord && secondOccupier.y_coord == current.y_coord && secondOccupier != current){
								secondOccupier.x_coord = oldX;
								secondOccupier.y_coord = oldY;
								secondStillHere = true;
							}
						}
					}
					
					//both occupiers still alive after all that jazz?
					boolean bothAlive = firstOccupier.energy > 0 && secondOccupier.energy > 0;
					
					//FIGHT HANDLING
					//both critters still here and want to fight
					if(bothAlive && firstStillHere && secondStillHere){
						Critter winner, loser;
						//roll (critters that don't want to fight will always roll 0)
						int firstRoll = (firstWantFight ? 1:0) * getRandomInt(firstOccupier.energy+1);
						int secondRoll = (secondWantFight ? 1:0) * getRandomInt(secondOccupier.energy+1);
						//establish winner and loser
						if(firstRoll == secondRoll){
							winner = getRandomInt(2) == 1 ? firstOccupier:secondOccupier;//coin toss
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}	
						else{
							winner = firstRoll > secondRoll ? firstOccupier:secondOccupier;
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}
						winner.energy += (loser.energy/2);*/
						
						/*REMOVE LOSER DOES THIS WORK I THINK IT DOES BECAUSE IT REFERS TO THE ORIGINAL OBJECT*/
						/*population.remove(loser);
						if(loser == firstOccupier)
							a-=1;//reexamines the current position on next outer iteration because list of critters will have shifted to replace this critter
					}//END FIGHT HANDLING		
				}//END ENCOUNTER HANDLING
			}//end for loop b
		}//end for loop a
		
		/*ENCOUNTER HANDLING SECOND ATTEMPT*/
		int i = 0;
		while(i < population.size()){
			Critter firstOccupier = population.get(i);
			for(int b = i+1; b < population.size(); b++){
				Critter secondOccupier = population.get(b);
				//ENCOUNTER HANDLING
				if(firstOccupier.x_coord == secondOccupier.x_coord && firstOccupier.y_coord == secondOccupier.y_coord){
					
					//firstOccupier still here and wants to fight?
					int oldX = firstOccupier.x_coord; int oldY = firstOccupier.y_coord;			
					boolean firstWantFight = firstOccupier.fight(secondOccupier.toString());					
					boolean firstStillHere = (oldX == firstOccupier.x_coord) && (oldY == firstOccupier.y_coord);
					
					//firstOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!firstStillHere){
						for(Critter current : population){
							if(firstOccupier.x_coord == current.x_coord && firstOccupier.y_coord == current.y_coord && firstOccupier != current){
								firstOccupier.x_coord = oldX;
								firstOccupier.y_coord = oldY;
								firstStillHere = true;
							}
						}
					}
						
					//secondOccupier still here and wants to fight?
					oldX = secondOccupier.x_coord; oldY = firstOccupier.y_coord;
					boolean secondWantFight = secondOccupier.fight(firstOccupier.toString());
					boolean secondStillHere = (oldX == secondOccupier.x_coord) && (oldY == secondOccupier.y_coord);
					
					//secondOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!secondStillHere){
						for(Critter current: population){
							if(secondOccupier.x_coord == current.x_coord && secondOccupier.y_coord == current.y_coord && secondOccupier != current){
								secondOccupier.x_coord = oldX;
								secondOccupier.y_coord = oldY;
								secondStillHere = true;
							}
						}
					}
					
					//both occupiers still alive after all that jazz?
					boolean bothAlive = firstOccupier.energy > 0 && secondOccupier.energy > 0;
					
					//FIGHT HANDLING
					//both critters still here and want to fight
					if(bothAlive && firstStillHere && secondStillHere){
						Critter winner, loser;
						//roll (critters that don't want to fight will always roll 0)
						int firstRoll = (firstWantFight ? 1:0) * getRandomInt(firstOccupier.energy+1);
						int secondRoll = (secondWantFight ? 1:0) * getRandomInt(secondOccupier.energy+1);
						//establish winner and loser
						if(firstRoll == secondRoll){
							winner = getRandomInt(2) == 1 ? firstOccupier:secondOccupier;//coin toss
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}	
						else{
							winner = firstRoll > secondRoll ? firstOccupier:secondOccupier;
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}
						winner.energy += (loser.energy/2);
						
						/*REMOVE LOSER DOES THIS WORK I THINK IT DOES BECAUSE IT REFERS TO THE ORIGINAL OBJECT*/
						population.remove(loser);
						if(loser == firstOccupier){
							b = population.size();//break out of inner loop because firstOccupier is gone
							i--;//reexamine position we just removed from (firstOccupier has been replaced)
						}
							
					}//END FIGHT HANDLING		
				}//END ENCOUNTER HANDLING
			}//end for loop b
			i++;
		}

		/*DEDUCT REST ENERGY*/
		for(Critter current: population)
			current.energy -= Params.rest_energy_cost;
		
		/*ADD ALGAE*/
		for(int algaeCount = 0; algaeCount < Params.refresh_algae_count; algaeCount++){
			Algae algaeToAdd = new Algae();
			algaeToAdd.setEnergy(Params.start_energy);
			algaeToAdd.setXCoord(rand.nextInt(Params.world_width));
			algaeToAdd.setYCoord(rand.nextInt(Params.world_height));
		}
		
		/*ADDING BABIES*/
		population.addAll(babies);
		
		/*ClEANING DEAD CRITTERS*/
		i = 0;
		while(i < population.size()){
			if(population.get(i).energy <= 0)
				population.remove(i);
			else
				i++;
		}
		System.out.println("Pop size: " + population.size());
		

		
		
		/*RESET MOVE FLAG*/
		for(Critter current: population)
			current.hasMoved = false;
		
			
			
			
			
	}//end of worldTimeStep

	
	public static void displayWorld() {
		char[][] world = new char[Params.world_width+2][Params.world_height+2];
		for (int i = 0; i < (Params.world_width+2); i++) {
			for (int j = 0; j < (Params.world_height+2); j++) {
				if ((i == 0 && j == 0) || (i == (Params.world_width+1) && j == 0) || (i == 0 && j == (Params.world_height+1)) || (i == (Params.world_width+1) && j == (Params.world_height+1))) {
					world[i][j] = '+';
				}
				else if (i == 0) {
					world[i][j] = '-';
				}
				else if (i == (Params.world_width+1)){
					world[i][j] = '-';
				}
				else if (j == 0) {
					world[i][j] = '|';
				}
				else if (j == (Params.world_height+1)) {
					world[i][j] = '|';
				}
				else {
					world[i][j] = ' ';
				}
			}
		}
		Iterator<Critter> iterator = population.iterator();
		while(iterator.hasNext()) {
			Critter c = iterator.next();
			world[c.x_coord+1][c.y_coord+1] = c.toString().charAt(0);
		}
		for (int i = 0; i < (Params.world_width+2); i++){
			for (int j = 0; j < (Params.world_height+2); j++) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
	}
}
