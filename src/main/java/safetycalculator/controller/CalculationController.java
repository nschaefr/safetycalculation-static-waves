package safetycalculator.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import safetycalculator.model.CalculationModel;
import safetycalculator.view.CalculationWindow;
import safetycalculator.view.InputWindow;
import safetycalculator.view.StartWindow;

public class CalculationController {

    public CalculationController() {
    }

    // Startet die Anwendung, indem ein StartWindow erstellt wird
    public void startApplication() {
        StartWindow startWindow = new StartWindow(this);
        startWindow.setVisible(true);
    }

    // Startet das InputWindow, um Benutzereingaben zu erfassen
    public void startInput() {
        InputWindow inputWindow = new InputWindow(this);
        inputWindow.setVisible(true);
    }

    // Berechnet die Sicherheit basierend auf den Eingabeparametern und dem Werkstoff
    public void berechneSicherheit(double[] inputParam, String werkstoff) {
        CalculationModel model = new CalculationModel();
        for (int i = 0; i < inputParam.length; i++) {
            model.setParameter(i, inputParam[i]);
        }
        model.setStreckgrenze(werkstoff);
        model.berechneSicherheitsfaktor();

        // Erstellt ein CalculationWindow mit dem berechneten Modell
        CalculationWindow calcWin = new CalculationWindow(model, this);
        calcWin.setVisible(true);
    }

    // Speichert die Berechnung in einer CSV-Datei
    public void speichereRechnung(CalculationModel model) {
        // Holt sich die URL der Ressource für die CSV-Datei
        URL resourceUrl = CalculationController.class.getResource("/files/rechnungen.csv");

        // Überprüft, ob die Ressource gefunden wurde
        if (resourceUrl != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(resourceUrl.getPath(), true))) {
                // Schreibt die CSV-Repräsentation des Models in die Datei
                writer.write(model.speichereCSV());
                writer.newLine();
            } catch (IOException e) {
                // Behandelt IOException und wirft RuntimeException im Fehlerfall
                throw new RuntimeException(e);
            }
        }
    }
}
