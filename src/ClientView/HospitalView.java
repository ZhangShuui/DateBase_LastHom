package ClientView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ClientView.ClientSelectWindow.*;

public class HospitalView extends JFrame implements ActionListener {
    public static final String[] hosCol = {"编号","名称","等级","地址"};
    public static final String[] docCol = {"医生编号","姓名","职称"};

    public static final String[] docColPlus = {"医生编号","姓名","职称","医院"};
    Connection conn;
    ClientSelectWindow c;
    JLabel titleLabel;
    JPanel viewPanel;
    JPanel operatePanel;
    JScrollPane up;
    JScrollPane low;
    public HospitalView(ClientSelectWindow clientSelectWindow){
        conn = clientSelectWindow.conn;
        c= clientSelectWindow;
        extracted();
    }
    JTable hospitalTable;
    JTable docTable;
    JTextField[] inputFields;//记录查询信息
    JButton[] searchButtons;
    DefaultTableModel hosModel;
    DefaultTableModel docModel;
    private void extracted() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1500,650);
        getContentPane().setLayout(new BorderLayout());
        titleLabel = new JLabel("医院查询系统",SwingConstants.CENTER);
        titleLabel.setSize(2000,50);
        titleLabel.setVisible(true);
        getContentPane().add(titleLabel,BorderLayout.NORTH);
        viewPanel= new JPanel();
        viewPanel.setVisible(true);
        viewPanel.setLayout(new GridLayout(2,1));
        Object[][] hosData = getHosData();
        hosModel =new DefaultTableModel(hosData,hosCol);
        hospitalTable = new JTable(hosModel);
        hospitalTable.setRowHeight(40);
        hospitalTable.setPreferredScrollableViewportSize(new Dimension(1000,1000));
        up = new JScrollPane(hospitalTable,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        viewPanel.add(up);
        Object[][] docData = getDocData();
        docModel = new DefaultTableModel(docData,docCol);
        docTable =new JTable(docModel);
        docTable.setRowHeight(40);
        docTable.setPreferredScrollableViewportSize(new Dimension(800,600));
        low = new JScrollPane(docTable,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        viewPanel.add(low);
        getContentPane().add(viewPanel,BorderLayout.CENTER);
        operatePanel = new JPanel();
        operatePanel.setVisible(true);
        operatePanel.setLayout(new GridLayout(6,2));
        inputFields = new JTextField[4];
        searchButtons = new JButton[4];
        String[] words = {"搜索某个等级医院","搜索相应职称医生","搜索某个医院的医生"};
        for (int i = 0; i < 3; i++) {
            inputFields[i] = new JTextField();
            inputFields[i].setVisible(true);
            searchButtons[i] = new JButton(words[i]);
            searchButtons[i].setVisible(true);
            searchButtons[i].addActionListener(this);
            operatePanel.add(searchButtons[i]);
            operatePanel.add(inputFields[i]);
            operatePanel.add(new JLabel());
            operatePanel.add(new JLabel());
        }
        getContentPane().add(operatePanel,BorderLayout.EAST);
    }

    private Object[][] getDocData() {
        Object[][] data=null;
        try {
            Statement hos = conn.createStatement();
            String sql = SELECT + "*" +FROM + "医生";
            System.out.println(sql);
            data = new String[100][4];
            ResultSet res = hos.executeQuery(sql);
            for (int i = 0; res.next(); i++) {
                data[i][0] = res.getString(docCol[0]);
                data[i][1] = res.getString(docCol[1]);
                data[i][2] = res.getString(docCol[2]);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[][] getHosData() {
        Object[][] data=null;
        try {
            Statement hos = conn.createStatement();
            String sub = "医院";
            data =new String[100][4];
            String sql = SELECT + "*" +FROM + "医院";
            System.out.println(sql);
            ResultSet res = hos.executeQuery(sql);
            System.out.println(res);
            for (int i = 0; res.next(); i++) {
                data[i][0] = res.getString(hosCol[0]);
                data[i][1] = res.getString(hosCol[1]);
                data[i][2] = res.getString(hosCol[2]);
                data[i][3] = res.getString(hosCol[3]);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String sql = null;
        if (source.getText().equals(searchButtons[0].getText())){
            sql = SELECT +"*" + FROM + "医院" +WHERE + "等级='"+inputFields[0].getText()+"'";
            System.out.println(sql);
            try {
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery(sql);
                Object[][] data = new String[20][4];
                for (int i = 0; res.next(); i++) {
                    data[i][0] = res.getString(hosCol[0]);
                    data[i][1] = res.getString(hosCol[1]);
                    data[i][2] = res.getString(hosCol[2]);
                    data[i][3] = res.getString(hosCol[3]);
                }
                hospitalTable.removeAll();
                DefaultTableModel newModel = new DefaultTableModel(data,hosCol);
                hospitalTable.setModel(newModel);
                hospitalTable.repaint();
                up.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if (source.getText().equals(searchButtons[2].getText())){
            sql = SELECT +"医生编号,姓名,职称,医院" + FROM +"医生plus" +WHERE + "医院='"+inputFields[2].getText()+"'";
            //yi wu !
            System.out.println(sql);
            try {
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery(sql);
                Object[][] data = new String[20][4];
                for (int i = 0; res.next(); i++) {
                    data[i][0] = res.getString(docColPlus[0]);
                    data[i][1] = res.getString(docColPlus[1]);
                    data[i][2] = res.getString(docColPlus[2]);
                    data[i][3] = res.getString(docColPlus[3]);
                }
                docTable.removeAll();
                DefaultTableModel newModel = new DefaultTableModel(data,docColPlus);
                docTable.setModel(newModel);
                docTable.repaint();
                low.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else if (source.getText().equals(searchButtons[1].getText())){
            sql = SELECT +"*" + FROM + "医生" +WHERE + "职称='"+inputFields[1].getText()+"'";
            System.out.println(sql);
            try {
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery(sql);
                Object[][] data = new String[20][3];
                for (int i = 0; res.next(); i++) {
                    data[i][0] = res.getString(docCol[0]);
                    data[i][1] = res.getString(docCol[1]);
                    data[i][2] = res.getString(docCol[2]);
                }
                docTable.removeAll();
                DefaultTableModel newModel = new DefaultTableModel(data,docCol);
                docTable.setModel(newModel);
                docTable.repaint();
                low.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
