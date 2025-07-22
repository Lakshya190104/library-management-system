import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class BorrowManager extends JFrame {
    public BorrowManager(int userId) {
        setTitle("Borrow or Return");
        setSize(500, 400);
        setLayout(null);

        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(100, 30, 200, 25);
        add(bookIdField);

        JButton borrowBtn = new JButton("Borrow Book");
        borrowBtn.setBounds(100, 70, 150, 30);
        add(borrowBtn);

        JButton returnBtn = new JButton("Return Book");
        returnBtn.setBounds(100, 120, 150, 30);
        add(returnBtn);

        borrowBtn.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO borrow (user_id, book_id, borrow_date) VALUES (?, ?, ?)");
                stmt.setInt(1, userId);
                stmt.setInt(2, bookId);
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();

                PreparedStatement updateBook = conn.prepareStatement("UPDATE books SET available = FALSE WHERE book_id = ?");
                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book borrowed!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        returnBtn.addActionListener(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE borrow SET returned = TRUE, return_date = ? WHERE book_id = ? AND user_id = ? AND returned = FALSE");
                stmt.setDate(1, Date.valueOf(LocalDate.now()));
                stmt.setInt(2, bookId);
                stmt.setInt(3, userId);
                int updated = stmt.executeUpdate();

                if (updated > 0) {
                    PreparedStatement updateBook = conn.prepareStatement("UPDATE books SET available = TRUE WHERE book_id = ?");
                    updateBook.setInt(1, bookId);
                    updateBook.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Book returned!");
                } else {
                    JOptionPane.showMessageDialog(this, "No active borrow record found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
