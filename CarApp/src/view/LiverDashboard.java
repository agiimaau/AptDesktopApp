package view;

import model.Car;
import model.User;
import service.CarService;
import service.ComplaintService;
import service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LiverDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private User currentUser;
    private CarService carService;
    private UserService userService;

    private JTable carTable;
    private DefaultTableModel carTableModel;
    private JButton addCarButton;
    private JButton editCarButton;
    private JButton deleteCarButton;
    private JButton viewFamilyButton;
    private JButton logoutButton;
    private JButton submitComplaintButton;

    public LiverDashboard(User user) throws Exception {
    	
        currentUser = user;
        carService = new CarService();
        userService = new UserService();

        setTitle("Оршин суугч");
        setSize(800, 600); // Adjust size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xDAF2BE)); // Soft background
        add(mainPanel);

        // Top Welcome Panel
        JLabel welcomeLabel = new JLabel("<html>*❀° Тавтай морил, <b>" + currentUser.getFirstname() + "</b> °❀*</html>", SwingConstants.CENTER);
       
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0x8B4513)); // Brown text
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel for Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(0xDAF2BE)); // Background color for the panel

        carTableModel = new DefaultTableModel(new String[]{"Машины ID", "Машины модел", "Машины дугаар"}, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make "Car ID" column non-editable
            }
        };

        // Table styling
        carTable = new JTable(carTableModel);
        carTable.setRowHeight(30);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setBackground(new Color(0xFFFFFF)); // White background
        carTable.setGridColor(new Color(0xA3C4BC)); // Grid color
        carTable.setBorder(new LineBorder(new Color(0xA3C4BC), 1, true));

        // ScrollPane styling
        JScrollPane carScrollPane = new JScrollPane(carTable);
        carScrollPane.getViewport().setBackground(new Color(0xFFFFFF)); // Set white background for the viewport
        carScrollPane.setBorder(new LineBorder(new Color(0xA3C4BC), 1, true)); // Border for the scroll pane

        centerPanel.add(carScrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        

        // Bottom Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBackground(new Color(0xDAF2BE));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        addCarButton = createStyledButton("Машин нэмэх");
        editCarButton = createStyledButton("Машиныг засах");
        deleteCarButton = createStyledButton("Машиныг устгах");
        viewFamilyButton = createStyledButton("Гэр бүлийн гишүүдээ харах");
        logoutButton = createStyledButton("Гарах");
        submitComplaintButton = createStyledButton("Гомдол гаргах");
       
        
        buttonPanel.add(addCarButton);
        buttonPanel.add(editCarButton);
        buttonPanel.add(deleteCarButton);
        buttonPanel.add(viewFamilyButton);
        buttonPanel.add(submitComplaintButton);
        buttonPanel.add(logoutButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        

        addListeners();
        loadCars();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0xFFD1DC)); // Light pink background
        button.setForeground(new Color(0x8B4513)); // Brown text
        button.setOpaque(true); 
        button.setContentAreaFilled(true); 
        button.setFocusPainted(false); 
        button.setBorder(new LineBorder(new Color(0xFFB6C1), 2, true)); // Rounded pink border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor for hover
        button.setPreferredSize(new Dimension(150, 40)); // Button size

        // Force using the BasicButtonUI to prevent Look and Feel overrides
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        return button;
    }



    private void loadCars() {
        try {
            List<Car> cars = carService.getCarsByHousehold(currentUser.getApartmentNumber(), currentUser.getDoorNumber());
            carTableModel.setRowCount(0); // Clear existing rows
            for (Car car : cars) {
                carTableModel.addRow(new Object[]{car.getCarId(), car.getCarModel(), car.getCarPlate()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addListeners() {
    	logoutButton.addActionListener(e -> {
    	    System.out.println("Logout button clicked!");
    	    SwingUtilities.invokeLater(() -> {
    	        try {
    	        	LoginScreen loginScreen = LoginScreen.getInstance();
    	            loginScreen.setVisible(true);
    	            dispose();
    	        } catch (Exception ex) {
    	            ex.printStackTrace();
    	            JOptionPane.showMessageDialog(null, "Нэвтрэх цонх нээхэд алдаа гарлаа.");
    	        }
    	    });
    	});


        addCarButton.addActionListener(e -> {
            try {
                List<Car> householdCars = carService.getCarsByHousehold(currentUser.getApartmentNumber(), currentUser.getDoorNumber());
                if (householdCars.size() >= 2) {
                    JOptionPane.showMessageDialog(null, "Нэг айл хамгийн ихдээ 2 машин л байна.");
                    return;
                }

                String carModel = JOptionPane.showInputDialog("Машиныхаа моделийг оруул:");
                if (carModel == null || carModel.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Машины модел хоосон байж болохгүй.");
                    return;
                }

                String carPlate = JOptionPane.showInputDialog("Машины дугаарыг оруул:");
                if (carPlate == null || carPlate.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Машины дугаар хоосон байж болохгүй.");
                    return;
                }

                boolean success = carService.addCar(currentUser.getUserId(), carModel, carPlate);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Машиныг амжилттай нэмлээ!");
                    loadCars();
                } else {
                    JOptionPane.showMessageDialog(null, "Машин нэмэхэд алдаа гарлаа.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Машин нэмэхэд алдаа гарлаа.");
            }
        });
        
            logoutButton.addActionListener(e -> {
                dispose(); // Close current window
                SwingUtilities.invokeLater(() -> {
                    try {
                    	LoginScreen.getInstance().setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            });

            addCarButton.addActionListener(e -> {
                try {
                    List<Car> householdCars = carService.getCarsByHousehold(currentUser.getApartmentNumber(), currentUser.getDoorNumber());
                    if (householdCars.size() >= 2) {
                        JOptionPane.showMessageDialog(null, "Нэг айл хамгийн ихдээ 2 машин л байна.");
                        return;
                    }

                    String carModel = JOptionPane.showInputDialog("Машиныхаа моделийг оруул:");
                    if (carModel == null || carModel.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Машины модел хоосон байж болохгүй.");
                        return;
                    }

                    String carPlate = JOptionPane.showInputDialog("Машины дугаарыг оруул:");
                    if (carPlate == null || carPlate.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Машины дугаар хоосон байж болохгүй.");
                        return;
                    }

                    boolean success = carService.addCar(currentUser.getUserId(), carModel, carPlate);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Машиныг амжилттай нэмлээ!");
                        loadCars();
                    } else {
                        JOptionPane.showMessageDialog(null, "Машин нэмэхэд алдаа гарлаа.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Машин нэмэхэд алдаа гарлаа.");
                }
            });

            editCarButton.addActionListener(e -> {
                int selectedRow = carTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int carId = (int) carTableModel.getValueAt(selectedRow, 0);
                    String carModel = (String) carTableModel.getValueAt(selectedRow, 1);
                    String carPlate = (String) carTableModel.getValueAt(selectedRow, 2);

                    JTextField modelField = new JTextField(carModel);
                    JTextField plateField = new JTextField(carPlate);

                    Object[] message = {
                        "Машины модел:", modelField,
                        "Машины дугаар:", plateField
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Машины мэдээллийг засах", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String newCarModel = modelField.getText().trim();
                        String newCarPlate = plateField.getText().trim();

                        if (newCarModel.isEmpty() || newCarPlate.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Машины модел болон дугаар хоосон байж болохгүй.");
                            return;
                        }

                        try {
                            Car carToEdit = carService.getCarById(carId);
                            if (carToEdit != null) {
                                carToEdit.setCarModel(newCarModel);
                                carToEdit.setCarPlate(newCarPlate);

                                boolean success = carService.updateCar(carToEdit);
                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Машины мэдээллийг амжилттай шинэчиллээ!");
                                    loadCars();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Машиныг шинэчлэхэд алдаа гарлаа.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Машин олдсонгүй.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Машиныг шинэчлэхэд алдаа гарлаа.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Засах машиныг сонгоно уу!");
                }
            });

            deleteCarButton.addActionListener(e -> {
                int selectedRow = carTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int carId = (int) carTableModel.getValueAt(selectedRow, 0);

                    int confirm = JOptionPane.showConfirmDialog(null, "Энэ машиныг устгахдаа итгэлтэй байна уу?", "Баталгаажуулалт", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            boolean success = carService.deleteCar(carId);
                            if (success) {
                                JOptionPane.showMessageDialog(null, "Машиныг амжилттай устгалаа!");
                                loadCars();
                            } else {
                                JOptionPane.showMessageDialog(null, "Машиныг устгахад алдаа гарлаа.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Машиныг устгахад алдаа гарлаа.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Устгах машиныг сонгоно уу!");
                }
            });

            viewFamilyButton.addActionListener(e -> {
                try {
                    List<User> familyMembers = userService.getUsersByApartment(currentUser.getApartmentNumber(), currentUser.getDoorNumber());
                    StringBuilder familyInfo = new StringBuilder("Гэр бүлийн гишүүд:\n");
                    for (User family : familyMembers) {
                        familyInfo.append(family.getFirstname()).append(" ").append(family.getLastname()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, familyInfo.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Гэр бүлийн гишүүдийн мэдээллийг ачаалахад алдаа гарлаа.");
                }
            });
            submitComplaintButton.addActionListener(e -> {
                String description = JOptionPane.showInputDialog("Гомдлын дэлгэрэнгүйг оруулна уу:");
                if (description != null && !description.trim().isEmpty()) {
                    try {
                        ComplaintService complaintService = new ComplaintService();
                        boolean success = complaintService.addComplaint(currentUser.getUserId(), description);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Гомдол амжилттай нэмэгдлээ!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Гомдол нэмэхэд алдаа гарлаа.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Гомдол нэмэхэд алдаа гарлаа.");
                    }
                }
            });
  

    }
}
