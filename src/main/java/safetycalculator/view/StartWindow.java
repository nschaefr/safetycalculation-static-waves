package safetycalculator.view;

import javax.swing.*;

import safetycalculator.controller.CalculationController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@SuppressWarnings("serial")
public class StartWindow extends JFrame {
	private final CalculationController controller;
    private JLabel textLabel;
    private JLabel textLabel2;
    private JButton weiterButton;
    private JLabel nameLabel;
    private JLabel nameLabel2;

    public StartWindow(CalculationController controller) {
        this.controller = controller;
        // Fenstereinstellungen
        setTitle("Sicherheitsüberprüfung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(680, 520);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Initialisieren der GUI Elemente
        ImageIcon startImageIcon = new ImageIcon(Objects.requireNonNull(StartWindow.class.getResource("/images/cover.png")));
        Image coverImage = startImageIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);

        textLabel = new JLabel("Überprüfung der Sicherheit");
        textLabel2 = new JLabel("für statisch beanspruchte Wellen");
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel2.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        textLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 40));
        textLabel2.setFont(new Font("Dialog", Font.BOLD, 22));
        textLabel2.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(textLabel2, BorderLayout.SOUTH);
        textPanel.setBackground(Color.WHITE);

        weiterButton = new JButton("Weiter");
        weiterButton.setPreferredSize(new Dimension(100, 30));
        weiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startInput();
                dispose();
            }
        });

        nameLabel = new JLabel("Schäfer Nils");
        nameLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        nameLabel2 = new JLabel("SWE-Projekt");
        nameLabel2.setFont(new Font("Dialog", Font.BOLD, 16));

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(Color.WHITE);
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameLabel2, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(namePanel, BorderLayout.WEST);
        bottomPanel.add(weiterButton, BorderLayout.EAST);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        add(textPanel, BorderLayout.NORTH);
        add(new JLabel(new ImageIcon(coverImage)), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Funktion zum Starten des InputWindows
    private void startInput() {
        controller.startInput();
    }
}
