package ma.enset.sma.sequential;

public class Test {
    public static void main(String[] args) {
        Population population = new Population(10);
        Individual individual = population.getIndividuals().get(0);

        System.out.println("Before mutation");
        System.out.println(individual.getGenes().toString());
        individual.swapMutation();
        System.out.println("After mutation");
        System.out.println(individual.getGenes().toString());
    }
}
