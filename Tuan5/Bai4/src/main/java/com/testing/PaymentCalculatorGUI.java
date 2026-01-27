package com.testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PaymentCalculatorGUI - Simple form for Payment Calculator
 */
public class PaymentCalculatorGUI extends JFrame {
    
    private PaymentCalculator calculator;
    
    // Components
    private JTextField ageField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JCheckBox childCheckBox;
    private JButton calculateButton;
    private JTextField paymentField;
    
    public PaymentCalculatorGUI() {
        calculator = new PaymentCalculator();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Payment Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 230);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // Use absolute positioning for exact layout
        mainPanel.setBackground(new Color(192, 192, 192));
        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        
        // Title
        JLabel titleLabel = new JLabel("Calculate the Payment for the Patient");
        titleLabel.setBounds(20, 10, 350, 20);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(titleLabel);
        
        // Gender checkboxes
        maleCheckBox = new JCheckBox("Male");
        maleCheckBox.setBounds(50, 40, 80, 20);
        maleCheckBox.setBackground(new Color(192, 192, 192));
        maleCheckBox.setSelected(true);
        maleCheckBox.addActionListener(e -> {
            if (maleCheckBox.isSelected()) {
                femaleCheckBox.setSelected(false);
                childCheckBox.setSelected(false);
            }
        });
        mainPanel.add(maleCheckBox);
        
        femaleCheckBox = new JCheckBox("Female");
        femaleCheckBox.setBounds(140, 40, 80, 20);
        femaleCheckBox.setBackground(new Color(192, 192, 192));
        femaleCheckBox.addActionListener(e -> {
            if (femaleCheckBox.isSelected()) {
                maleCheckBox.setSelected(false);
                childCheckBox.setSelected(false);
            }
        });
        mainPanel.add(femaleCheckBox);
        
        childCheckBox = new JCheckBox("Child (0 - 17 years)");
        childCheckBox.setBounds(230, 40, 150, 20);
        childCheckBox.setBackground(new Color(192, 192, 192));
        childCheckBox.addActionListener(e -> {
            if (childCheckBox.isSelected()) {
                maleCheckBox.setSelected(false);
                femaleCheckBox.setSelected(false);
            }
        });
        mainPanel.add(childCheckBox);
        
        // Age label and field
        JLabel ageLabel = new JLabel("Age (Years)");
        ageLabel.setBounds(40, 80, 80, 20);
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        mainPanel.add(ageLabel);
        
        ageField = new JTextField();
        ageField.setBounds(120, 78, 100, 25);
        mainPanel.add(ageField);
        
        // Calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(240, 78, 90, 25);
        calculateButton.setBackground(new Color(212, 208, 200));
        calculateButton.addActionListener(new CalculateButtonListener());
        mainPanel.add(calculateButton);
        
        // Payment label and field
        JLabel paymentLabel = new JLabel("Payment is");
        paymentLabel.setBounds(40, 120, 80, 20);
        paymentLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        mainPanel.add(paymentLabel);
        
        paymentField = new JTextField();
        paymentField.setBounds(120, 118, 100, 25);
        paymentField.setEditable(false);
        paymentField.setBackground(Color.WHITE);
        mainPanel.add(paymentField);
        
        JLabel euroLabel = new JLabel("euro €");
        euroLabel.setBounds(230, 120, 60, 20);
        euroLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        mainPanel.add(euroLabel);
        
        add(mainPanel);
    }
    
    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get age
                String ageText = ageField.getText().trim();
                if (ageText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        PaymentCalculatorGUI.this,
                        "Please enter age!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                int age = Integer.parseInt(ageText);
                
                // Get gender
                PaymentCalculator.Gender gender;
                if (maleCheckBox.isSelected()) {
                    gender = PaymentCalculator.Gender.MALE;
                } else if (femaleCheckBox.isSelected()) {
                    gender = PaymentCalculator.Gender.FEMALE;
                } else if (childCheckBox.isSelected()) {
                    gender = PaymentCalculator.Gender.CHILD;
                } else {
                    JOptionPane.showMessageDialog(
                        PaymentCalculatorGUI.this,
                        "Please select a gender!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                // Calculate
                double payment = calculator.calculatePayment(age, gender);
                
                // Display result
                paymentField.setText(String.format("%.0f", payment));
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    PaymentCalculatorGUI.this,
                    "Age must be a number!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    PaymentCalculatorGUI.this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            PaymentCalculatorGUI gui = new PaymentCalculatorGUI();
            gui.setVisible(true);
        });
    }
}
