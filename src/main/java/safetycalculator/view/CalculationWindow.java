package safetycalculator.view;

import javax.swing.*;

import safetycalculator.controller.CalculationController;
import safetycalculator.model.CalculationModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@SuppressWarnings("serial")
public class CalculationWindow extends JFrame {
	private final JLabel text;
    private final JLabel maxBiegespannung;
    private final JLabel maxBiegespannungWert;
    private final JLabel biegefliessgrenze;
    private final JLabel biegefliessgrenzeWert;
    private final JLabel maxTorsionsspannung;
    private final JLabel maxTorsionsspannungWert;
    private final JLabel torsionsfliessgrenze;
    private final JLabel torsionsfliessgrenzeWert;
    private final JLabel text2;
    private final JLabel sicherheitsfaktorText;
    private final JLabel sicherheitsfaktor;
    private final JLabel sicherheitsfaktorErgebnis;
    private final JLabel sicherheitsfaktorEntscheidung;
    private final JLabel sicherheitsfaktorVergleich;
    private final JButton speichereRechnung;

    public CalculationWindow(CalculationModel model, CalculationController controller) {
        // Fenstereinstellungen
        setTitle("Berechnung");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Initialisieren der GUI Elemente
        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        outputPanel = new JPanel(new GridBagLayout());

        text = new JLabel("Zu berechnende Werte für Sicherheitsüberprüfung");
        text.setFont(new Font("Dialog", Font.BOLD, 17));
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.#", symbols);

        maxBiegespannung = new JLabel("Maximale Biegespannung");
        maxBiegespannung.setFont(new Font("Dialog", Font.BOLD, 13));
        maxBiegespannungWert = new JLabel("<html>σ<sub>Bmax</sub> = " + model.getMaxBiegespannung() + " N/mm²</html>");

        biegefliessgrenze = new JLabel("Biegefließgrenze");
        biegefliessgrenze.setFont(new Font("Dialog", Font.BOLD, 13));
        biegefliessgrenzeWert = new JLabel("<html>σ<sub>BF</sub> = " + model.getBiegefliessgrenze() + " N/mm²</html>");

        maxTorsionsspannung = new JLabel("Maximale Torsionsspannung");
        maxTorsionsspannung.setFont(new Font("Dialog", Font.BOLD, 13));
        maxTorsionsspannungWert = new JLabel("<html>τ<sub>Tmax</sub> = " + model.getMaxTorsionsspannung() + " N/mm²</html>");

        torsionsfliessgrenze = new JLabel("Torsionsfließgrenze");
        torsionsfliessgrenze.setFont(new Font("Dialog", Font.BOLD, 13));
        torsionsfliessgrenzeWert = new JLabel("<html>τ<sub>TF</sub> = " + model.getTorsionsfliessgrenze() + " N/mm²</html>");

        maxBiegespannungWert.setBackground(Color.WHITE);
        maxBiegespannungWert.setOpaque(true);
        maxBiegespannungWert.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        biegefliessgrenzeWert.setBackground(Color.WHITE);
        biegefliessgrenzeWert.setOpaque(true);
        biegefliessgrenzeWert.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        maxTorsionsspannungWert.setBackground(Color.WHITE);
        maxTorsionsspannungWert.setOpaque(true);
        maxTorsionsspannungWert.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        torsionsfliessgrenzeWert.setBackground(Color.WHITE);
        torsionsfliessgrenzeWert.setOpaque(true);
        torsionsfliessgrenzeWert.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        text2 = new JLabel("Berechnung der vorhandenen Sicherheit");
        text2.setFont(new Font("Dialog", Font.BOLD, 17));
        text2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        sicherheitsfaktorText = new JLabel("Sicherheitsfaktor");
        sicherheitsfaktorText.setFont(new Font("Dialog", Font.BOLD, 13));

        sicherheitsfaktor = new JLabel("<html>S<sub>F</sub> = " + decimalFormat.format(model.getSicherheitsfaktor()) + "</html>");
        sicherheitsfaktor.setFont(new Font("Dialog", Font.BOLD, 13));
        sicherheitsfaktor.setBackground(Color.WHITE);
        sicherheitsfaktor.setOpaque(true);
        sicherheitsfaktor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        sicherheitsfaktorErgebnis = new JLabel("Ergebnis");
        sicherheitsfaktorErgebnis.setFont(new Font("Dialog", Font.BOLD, 13));

        sicherheitsfaktorEntscheidung = new JLabel(model.getSicherheitsentscheidung());
        sicherheitsfaktorEntscheidung.setBackground(Color.WHITE);
        sicherheitsfaktorEntscheidung.setOpaque(true);

        sicherheitsfaktorVergleich = new JLabel();
        if (model.getSicherheitsentscheidung().startsWith("A")) {
            sicherheitsfaktorVergleich.setText("<html>S<sub>F</sub>  = " + decimalFormat.format(model.getSicherheitsfaktor()) + " ≥ S<sub>Fmin</sub> = 1.5</html>");
            sicherheitsfaktorEntscheidung.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GREEN),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else {
            sicherheitsfaktorVergleich.setText("<html>S<sub>F</sub> = " + decimalFormat.format(model.getSicherheitsfaktor()) + " ≤ S<sub>Fmin</sub> = 1.5</html>");
            sicherheitsfaktorEntscheidung.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        sicherheitsfaktorVergleich.setBackground(Color.WHITE);
        sicherheitsfaktorVergleich.setOpaque(true);
        sicherheitsfaktorVergleich.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        speichereRechnung = new JButton("Speichern");
        speichereRechnung.setPreferredSize(new Dimension(110, 28));
        speichereRechnung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.speichereRechnung(model);
                dispose();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 15));
        bottomPanel.setPreferredSize(new Dimension(10, 45));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(speichereRechnung);

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(15, 20, 0, 0);

