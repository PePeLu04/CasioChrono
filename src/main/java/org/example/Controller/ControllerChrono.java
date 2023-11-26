package org.example.Controller;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ControllerChrono {
    private boolean running = false;
    private boolean stopped = false;
    private int horas = 0, minutos = 0, segundos = 0, milisegundos = 0;
    private Thread hiloCronometro;

    @FXML
    private Text displayTiempo;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnDetener;

    @FXML
    private Pane colorPane;
    @FXML
    private Button btnCambiarColor;

    @FXML
    private void iniciarCronometro() {
        if (!running) {
            startCronometro();
            btnIniciar.setText("CONTANDO");
            btnIniciar.setDisable(true);
        }
    }

    @FXML
    private void detenerCronometro() {
        if (running && !stopped) {
            stopCronometro();
            btnDetener.setText("REANUDAR");
            btnIniciar.setText("DETENIDO");
            stopped = true;
        } else if (stopped) {
            resumeCronometro();
            btnDetener.setText("DETENER");
            btnIniciar.setText("CONTANDO");
            stopped = false;
        }
    }

    @FXML
    private void reiniciarCronometro() {
        stopCronometro();
        horas = minutos = segundos = milisegundos = 0;
        displayTiempo.setText("00:00:00:000");
        btnIniciar.setText("INICIAR");
        btnIniciar.setDisable(false);
        btnDetener.setText("DETENER");
        stopped = false;
    }

    private void startCronometro() {
        running = true;
        hiloCronometro = new Thread(() -> {
            long tiempoAnterior = System.currentTimeMillis();
            while (running) {
                long tiempoActual = System.currentTimeMillis();
                long diferenciaTiempo = tiempoActual - tiempoAnterior;
                milisegundos += diferenciaTiempo;
                tiempoAnterior = tiempoActual;

                if (milisegundos >= 1000) {
                    milisegundos -= 1000;
                    segundos++;
                    if (segundos >= 60) {
                        segundos = 0;
                        minutos++;
                        if (minutos >= 60) {
                            minutos = 0;
                            horas++;
                        }
                    }
                }

                String tiempo = String.format("%02d:%02d:%02d:%03d", horas, minutos, segundos, milisegundos);
                Platform.runLater(() -> displayTiempo.setText(tiempo));

                try {
                    Thread.sleep(1); // Esperar un milisegundo para reducir la carga del bucle
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hiloCronometro.setDaemon(true);
        hiloCronometro.start();
    }

    private void stopCronometro() {
        running = false;
    }

    private void resumeCronometro() {
        startCronometro();
    }

    @FXML
    private void cambiarColor() {
        btnCambiarColor.setOnMousePressed(event -> {
            // Al presionar el botón, cambia el color del Pane a rojo
            colorPane.setStyle("-fx-background-color: #58D68D;");
        });

        btnCambiarColor.setOnMouseReleased(event -> {
            // Al soltar el botón, elimina el color de fondo del Pane (vuelve a su color original)
            colorPane.setStyle("");
        });
    }
}
