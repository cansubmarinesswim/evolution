package evolution.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class Dna {

	public static final int DNA_LENGTH = 32;
	public ArrayList<Integer> sequence;

	public Dna() {
		this.sequence = generateSequence();		
	}
	
	public Dna(Dna mom, Dna dad) {
		this.sequence = generateSequence(mom, dad);
	}
	
	public ArrayList<Integer> getSequence() {
		return sequence;
	}
	
	private ArrayList<Integer> generateSequence(){
		/*
		 * Generate DNA sequence randomly, without parents involved.
		 * 
		 * Generating DNA sequence, based on splitting a list randomly
		 * into 8 parts and populating each of them with subsequent numbers from 0 to 7.
		 * 
		 * This approach feels more cool and "random" than easier method of just adding all 8 digits,
		 * and then populating rest of the list with random numbers from the same range.
		 * But I know that it's slower.
		 */
		ArrayList<Integer> sequence = new ArrayList<>();
		
		ArrayList<Integer> range = new ArrayList<>(Dna.DNA_LENGTH - 1);
		for(int i = 1; i < Dna.DNA_LENGTH -1; i++){
			range.add(i);
		}
		Collections.shuffle(range);
		
		ArrayList<Integer> divisionIndexes = (ArrayList<Integer>) range.stream().limit(7).sorted().collect(Collectors.toList());
		
		int direction = 0;
		for (int i=0 ; i<Dna.DNA_LENGTH; i++) {
			sequence.add(direction);
			if(divisionIndexes.contains(i)) {
				direction++;
			}
		}
		return sequence;
	}
	
	private ArrayList<Integer> generateSequence(Dna mom, Dna dad){
		
		/*
		 * Generate DNA sequence randomly, based on genes from parents.
		 * 
		 * Two splitting points are randomly picked, and at these indexes
		 * DNA sequence of both parents is divided. One part is taken from
		 * one parent, and two parts are taken from another - which one gives two
		 * is random.
		 * 
		 * If child didn't inherited at least one of each possible genes from parents,
		 * then random genes, that occur more than 2 times are replaced by the missing one.
		 */
		
		ArrayList<Integer> momsDna = mom.getSequence();
		ArrayList<Integer> dadsDna = dad.getSequence();

		Random random = new Random();

		int[] splittingIndexes = random.ints(2, 1, 32).sorted().toArray();
		
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		sequence.addAll(momsDna.subList(0, splittingIndexes[0]));
		sequence.addAll(dadsDna.subList(splittingIndexes[0], splittingIndexes[1]));
		
		if(random.nextInt() % 2 == 0) {
			sequence.addAll(momsDna.subList(splittingIndexes[1], Dna.DNA_LENGTH));
		} else {
			sequence.addAll(dadsDna.subList(splittingIndexes[1], Dna.DNA_LENGTH));
		}

		for (int gene =0; gene<=7; gene++) {
			if (! sequence.contains(gene)) {
				boolean foundNotSingleGene = false;
				while(! foundNotSingleGene) {
					int randomCodon = random.nextInt(32);
						if(Collections.frequency(sequence, sequence.get(randomCodon)) >= 2) {
							sequence.set(sequence.indexOf(sequence.get(randomCodon)), gene);
							foundNotSingleGene=true;
						}
				}
			}
		}
		Collections.sort(sequence);	
		return sequence;
	}

	@Override
	public String toString() {
		return "Dna [sequence=" + sequence + "]";
	}

	public static void main(String[] args) {
		/*
		 * Testing constructors
		 */
		Dna mom = new Dna();
		Dna dad = new Dna();
		
		Dna son = new Dna(mom,dad);
	}
	
}
