package ma.enset.sma;

import java.util.*;
import java.util.List;

public class Population{
    private List<Individual> individuals = new ArrayList<>();
    private Individual firstFittest;
    private Individual secondFittest;


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
        // TODO: 10/05/2023 Make population size static by changing old individuals with new ones
        individuals.add(individual1);
        individuals.add(individual2);

    }

    // Perform mutation on the population
    public void mutation(double mutationRate){
        // mutationRate is a value between 0 and 1
        // It represents the probability of performing mutation on an individual
        for(Individual individual : individuals){
            if(new Random().nextDouble(1) <= mutationRate){
                // Generate a random mutation point
                Random random = new Random();
                int mutationPoint = random.nextInt(individual.getGenes().length);

                // Flip values at the mutation point
                individual.getGenes()[mutationPoint] = individual.getGenes()[mutationPoint] == 0 ? 1 : 0;
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
