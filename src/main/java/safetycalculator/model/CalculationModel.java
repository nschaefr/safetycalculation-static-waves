package safetycalculator.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculationModel {
    // 0 = F, 1 = D, 2 = l_1, 3 = l_2
    private double[] parameters = new double[4];
    private int streckgrenze;
    private double maxBiegespannung;
    private double biegefliessgrenze;
    private double maxTorsionsspannung;
    private double torsionsfliessgrenze;
    final private double mindestsicherheit = 1.5;
    final private double korrekturfaktor = 0.75;
    private double sicherheitsfaktor;
    private String sicherheitsentscheidung;
    private String werkstoff;

    // Setzt die Streckgrenze basierend auf dem Werkstoff
    public void setStreckgrenze(String werkstoff) {
        switch (werkstoff) {
            case "S235JR":
                streckgrenze = 235;
                break;
            case "S275JR":
                streckgrenze = 275;
                break;
            case "S355JR":
                streckgrenze = 355;
                break;
            case "S450J0":
                streckgrenze = 450;
                break;
            case "S185":
                streckgrenze = 185;
                break;
        }
        this.werkstoff = werkstoff;
    }

    // Setzt die Werte der Parameter
    public void setParameter(int index, double value) {
        parameters[index] = value;
    }

    // Getter-Methoden für die berechneten Werte
    public double getMaxTorsionsspannung() {
        return maxTorsionsspannung;
    }

    public double getTorsionsfliessgrenze() {
        return torsionsfliessgrenze;
    }

    public double getMaxBiegespannung() {
        return maxBiegespannung;
    }

    public double getBiegefliessgrenze() {
        return biegefliessgrenze;
    }

    public double getSicherheitsfaktor() {
        return sicherheitsfaktor;
    }

    // Gibt die Sicherheitsentscheidung zurück
    public String getSicherheitsentscheidung() {
        if (sicherheitsfaktor >= mindestsicherheit) {
            sicherheitsentscheidung = "Ausreichende Sicherheit gegen Fließen";
        } else {
            sicherheitsentscheidung = "Unzureichende Sicherheit gegen Fließen";
        }

        return sicherheitsentscheidung;
    }

    // Berechnet den Sicherheitsfaktor basierend auf den Eingabeparametern
    public void berechneSicherheitsfaktor() {
        berechneBiegespannung();
        berechneTorsionsspannung();

        sicherheitsfaktor = 1 / (Math.sqrt(Math.pow((maxBiegespannung / biegefliessgrenze), 2) + Math.pow((maxTorsionsspannung / torsionsfliessgrenze), 2)));
    }

    // Berechnet die Torsionsspannung und Torsionsfliessgrenze
    public void berechneTorsionsspannung() {
        maxTorsionsspannung = (parameters[0] * parameters[3]) / ((Math.PI / 16) * Math.pow(parameters[1], 3));
        torsionsfliessgrenze = (1.2 * streckgrenze * korrekturfaktor) / Math.sqrt(3);
    }

    // Berechnet die Biegespannung und Biegefliessgrenze
    public void berechneBiegespannung() {
        maxBiegespannung = (parameters[0] * parameters[2]) / ((Math.PI / 32) * Math.pow(parameters[1], 3));
        biegefliessgrenze = 1.2 * streckgrenze * korrekturfaktor;
    }

    // Erstellt eine CSV-Repräsentation des Models
    public String speichereCSV() {
        StringBuilder csv = new StringBuilder();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);

        // Fügt die Parameter zur CSV hinzu
        for (double parameter : parameters) {
            csv.append(parameter).append(",");
        }

        // Fügt den Sicherheitsfaktor, Werkstoff und die Sicherheitsentscheidung zur CSV hinzu
        csv.append(werkstoff).append(",");
        csv.append(decimalFormat.format(sicherheitsfaktor)).append(",");
        csv.append((sicherheitsentscheidung.startsWith("A")) ? "Ausreichend" : "Unzureichend");

        return csv.toString();
    }
}
