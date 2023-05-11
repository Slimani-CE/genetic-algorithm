package ma.enset.sma;

import java.util.Arrays;

public class AGApp {
    private static final int MAX_GENERATIONS = 50;
    private static final int POPULATION_SIZE = 10;
    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(POPULATION_SIZE);
        population.calculateIndFitness();
        population.selection();

        // Perform evolution
        System.out.println("Evolution in progress...");
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            population.crossover();
            population.mutation(0.5);
            population.calculateIndFitness();
            population.selection();
            // Display fitness of the fittest individual
            System.out.println("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ") Chromosome: " + Arrays.toString(population.getFirstFittest().getGenes()));
        }

        // Get first fittest individual
        System.out.println("First fittest individual (Fitness: " + population.getFirstFittest().getFitness() + "): " + Arrays.toString(population.getFirstFittest().getGenes()));
    }
}