package ma.enset.sma.sequential;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Individual implements Comparable{
    // Chromosome
    private List<Character> genes = new ArrayList<>();
    // Fitness score
    private double fitness;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ/.,;:!?(){}[]<>@#$%^&*+-=0123456789\n\t\\\"',_`~|<>&";
    public Individual(int chromosomeLength){
        for(int i = 0; i < chromosomeLength; i++){
            // Initialize genes with random values
            genes.add((char)(new Random().nextInt(26) + 'a'));
        }
    }

    public void calculateFitness(String target){
        // Initialize fitness to chromosome length
        fitness = genes.size();

        for(int i = 0; i < genes.size(); i++)
            if(genes.get(i).equals(target.charAt(i)))
                fitness -= 1;
    }

    public double getFitness() {
        return fitness;
    }

    public List<Character> getGenes() {
        return genes;
    }

    public void setGenes(List<Character> genes) {
        this.genes = genes;
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
        Random random = new Random();

        int nbrGenesInserted = random.nextInt(genes.size());
        nbrGenesInserted = 1;
        Individual newIndividual = new Individual(genes.size());
        newIndividual.setGenes(new ArrayList<>(genes));

        random.ints(nbrGenesInserted, 0, newIndividual.genes.size()).forEach(index -> {
            char gnome = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            newIndividual.genes.set(index, gnome);
        });

        return newIndividual;
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

    public Individual scrambleMutation(String target) {
        Individual newIndividual = new Individual(genes.size());
        newIndividual.genes = new ArrayList<>(genes);
        for(int i = 0; i < genes.size(); i++)
            if(genes.get(i).equals(target.charAt(i)))
                newIndividual.genes.set(i, (char)(new Random().nextInt(26) + 'a'));
        return newIndividual;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "genes=" + genes +
                ", fitness=" + fitness +
                '}';
    }
}
