package ma.enset.sma.sequential;

public class AGApp {
    private static final int MAX_GENERATIONS = 5000;
    private static final int POPULATION_SIZE = 100;
    private static final String TARGET = "mustaphaslimani";
    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(POPULATION_SIZE, TARGET);
        population.calculateIndFitness();
        population.selection();

        // Perform evolution
        System.out.println("Evolution in progress...");
        for (int i = 0; i < MAX_GENERATIONS && population.getFirstFittest().getFitness() != 0; i++) {
            population.selection();
            population.crossover();
            population.mutation(0.5, Population.RANDOM_MUTATION);
            population.calculateIndFitness();
            // Display fitness of the fittest individual
            System.out.println("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ") Chromosome: " + population.getFirstFittest().getGenes().toString());
        }

        // Get first fittest individual
        System.out.println("First fittest individual (Fitness: " + population.getFirstFittest().getFitness() + "): " + population.getFirstFittest().getGenes().toString());
    }
}