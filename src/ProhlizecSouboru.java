import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class ProhlizecSouboru extends JFrame {
    private JPanel panMain;
    private JButton ofbt;
    private JTextArea ta1;
    private File currentFile;

    public ProhlizecSouboru() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Text File View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panMain = new JPanel();
        panMain.setLayout(new BorderLayout());
        setContentPane(panMain);
        initMenu();

        ofbt.addActionListener(e -> openFile());
        panMain.add(ofbt, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(ta1);
        panMain.add(scrollPane, BorderLayout.CENTER);

        pack();
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu akceMenu = new JMenu("Akce");
        menuBar.add(akceMenu);

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(e -> openFile());
        akceMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveFile());
        akceMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save as...");
        saveAsItem.addActionListener(e -> saveFileAs());
        akceMenu.add(saveAsItem);
    }

   private void openFile() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fc.getSelectedFile();
            try {
                Scanner scanner = new Scanner(currentFile);
                String content = "";
                while (scanner.hasNextLine()) {
                    content += scanner.nextLine() + "\n";
                }
                scanner.close();
                ta1.setText(content);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Chyba při čtení souboru: " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try {
                FileWriter writer = new FileWriter(currentFile);
                writer.write(ta1.getText());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Chyba při zápisu souboru: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nebyl otevřen žádný soubor. Prosím, otevřete soubor nebo zvolte 'Save as...'");
        }
    }

    private void saveFileAs() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fc.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(currentFile);
                writer.write(ta1.getText());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Chyba při zápisu souboru: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ProhlizecSouboru gui = new ProhlizecSouboru();
        gui.setVisible(true);
    }
}
