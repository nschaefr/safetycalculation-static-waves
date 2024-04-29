package safetycalculator.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVView extends JFrame {

    private static final long serialVersionUID = 1L;
	private JTable table;

    public CSVView() {
        setTitle("Meine Rechnungen");

        createTableFromCSV();

        // Fenstereinstellungen
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(680, 520);
    }

    // Erstellt Tabelle basierend auf CSV-File
    @SuppressWarnings("serial")
	private void createTableFromCSV() {
        try (InputStream inputStream = CSVView.class.getResourceAsStream("/files/rechnungen.csv")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String[] columnNames = {"F", "D", "<html>l<sub>1</sub></html>", "<html>l<sub>2</sub></html>", "Werkstoff", "<html>S<sub>F</sub></html>", ""};

                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
					@Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split(",");
                    tableModel.addRow(rowData);
                }

                table = new JTable(tableModel);
                table.setShowGrid(true);
                table.setGridColor(Color.LIGHT_GRAY);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                JTableHeader header = table.getTableHeader();
                header.setFont(new Font("Arial", Font.BOLD, 14));

                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setMinWidth(80);
                    table.getColumnModel().getColumn(i).setMaxWidth(100);
                    table.getColumnModel().getColumn(i).setResizable(false);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}