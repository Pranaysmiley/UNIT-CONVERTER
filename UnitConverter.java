import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class UnitConverter extends JFrame {
    private JPanel panel;
    private JLabel titleLabel;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel resultLabel;
    private JComboBox<String> categoryBox; // To select category
    private JComboBox<String> fromUnitBox;
    private JComboBox<String> toUnitBox;
    private JButton convertButton;
    private JTextField inputField;

    public UnitConverter() {
        // Initialize frame properties
        setTitle("Unit Converter");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize panel and set layout
        panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        // Initialize components
        titleLabel = new JLabel("Unit Converter");
        fromLabel = new JLabel("From:");
        toLabel = new JLabel("To:");
        resultLabel = new JLabel("Result:");
        inputField = new JTextField(10);

        // Category options (Length, Temperature, Square Root, Cube)
        String[] categories = {"Length", "Temperature", "Square Root", "Cube","Time"};

        categoryBox = new JComboBox<>(categories);
        fromUnitBox = new JComboBox<>();
        toUnitBox = new JComboBox<>();
        convertButton = new JButton("Convert");

        // Set component bounds (x, y, width, height)
        titleLabel.setBounds(140, 20, 250, 20);
        categoryBox.setBounds(150, 50, 100, 20);
        fromLabel.setBounds(50, 90, 80, 20);
        fromUnitBox.setBounds(100, 90, 100, 20);
        toLabel.setBounds(210, 90, 80, 20);
        toUnitBox.setBounds(250, 90, 100, 20);
        inputField.setBounds(100, 120, 100, 20);
        convertButton.setBounds(150, 150, 100, 30);
        resultLabel.setBounds(150, 200, 200, 20);

        // Add components to panel
        panel.add(titleLabel);
        panel.add(categoryBox);
        panel.add(fromLabel);
        panel.add(fromUnitBox);
        panel.add(toLabel);
        panel.add(toUnitBox);
        panel.add(inputField);
        panel.add(convertButton);
        panel.add(resultLabel);

        // Set category change listener to update unit options
        categoryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUnits();
            }
        });

        // Set up action for the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertUnits();
            }
        });

        // Initialize with Length units
        updateUnits();

        setVisible(true);
    }

    // Method to update the units based on selected category
    private void updateUnits() {
        String category = (String) categoryBox.getSelectedItem();

        if (category.equals("Length")) {
            fromUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Kilometer", "Meter", "Centimeter", "Millimeter"}));
            toUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Kilometer", "Meter", "Centimeter", "Millimeter"}));
        } else if (category.equals("Temperature")) {
            fromUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"}));
            toUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"}));
        } else if (category.equals("Square Root") || category.equals("Cube")) {
            fromUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Value"}));
            toUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Square Root", "Cube"}));
        }
        else if (category.equals("Time")) {
            fromUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Second", "Minute", "Hour", "Day"}));
            toUnitBox.setModel(new DefaultComboBoxModel<>(new String[]{"Second", "Minute", "Hour", "Day"}));
        }
    }

    // Method to convert units and perform operations
    private void convertUnits() {
        try {
            double value = Double.parseDouble(inputField.getText());
            String category = (String) categoryBox.getSelectedItem();
            String fromUnit = (String) fromUnitBox.getSelectedItem();
            String toUnit = (String) toUnitBox.getSelectedItem();
            DecimalFormat df = new DecimalFormat("#.######");

            if (category.equals("Length")) {
                // Length Conversion Logic
                double meters = switch (fromUnit) {
                    case "Kilometer" -> value * 1000;
                    case "Meter" -> value;
                    case "Centimeter" -> value / 100;
                    case "Millimeter" -> value / 1000;
                    default -> throw new IllegalArgumentException("Invalid from-unit selected.");
                };

                // Convert meters to the target unit
                double resultValue = switch (toUnit) {
                    case "Kilometer" -> meters / 1000;
                    case "Meter" -> meters;
                    case "Centimeter" -> meters * 100;
                    case "Millimeter" -> meters * 1000;
                    default -> throw new IllegalArgumentException("Invalid to-unit selected.");
                };

                resultLabel.setText("Result: " + df.format(resultValue) + " " + toUnit);

            } else if (category.equals("Temperature")) {
                // Temperature Conversion Logic
                double resultValue = 0;

                if (fromUnit.equals("Celsius")) {
                    if (toUnit.equals("Fahrenheit")) {
                        resultValue = (value * 9/5) + 32;
                    } else if (toUnit.equals("Kelvin")) {
                        resultValue = value + 273.15;
                    } else {
                        resultValue = value;
                    }
                } else if (fromUnit.equals("Fahrenheit")) {
                    if (toUnit.equals("Celsius")) {
                        resultValue = (value - 32) * 5/9;
                    } else if (toUnit.equals("Kelvin")) {
                        resultValue = (value - 32) * 5/9 + 273.15;
                    } else {
                        resultValue = value;
                    }
                } else if (fromUnit.equals("Kelvin")) {
                    if (toUnit.equals("Celsius")) {
                        resultValue = value - 273.15;
                    } else if (toUnit.equals("Fahrenheit")) {
                        resultValue = (value - 273.15) * 9/5 + 32;
                    } else {
                        resultValue = value;
                    }
                }

                resultLabel.setText("Result: " + df.format(resultValue) + " " + toUnit);

            } else if (category.equals("Square Root")) {
                // Square Root Calculation Logic
                double resultValue = Math.sqrt(value);
                resultLabel.setText("Result: " + df.format(resultValue));

            } else if (category.equals("Cube")) {
                // Cube Calculation Logic
                double resultValue = Math.pow(value, 3);
                resultLabel.setText("Result: " + df.format(resultValue));

            }

            else if (category.equals("Time")) {
                // Time Conversion Logic
                double resultValue = 0;
            
                if (fromUnit.equals("Second")) {
                    if (toUnit.equals("Minute")) {
                        resultValue = value / 60;
                    } else if (toUnit.equals("Hour")) {
                        resultValue = value / 3600;
                    } else if (toUnit.equals("Day")) {
                        resultValue = value / 86400;
                    } else {
                        resultValue = value;
                    }
                } else if (fromUnit.equals("Minute")) {
                    if (toUnit.equals("Second")) {
                        resultValue = value * 60;
                    } else if (toUnit.equals("Hour")) {
                        resultValue = value / 60;
                    } else if (toUnit.equals("Day")) {
                        resultValue = value / 1440;
                    } else {
                        resultValue = value;
                    }
                } else if (fromUnit.equals("Hour")) {
                    if (toUnit.equals("Second")) {
                        resultValue = value * 3600;
                    } else if (toUnit.equals("Minute")) {
                        resultValue = value * 60;
                    } else if (toUnit.equals("Day")) {
                        resultValue = value / 24;
                    } else {
                        resultValue = value;
                    }
                } else if (fromUnit.equals("Day")) {
                    if (toUnit.equals("Second")) {
                        resultValue = value * 86400;
                    } else if (toUnit.equals("Minute")) {
                        resultValue = value * 1440;
                    } else if (toUnit.equals("Hour")) {
                        resultValue = value * 24;
                    } else {
                        resultValue = value;
                    }
                }
            
                resultLabel.setText("Result: " + df.format(resultValue) + " " + toUnit);
            }
            
} catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a numeric value.");
        }
    }

    public static void main(String[] args) {
        new UnitConverter();
    }
}
