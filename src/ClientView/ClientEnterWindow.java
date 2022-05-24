package ClientView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static ClientView.ClientSelectWindow.*;

public class ClientEnterWindow extends JFrame implements ActionListener {
    public JButton enter;
    public JButton signUp;
    public JTextField InputName;
    public JPasswordField InputPwd;
    public JLabel ShowTitle;
    public final String driver = "com.mysql.cj.jdbc.Driver";//���ݿ�����������Ӧ���ַ���
    public JPanel centerPanel;
    public JPanel bottomPanel;
    public final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";

    Connection conn;
    public ClientEnterWindow(){
        conn = null;
        try {
            Class.forName(driver);
        } catch (java.lang.ClassNotFoundException ee) {
            System.out.println("加载驱动失败");
            return;
        }
        try {
            conn = DriverManager.getConnection(URL, "zsr", "123456");
            System.out.println("连接成功");
        } catch (Exception ee) {
            System.out.println("连接失败");
        }
        MyInit();
    }

    private void MyInit() {
        setVisible(true);
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2,2));
        centerPanel.setSize(200,100);
        JLabel nameLabel=new JLabel("请输入账号：");
        JLabel pwdLabel = new JLabel("请输入密码：");
        nameLabel.setVisible(true);
        pwdLabel.setVisible(true);
        nameLabel.setSize(50,50);
        pwdLabel.setSize(50,50);
        InputName = new JTextField();
        InputName.setVisible(true);
        InputName.setSize(150,50);
        InputPwd = new JPasswordField();
        InputPwd.setVisible(true);
        InputPwd.setSize(150,50);
        centerPanel.add(nameLabel);
        centerPanel.add(InputName);
        centerPanel.add(pwdLabel);
        centerPanel.add(InputPwd);
        centerPanel.setVisible(true);

        enter = new JButton("登录");
        enter.setVisible(true);
        enter.addActionListener(this);
        signUp = new JButton("注册");
        signUp.setVisible(true);
        signUp.addActionListener(this);
        bottomPanel =new JPanel();
        bottomPanel.setLayout(new GridLayout(1,2));
        bottomPanel.setVisible(true);
        bottomPanel.add(enter);
        bottomPanel.add(signUp);
        ShowTitle = new JLabel("欢迎进入肺结节查询系统",SwingConstants.CENTER);
        ShowTitle.setVisible(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(ShowTitle,BorderLayout.NORTH);
        getContentPane().add(centerPanel,BorderLayout.CENTER);
        getContentPane().add(bottomPanel,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,300);
        setLocation(200,200);
    }

    public static void main(String[] args) {
        ClientEnterWindow mainC= new ClientEnterWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        if (source.getText().equals("登录") ){
            String logString = SELECT + "username" + "," +
                    "pwd" + FROM + "user" +
                    WHERE + "username= " +
                    "'" + InputName.getText() + "'" +
                    AND + "pwd= " + "'" + InputPwd.getText() +"'";
            System.out.println(logString);
            try {
                Statement Log= conn.createStatement();
//                Log.execute(logString);
                ResultSet res = Log.executeQuery(logString);
                boolean entered = false;
                while (res.next()){
                    entered = true;
                }
                if (entered){
                    System.out.println();
                    System.out.println("登陆成功");
                    new ClientSelectWindow(this);
                    setVisible(false);
                }else {
                    System.out.println("登录失败");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if (source.getText().equals("注册")){
            String signString = INSERT + "user(username,pwd)" +VALUES
            + "('" + InputName.getText()+"','"+InputPwd.getText()+"')";
            System.out.println(signString);
            try {
                Statement statement = conn.createStatement();
                int res = statement.executeUpdate(signString);
                System.out.println(res);
                if (res == 1){
                    System.out.println("Insert success!");
                    
                }else  {
                    System.out.println("false");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
