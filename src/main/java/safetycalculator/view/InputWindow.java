package safetycalculator.view;

import javax.swing.*;

import safetycalculator.controller.CalculationController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@SuppressWarnings("serial")
public class InputWindow extends JFrame {
	private final JLabel parameterLabel;
    private final JPanel skizzenPanel;
    private final JPanel eingabePanel;
    private final JTextField[] paramFelder;
    private final JButton berechneButton;
    private final CalculationController controller;
    private final JComboBox<String> werkstoffCombo;
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu meineRechnungen = new JMenu("Meine Berechnungen");
    private final JMenuItem zeigeRechnungen = new JMenuItem("Anzeigen");

    public InputWindow(CalculationController controller) {
        this.controller = controller;
        // Fenstereinstellungen
        setTitle("Sicherheitsüberprüfung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(680, 520);
        getContentPane().setBackground(Color.WHITE);

        // Initialisieren der GUI Elemente
        meineRechnungen.add(zeigeRechnungen);
        menuBar.add(meineRechnungen);
        menuBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setJMenuBar(menuBar);

        zeigeRechnungen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVView view = new CSVView();
                view.setVisible(true);
            }
        });

        parameterLabel = new JLabel("Parameter");
        parameterLabel.setFont(new Font("Dialog", Font.BOLD, 16));

        skizzenPanel = new JPanel(new BorderLayout());
        skizzenPanel.setBackground(Color.WHITE);
        skizzenPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 60, 45));
        ImageIcon imageIcon1 = new ImageIcon(Objects.requireNonNull(StartWindow.class.getResource("/images/skizze-1.png")));
        Image image1 = imageIcon1.getImage().getScaledInstance(320, 140, Image.SCALE_SMOOTH);
        ImageIcon imageIcon2 = new ImageIcon(Objects.requireNonNull(StartWindow.class.getResource("/images/skizze-2.png")));
        Image image2 = imageIcon2.getImage().getScaledInstance(220, 160, Image.SCALE_SMOOTH);
        skizzenPanel.add(new JLabel(new ImageIcon(image1)), BorderLayout.NORTH);
        skizzenPanel.add(new JLabel(new ImageIcon(image2)), BorderLayout.SOUTH);

        eingabePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(20, 20, 0, 0);

        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.gridx = 1;
        gbcTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcTextField.weightx = 0.3;
        gbcTextField.insets = new Insets(20, 10, 0, 20);

        gbcLabel.gridy = 0;
        eingabePanel.add(parameterLabel, gbcLabel);

        gbcLabel.gridy = 0;
        eingabePanel.add(new JLabel(""), gbcLabel);

        paramFelder = new JTextField[4];
        String paramName = null;

        for (int i = 0; i < paramFelder.length; i++) {
            paramFelder[i] = new JTextField();
            paramName = switch (i) {
                case 0 -> "F in N";
                case 1 -> "D in mm";
                case 2 -> "l₁ in mm";
                case 3 -> "l₂ in mm";
                default -> paramName;
            };
            gbcLabel.gridy = i + 1;
            eingabePanel.add(new JLabel(paramName), gbcLabel);
            gbcTextField.gridy = i + 1;
            eingabePanel.add(paramFelder[i], gbcTextField);
        }

        String[] valuesRp = {"-- auswählen --", "S235JR", "S275JR", "S355JR", "S450J0", "S185"};
        JLabel labelRp = new JLabel("Werkstoff");
        gbcLabel.gridy = 5;
        werkstoffCombo = new JComboBox<>(valuesRp);
        gbcTextField.gridy = 5;

        eingabePanel.add(labelRp, gbcLabel);
        eingabePanel.add(werkstoffCombo, gbcTextField);

        berechneButton = new JButton("Berechnen");
        berechneButton.setPreferredSize(new Dimension(150, 45));
        berechneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                berechneSicherheit();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(berechneButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(skizzenPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
        rightPanel.add(eingabePanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.setBackground(Color.WHITE);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Funktion zum Weiterleiten der Param an Controller
    private void berechneSicherheit() {
        try {
            double[] inputValues = new double[paramFelder.length];
            String werkstoff;

            for (int i = 0; i < paramFelder.length; i++) {
                inputValues[i] = Double.parseDouble(paramFelder[i].getText());
            }

            if (werkstoffCombo.getSelectedIndex() > 0) {
                werkstoff = Objects.requireNonNull(werkstoffCombo.getSelectedItem()).toString();
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Werkstoff aus.");
                return;
            }

            controller.berechneSicherheit(inputValues, werkstoff);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ungültige Eingabe. Bitte geben Sie nur numerische Werte ein.");
        }
    }
}
