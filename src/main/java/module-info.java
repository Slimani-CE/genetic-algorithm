module com.mustaphaslimani.geneticalgorithmgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;

    opens com.mustaphaslimani.geneticalgorithmgui to javafx.fxml;
    exports com.mustaphaslimani.geneticalgorithmgui;
    exports com.mustaphaslimani.geneticalgorithmgui.sma;
}