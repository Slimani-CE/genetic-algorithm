package ma.enset.sma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population{
    private List<Individual> individuals = new ArrayList<>();


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
        Collections.sort(individuals);
    }

    //
    public void crossover(){

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

}
