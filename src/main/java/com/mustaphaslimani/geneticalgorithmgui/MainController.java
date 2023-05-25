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
    Label msgField;
    @FXML
    Label msgDetails;
    @FXML
    TextField populationSizeField;
    @FXML
    Slider mutationRateSlider;
    @FXML
    Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Start button event handler
        startButton.setOnAction(actionEvent -> {
            // Empty details label
            msgDetails.setText("");
            if (targetField.getText().isEmpty() || maxGenField.getText().isEmpty() || populationSizeField.getText().isEmpty()) {
                msgField.setText("Please fill all fields");
                msgField.setTextFill(javafx.scene.paint.Color.web("tomato"));
            }
            else {
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
                        Platform.runLater(() -> {
                            msgField.setText("Generation: " + (i + 1) + " (Fittest: " + population.getFirstFittest().getFitness() + ")");
                            msgField.setTextFill(javafx.scene.paint.Color.web("#000000"));
                        });
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
                            if (population.getFirstFittest().getFitness() == 0) {
                                Platform.runLater(() -> {
                                    msgField.setText("Solution found!");
                                    msgField.setTextFill(javafx.scene.paint.Color.web("green"));
                                });
                            } else {
                                Platform.runLater(() -> {
                                    msgField.setText("Maximum generation reached!");
                                    msgField.setTextFill(javafx.scene.paint.Color.web("tomato"));
                                });
                            }
                            // Add details: Generation, fitness
                            Platform.runLater(() -> {
                                msgDetails.setText("Generation: " + i + " (Fittest: " + population.getFirstFittest().getFitness() + ")");
                                msgDetails.setTextFill(javafx.scene.paint.Color.web("#000000"));
                            });
                        }
                    }
                };
                timer.schedule(task, 0, 50);
            }
        });
    }

    public void setSolution(String solution) {
        solutonTextField.setText(solution);
    }
}