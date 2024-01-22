package org.harsh.Model.Management;

import org.harsh.Database.DBManager;
import org.harsh.Model.Product;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductManagement extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable taskTable;
    DBManager db = new DBManager();
    private final JTextField pr_name, pr_price, pr_quantity;
    private final JButton addBtn, updateBtn, deleteBtn, backBtn;
    private final ProductManagement product = this;

    public ProductManagement() {
        tableModel = new DefaultTableModel();

        setTitle("Product Management");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel.addColumn("Sr No");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");

        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setPreferredSize(new Dimension(700, 200));

        pr_name = new JTextField(20);
        pr_price = new JTextField(20);
        pr_quantity = new JTextField(20);

        addBtn = new JButton("Add Product");
        updateBtn = new JButton("Update Product");
        deleteBtn = new JButton("Delete Product");
        backBtn = new JButton("Go Back");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(pr_name);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(pr_price);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(pr_quantity);
        inputPanel.add(addBtn);
        inputPanel.add(updateBtn);
        inputPanel.add(deleteBtn);
        inputPanel.add(backBtn);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.NORTH);
        contentPane.add(inputPanel, BorderLayout.CENTER);

        addBtn.addActionListener((ActionEvent e) -> addTask());
        updateBtn.addActionListener(e -> updateTask());
        deleteBtn.addActionListener(e -> deleteProduct());
        backBtn.addActionListener(e -> product.setVisible(false));
    }

    public void loadTasks() {

        List<Product> product = db.getAllProducts();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (Product p : product) {
            tableModel.addRow(new Object[]{
                    p.getProductId(), p.getProductName(), p.getProductPrice(), p.getProductStock()
            });
        }
    }

    private void addTask() {
        String name = pr_name.getText();
        int price = Integer.parseInt(pr_price.getText());
        int quantity = Integer.parseInt(pr_quantity.getText());

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        if (pr_price.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        if (pr_quantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }

        Product newproduct = new Product(name, price, quantity);

        int generatedTaskId = db.addProduct(newproduct);

        if (generatedTaskId > 0) {
            newproduct.setProductId(generatedTaskId);
            tableModel.addRow(new Object[]{newproduct.getProductId(), newproduct.getProductName(), newproduct.getProductPrice(), newproduct.getProductStock()});
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Product Added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Product addition failed.");
        }
    }

    private void clearInputFields() {
        pr_name.setText("");
        pr_price.setText("");
        pr_quantity.setText("");
    }

    private void updateTask() {
        int selectedRow = taskTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a task to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = pr_name.getText();
        double price = Double.parseDouble(pr_price.getText());
        int quantity = Integer.parseInt(pr_quantity.getText());

        Product updatedProduct = new Product(id, name, price, quantity);

        if (db.updateProduct(updatedProduct)) {
            tableModel.setValueAt(updatedProduct.getProductId(), selectedRow, 0);
            tableModel.setValueAt(updatedProduct.getProductName(), selectedRow, 1);
            tableModel.setValueAt(updatedProduct.getProductPrice(), selectedRow, 2);
            tableModel.setValueAt(updatedProduct.getProductStock(), selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Product updated Successfully.");
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Product update failed.");
        }
    }

    private void deleteProduct() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a Product to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        Double price = (Double) tableModel.getValueAt(selectedRow, 2);
        int quantity = (int) tableModel.getValueAt(selectedRow, 3);

        Product pr = new Product(id, name, price, quantity);

        if (db.deleteProduct(pr)) {
            tableModel.removeRow(selectedRow);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Product Deleted Successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Product deletion failed.");
        }
    }
}