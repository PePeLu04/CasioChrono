package org.example.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;

public class StartController {

    @FXML
    private void switchToChrono() throws IOException {
        App.setRoot("chrono");
    }
}