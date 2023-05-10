package ma.enset.sma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    //
    public void crossover(){
        // Generate a random crossover point
        Random random = new Random();
        int crossoverPoint = random.nextInt(individuals.get(0).getGenes().length - 1) + 1;
    }

    // Get the fittest individual
    public Individual getFittest(){
        Individual fittest = individuals.get(0);
        for(Individual individual: individuals){
            if(individual.getFitness() > fittest.getFitness())
                fittest = individual;
        }
        return fittest;
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
