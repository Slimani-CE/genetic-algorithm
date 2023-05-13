package ma.enset.sma.sequential;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual implements Comparable{
    // Chromosome
    private List<Character> genes = new ArrayList<>();
    private int fitness;
    private final int geneLength = 10;

    public Individual(){
        for(int i = 0; i < geneLength; i++){
            // Initialize genes with random values
            genes.add((char)(new Random().nextInt(26) + 'a'));
        }
    }

    public void calculateFitness(String target){
        fitness = 0;
        for(int i = 0; i < genes.size(); i++){
            if(genes.get(i) == target.charAt(i))
                fitness++;
        }
        fitness /= target.length();
    }

    public int getFitness() {
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

    public void insertionMutation() {
        // TODO: 13/05/2023 Implement insertion mutation
    }

    public void swapMutation() {
        int firstIndex = new Random().nextInt(genes.size());
        int secondIndex = new Random().nextInt(genes.size());

        char temp = genes.get(firstIndex);
        genes.set(firstIndex, genes.get(secondIndex));
        genes.set(secondIndex, temp);
    }

    public void inversionMutation() {
        // TODO: 13/05/2023 Implement inversion mutation
    }
}