        GridBagConstraints gbcLabelWert = new GridBagConstraints();
        gbcLabelWert.gridx = 0;
        gbcLabelWert.fill = GridBagConstraints.HORIZONTAL;
        gbcLabelWert.insets = new Insets(10, 20, 0, 20);

        gbcLabel.gridy = 0;
        outputPanel.add(text, gbcLabel);

        gbcLabel.gridy = 1;
        outputPanel.add(maxBiegespannung, gbcLabel);
        gbcLabelWert.gridy = 2;
        outputPanel.add(maxBiegespannungWert, gbcLabelWert);

        gbcLabel.gridy = 3;
        outputPanel.add(biegefliessgrenze, gbcLabel);
        gbcLabelWert.gridy = 4;
        outputPanel.add(biegefliessgrenzeWert, gbcLabelWert);

        gbcLabel.gridy = 5;
        outputPanel.add(maxTorsionsspannung, gbcLabel);
        gbcLabelWert.gridy = 6;
        outputPanel.add(maxTorsionsspannungWert, gbcLabelWert);

        gbcLabel.gridy = 7;
        outputPanel.add(torsionsfliessgrenze, gbcLabel);
        gbcLabelWert.gridy = 8;
        outputPanel.add(torsionsfliessgrenzeWert, gbcLabelWert);

        gbcLabel.gridy = 9;
        outputPanel.add(text2, gbcLabel);

        gbcLabel.gridy = 10;
        outputPanel.add(sicherheitsfaktorText, gbcLabel);

        gbcLabelWert.gridy = 11;
        outputPanel.add(sicherheitsfaktor, gbcLabelWert);

        gbcLabelWert.gridy = 12;
        outputPanel.add(sicherheitsfaktorVergleich, gbcLabelWert);

        gbcLabel.gridy = 13;
        outputPanel.add(sicherheitsfaktorErgebnis, gbcLabel);

        gbcLabelWert.gridy = 14;
        gbcLabelWert.insets = new Insets(10, 20, 20, 20);
        outputPanel.add(sicherheitsfaktorEntscheidung, gbcLabelWert);

        add(outputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
    }
}
