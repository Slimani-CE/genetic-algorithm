package ma.enset.sma.sequential;

public class Test {
    public static void main(String[] args) {
        Population population = new Population(10, "a");
        Individual individual = population.getIndividuals().get(0);

        System.out.println("Before crossover: " + individual.getGenes().toString());
        population.crossover();
        System.out.println("After crossover: " + individual.getGenes().toString());

    }
}
