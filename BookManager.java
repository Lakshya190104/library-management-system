import javax.swing.*;
import java.sql.*;

public class BookManager extends JFrame {
    public BookManager() {
        setTitle("Book Manager");
        setSize(400, 300);
        setLayout(null);

        JTextField titleField = new JTextField();
        titleField.setBounds(100, 30, 200, 25);
        add(titleField);

        JTextField authorField = new JTextField();
        authorField.setBounds(100, 70, 200, 25);
        add(authorField);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(150, 110, 100, 30);
        add(addBtn);

        addBtn.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();

            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)");
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book added!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
