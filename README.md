# 🧬 Genetic Algorithm Implementation in Java
## Under development... 🚀

## 📝 Table of Contents
- [Overview](#-overview)
- [Genetic Algorithm Idea & Implementation](#-genetic-algorithm-idea--implementation)
  - [Calculating the Fitness Score](#calculating-the-fitness-score)
  - [Selection](#selection)
  - [Crossover](#crossover)
  - [Mutation](#mutation)
- [Results illustration](#-results)

## 📖 Overview
This project is a Java implementation of a Genetic Algorithm, a stochastic optimization algorithm inspired by natural selection and genetics. 

## 🧬 Genetic Algorithm Idea & Implementation
The idea behind the Genetic Algorithm is to find the best solution to a problem by mimicking the process of natural selection and genetics. The algorithm starts with a population of individuals, each one representing a possible solution to the problem. Each individual is evaluated and assigned a fitness score, which represents how good the solution is. The individuals with the best fitness scores are selected to reproduce, and the offspring of the reproduction process will be the new population. The process is repeated until a solution is found or a maximum number of generations is reached.

![ga-image](assets/ga-image.png)

### Calculating the Fitness Score
In our case an individual is a string of characters, and the problem is to find a string that matches a given target string. The fitness score of an individual is calculated by counting the number of characters that didn't match the target string. The lower the fitness score, the better the solution. For example, if the target string is `Hello World!` and the individual is `Hello World?`, the fitness score will be 1, because the `?` character doesn't match the `!` character.

- The corresponding Java code is:
```java
public class Individual {
    // Chromosome
    private List<Character> genes = new ArrayList<>();
    // Fitness score
    private double fitness;

    public void calculateFitness(String target) {
        // Initialize fitness to chromosome length
        fitness = genes.size();

        for(int i = 0; i < genes.size(); i++)
            if(genes.get(i).equals(target.charAt(i)))
                fitness -= 1;
    }
}
```

### Selection
The selection is the process of choosing the individuals that will reproduce. The individuals with the best fitness scores have a higher chance of being selected, but the individuals with the worst fitness scores also have a chance of being selected. This is done to avoid the algorithm getting stuck in a local minimum.

In our case, the size of the population is fixed, so the selection process consists of sorting the individuals by their fitness scores and keeping the fittest individuals. The fittest individuals will be the parents of the next generation.
- The corresponding Java code is:
```java
public class Population {
    private final int populationInitSize;
    private List<Individual> individuals = new ArrayList<>();
    private Individual firstFittest;
    private Individual secondFittest;
    
    public void selection(){
        Collections.sort(individuals);
        
        // Keep populationSize fittest individuals
        individuals = individuals.subList(0, populationInitSize);
        
        firstFittest = individuals.get(0);
        secondFittest = individuals.get(1);
    }
}
```

### Crossover
The crossover is the process of creating the offspring of the reproduction process. The offspring will inherit some of the characteristics of their parents. In our case, the offspring will inherit some of the characters of their parents. The crossover is done by choosing a random crossover point and swapping the characters of the parents after that point. For example, if the parents are `Hello World!` and `Hello World?`, and the crossover point is 6, the offspring will be `Hello World?` and `Hello World!`.

![crossover image](assets/crossover-image.png)

- The corresponding Java code is:
```java
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
```

### Mutation
The mutation is the process of changing some characteristics of the individuals. There are multiple ways of doing this. In our case, the mutation is done by randomly changing a character of the individual. For example, if the individual is `Hello World!`, and the mutation point is 6, the individual <b>may</b> become `Hello World?`.

The mutation is probabilistic, which means that there is a chance that the individual won't be mutated.
- The corresponding Java code of mutation is:
```java
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
                default:
                    System.out.println("Invalid mutation type");
            }
        }
    }
    individuals.addAll(newIndividuals);
}
```
- Mutation by insertion: a random character is inserted in a random position of the individual.
```java
public Individual insertionMutation() {
    Random random = new Random();

    int nbrGenesInserted = random.nextInt(genes.size());
    
    Individual newIndividual = new Individual(genes.size());
    newIndividual.setGenes(new ArrayList<>(genes));

    random.ints(nbrGenesInserted, 0, newIndividual.genes.size()).forEach(index -> {
    char gnome = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
    newIndividual.genes.set(index, gnome);
    });

    return newIndividual;
}
```
- Mutation by swapping: two random characters are swapped.
```java
public Individual swapMutation() {
    int firstIndex = new Random().nextInt(genes.size());
    int secondIndex = new Random().nextInt(genes.size());

    Individual newIndividual = new Individual(genes.size());
    newIndividual.genes = new ArrayList<>(genes);
    newIndividual.genes.set(firstIndex, genes.get(secondIndex));
    newIndividual.genes.set(secondIndex, genes.get(firstIndex));
    return newIndividual;
}
```
- Mutation by inversion: a random substring is inverted.
```java
public Individual inversionMutation() {
    // Generate two random indices
    int firstIndex = new Random().nextInt(genes.size());
    int secondIndex = new Random().nextInt(genes.size());

    // Create a new individual and copy the genes
    Individual newIndividual = new Individual(genes.size());
    newIndividual.genes = new ArrayList<>(genes);

    if(firstIndex > secondIndex){
    int temp = firstIndex;
    firstIndex = secondIndex;
    secondIndex = temp;
    }

    // Reverse the genes between the two indices
    ArrayList<Character> subList = new ArrayList<>(newIndividual.genes.subList(firstIndex, secondIndex));
    Collections.reverse(subList);
    newIndividual.genes.subList(firstIndex, secondIndex).clear();
    newIndividual.genes.addAll(firstIndex, subList);

    return newIndividual;
}
```


### Main application
```java
public class AGApp {
    private static final int MAX_GENERATIONS = 5000;
    private static final int POPULATION_SIZE = 100;
    private static final String TARGET = "Hello World!";
    public static void main(String[] args) {
        // Create an initial population
        Population population = new Population(POPULATION_SIZE, TARGET);
        population.calculateIndFitness();
        population.selection();

        // Perform evolution
        System.out.println("Evolution in progress...");
        for (int i = 0; i < MAX_GENERATIONS && population.getFirstFittest().getFitness() != 0; i++) {
            population.calculateIndFitness();
            population.selection();
            population.crossover();
            // 0.5 of chance that the mutation will be performed
            population.mutation(0.5, Population.INSERTION_MUTATION);
            // Display fitness of the fittest individual
            System.out.println("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ") Chromosome: " + population.getFirstFittest().getGenes().toString());
        }

        // Get first fittest individual
        System.out.println("First fittest individual (Fitness: " + population.getFirstFittest().getFitness() + "): " + population.getFirstFittest().getGenes().toString());
    }
}
```
## 📝 Results