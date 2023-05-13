package ma.enset.sma.sequential;

import java.util.*;
import java.util.List;

public class Population{
    private List<Individual> individuals = new ArrayList<>();
    private Individual firstFittest;
    private Individual secondFittest;
    private String target = "hello";

    // Population final attributes
    public static final int INSERTION = 0;
    public static final int SWAP = 1;
    public static final int INVERSION = 2;

    // Create a population
    public Population(int populationInitSize){
        // Initialize population
        for(int i = 0; i < populationInitSize; i++){
           individuals.add(new Individual());
        }
    }

    // Calculate individuals fitness
    public void calculateIndFitness(){
        for(Individual individual: individuals){
            individual.calculateFitness(target);
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
        int crossoverPoint = random.nextInt(individuals.get(0).getGenes().size() - 1) + 1;
        // Create new individuals to store new individuals
        Individual individual1 = new Individual();
        Individual individual2 = new Individual();

        // Swap first half of genes
        for(int i = 0; i < crossoverPoint; i++){
            individual1.getGenes().add(i, secondFittest.getGenes().get(i));
            individual2.getGenes().add(i, firstFittest.getGenes().get(i));
        }

        // Initialize second half of genes
        for(int i = crossoverPoint; i < individual1.getGenes().size(); i++){
            individual1.getGenes().add(i, firstFittest.getGenes().get(i));
            individual2.getGenes().add(i, secondFittest.getGenes().get(i));
        }
        // Calculate fitness of new individuals
        individual1.calculateFitness(target);
        individual2.calculateFitness(target);

        // Add new individuals to the population
        // TODO: 10/05/2023 Make population size static by changing old individuals with new ones
        individuals.add(individual1);
        individuals.add(individual2);

    }

    // Perform mutation on the population
    public void mutation(double mutationRate, int mutationType){
        // mutationRate is a value between 0 and 1
        // It represents the probability of performing mutation on an individual
        for(Individual individual : individuals){
            // Check if mutation should be performed
            if(new Random().nextDouble(1) <= mutationRate){
                switch(mutationType){
                    case Population.INSERTION:
                        individual.insertionMutation();
                        break;
                    case Population.SWAP:
                        individual.swapMutation();
                        break;
                    case Population.INVERSION:
                        individual.inversionMutation();
                        break;
                    default:
                        System.out.println("Invalid mutation type");
                }
            }
        }
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
