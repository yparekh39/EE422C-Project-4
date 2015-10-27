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
package project4;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InvalidCritterException {
		Scanner kb = new Scanner(System.in);
		while(true) {
			System.out.print("critters> ");
			String input = kb.nextLine();
			String[] splitInput = input.split("\\s+");

			if (splitInput[0].equals("quit")) { 
				if (splitInput.length > 1) { System.out.println("error processing: " + input); }
				kb.close(); 
				return; 
			}
			else if (splitInput[0].equals("show")) { 
				if (splitInput.length > 1) { System.out.println("error processing: " + input); }
				Critter.displayWorld(); 
				continue;
			}
			else if (splitInput[0].equals("step")) { 
				int stepCount = 1;
				if (splitInput.length == 2) {
					try {
						stepCount = Integer.parseInt(splitInput[1]);
					} catch(NumberFormatException e) {
						System.out.println("error processing: " + input);
						continue;
					}
				}
				else if (splitInput.length > 2) {
					System.out.println("error processing: " + input);
					continue;
				}
				for (int i = 0; i < stepCount; i++) {
					Critter.worldTimeStep();
				}
			}
			else if (splitInput[0].equals("seed")) {
//				System.out.println(splitInput.length);
				if (splitInput.length == 1) { continue; }
				if ( splitInput.length > 2) { 
					System.out.println("error processing: " + input); 
					continue;
				} 
				try {
					Critter.setSeed(Long.parseLong(splitInput[splitInput.length-1]));
				} catch(NumberFormatException e) {
					System.out.println("error processing: " + input);
					continue;
				}
			}
			else if (splitInput[0].equals("make")) {
				//System.out.println(Arrays.toString(splitInput));
				int makeCount = 1;
				if (splitInput.length == 1) { System.out.println("error processing: " + input); continue; }
				if (splitInput.length > 3) { System.out.println("error processing: " + input); continue; }
				else if (splitInput.length == 3) { makeCount = Integer.parseInt(splitInput[2]); }
				try {
					for(int i = 0; i < makeCount; i++) {
						Critter.makeCritter(splitInput[1]);
					}
				} catch (InvalidCritterException e) {
					System.out.println("error processing: " + input);
					continue;
				}
			}
			else if (splitInput[0].equals("stats")) {
				if (splitInput.length != 2) {
					System.out.println("error processing: " + input);
					continue;
				}
				try {
					List<Critter> instances= Critter.getInstances(splitInput[1]);
					Class<?> myClass = null;
					Method m = null;
					try {
						myClass = Class.forName(splitInput[1]);
					} catch (ClassNotFoundException e) {
						throw new InvalidCritterException(splitInput[1]); 
					}
					try {
						m = myClass.getMethod("runStats", List.class);
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					try {
						m.invoke(null, instances);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InvalidCritterException e) {
					System.out.println("error processing: " + input);
					continue;
				}
			}
			else {
				System.out.println("error processing: " + input); 
				continue; 
			}
		}
	}
}
