package view;

import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginScreen extends JFrame {
	
    /**
	 * 
	 */
	private static LoginScreen instance;

    public static synchronized LoginScreen getInstance() throws Exception {
        if (instance == null) {
            instance = new LoginScreen();
        }
        return instance;
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null; // Reset the instance when the screen is disposed
    }
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    private UserService userService;

    private LoginScreen() throws Exception {
        userService = new UserService();

        setTitle("Нэвтрэх");
        setSize(900, 500); // Adjust size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);
     

        // Left Panel for Image and Label
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for tighter spacing

        // Label above the picture
        JLabel topLabel = new JLabel("Мэлхий СӨХ-д тавтай морил", SwingConstants.CENTER);
        topLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Cute font
        topLabel.setForeground(new Color(0x8B4513)); // Soft blue color
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel
        leftPanel.add(topLabel);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        // Add a small vertical gap
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Image
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("src/images/frogg.png"); // Replace with your image path
        Image resizedImage = originalIcon.getImage().getScaledInstance(420, 360, Image.SCALE_SMOOTH); // Adjust size
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        imageLabel.setIcon(resizedIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align within the panel
        leftPanel.add(imageLabel);
        
        leftPanel.setBackground(new Color(0xFFFFFF));

        // Add the left panel to the main layout
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right Panel for Login Form
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        loginPanel.setBackground(new Color(0xDAF2BE)); // Soft pastel background
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Хэрэглэгчийн нэр:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        loginPanel.add(usernameLabel, gbc);

        usernameField = createStyledTextField();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Нууц үг:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        loginPanel.add(passwordLabel, gbc);

        passwordField = createStyledPasswordField();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, gbc);

        // Login Button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20)); // Center aligned with spacing
        buttonsPanel.setBackground(new Color(0xDAF2BE)); // Match background color

        // Add Login Button
        loginButton = createStyledButton("Нэвтрэх");
        buttonsPanel.add(loginButton);

        // Add Sign Up Button
        signUpButton = createStyledButton("Бүртгүүлэх");
        buttonsPanel.add(signUpButton);

        // Add Buttons Panel to the Login Panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonsPanel, gbc);

        addListeners();
    }

    // Method to create a styled text field
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(new LineBorder(new Color(0xA3C4BC), 2, true)); // Rounded border
        textField.setBackground(new Color(0xFFFFFF)); // White background
        textField.setMargin(new Insets(5, 5, 5, 5)); // Inner padding
        return textField;
    }

    // Method to create a styled password field
    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(new LineBorder(new Color(0xA3C4BC), 2, true)); // Rounded border
        passwordField.setBackground(new Color(0xFFFFFF)); // White background
        passwordField.setMargin(new Insets(5, 5, 5, 5)); // Inner padding
        return passwordField;
    }

    // Method to create a styled button
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


    private void addListeners() {
    	loginButton.addActionListener(e -> {
    	    String username = usernameField.getText();
    	    String password = new String(passwordField.getPassword());
    	    try {
    	        User user = userService.loginUser(username, password);
    	        if (user != null) {
    	            dispose(); // Ensure the current window is closed
    	            if ("admin".equalsIgnoreCase(user.getRole())) {
    	                SwingUtilities.invokeLater(() -> {
    	                    try {
    	                        new AdminDashboard().setVisible(true);
    	                    } catch (Exception ex) {
    	                        ex.printStackTrace();
    	                    }
    	                });
    	            } else {
    	                SwingUtilities.invokeLater(() -> {
    	                    try {
    	                        new LiverDashboard(user).setVisible(true);
    	                    } catch (Exception ex) {
    	                        ex.printStackTrace();
    	                    }
    	                });
    	            }
    	        } else {
    	            JOptionPane.showMessageDialog(null, "Нууц үг эсвэл нэвтрэх нэр буруу байна.");
    	        }
    	    } catch (Exception ex) {
    	        ex.printStackTrace();
    	        JOptionPane.showMessageDialog(null, "Нэвтрэхэд алдаа гарлаа.");
    	    }
    	});

    	signUpButton.addActionListener(e -> {
    	    dispose(); // Close current window before transitioning
    	    SwingUtilities.invokeLater(() -> {
    	        try {
    	            new SignUpScreen().setVisible(true);
    	        } catch (Exception ex) {
    	            ex.printStackTrace();
    	        }
    	    });
    	
            /*JTextField firstnameField = new JTextField();
            JTextField lastnameField = new JTextField();
            JTextField apartmentField = new JTextField();
            JTextField doorField = new JTextField();
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JPasswordField();

            Object[] message = {
                    "First Name:", firstnameField,
                    "Last Name:", lastnameField,
                    "Apartment Number:", apartmentField,
                    "Door Number:", doorField,
                    "Username:", usernameField,
                    "Password:", passwordField,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Бүртгүүлэх", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String firstname = firstnameField.getText();
                    String lastname = lastnameField.getText();
                    String apartment = apartmentField.getText();
                    String door = doorField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    if (firstname.isEmpty() || lastname.isEmpty() || apartment.isEmpty() || door.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Бүх талбаруудыг бөглөнө үү!");
                        return;
                    }

                    User newUser = new User(lastname, firstname, apartment, door, username, password, "liver");
                    boolean success = userService.saveUser(newUser);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Амжилттай бүртгэгдлээ. Нэвтэрнэ үү.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Бүртгэл хийхэд алдаа гарлаа.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Бүртгэхэд алдаа гарлаа.");
                }
            }*/
        });
    }
}
