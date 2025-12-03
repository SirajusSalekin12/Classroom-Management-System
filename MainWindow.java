import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    private JTextField tfName;
    private JTextField tfId;
    private JLabel lblNum;
    private JLabel lblOfSerial;
    private int count = 0;
    
    ArrayList<Student> studentList; 
    DefaultTableModel dtm;
    String header[] = new String[] {"Serial","Name", "ID","Grade"}; 
    private JTable list;
    private JTextField tfMark;
    private boolean addMark = false;
    private boolean updateMark = false;
    private JTextField tfQuizNo;
    private JButton btnNext;

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        EventQueue.invokeLater(() -> {
            try {
                MainWindow frame = new MainWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void display() {
        dtm.setRowCount(0);
        for (int i = 0; i<studentList.size(); i++ ) {
            Object[] obj = {i+1,studentList.get(i).getName(),studentList.get(i).getId(),studentList.get(i).getGrade()};
            dtm.addRow(obj);
        }
    }

    public MainWindow() {
        setTitle("Dashboard - Student Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 250));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // --- Data Setup ---
        studentList = new ArrayList<>();
        dtm = new DefaultTableModel(header,0);
        list = new JTable(dtm);
        list.setRowHeight(25);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        list.getTableHeader().setBackground(new Color(230, 230, 230));
        
        // Scroll Pane for Table (CENTER)
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // --- Side Panel for Controls (EAST) ---
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(300, 0));
        sidePanel.setBackground(new Color(245, 245, 250));
        contentPane.add(sidePanel, BorderLayout.EAST);

        // 1. New Student Panel
        JPanel pnlStudent = createGroupPanel("New Student");
        pnlStudent.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        tfName = new JTextField(15);
        tfId = new JTextField(15);
        JButton btnAddStudent = createStyledButton("Add Student", new Color(40, 167, 69));

        addToGrid(pnlStudent, new JLabel("Name:"), 0, 0, gbc);
        addToGrid(pnlStudent, tfName, 1, 0, gbc);
        addToGrid(pnlStudent, new JLabel("ID:"), 0, 1, gbc);
        addToGrid(pnlStudent, tfId, 1, 1, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        pnlStudent.add(btnAddStudent, gbc);

        // 2. Grading Panel
        JPanel pnlGrading = createGroupPanel("Grading & Marks");
        pnlGrading.setLayout(new GridBagLayout());
        
        tfMark = new JTextField(10);
        tfQuizNo = new JTextField(10);
        lblNum = new JLabel("-");
        lblOfSerial = new JLabel("Status: Idle");
        lblOfSerial.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        JButton btnAddTestMark = createStyledButton("Start Marking", new Color(0, 123, 255));
        JButton btnUpdateTestMark = createStyledButton("Update Mark", new Color(23, 162, 184));
        btnNext = createStyledButton("Next Student >", Color.GRAY);
        btnNext.setEnabled(false);

        // Layout for Grading
        gbc.gridwidth = 1;
        addToGrid(pnlGrading, new JLabel("Score:"), 0, 0, gbc);
        addToGrid(pnlGrading, tfMark, 1, 0, gbc);
        addToGrid(pnlGrading, new JLabel("Quiz #:"), 0, 1, gbc);
        addToGrid(pnlGrading, tfQuizNo, 1, 1, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        pnlGrading.add(lblOfSerial, gbc);
        
        JPanel statusPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPnl.setOpaque(false);
        statusPnl.add(new JLabel("Student #:"));
        statusPnl.add(lblNum);
        gbc.gridy = 3;
        pnlGrading.add(statusPnl, gbc);

        gbc.gridy = 4; pnlGrading.add(btnAddTestMark, gbc);
        gbc.gridy = 5; pnlGrading.add(btnUpdateTestMark, gbc);
        gbc.gridy = 6; pnlGrading.add(btnNext, gbc);

        // 3. File Operations
        JPanel pnlFile = createGroupPanel("File Actions");
        JButton btnSave = createStyledButton("Save Data", new Color(50, 50, 50));
        pnlFile.add(btnSave);

        // Add groups to Side Panel
        sidePanel.add(pnlStudent);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(pnlGrading);
        sidePanel.add(Box.createVerticalGlue()); // Push file button to bottom
        sidePanel.add(pnlFile);

        // --- Logic Implementation (Preserving your original logic) ---

        btnAddStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tfName.getText().isEmpty() || tfId.getText().isEmpty()) return;
                Student student = new Student(tfName.getText(),tfId.getText());
                studentList.add(student);
                display();
                tfName.setText("");
                tfId.setText("");
            }
        });

        btnAddTestMark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblOfSerial.setText("Adding Marks sequentially...");
                btnNext.setEnabled(true);
                btnNext.setBackground(new Color(255, 193, 7)); // Yellow/Orange
                btnNext.setForeground(Color.BLACK);
                lblNum.setText("1");
                addMark = true;
                updateMark = false;
            }
        });

        btnUpdateTestMark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblOfSerial.setText("Updating Marks sequentially...");
                btnNext.setEnabled(true);
                btnNext.setBackground(new Color(255, 193, 7));
                btnNext.setForeground(Color.BLACK);
                lblNum.setText("1");
                updateMark = true;
                addMark = false;
            }
        });

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(count<studentList.size()) {
                    if(count<studentList.size()-1) {
                        lblNum.setText(String.valueOf(count+2));
                    } else {
                        lblNum.setText("-");
                        lblOfSerial.setText("Finished. Click Next to reset.");
                    }

                    try {
                        if(addMark) {
                            String m = tfMark.getText();
                            if (!m.equals("")) {
                                studentList.get(count).mark.add(Integer.parseInt(tfMark.getText()));
                                studentList.get(count).setGrade();
                                count++;
                                display();
                            } else {
                                count++; // Skip if empty
                            }
                        } else if (updateMark) {
                            String mark = tfMark.getText();
                            String quiz = tfQuizNo.getText();
                            if(!quiz.equals("") && !mark.equals("")) {
                                int qIndex = Integer.parseInt(quiz)-1;
                                if(qIndex >= 0 && qIndex < studentList.get(count).mark.size()) {
                                    studentList.get(count).mark.set(qIndex, Integer.parseInt(mark));
                                    studentList.get(count).setGrade();
                                }
                                count++;
                                display();
                            } else {
                                count++;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers");
                    }
                }
                else {
                    btnNext.setEnabled(false);
                    btnNext.setBackground(Color.GRAY);
                    btnNext.setForeground(Color.WHITE);
                    lblOfSerial.setText("Status: Idle");
                    count = 0;
                    addMark = false;
                    updateMark = false;
                }
                
                tfMark.setText("");
                tfQuizNo.setText("");
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filePath = "data.txt";
                File file = new File(filePath);
                try {
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    // Add header
                    for(int j=0; j<list.getColumnCount(); j++) {
                        bw.write(list.getColumnName(j) + "\t");
                    }
                    bw.newLine();

                    for(int i= 0; i<list.getRowCount(); i++ ) {
                        for (int j = 0; j < list.getColumnCount(); j++) {
                            bw.write(list.getValueAt(i, j).toString()+ "\t");
                        }
                        bw.newLine();
                    }
                    bw.close();
                    fw.close();
                    JOptionPane.showMessageDialog(null, "Data Saved to data.txt");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    // --- Helper Methods for UI ---

    private void addToGrid(JPanel p, Component c, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        p.add(c, gbc);
    }

    private JPanel createGroupPanel(String title) {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), title, 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
}