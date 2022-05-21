package ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ClientSelectWindow extends JFrame implements ActionListener {
    public static final String SELECT = " SELECT ";
    public static final String AND = " AND ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";

    public static final String INSERT = " INSERT INTO ";
    public static final String VALUES = " VALUES ";
    public static final String DELETE = " DELETE ";
    public ClientEnterWindow enterView;
    public ClientSelectWindow(ClientEnterWindow clientEnterWindow){
        enterView = clientEnterWindow;
        Init();
    }
    Connection conn;
    public JButton hospitalView;//����ҽԺ��Ϣ��ͼ���������ѯ
    public JButton lungNoduleView;//�����ν����Ϣ��ͼ�������ѯ
    public JButton medicineView;//����������Ϣ
    public JPanel mainPanel;
    private void Init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(300,300);
        conn = enterView.conn;
        mainPanel= new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        mainPanel.setVisible(true);
        hospitalView = new JButton("查询医院信息");
        hospitalView.setVisible(true);
        hospitalView.setSize(100,200);
        lungNoduleView = new JButton("查询肺结节信息");
        lungNoduleView.setVisible(true);
        lungNoduleView.setSize(100,200);
        medicineView = new JButton("查询药物信息");
        medicineView.setVisible(true);
        medicineView.setSize(100,200);
        hospitalView.addActionListener(this);
        lungNoduleView.addActionListener(this);
        medicineView.addActionListener(this);
        mainPanel.add(lungNoduleView);
        mainPanel.add(hospitalView);
        mainPanel.add(medicineView);
        getContentPane().setLayout(new GridLayout(1,2));
        JLabel label = new JLabel("查询信息");
        label.setVisible(true);
        label.setSize(200,200);
        getContentPane().add(label);
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton so = (JButton) e.getSource();
        if (so.getText().equals(hospitalView.getText())){
            new HospitalView(this);
            setVisible(false);
        }else if (so.getText().equals(lungNoduleView.getText())){
            new LungNoduleView(this);
            setVisible(false);
        }else if (so.getText().equals(medicineView.getText())){
            new MedicineView(this);
            setVisible(false);
        }
    }
}
