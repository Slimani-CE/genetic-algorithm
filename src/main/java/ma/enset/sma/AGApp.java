package ma.enset.sma;

import java.util.Arrays;

public class AGApp {
    public static void main(String[] args) {
        Individual solution = null;
        for(int i = 0; i < 100; i++){
            Individual individual = new Individual();
            individual.calculateFitness();
            if (individual.getFitness() == individual.getGenes().length)
                solution = individual;
            System.out.println("Chromosome: " + Arrays.toString(individual.getGenes()) + " Fitness: " + individual.getFitness());
        }
        if(solution != null)
            System.out.println("Solution: " + Arrays.toString(solution.getGenes()) + " Fitness: " + solution.getFitness());
        else
            System.out.println("No solution found");
    }
}