package com.nist.practice;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ProductForm {
	JTextField t1, t2, t3, t4;
    JComboBox<String> combo;
    int productId = -1;

    public ProductForm() {
    	 
        JFrame frame = new JFrame("Product Form");
        frame.setSize(600, 450);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Product Name");
        l1.setBounds(20, 20, 120, 30);
        frame.add(l1);
        t1 = new JTextField();
        t1.setBounds(150, 20, 200, 30);
        frame.add(t1);

        JLabel l2 = new JLabel("Quantity");
        l2.setBounds(20, 70, 120, 30);
        frame.add(l2);
        t2 = new JTextField();
        t2.setBounds(150, 70, 200, 30);
        frame.add(t2);

        JLabel l3 = new JLabel("Price");
        l3.setBounds(20, 120, 120, 30);
        frame.add(l3);
        t3 = new JTextField();
        t3.setBounds(150, 120, 200, 30);
        frame.add(t3);

        JLabel l4 = new JLabel("Supplier");
        l4.setBounds(20, 170, 120, 30);
        frame.add(l4);
        t4 = new JTextField();
        t4.setBounds(150, 170, 200, 30);
        frame.add(t4);

        JLabel l5 = new JLabel("Category");
        l5.setBounds(20, 220, 120, 30);
        frame.add(l5);
        String categories[] = {"Electronics", "Food", "Clothing", "Furniture","Household","Other"};
        combo = new JComboBox<String>(categories);
        combo.setBounds(150, 220, 200, 30);
        frame.add(combo);

        JCheckBox checkbox = new JCheckBox("Accept terms and conditions");
        checkbox.setBounds(150, 270, 250, 30);
        frame.add(checkbox);

        JButton submit = new JButton("Submit");
        submit.setBounds(150, 320, 100, 30);
        frame.add(submit);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = t1.getText();
                int quantity = Integer.parseInt(t2.getText());
                double price = Double.parseDouble(t3.getText());
                String supplier = t4.getText();
                String category = combo.getSelectedItem().toString();

                try {

                    if(productId == -1) {

                        String sql = "INSERT INTO product (product_name, quantity, price, supplier, category) VALUES (?,?,?,?,?)";
                        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

                        ps.setString(1, name);
                        ps.setInt(2, quantity);
                        ps.setDouble(3, price);
                        ps.setString(4, supplier);
                        ps.setString(5, category);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(frame,"Product Added!");

                    } else {

                        String sql = "UPDATE product SET product_name=?, quantity=?, price=?, supplier=?, category=? WHERE id=?";
                        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

                        ps.setString(1, name);
                        ps.setInt(2, quantity);
                        ps.setDouble(3, price);
                        ps.setString(4, supplier);
                        ps.setString(5, category);
                        ps.setInt(6, productId);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(frame,"Product Updated!");

                    }

                    frame.dispose();
                    new ProductDetails();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
    }
    public static void main(String[] args) {
		new ProductForm();
	}
    
}