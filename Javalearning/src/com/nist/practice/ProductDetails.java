package com.nist.practice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ProductDetails {

    public ProductDetails() {
        JFrame frame = new JFrame("Stock Management");
        frame.setSize(700, 500);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Search:");
        l1.setBounds(400, 10, 60, 30);
        frame.add(l1);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(460, 10, 150, 30);
        frame.add(searchTextField);
        
        JComboBox<String> suggestionBox = new JComboBox<>();
        suggestionBox.setBounds(460, 40, 150, 30);
        suggestionBox.setVisible(false);  // paila hidden hunxa
        frame.add(suggestionBox);
        
        
        
        JLabel catLabel = new JLabel("Category:");
        catLabel.setBounds(20, 10, 70, 30);
        frame.add(catLabel);

        String[] categories = {"All", "Electronics", "Clothing", "Stationery", "Furniture", "Household"};
        JComboBox<String> categoryFilter = new JComboBox<>(categories);
        categoryFilter.setBounds(90, 10, 120, 30);
        frame.add(categoryFilter);

        String columns[] = {"ID", "Product Name", "Quantity", "Price", "Supplier", "Category"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 50, 640, 300);
        frame.add(scroll);

        JButton b1 = new JButton("New");
        b1.setBounds(50, 370, 100, 30);
        frame.add(b1);

        JButton b2 = new JButton("Delete");
        b2.setBounds(200, 370, 100, 30);
        frame.add(b2);

        JButton b3 = new JButton("Edit");
        b3.setBounds(350, 370, 100, 30);
        frame.add(b3);
        
        JButton generateBill = new JButton("Generate Bill");
        generateBill.setBounds(500, 370, 100, 30); 
        frame.add(generateBill);

        // load 
        try {
            String sql = "SELECT * FROM product";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("supplier"),
                        rs.getString("category")
                });
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // New button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProductForm();
            }
        });

        // Delete button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Select a product!");
                    return;
                }
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    String sql = "DELETE FROM product WHERE id=?";
                    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Deleted successfully!");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        // Edit button
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Select a product!");
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String name = tableModel.getValueAt(selectedRow, 1).toString();
                int quantity = (int) tableModel.getValueAt(selectedRow, 2);
                double price = (double) tableModel.getValueAt(selectedRow, 3);
                String supplier = tableModel.getValueAt(selectedRow, 4).toString();
                String category = tableModel.getValueAt(selectedRow, 5).toString();

                ProductForm form = new ProductForm();

                form.t1.setText(name);
                form.t2.setText(String.valueOf(quantity));
                form.t3.setText(String.valueOf(price));
                form.t4.setText(supplier);
                form.combo.setSelectedItem(category);

                form.productId = id;

            }
        });
        
        generateBill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); 

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Select a product to generate bill!");
                    return;
                }

                
                String name = tableModel.getValueAt(selectedRow, 1).toString();
                int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                double price = (double) tableModel.getValueAt(selectedRow, 3);
                double total = quantity * price;

               
                JFrame billFrame = new JFrame("Billing");
                billFrame.setSize(400, 400);
                billFrame.setLayout(null);

                String columns[] = {"Product Name", "Quantity", "Price", "Total"};
                DefaultTableModel billModel = new DefaultTableModel(columns, 0);
                JTable billTable = new JTable(billModel);
                JScrollPane scroll = new JScrollPane(billTable);
                scroll.setBounds(20, 50, 350, 250);
                billFrame.add(scroll);

                billModel.addRow(new Object[]{name, quantity, price, total});

                JLabel totalLabel = new JLabel("Total: " + total);
                totalLabel.setBounds(20, 320, 200, 30);
                billFrame.add(totalLabel);

                billFrame.setVisible(true);
            }
        });

        // Search (press Enter style)
        searchTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String search = searchTextField.getText();
                tableModel.setRowCount(0);
                try {
                    String sql = "SELECT * FROM product WHERE LOWER(product_name) LIKE ?";
                    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
                    ps.setString(1, "%" + search.toLowerCase() + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price"),
                                rs.getString("supplier"),
                                rs.getString("category")
                        });
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        
        
        categoryFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected category from the ComboBox
                String selectedCategory = categoryFilter.getSelectedItem().toString();

                // Clear the table before loading new data
                tableModel.setRowCount(0);

                try {
                    String sql;
                    PreparedStatement ps;

                    if (selectedCategory.equals("All")) {
                        // Show all products if "All" is selected
                        sql = "SELECT * FROM product";
                        ps = DatabaseConnection.getConnection().prepareStatement(sql);
                    } else {
                        // Filter by selected category
                        sql = "SELECT * FROM product WHERE category = ?";
                        ps = DatabaseConnection.getConnection().prepareStatement(sql);
                        ps.setString(1, selectedCategory);
                    }

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        tableModel.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price"),
                                rs.getString("supplier"),
                                rs.getString("category")
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        searchTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = searchTextField.getText().trim();
                suggestionBox.removeAllItems();

                if (text.isEmpty()) {
                    suggestionBox.setVisible(false);
                    return;
                }

                try {
                    String sql = "SELECT product_name FROM product WHERE product_name LIKE ?";
                    PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
                    ps.setString(1, text + "%");  // starts with typed text
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        suggestionBox.addItem(rs.getString("product_name"));
                    }

                    if (suggestionBox.getItemCount() > 0) {
                        suggestionBox.setVisible(true);
                    } else {
                        suggestionBox.setVisible(false);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ProductDetails();
    }
}