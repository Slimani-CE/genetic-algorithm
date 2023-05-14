package ma.enset.sma.sequential;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Individual implements Comparable{
    // Chromosome
    private List<Character> genes = new ArrayList<>();
    private double fitness;

    public Individual(int chromosomeLength){
        for(int i = 0; i < chromosomeLength; i++){
            // Initialize genes with random values
            genes.add((char)(new Random().nextInt(26) + 'a'));
        }
    }

    public void calculateFitness(String target){
        // Fitness is the mean of the absolute difference of the distance
        // between each two characters of the target and the genes
        fitness = 0;
        for(int i = 0; i < genes.size(); i++){
            int diff = Math.abs(target.charAt(i) - genes.get(i));
            if(diff > 13)
                diff = 26 - diff;
            fitness += diff;
        }
        fitness /= target.length();
    }

    public double getFitness() {
        return fitness;
    }

    public List<Character> getGenes() {
        return genes;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if(this.fitness > individual.fitness)
            return 1;
        else if(this.fitness < individual.fitness)
            return -1;
        else
            return 0;
    }

    public Individual insertionMutation() {
        int firstIndex = new Random().nextInt(genes.size());
        int secondIndex = new Random().nextInt(genes.size());

        Individual newIndividual = new Individual(genes.size());
        newIndividual.genes = new ArrayList<>(genes);
        char temp = newIndividual.genes.get(secondIndex);
        newIndividual.genes.remove(secondIndex);
        newIndividual.genes.add(firstIndex, temp);

        return new Individual(genes.size());
    }

    public Individual swapMutation() {
        int firstIndex = new Random().nextInt(genes.size());
        int secondIndex = new Random().nextInt(genes.size());

        Individual newIndividual = new Individual(genes.size());
        newIndividual.genes = new ArrayList<>(genes);
        newIndividual.genes.set(firstIndex, genes.get(secondIndex));
        newIndividual.genes.set(secondIndex, genes.get(firstIndex));
        return newIndividual;
    }

    public Individual inversionMutation() {
        // Generate two random indices
        int firstIndex = new Random().nextInt(genes.size());
        int secondIndex = new Random().nextInt(genes.size());

        // Create a new individual and copy the genes
        Individual newIndividual = new Individual(genes.size());
        newIndividual.genes = new ArrayList<>(genes);

        if(firstIndex > secondIndex){
            int temp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = temp;
        }

        // Reverse the genes between the two indices
        ArrayList<Character> subList = new ArrayList<>(newIndividual.genes.subList(firstIndex, secondIndex));
        Collections.reverse(subList);
        newIndividual.genes.subList(firstIndex, secondIndex).clear();
        newIndividual.genes.addAll(firstIndex, subList);

        return newIndividual;
    }
}
