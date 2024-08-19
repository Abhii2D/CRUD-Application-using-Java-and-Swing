import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Vector;

public class hh extends JFrame {
    private static final String url = "jdbc:mysql://localhost:3306/Dank";
    private static final String username = "root";
    private static final String password = "abhijeet@2002";
    private JTable JTab;
    private JPanel panel1;
    private JScrollPane ScrolTable;
    private JLabel JUserID;
    private JTextField UID;
    private JTextField UName;
    private JButton insertButton;
    private JLabel HEad;
    private JButton Jdeletebtn;
    private JButton JUpdatebtn;
    private JButton JReadbtn;
    private JLabel MYDB;
    private JTextField JDEp;
    private JTextField Jcity;
    private JLabel citylabel;

    public void rep() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            DefaultTableModel ttt = (DefaultTableModel) JTab.getModel();

            String query = "select * from students";
            ResultSet resut = statement.executeQuery(query);
            ttt.setColumnCount(0);
            ttt.setRowCount(0);
            ttt.addColumn("ID");
            ttt.addColumn("Name");
            ttt.addColumn("Department");
            ttt.addColumn("City");
            while (resut.next()) {
                Vector row = new Vector();
                String name2 = resut.getString("Name");
                int iden = resut.getInt("id");
                String dep = resut.getString("Department");
                String city = resut.getString("City");
                System.out.println(name2);

                row.add(iden);
                row.add(name2);
                row.add(dep);
                row.add(city);

                ttt.addRow(row);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void InsertDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        insertButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent f) {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    int id = Integer.parseInt(UID.getText());
                    String Dep = JDEp.getText();
                    String name = UName.getText();
                    String city = Jcity.getText();
                    String addQuery = String.format("INSERT INTO students(id,Name,Department,City)VALUES('%o','%s','%s','%s')", id, name, Dep, city);
                    statement.executeUpdate(addQuery);
                    UID.setText("");
                    UName.setText("");
                    JDEp.setText("");
                    Jcity.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    hh() {

        setContentPane(panel1);
        setTitle("CRuD Application in Java");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        validate();
        this.rep();
        this.InsertDB();
        insertButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rep();
            }
        });

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Connection cone;
        try {
            cone = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Jdeletebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);


                    String Query = "delete from students where id = ?";
                    PreparedStatement statement = connection.prepareStatement(Query);
                    statement.setInt(1, Integer.parseInt(UID.getText()));
                    statement.executeUpdate();

                    UID.setText("");

                    rep();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        JUpdatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    PreparedStatement pst = cone.prepareStatement("update students set Name=?,Department=?,City=? where id=?");
                    pst.setString(1, UName.getText());
                    pst.setString(2, JDEp.getText());
                    pst.setString(3, Jcity.getText());
                    pst.setInt(4, Integer.parseInt(UID.getText()));
                    pst.executeUpdate();
                    UName.setText("");
                    JDEp.setText("");
                    Jcity.setText("");
                    UID.setText("");
                    rep();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        JReadbtn.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                DefaultTableModel ob = (DefaultTableModel) JTab.getModel();
                TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
                JTab.setRowSorter(obj);
                obj.setRowFilter(RowFilter.regexFilter(UName.getText()));
                obj.setRowFilter(RowFilter.regexFilter(UID.getText()));
                System.out.println("Read Success");
                UName.setText("");
                UID.setText("");
            }
        });
    }
}












