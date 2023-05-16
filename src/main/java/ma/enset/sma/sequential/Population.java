package ma.enset.sma.sequential;

import java.util.*;
import java.util.List;

public class Population{
    private final int populationInitSize;
    private List<Individual> individuals = new ArrayList<>();
    private Individual firstFittest;
    private Individual secondFittest;
    private String target;

    // Population final attributes
    public static final int INSERTION_MUTATION = 0;
    public static final int SWAP_MUTATION = 1;
    public static final int INVERSION_MUTATION = 2;
    public static final int SCRAMBLE_MUTATION = 3;
    public static final int RANDOM_MUTATION = 4;

    // Create a population
    public Population(int populationInitSize, String target){
        this.populationInitSize = populationInitSize;
        this.target = target;
        // Initialize population
        for(int i = 0; i < populationInitSize; i++){
           individuals.add(new Individual(target.length()));
        }
    }

    // Calculate individuals fitness
    public void calculateIndFitness(){
        for(Individual individual: individuals){
            individual.calculateFitness(target);
        }
    }

    public void selection(){
        Collections.sort(individuals);
        // Keep populationSize fittest individuals

        individuals = individuals.subList(0, populationInitSize);
        firstFittest = individuals.get(0);
        secondFittest = individuals.get(1);
    }

    // Perform crossover on individuals and add new individuals to the population
    public void crossover(){
        // Generate a random crossover point
        Random random = new Random();
        int crossoverPoint = random.nextInt(individuals.get(0).getGenes().size());
        // Create new individuals to store new individuals
        Individual individual1 = new Individual(target.length());
        Individual individual2 = new Individual(target.length());
        System.out.println("Crossover point: " + crossoverPoint);
        // Swap first half of genes
        for(int i = 0; i <= crossoverPoint; i++){
            individual1.getGenes().set(i, secondFittest.getGenes().get(i));
            individual2.getGenes().set(i, firstFittest.getGenes().get(i));
        }

        // Initialize second half of genes
        for(int i = crossoverPoint + 1; i < individual1.getGenes().size(); i++){
            individual1.getGenes().set(i, firstFittest.getGenes().get(i));
            individual2.getGenes().set(i, secondFittest.getGenes().get(i));
        }
        // Calculate fitness of new individuals
        individual1.calculateFitness(target);
        individual2.calculateFitness(target);

        // Add new individuals to the population
        individuals.add(individual1);
        individuals.add(individual2);

    }

    // Perform mutation on the population
    public void mutation(double mutationRate, int mutationType){
        // mutationRate is a value between 0 and 1
        // It represents the probability of performing mutation on an individual
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        if(new Random().nextDouble(1) <= mutationRate){
            for(Individual individual : individuals) {
                // Check if mutation should be performed
                if (mutationType == Population.RANDOM_MUTATION)
                    mutationType = new Random().nextInt(Population.RANDOM_MUTATION);

                switch (mutationType) {
                    case Population.INSERTION_MUTATION:
                        newIndividuals.add(individual.insertionMutation());
                        break;
                    case Population.SWAP_MUTATION:
                        newIndividuals.add(individual.swapMutation());
                        break;
                    case Population.INVERSION_MUTATION:
                        newIndividuals.add(individual.inversionMutation());
                        break;
                    case Population.SCRAMBLE_MUTATION:
                        newIndividuals.add(individual.scrambleMutation(target));
                        break;
                    default:
                        System.out.println("Invalid mutation type");
                }
            }
        }
        individuals.addAll(newIndividuals);
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getFirstFittest() {
        return firstFittest;
    }

    public Individual getSecondFittest() {
        return secondFittest;
    }
}
