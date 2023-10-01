package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField displayField;
    private JButton[] digitButtons;
    private JButton[] operationButtons;
    private StringBuilder currentInput = new StringBuilder();
    private double firstOperand = 0;
    private String currentOperation = "";


    public Calculator() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        displayField = new JTextField(10);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(displayField, gbc);

        digitButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = new JButton(Integer.toString(i));
            gbc.gridx = i % 3;
            gbc.gridy = 3 - (i / 3);
            gbc.gridwidth = 1;
            panel.add(digitButtons[i], gbc);
            digitButtons[i].addActionListener(new DigitButtonListener(Integer.toString(i)));
        }

        operationButtons = new JButton[4];
        String[] operations = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            operationButtons[i] = new JButton(operations[i]);
            gbc.gridx = 3;
            gbc.gridy = i + 1;
            panel.add(operationButtons[i], gbc);
            operationButtons[i].addActionListener(new OperationButtonListener(operations[i]));
        }

        JButton equalsButton = new JButton("=");
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(equalsButton, gbc);
        equalsButton.addActionListener(new EqualsButtonListener());

        JButton resetButton = new JButton("C");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(resetButton, gbc);
        resetButton.addActionListener(new ResetButtonListener());

        JButton deleteButton = new JButton("Del");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(deleteButton, gbc);
        deleteButton.addActionListener(new DeleteButtonListener());

        add(panel);
        setVisible(true);
    }

    private class DigitButtonListener implements ActionListener {
        private String digit;

        public DigitButtonListener(String digit) {
            this.digit = digit;
        }

        @Override
    public void actionPerformed(ActionEvent e) {
        currentInput.append(digit);
        displayField.setText(currentInput.toString());
    }
}

    private class OperationButtonListener implements ActionListener {
        private String operation;

        public OperationButtonListener(String operation) {
            this.operation = operation;
        }

@Override
    public void actionPerformed(ActionEvent e) {
        if (!currentOperation.isEmpty()) {
            performOperation();
        }
        currentOperation = operation;
        firstOperand = Double.parseDouble(currentInput.toString());
        
        currentInput.setLength(0);
    }
}

    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!currentOperation.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput.toString());
            performOperation();
            currentOperation = "";
        }
            currentInput.setLength(0);
    }
}
    private class ResetButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        
        currentInput.setLength(0);
        firstOperand = 0;
        currentOperation = "";
        displayField.setText("");
    }
}

private class DeleteButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
            displayField.setText(currentInput.toString());
        }
    }
}

    private void performOperation() {
    double result = 0;
    double secondOperand = Double.parseDouble(currentInput.toString());

    switch (currentOperation) {
        case "+":
            result = firstOperand + secondOperand;
            break;
        case "-":
            result = firstOperand - secondOperand;
            break;
        case "*":
            result = firstOperand * secondOperand;
            break;
        case "/":
            if (secondOperand != 0) {
                result = firstOperand / secondOperand;
            } else {
                displayField.setText("Error");
                return;
            }
            break;
    }

    displayField.setText(Double.toString(result));
    currentInput.setLength(0);
    currentInput.append(result);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
