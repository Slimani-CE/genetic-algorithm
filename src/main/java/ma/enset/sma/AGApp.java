package ma.enset.sma;

import java.util.Arrays;

public class AGApp {
    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(10);
        population.calculateIndFitness();
        population.selection();

        // First generation
        System.out.println("First generation ....................................................");
        for (Individual individual : population.getIndividuals()){
            System.out.println("Chromosome : " + Arrays.toString(individual.getGenes()) + " Fitness : " + individual.getFitness());
        }

        // Evolve second generation
        population.crossover();
        population.calculateIndFitness();
        population.selection();

        // Second generation
        System.out.println("Second generation ....................................................");
        for (Individual individual : population.getIndividuals()){
            System.out.println("Chromosome : " + Arrays.toString(individual.getGenes()) + " Fitness : " + individual.getFitness());
        }
    }
}