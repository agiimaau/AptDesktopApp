package view;

import model.Car;
import model.Complaint;
import model.User;
import service.ComplaintService;
import service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable userTable;
    private DefaultTableModel userTableModel;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton logoutButton;
    private JButton showCarsButton;
    private UserService userService;
    private JButton viewComplaintsButton;

    public AdminDashboard() throws Exception {
        userService = new UserService();

        setTitle("Админы хэсэг");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xDAF2BE)); // Pastel green background
        add(mainPanel);

        // Header Panel
        JLabel titleLabel = new JLabel("Бүртгэгдсэн оршин суугчид", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x8B4513)); // Brown text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(0xFFFFFF)); // White background
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userTableModel = new DefaultTableModel(new String[]{"ID", "Нэр", "Байр", "Хаалга", "Хэрэглэгчийн нэр", "Үүрэг"}, 0);
        userTable = new JTable(userTableModel);
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setBackground(new Color(0xFFFFFF)); // Table background
        userTable.setGridColor(new Color(0xA3C4BC)); // Grid color
        userTable.setBorder(new LineBorder(new Color(0xA3C4BC), 1, true));

        JScrollPane userScrollPane = new JScrollPane(userTable);
        userScrollPane.getViewport().setBackground(new Color(0xFFFFFF)); // Scroll pane background
        userScrollPane.setBorder(new LineBorder(new Color(0xA3C4BC), 1, true));
        tablePanel.add(userScrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, spacing of 10
        buttonPanel.setBackground(new Color(0xDAF2BE)); // Match main background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addUserButton = createStyledButton("Хэрэглэгч нэмэх");
        editUserButton = createStyledButton("Хэрэглэгчийг засах");
        deleteUserButton = createStyledButton("Хэрэглэгчийг устгах");
        showCarsButton = createStyledButton("Бүх машиныг харуулах");
        logoutButton = createStyledButton("Гарах");
        viewComplaintsButton = createStyledButton("Гомдлуудыг харах");
        
       
        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(showCarsButton);
        buttonPanel.add(viewComplaintsButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addListeners();
        loadLiverUsers();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0xFFD1DC)); // Light pink background
        button.setForeground(new Color(0x8B4513)); // Brown text
        button.setOpaque(true); // Ensure the background is visible
        button.setContentAreaFilled(true); // Ensure content area is filled
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(new LineBorder(new Color(0xFFB6C1), 2, true)); // Rounded pink border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor for hover
        button.setPreferredSize(new Dimension(150, 40)); // Button size

        // Force using the BasicButtonUI to prevent Look and Feel overrides
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        return button;
    }

    private void loadLiverUsers() {
        try {
            List<User> liverUsers = userService.getLiverUsers();
            userTableModel.setRowCount(0); // Clear table rows
            for (User user : liverUsers) {
                userTableModel.addRow(new Object[]{
                        user.getUserId(),
                        user.getFirstname() + " " + user.getLastname(),
                        user.getApartmentNumber(),
                        user.getDoorNumber(),
                        user.getUsername(),
                        user.getRole()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addListeners() {
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginScreen.getInstance().setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        addUserButton.addActionListener(e -> {
            JTextField firstnameField = new JTextField();
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

            int option = JOptionPane.showConfirmDialog(null, message, "Шинэ хэрэглэгч нэмэх", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String firstname = firstnameField.getText();
                    String lastname = lastnameField.getText();
                    String apartment = apartmentField.getText();
                    String door = doorField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    if (firstname.isEmpty() || lastname.isEmpty() || apartment.isEmpty() || door.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Бүх талбарыг бөглөнө үү!");
                        return;
                    }

                    User newUser = new User(lastname, firstname, apartment, door, username, password, "liver");
                    boolean success = userService.saveUser(newUser);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Оршин суугч амжилттай нэмэгдлээ.");
                        loadLiverUsers();
                    } else {
                        JOptionPane.showMessageDialog(null, "Хэрэглэгч нэмэхэд алдаа гарлаа.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Хэрэглэгчийг нэмэхэд алдаа гарлаа.");
                }
            }
        });

        editUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) userTableModel.getValueAt(selectedRow, 0);
                try {
                    User userToEdit = userService.getUserById(userId);
                    if (userToEdit == null) {
                        JOptionPane.showMessageDialog(null, "Хэрэглэгч олдсонгүй.");
                        return;
                    }

                    JTextField firstnameField = new JTextField(userToEdit.getFirstname());
                    JTextField lastnameField = new JTextField(userToEdit.getLastname());
                    JTextField apartmentField = new JTextField(userToEdit.getApartmentNumber());
                    JTextField doorField = new JTextField(userToEdit.getDoorNumber());
                    JTextField usernameField = new JTextField(userToEdit.getUsername());

                    Object[] message = {
                            "First Name:", firstnameField,
                            "Last Name:", lastnameField,
                            "Apartment Number:", apartmentField,
                            "Door Number:", doorField,
                            "Username:", usernameField,
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Edit User", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        userToEdit.setFirstname(firstnameField.getText());
                        userToEdit.setLastname(lastnameField.getText());
                        userToEdit.setApartmentNumber(apartmentField.getText());
                        userToEdit.setDoorNumber(doorField.getText());
                        userToEdit.setUsername(usernameField.getText());

                        boolean success = userService.updateUser(userToEdit);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Хэрэглэгч амжилттай шинэчлэгдлээ!");
                            loadLiverUsers();
                        } else {
                            JOptionPane.showMessageDialog(null, "Хэрэглэгчийг шинэчлэхэд алдаа гарлаа.");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Хэрэглэгчийг засахад алдаа гарлаа.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Засах хэрэглэгчээ сонгоно уу!");
            }
        });

        deleteUserButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int userId = (int) userTableModel.getValueAt(selectedRow, 0);
                try {
                    boolean success = userService.deleteUser(userId);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Амжилттай хэрэглэгчийг устгалаа!");
                        loadLiverUsers();
                    } else {
                        JOptionPane.showMessageDialog(null, "Хэрэглэгчийг устгахад алдаа гарлаа.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Устгах хэрэглэгчээ сонгоно уу!");
            }
        });

        showCarsButton.addActionListener(e -> {
            try {
                List<Car> allCars = userService.getAllCars();
                DefaultTableModel carTableModel = new DefaultTableModel(new String[]{"Машины ID", "Модел", "Дугаар", "Эзэмшигчийн ID"}, 0);
                for (Car car : allCars) {
                    carTableModel.addRow(new Object[]{car.getCarId(), car.getCarModel(), car.getCarPlate(), car.getUserId()});
                }

                JTable carTable = new JTable(carTableModel);
                JScrollPane carScrollPane = new JScrollPane(carTable);
                carScrollPane.getViewport().setBackground(new Color(0xFFFFFF));
                carScrollPane.setBorder(new LineBorder(new Color(0xA3C4BC), 1, true));

                JFrame carFrame = new JFrame("All Cars");
                carFrame.setSize(600, 400);
                carFrame.setLocationRelativeTo(null);
                carFrame.add(carScrollPane);
                carFrame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Машины мэдээллийг авахад алдаа гарлаа.");
            }
        });
        viewComplaintsButton.addActionListener(e -> {
            try {
                ComplaintService complaintService = new ComplaintService();
                List<Complaint> complaints = complaintService.getAllComplaints(); // Create this method in ComplaintService
                DefaultTableModel complaintTableModel = new DefaultTableModel(new String[]{"Гомдлын ID", "Хэрэглэгчийн ID", "Дэлгэрэнгүй", "Төлөв"}, 0);

                for (Complaint complaint : complaints) {
                    complaintTableModel.addRow(new Object[]{
                        complaint.getComplaintId(),
                        complaint.getUserId(),
                        complaint.getDescription(),
                        complaint.getStatus()
                    });
                }

                JTable complaintTable = new JTable(complaintTableModel);
                JScrollPane complaintScrollPane = new JScrollPane(complaintTable);
                JFrame complaintFrame = new JFrame("Гомдлууд");
                complaintFrame.setSize(800, 400);
                complaintFrame.add(complaintScrollPane);
                complaintFrame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Гомдлын мэдээллийг ачаалахад алдаа гарлаа.");
            }
        });

    }
}
