package com.mustaphaslimani.geneticalgorithmgui.sequential;

public class Test {
    public static void main(String[] args) {
        Individual individual = new Individual(5);

        System.out.println("BEFORE MUTATION .....");
        System.out.println(individual);
        System.out.println("AFTER MUTATION ......");
        System.out.println(individual.insertionMutation());

    }
}
