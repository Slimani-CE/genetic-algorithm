package ma.enset.sma;

public class Individual implements Comparable{
    // Chromosome
    private int genes[] = new int[10];
    private int fitness;

    public Individual(){
        for(int i = 0; i < genes.length; i++){
            if(Math.random() >= 0.5)
                genes[i] = 1;
            else
                genes[i] = 0;
        }
    }

    public void calculateFitness(){
        for(int gene: genes){
            if(gene == 1)
                fitness += gene;
        }
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if(this.fitness > individual.fitness)
            return -1;
        else if(this.fitness < individual.fitness)
            return 1;
        else
            return 0;
    }
}
