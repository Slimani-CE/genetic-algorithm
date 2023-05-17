//package com.mustaphaslimani.geneticalgorithmgui.sma;
//
//import jade.core.Agent;
//import jade.core.behaviours.OneShotBehaviour;
//
//import java.util.Random;
//
//public class IndividualAgent extends Agent {
//    // Chromosome
//    private int genes[] = new int[10];
//    private int fitness;
//
//    @Override
//    protected void setup() {
//
//        // Calculate fitness
//        addBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
//                fitness = 0;
//                for(int gene: genes){
//                    if(gene == 1)
//                        fitness += gene;
//                }
//            }
//        });
//
//        // Create new agent
//
//
//        // Perform crossover
//        addBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
//
//            }
//        });
//
//        // Perform mutation
//        addBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
//                Random random = new Random();
//                int mutationPoint = random.nextInt(genes.length);
//                    genes[mutationPoint] = genes[mutationPoint] == 0 ? 1 : 0;
//            }
//        });
//    }
//}
