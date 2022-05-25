package ClientView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static ClientView.ClientSelectWindow.*;

public class LungNoduleView extends JFrame implements ActionListener {
    Connection conn;
    ClientSelectWindow c;
    public static final String[] patientCol = {"病人编号","病例数","负责医生"};
    public static final String[] medicineCol = {"药物编号","名称","总量","生产公司"};
    public static final String[] noduleCol = {"病例号","位置","特征","良恶性"};
    JLabel titleLabel;
    JPanel viewPanel;
    JPanel operatePanel;
    JScrollPane up;
    JScrollPane low;
    JTable patientTable;
    JTable medicineTable;
    JPanel secondPanel;
    DefaultTableModel patientModel;
    DefaultTableModel medicineModel;
    public LungNoduleView(ClientSelectWindow clientSelectWindow) {
        c= clientSelectWindow;
        conn = clientSelectWindow.conn;
        Init();

    }
    JTextField patientId;
    JButton delPatient;
    JTextField patientIdUpdate;
    JTextField noduleUpdate;
    JTextField medicineName;
    JTextField medicineNum;
    JPanel thirdPanel;
    JButton updateNodule;
    JButton addMedicine;
    JPanel zeroPanel;
    JButton checkNodule;
    JTextField inputId;
    private void Init() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500,650);
        getContentPane().setLayout(new BorderLayout());
        titleLabel = new JLabel("医院查询系统",SwingConstants.CENTER);
        titleLabel.setSize(2000,50);
        titleLabel.setVisible(true);
        getContentPane().add(titleLabel,BorderLayout.NORTH);
        viewPanel= new JPanel();
        viewPanel.setVisible(true);
        viewPanel.setLayout(new GridLayout(2,1));
        Object[][] patientData = getPatientData();
        patientModel = new DefaultTableModel(patientData,patientCol);
        patientTable = new JTable(patientModel);
        up = new JScrollPane(patientTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        up.setVisible(true);
        viewPanel.add(up);
        Object[][] medicineData = getMedicineData();
        medicineModel = new DefaultTableModel(medicineData,medicineCol);
        medicineTable = new JTable(medicineModel);
        low = new JScrollPane(medicineTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        low.setVisible(true);
        viewPanel.add(low);
        getContentPane().add(viewPanel,BorderLayout.CENTER);

        operatePanel = new JPanel();
        operatePanel.setSize(800,650);
        getContentPane().add(operatePanel,BorderLayout.EAST);
        operatePanel.setLayout(new GridLayout(6,1));
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(1,2));
        patientId = new JTextField();
        delPatient = new JButton("删除病人记录");
        delPatient.addActionListener(this);
        firstPanel.add(patientId);
        firstPanel.add(delPatient);
        operatePanel.add(firstPanel);
        zeroPanel = new JPanel();
        zeroPanel.setLayout(new GridLayout(1,2));
        inputId = new JTextField();
        inputId.setVisible(true);
        zeroPanel.add(inputId);
        checkNodule = new JButton("输入病人编号，查看其病例:");
        checkNodule.setVisible(true);
        checkNodule.addActionListener(this);
        zeroPanel.add(checkNodule);
        operatePanel.add(zeroPanel);
        secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(1,4));
        patientIdUpdate = new JTextField();
        patientIdUpdate.setVisible(true);
        secondPanel.add(patientIdUpdate);
        secondPanel.add(new JLabel("左侧输入病人编号，右侧输入病例号"));
        noduleUpdate = new JTextField();
        noduleUpdate.setVisible(true);
        secondPanel.add(noduleUpdate);
        updateNodule = new JButton("更新信息");
        updateNodule.setVisible(true);
        updateNodule.addActionListener(this);
        secondPanel.add(updateNodule);
        operatePanel.add(secondPanel);
        thirdPanel =new JPanel();
        thirdPanel.setVisible(true);
        thirdPanel.setLayout(new GridLayout(1,4));
        medicineName = new JTextField();
        medicineName.setVisible(true);
        thirdPanel.add(medicineName);
        thirdPanel.add(new JLabel("左侧输入药物名称，右侧输入添加数量"));
        medicineNum = new JTextField();
        medicineNum.setVisible(true);
        thirdPanel.add(medicineNum);
        addMedicine = new JButton("添加或处理药品");
        addMedicine.addActionListener(this);
        addMedicine.setVisible(true);
        thirdPanel.add(addMedicine);
        operatePanel.add(thirdPanel);
    }

    private Object[][] getMedicineData() {
        Object[][] data = new Object[100][4];
        String sql = SELECT + "*" +  FROM + "药物" ;
        System.out.println(sql);
        try {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            for (int i = 0; res.next(); i++) {
                data[i][0] = res.getString(medicineCol[0]);
                data[i][1] = res.getString(medicineCol[1]);
                data[i][2] = res.getString(medicineCol[2]);
                data[i][3] = res.getString(medicineCol[3]);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[][] getPatientData() {
        Object[][] data = new Object[100][3];
        String sql = SELECT + "*" +  FROM +"病人plus" ;
        System.out.println(sql);
        try {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            for (int i = 0; res.next(); i++) {
                data[i][0] = res.getString(patientCol[0]);
                data[i][1] = res.getString(patientCol[1]);
                data[i][2] = res.getString(patientCol[2]);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton s = (JButton) e.getSource();
        if (s.getText().equals("删除病人记录")){
            try {
                String sql = "{CALL del(?)}";
                CallableStatement clm = conn.prepareCall(sql);
                Integer num = Integer.parseInt(patientId.getText());
                clm.setInt(1,num);
                int res = clm.executeUpdate();
                if (res > 0){
                    System.out.println("调用成功");
                    JOptionPane.showConfirmDialog(this,"修改成功!");
                    Object[][] data = getPatientData();
                    DefaultTableModel newModel = new DefaultTableModel(data,patientCol);
                    patientTable.setModel(newModel);
                    patientTable.repaint();
                    up.repaint();
                    viewPanel.repaint();
                }else {
                    JOptionPane.showConfirmDialog(this,"修改失败!");
                    System.out.println("调用失败");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(this,"修改失败!");
                throw new RuntimeException(ex);
            }

        } else if (s.getText().equals("更新信息")) {
            String sql = INSERT + "病人" +VALUES +"("+patientIdUpdate.getText()+","+noduleUpdate.getText()+")" ;
            try {
                Statement statement = conn.createStatement();
                System.out.println(sql);
                int result = statement.executeUpdate(sql);
                if (result > 0){
                    patientTable.removeAll();
                    patientModel = new DefaultTableModel(getPatientData(),patientCol);
                    patientTable.setModel(patientModel);
                    patientTable.repaint();
                    up.repaint();
                    viewPanel.repaint();
                    JOptionPane.showConfirmDialog(this,"插入成功");

                }else {
                    JOptionPane.showConfirmDialog(this,"插入失败");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(this,"插入失败");
                throw new RuntimeException(ex);
            }
        } else if (s.getText().equals("添加或处理药品")) {
            try {
                String sql = "{CALL callables(?,?)}";
                CallableStatement clm = conn.prepareCall(sql);
                clm.setString(1,medicineName.getText());
                Integer num = Integer.parseInt(medicineNum.getText());
                clm.setInt(2,num);
                int res = clm.executeUpdate();
                if (res > 0){
                    System.out.println("调用成功");
                    JOptionPane.showConfirmDialog(this,"修改成功!");
                    Object[][] data = getMedicineData();
                    DefaultTableModel newModel = new DefaultTableModel(data,medicineCol);
                    medicineTable.setModel(newModel);
                    medicineTable.repaint();
                    low.repaint();
                    viewPanel.repaint();
                }else {
                    JOptionPane.showConfirmDialog(this,"修改失败!");
                    System.out.println("调用失败");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(this,"修改失败!");
                throw new RuntimeException(ex);
            }
        }else if (s.getText().equals(checkNodule.getText())){
            String sql = SELECT + "b."+noduleCol[0]+","+noduleCol[1]+","+noduleCol[2]+","+noduleCol[3] + FROM +"肺结节"+" a"+JOIN +"病人"+" b" +ON+"a.病例号=b.病例号" +WHERE + "病人编号="+ inputId.getText();
            System.out.println(sql);
            try {
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery(sql);
                Object[][] data = new String[50][4];
                for (int i = 0; res.next(); i++) {
                    data[i][0] = res.getString(noduleCol[0]);
                    data[i][1] = res.getString(noduleCol[1]);
                    data[i][2] = res.getString(noduleCol[2]);
                    data[i][3] = res.getString(noduleCol[3]);
                }
                patientTable.removeAll();
                DefaultTableModel newModel = new DefaultTableModel(data,noduleCol);
                patientTable.setModel(newModel);
                patientTable.repaint();
                up.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
