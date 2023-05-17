module com.mustaphaslimani.geneticalgorithmgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mustaphaslimani.geneticalgorithmgui to javafx.fxml;
    exports com.mustaphaslimani.geneticalgorithmgui;
}