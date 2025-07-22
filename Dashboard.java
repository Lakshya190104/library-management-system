import javax.swing.*;

public class Dashboard extends JFrame {
    private int userId;

    public Dashboard(int userId) {
        this.userId = userId;

        setTitle("Library Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton booksBtn = new JButton("Manage Books");
        booksBtn.setBounds(100, 50, 200, 30);
        add(booksBtn);

        JButton borrowBtn = new JButton("Borrow/Return");
        borrowBtn.setBounds(100, 100, 200, 30);
        add(borrowBtn);

        booksBtn.addActionListener(e -> new BookManager().setVisible(true));
        borrowBtn.addActionListener(e -> new BorrowManager(userId).setVisible(true));
    }
}
