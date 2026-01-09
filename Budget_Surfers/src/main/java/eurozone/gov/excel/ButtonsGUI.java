package eurozone.gov.excel;

import javax.swing.*;
import java.awt.*;

public class ButtonsGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Μενού επιλογών");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // Κύριο panel με κάθετη στοίχιση
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonSize = new Dimension(900, 80);

        // Button 1
        JButton blueButton1 = createBlueButton("Προβολή στοιχείων κρατικού προϋπολογισμού", buttonSize);
        addButtonWithSpacing(mainPanel, blueButton1);

        // ==== Button 2 με 3 υπο-κουμπιά (toggle) ====
        JPanel button2Container = new JPanel();
        button2Container.setLayout(new BoxLayout(button2Container, BoxLayout.Y_AXIS));
        button2Container.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton blueButton2 = createBlueButton("Σύγκριση ποσοστιαίων δαπανών ανά τομέα με τους μέσους όρους της Ευρωζώνης", buttonSize);

        // Panel για τα 3 πράσινα υπο-κουμπιά
        JPanel subButtonsPanel = new JPanel();
        subButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        subButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ΔΕΝ βάζουμε maximumSize εδώ για να μην κόβει τα κουμπιά

        // Τα 3 πράσινα υπο-κουμπιά
        JButton greenSub1 = createGreenSubButton("Εμφάνιση ποσοστιαίων δαπανών της Ελλάδας ανά τομέα");
        JButton greenSub2 = createGreenSubButton("σύγκριση με τους τομείς της Ευρωζώνης");
        JButton greenSub3 = createGreenSubButton("πίσω");

        subButtonsPanel.add(greenSub1);
        subButtonsPanel.add(greenSub2);
        subButtonsPanel.add(greenSub3);

        // Αρχικά κρυφά
        subButtonsPanel.setVisible(false);

        // Προσθήκη στο container του button2
        button2Container.add(blueButton2);
        button2Container.add(Box.createRigidArea(new Dimension(0, 15))); // απόσταση
        button2Container.add(subButtonsPanel);

        // Toggle όταν πατιέται το blueButton2
        blueButton2.addActionListener(e -> {
            subButtonsPanel.setVisible(!subButtonsPanel.isVisible());
            frame.revalidate();
            frame.repaint();
        });

        mainPanel.add(button2Container);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Υπόλοιπα μπλε κουμπιά
        JButton blueButton3 = createBlueButton("Σύγκριση του προϋπολογισμού τα τελευταία 5 έτη", buttonSize);
        JButton blueButton4 = createBlueButton("Σύγκριση βιοτικού επιπέδου της Ελλάδας με άλλες χώρες της Ευρωζώνης", buttonSize);
        JButton blueButton5 = createBlueButton("Ανάλυση ποσοστιαίων δαπανών ανά περιφέρεια", buttonSize);
        JButton blueButton6 = createBlueButton("Σύγκριση φορολογικών εσόδων αναλογικά με τον μέσο όρο της Ευρωζώνης", buttonSize);
        JButton blueButton7 = createBlueButton("Επεξεργασία στοιχείων προϋπολογισμού", buttonSize);

        addButtonWithSpacing(mainPanel, blueButton3);
        addButtonWithSpacing(mainPanel, blueButton4);
        addButtonWithSpacing(mainPanel, blueButton5);
        addButtonWithSpacing(mainPanel, blueButton6);
        addButtonWithSpacing(mainPanel, blueButton7);

        // Κόκκινο κουμπί εξόδου
        JButton redButton = new JButton("Έξοδος");
        redButton.setBackground(Color.RED);
        redButton.setForeground(Color.WHITE);
        redButton.setOpaque(true);
        redButton.setBorderPainted(false);
        redButton.setPreferredSize(new Dimension(300, 70));
        redButton.setFont(new Font("Arial", Font.BOLD, 20));
        redButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        redButton.addActionListener(e -> System.exit(0));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(redButton);
        redButton.addActionListener(e -> {
            System.out.println("Έξοδος από το πρόγραμμα");
                    
        });

        // Scroll pane για να κυλάει αν χρειαστεί
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    // Μέθοδος για μπλε κουμπιά
    private static JButton createBlueButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Μέθοδος για πράσινα υπο-κουμπιά
    private static JButton createGreenSubButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 140, 0)); // Σκούρο πράσινο
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(280, 60));
        button.setMaximumSize(new Dimension(280, 60)); // Σταθερό μέγεθος
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Βοηθητική για απόσταση μεταξύ κουμπιών
    private static void addButtonWithSpacing(JPanel panel, JComponent button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
}