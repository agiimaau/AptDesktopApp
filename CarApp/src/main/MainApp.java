package main;

import view.LoginScreen;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Set up the look and feel for the application
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the application by showing the login screen
        SwingUtilities.invokeLater(() -> {
            try {
            	LoginScreen.getInstance().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Апп-ыг ажиллуулахад алдаа гарлаа.");
            }
        });
    }
}
