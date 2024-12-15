package view;

import service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignUpScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField lastnameField;
    private JTextField firstnameField;
    private JTextField apartmentNumberField;
    private JTextField doorNumberField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    private UserService userService;

    public SignUpScreen() throws Exception {
        userService = new UserService();

        setTitle("Бүртгүүлэх");
        setSize(600, 500); // Adjust size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Add window listener to show LoginScreen when this window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                openLoginScreen();
            }
        });

        // Main panel with GridBagLayout for structured design
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0xDAF2BE)); // Soft pastel background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for elements
        add(mainPanel);

        lastnameField = createStyledTextField();
        firstnameField = createStyledTextField();
        apartmentNumberField = createStyledTextField();
        doorNumberField = createStyledTextField();
        usernameField = createStyledTextField();
        passwordField = createStyledPasswordField();

        addField("Овог:", lastnameField, mainPanel, gbc, 0);
        addField("Нэр:", firstnameField, mainPanel, gbc, 1);
        addField("Байрны дугаар:", apartmentNumberField, mainPanel, gbc, 2);
        addField("Хаалганы дугаар:", doorNumberField, mainPanel, gbc, 3);
        addField("Хэрэглэгчийн нэр:", usernameField, mainPanel, gbc, 4);
        addField("Нууц үг:", passwordField, mainPanel, gbc, 5);

        // Register button with stylish design
        registerButton = createStyledButton("Бүртгүүлэх");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        // Add action listener to the button
        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String lastname = lastnameField.getText();
        String firstname = firstnameField.getText();
        String apartmentNumber = apartmentNumberField.getText();
        String doorNumber = doorNumberField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate inputs
        if (lastname == null || lastname.trim().isEmpty() ||
            firstname == null || firstname.trim().isEmpty() ||
            apartmentNumber == null || apartmentNumber.trim().isEmpty() ||
            doorNumber == null || doorNumber.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Бүх талбаруудыг бөглөнө үү!", "Алдаа", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean isRegistered = userService.registerUser(lastname, firstname, apartmentNumber, doorNumber, username, password);
            if (isRegistered) {
                JOptionPane.showMessageDialog(this, "Амжилттай бүртгэгдлээ!", "Амжилттай", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close SignUpScreen
            } else {
                JOptionPane.showMessageDialog(this, "Хэрэглэгчийн нэр аль хэдийн бүртгэгдсэн байна!", "Алдаа", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Бүртгэхэд алдаа гарлаа.", "Алдаа", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLoginScreen() {
        SwingUtilities.invokeLater(() -> {
            try {
            	LoginScreen.getInstance().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void addField(String label, JComponent field, JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(new LineBorder(new Color(0xA3C4BC), 2, true)); // Rounded border
        textField.setBackground(new Color(0xFFFFFF)); // White background
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(new LineBorder(new Color(0xA3C4BC), 2, true)); // Rounded border
        passwordField.setBackground(new Color(0xFFFFFF)); // White background
        return passwordField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        // Set default button styles
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0xFFD1DC)); // Pastel pink background
        button.setForeground(new Color (0x8B4513)); // Text color
        button.setOpaque(true); // Ensure the background is visible
        button.setContentAreaFilled(true); // Fill the background
        button.setContentAreaFilled(true);
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(new LineBorder(new Color(0xFFB6C1), 2, true)); // Rounded pastel pink border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        button.setPreferredSize(new Dimension(150, 40)); // Set size
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // Use basic UI for consistent styling

        // Add MouseListener for hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color defaultColor = button.getBackground();
            Color hoverColor = new Color(0xFFC1D6); // Slightly darker pastel pink for hover

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hoverColor); // Change to hover color
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(defaultColor); // Revert to default color
            }
        });

        return button;
    }
}
