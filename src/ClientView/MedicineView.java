package ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static ClientView.ClientSelectWindow.*;

public class MedicineView extends JFrame implements ActionListener{
    Connection conn;
    ClientSelectWindow c;
    public MedicineView(ClientSelectWindow clientSelectWindow) {
        c = clientSelectWindow;
        conn = clientSelectWindow.conn;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
