package Labs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.*;
import server.Message;
import server.MessageType;




public class NetMenu extends JPanel {
	public JTextField period_drone, period_worker, life_worker, usernameField;
	public JComboBox<String> probability, prt_drones, prt_workers;
	public JButton start_btn,stop_btn,show_bees,connect_btn,disconnect_btn;
	public static JRadioButton show_info,neshow_info;
	public ButtonGroup gr;
	public JCheckBox show_box;
	public DefaultListModel userListModel;
	public JList userList;
	public Client cl;
	
	public NetMenu(){
		setPreferredSize(new Dimension(Style.menu_width, Style.menu_height));
		setBackground(Style.menu_background);

        show_bees= Style.newButton("œÓÍ‡Á‡Ú¸");
        
        setVisible(true);
        
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setPreferredSize(new Dimension(120, 250));
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = userList.locationToIndex(e.getPoint());
                    cl.sendMessage(new Message(MessageType.OBJECTS_SWAP, (String)userListModel.get(index), null));
                    System.out.println("–ó–∞–ø—Ä–æ—Å –Ω–∞ –æ–±—ä–µ–∫—Ç—ã");
                }
            }
        });
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 24));
        
        
        connect_btn = Style.newButton("Connect");
        connect_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userListModel.clear();
                cl=new Client(userListModel);
                cl.sendMessage(new Message(MessageType.CONNECT, usernameField.getText(), null));
                connect_btn.setEnabled(false);
                disconnect_btn.setEnabled(true);
                usernameField.setEditable(false);
            }
        });
        
        disconnect_btn = Style.newButton("Disconnect");
        disconnect_btn.setEnabled(false);
        disconnect_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.sendMessage(new Message(MessageType.DISCONNECT, usernameField.getText(), null));
                userListModel.clear();
                disconnect_btn.setEnabled(false);
                connect_btn.setEnabled(true);
                usernameField.setEditable(true);
            }
        });
        
        JButton saveAutoSQL = Style.newButton("saveDroneSQL");
        add(saveAutoSQL);
        saveAutoSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.saveDroneSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton saveMotoSQL = Style.newButton("saveWorkerSQL");
        add(saveMotoSQL);
        saveMotoSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.saveWorkerSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton saveAllSQL = Style.newButton("saveAllSQL");
        add(saveAllSQL);
        saveAllSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.saveSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton loadAutoSQL = Style.newButton("loadDroneSQL");
        add(loadAutoSQL);
        loadAutoSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.loadDroneSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton loadMotoSQL = Style.newButton("loadWorkerSQL");
        add(loadMotoSQL);
        loadMotoSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.loadWorkerSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton loadAllSQL = Style.newButton("loadAllSQL");
        add(loadAllSQL);
        loadAllSQL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    habitat.loadSQL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        //add(userList);
        //add(usernameField);
        add(userList);
        add(usernameField);
        add(connect_btn);
        add(disconnect_btn);
        add(show_bees);
	}
	
}
