package ma.enset.sma.sequential;

import java.util.ArrayList;
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
        // TODO: 13/05/2023 Implement insertion mutation
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
        // TODO: 13/05/2023 Implement inversion mutation
        return new Individual(genes.size());
    }
}
