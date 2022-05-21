package ClientView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static ClientView.ClientSelectWindow.*;

public class LungNoduleView extends JFrame implements ActionListener {
    Connection conn;
    ClientSelectWindow c;
    public LungNoduleView(ClientSelectWindow clientSelectWindow) {
        c= clientSelectWindow;
        conn = clientSelectWindow.conn;


    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
