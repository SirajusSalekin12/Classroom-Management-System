import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Home {

    private JFrame frmCms;
    private JTextField tfFI;
    private JPasswordField tfP; // Changed to JPasswordField for security
    private JLabel lblPrompt;

    public static void main(String[] args) {
        // Apply Modern Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                Home window = new Home();
                window.frmCms.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Home() {
        initialize();
    }

    private void initialize() {
        frmCms = new JFrame();
        frmCms.setTitle("Classroom Management System");
        frmCms.setBounds(100, 100, 600, 450);
        frmCms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCms.setLocationRelativeTo(null); // Center on screen

        // Main background panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        frmCms.setContentPane(mainPanel);

        // Login Box Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(30, 40, 30, 40)));

        // Title
        JLabel titleLabel = new JLabel("Faculty Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(50, 50, 50));

        // Inputs
        tfFI = new JTextField(15);
        tfP = new JPasswordField(15); // Password field masks text
        
        styleField(tfFI);
        styleField(tfP);

        // Button
        JButton btnLogin = new JButton("Login");
        styleButton(btnLogin);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pass = new String(tfP.getPassword());
                if(tfFI.getText().equals("SVA") && pass.equals("1234")) {
                    frmCms.dispose();
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                } else {
                    lblPrompt.setText("Invalid Credentials");
                    lblPrompt.setForeground(Color.RED);
                }
            }
        });

        lblPrompt = new JLabel(" ");
        lblPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrompt.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Add components to login panel with spacing
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(createLabel("Faculty Initials:"));
        loginPanel.add(tfFI);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(createLabel("Password:"));
        loginPanel.add(tfP);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(btnLogin);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(lblPrompt);

        mainPanel.add(loginPanel);
    }

    // Helper methods for styling
    private void styleButton(JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(200, 40));
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(200, 30));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return lbl;
    }
}