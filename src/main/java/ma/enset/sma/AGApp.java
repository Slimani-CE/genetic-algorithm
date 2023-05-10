package ma.enset.sma;

import java.util.Arrays;

public class AGApp {
    public static void main(String[] args) {
        Population population = new Population(10);
        population.calculateIndFitness();
        population.selection();
        for (Individual individual : population.getIndividuals()){
            System.out.println("Chromosome : " + Arrays.toString(individual.getGenes()) + " Fitness : " + individual.getFitness());
        }
    }
}