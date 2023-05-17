package com.mustaphaslimani.geneticalgorithmgui;

import com.mustaphaslimani.geneticalgorithmgui.sequential.Population;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    TextField targetField;
    @FXML
    TextField maxGenField;
    @FXML
    TextField solutonTextField;
    @FXML
    TextField populationSizeField;
    @FXML
    Slider mutationRateSlider;
    @FXML
    Button startButton;
    @FXML
    Button testButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testButton.setOnAction(actionEvent -> {
            System.out.println("Test button clicked");
            System.out.println("Target: " + targetField.getText());
            System.out.println("Max generation: " + maxGenField.getText());
            System.out.println("Population size: " + populationSizeField.getText());
            System.out.println("Mutation rate: " + mutationRateSlider.getValue());
        });

        // Start button event handler
        startButton.setOnAction(actionEvent -> {
            // Get target string
            String target = targetField.getText();
            // Get max generation
            int maxGen = Integer.parseInt(maxGenField.getText());
            // Start GA
            // Create an initial population
            Population population = new Population(Integer.parseInt(populationSizeField.getText()), target);
            population.calculateIndFitness();
            population.selection();
            System.out.println("Evolution in progress...");
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int i = 0;
                @Override
                public void run() {
                    setSolution(population.getFirstFittest().getGenes().toString());
                    population.calculateIndFitness();
                    population.selection();
                    population.crossover();
                    population.mutation(mutationRateSlider.getValue(), Population.INSERTION_MUTATION);
                    // Display fitness of the fittest individual
                    System.out.println("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ") Chromosome: " + population.getFirstFittest().getGenes().toString());
                    i++;
                    if (i == maxGen || population.getFirstFittest().getFitness() == 0) {
                        timer.cancel();
                        timer.purge();
                        // Get first fittest individual
                        System.out.println("First fittest individual (Fitness: " + population.getFirstFittest().getFitness() + "): " + population.getFirstFittest().getGenes().toString());
                        setSolution(population.getFirstFittest().getGenes().toString());
                    }
                }
            };
            timer.schedule(task, 0, 50);

        });
    }

    public void startGA(String target, int maxGen) {
        // Create an initial population
        Population population = new Population(1000, target);
        population.calculateIndFitness();
        population.selection();

        // Perform evolution
        System.out.println("Evolution in progress...");
        for (int i = 0; i < maxGen && population.getFirstFittest().getFitness() != 0; i++) {
            setSolution(population.getFirstFittest().getGenes().toString());
            population.calculateIndFitness();
            population.selection();
            population.crossover();
            population.mutation(0.5, Population.INSERTION_MUTATION);
            // Display fitness of the fittest individual
            System.out.println("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ") Chromosome: " + population.getFirstFittest().getGenes().toString());
        }

        // Get first fittest individual
        System.out.println("First fittest individual (Fitness: " + population.getFirstFittest().getFitness() + "): " + population.getFirstFittest().getGenes().toString());
        setSolution(population.getFirstFittest().getGenes().toString());
    }

    public void setSolution(String solution) {
//        Platform.runLater(() -> {
            // Print solution and wait 1 second
            solutonTextField.setText(solution);
//        });
    }
}