package ma.enset.sma;

import java.util.*;

public class Population{
    private List<Individual> individuals = new ArrayList<>();
    private Individual firstFittest;
    private Individual secondFittest;


    // Create a population
    public Population(int populationSize){
        // Initialize population
        for(int i = 0; i < populationSize; i++){
           individuals.add(new Individual());
        }
    }

    // Calculate individuals fitness
    public void calculateIndFitness(){
        for(Individual individual: individuals){
            individual.calculateFitness();
        }
    }

    public void selection(){
        Collections.sort(individuals, Collections.reverseOrder());
        firstFittest = individuals.get(0);
        secondFittest = individuals.get(1);
    }

    // Perform crossover on individuals and add new individuals to the population
    public void crossover(){
        // Generate a random crossover point
        Random random = new Random();
        int crossoverPoint = random.nextInt(individuals.get(0).getGenes().length - 1) + 1;

        // Create new individuals to store new individuals
        Individual individual1 = new Individual();
        Individual individual2 = new Individual();

        // Swap first half of genes
        for(int i = 0; i < crossoverPoint; i++){
            individual1.getGenes()[i] = secondFittest.getGenes()[i];
            individual2.getGenes()[i] = firstFittest.getGenes()[i];
        }

        // Initialize second half of genes
        for(int i = crossoverPoint; i < individual1.getGenes().length; i++){
            individual1.getGenes()[i] = firstFittest.getGenes()[i];
            individual2.getGenes()[i] = secondFittest.getGenes()[i];
        }
        // Calculate fitness of new individuals
        individual1.calculateFitness();
        individual2.calculateFitness();

        // Add new individuals to the population
        individuals.add(individual1);
        individuals.add(individual2);

        // Print the crossover point
        System.out.println("Crossover Point : " + crossoverPoint);
        // Print chromosome before crossover
        System.out.println("Before crossover : ");
        System.out.println("First fittest : " + firstFittest.getFitness() + " " + Arrays.toString(firstFittest.getGenes()));
        System.out.println("Second fittest : " + secondFittest.getFitness() + " " + Arrays.toString(secondFittest.getGenes()));
        // Print chromosome after crossover
        System.out.println("After crossover : ");
        System.out.println("First fittest : " + individual1.getFitness() + " " + Arrays.toString(individual1.getGenes()));
        System.out.println("Second fittest : " + individual2.getFitness() + " " + Arrays.toString(individual2.getGenes()));

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
