/*
 * Лабораторная работа по дисциплине "технология программирования"
 * Класс MyFrame (Отвечает за создание окна приложения) 
 * Выполнил Симонов Виктор, студент группы: АВТ-509
 */

package Labs;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicMenuBarUI;


public class MyFrame extends JFrame {
	private static int width=1000;
	private static int height=600;
	private static JLabel time;
	private JLabel status,information;
	private Menu menu;
	private NetMenu net_menu;
	private Space space;
	private JPanel info;
	private JMenuBar myMenu;
	private int period_drone_buffer, period_worker_buffer;
	private DroneAI droneAI;
    private WorkerAI workerAI;
	
	public MyFrame(String name){
		super(name);
		
        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());
        createInfo();
        MyFile file = new MyFile();
        space = new Space();
        menu = new Menu();
        net_menu = new NetMenu();
        myMenu = new JMenuBar();
        myMenu.add(new MenuBar("меню"));
        myMenu.setVisible(true);
        myMenu.setBackground(Style.menubar_background);
        setLayout(new BorderLayout());
        add(myMenu,BorderLayout.NORTH);
        add(info, BorderLayout.SOUTH);
        add(space, BorderLayout.CENTER);
        add(menu, BorderLayout.EAST);
        add(net_menu, BorderLayout.WEST);
        menu.start_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	habitat.newHabitat();
	    		genStatus(true);
	    		if(!menu.show_box.isSelected()){
	    			time.setVisible(false);
	    			status.setVisible(false);
	    		}
	    		if(menu.show_info.isSelected()){
	    			time.setVisible(true);
	    		}
	    		information.setText("");
	    		space.repaint(); 
	    		menu.stop_workers.setEnabled(true);
	    		menu.stop_drones.setEnabled(true);
	    		Singleton.clear();
	    		workerAI.continuE();
	    		droneAI.continuE();
	    		
            }
        });
        menu.stop_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	stopWindow();
            }
        });
        
        menu.show_bees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	JDialog ex = new JDialog(MyFrame.this, "Живые пчелы");
                ex.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                ex.setSize(500, 200);
                ex.setLocation(360,300);
                String str= new String();
                for(Map.Entry e : (Singleton.getTreeMap()).entrySet()){
                	str+=e.getKey()+" "+ e.getValue()+"\n";
            	}
                
                TextArea text_box = new TextArea(str);
                text_box.setPreferredSize(new Dimension(400,120));
                text_box.setEditable(false);

                ex.add(text_box);
                ex.setLayout(new FlowLayout(FlowLayout.CENTER));
                JButton ok_btn = new JButton("OK");
                ex.add(ok_btn);
                ok_btn.addActionListener(new ActionListener(){
                	public void actionPerformed(ActionEvent evt) {
                		ex.setVisible(false);
                    }
                }
                );
                ex.setVisible(true);
            	
            }
        });
        
        menu.stop_workers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	if(workerAI.getStatus()){
            		workerAI.continuE();
            		menu.stop_workers.setText("Остановить рабочих");
            	}
            	else{
            		workerAI.pause();
            		menu.stop_workers.setText("Запустить рабочих");
            	}
            }
        });
        
        menu.stop_drones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	if(droneAI.getStatus()){
            		droneAI.continuE();
            		menu.stop_drones.setText("Остановить трутней");
            	}
            	else{
            		droneAI.pause();
            		menu.stop_drones.setText("Запустить трутней");
            	}
            }
        });
        
        menu.show_info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	time.setVisible(menu.show_info.isEnabled());
            }
        });
        menu.neshow_info.setSelected(true);
        menu.neshow_info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	time.setVisible(!menu.show_info.isEnabled());
            }
        });
        menu.show_box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	time.setVisible(menu.show_box.isSelected());
            	status.setVisible(menu.show_box.isSelected());
            	menu.show_box.setFocusable(false);
            	menu.neshow_info.setSelected(!menu.show_box.isSelected());
            	menu.show_info.setSelected(menu.show_box.isSelected());
            }
        });
        
        menu.period_drone.getDocument().addDocumentListener(new DocumentListener(){
        	public void insertUpdate(DocumentEvent event) { 
        		try{
        			habitat.n1=Integer.parseInt(menu.period_drone.getText());
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
        	}
            public void removeUpdate(DocumentEvent event) { }
            public void changedUpdate(DocumentEvent event) { 
            	try{
            		habitat.n1=Integer.parseInt(menu.period_drone.getText());
            	}catch(NumberFormatException e){
            		errorWindow("Данные введены некоректно");
            	};
            }
        });
        
        menu.period_worker.getDocument().addDocumentListener(new DocumentListener(){
        	public void insertUpdate(DocumentEvent event) { 
        		try{
        			habitat.n2=Integer.parseInt(menu.period_worker.getText());
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        			//period_worker.setText(""+habitat.n2);
        		};
        	}
            public void removeUpdate(DocumentEvent event) { 
            	
            }
            public void changedUpdate(DocumentEvent event) { 
            	try{
        			habitat.n2=Integer.parseInt(menu.period_worker.getText());
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
            }
        });
        
        menu.life_worker.getDocument().addDocumentListener(new DocumentListener(){
        	public void insertUpdate(DocumentEvent event) { 
        		try{
        			habitat.worker_life=Integer.parseInt(menu.life_worker.getText())*1000;
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
        	}
            public void removeUpdate(DocumentEvent event) { 
            	
            }
            public void changedUpdate(DocumentEvent event) { 
            	try{
        			habitat.worker_life=Integer.parseInt(menu.life_worker.getText())*1000;
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
            }
        });
        
        menu.life_drone.getDocument().addDocumentListener(new DocumentListener(){
        	public void insertUpdate(DocumentEvent event) { 
        		try{
        			habitat.drone_life=Integer.parseInt(menu.life_drone.getText())*1000;
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
        	}
            public void removeUpdate(DocumentEvent event) { 
            	
            }
            public void changedUpdate(DocumentEvent event) { 
            	try{
        			habitat.drone_life=Integer.parseInt(menu.life_drone.getText())*1000;
        		}catch(NumberFormatException e){
        			errorWindow("Данные введены некоректно");
        		};
            }
        });
        
        menu.probability.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                habitat.p = (double)(menu.probability.getSelectedIndex()+1)/10;
                menu.probability.setFocusable(false);
            }
        });
        
        menu.prt_drones.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	droneAI.setThreadPriority(menu.prt_drones.getSelectedIndex()+1);
                menu.prt_drones.setFocusable(false);
            }
        });
        menu.prt_workers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	workerAI.setThreadPriority(menu.prt_workers.getSelectedIndex()+1);
                menu.prt_workers.setFocusable(false);
            }
        });
        
        menu.start_console.addActionListener(new ActionListener() {
        	
        	
        	
            @Override
            public void actionPerformed(ActionEvent evt) {
            	new Console();
            	Thread th = new Thread(new Help());
            	th.start();
            }
        });
        
        setFocusable(true);
        addKeyListener(new KeyListener() {
    	    public void keyReleased(KeyEvent e) {
    	    	if(e.getKeyCode()==KeyEvent.VK_B){
    	    		habitat.newHabitat();
    	    		genStatus(true);
    	    		time.setVisible(false);
    	    		status.setVisible(false);
    	    		information.setText("");
    	    		space.repaint();
    	    	}
    	    	if(e.getKeyCode()==KeyEvent.VK_E){ 
    	    		stopWindow();
    	    	}
    	    	if(e.getKeyCode()==KeyEvent.VK_T){
    	    		time.setVisible(!time.isVisible());
            		status.setVisible(!status.isVisible());
            		menu.neshow_info.setSelected(!status.isVisible());
            		menu.show_info.setSelected(status.isVisible());
    	    	}
    	    }
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
    	});
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
            	Style.width = getSize().width;
	        	Style.height = getSize().height;
	        	Style.info_width = Style.width;
	        	Style.menu_height = Style.height;
	        	Style.space_width=Style.width-Style.menu_width-15;
	        	Style.space_height=Style.height-Style.info_height*3-5;
                space.setPreferredSize(new Dimension(Style.space_width, Style.space_height));
                repaint();
            }
        });
        
        addWindowListener(new WindowListener(){
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				MyFile.writeConfig();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
               repaint();
            }
        });
        
        setVisible(true);
        setSize(Style.width,Style.height);
        setLocation(60,10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        droneAI=new DroneAI();
        workerAI=new WorkerAI();
        droneAI.start();
        workerAI.start();
	}
	
	@Override
	public void paint(Graphics g){
		info.repaint();
        menu.repaint();
        myMenu.repaint();
        space.repaint();
        net_menu.repaint();
	}
	
	public static int getW(){
		return Style.width-200-20;
	}
	
	public static int getH(){
		return Style.height-30-25;
	}
	
	private void stopWindow(){
		JDialog dialog = new JDialog(MyFrame.this, "Завершение симуляции");
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(200, 150);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton ok_btn = new JButton("OK");
        JButton net_btn = new JButton("Отмена");
        dialog.add(new JLabel("Завершить симуляцию?"));
        dialog.add(ok_btn);
        dialog.add(net_btn);
        dialog.setLocation(360,300);
        dialog.setVisible(true);
        
        ok_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
        		dialog.setVisible(false);
        		droneAI.pause();
	    		workerAI.pause();
        		genStatus(false);
	    		status.setText("Статус: завершена");
	    		time.setVisible(true);
	    		status.setVisible(true);
	    		information.setText("Рабочие: " + habitat.num_of_workers + " Трутни: " + habitat.num_of_drones);
	    		menu.stop_workers.setEnabled(false);
	    		menu.stop_drones.setEnabled(false);
	    		
            }
        }
        );
        
        net_btn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt) {
               	dialog.setVisible(false);
            }
        }
        );
	}
	
	private void errorWindow(String message){
		JDialog ex = new JDialog(MyFrame.this, "Ошибка");
		JPanel pan = new JPanel();
		JPanel pan2 = new JPanel();
        ex.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ex.setSize(270, 160);
        ex.setLocation(360,300);
        pan.setLayout(new FlowLayout(FlowLayout.CENTER));
        pan2.setLayout(new FlowLayout(FlowLayout.CENTER));
        pan.add(new JLabel(message));
        ex.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton ok_btn = new JButton("OK");
        pan2.add(ok_btn);
        
        pan.setPreferredSize(new Dimension(270,30));
        pan2.setPreferredSize(new Dimension(270,50));
        pan.setVisible(true);
        pan2.setVisible(true);
        ex.add(pan);
        ex.add(pan2);
        ok_btn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt) {
        		ex.setVisible(false);
        		
            }
        }
        );
        ex.setVisible(true);
	}
	
	private void createInfo(){
		info=new JPanel();
		info.setPreferredSize(new Dimension(Style.info_width, Style.info_height));
		info.setLayout(new FlowLayout(FlowLayout.LEFT));
		info.setBackground(Style.info_background);
		time = new JLabel();
        status = new JLabel();
        information = new JLabel();
        info.add(time);
        info.add(status);
        info.add(information);
        status.setFont(Style.text_style1);
        status.setForeground(Style.text_color);
        status.setText("Статус: подготовка к запуску");
        time.setFont(Style.text_style1);
        time.setForeground(Style.text_color);
        time.setText("Время: 0с");
        information.setFont(Style.text_style1);
        information.setForeground(Style.text_color);
        time.setVisible(false);
        status.setVisible(false);
        info.setVisible(true);
	}
	
	private void genStatus(boolean st){
		habitat.status = st;
    	status.setText(st? "Статус: выполнение":"Статус: остановка");
    	menu.start_btn.setEnabled(!st);
    	menu.stop_btn.setEnabled(st);
    	menu.start_btn.setFocusable(false); 
	}
	
	public Graphics getSpace(){
		return space.getGraphics();
	}
	
	public void setTimeText(long current_time){
		time.setText("Время: " + current_time/1000+"c");
	}
	
}

class Help implements Runnable{
    @Override
    public void run() {
    	while(true){
            try {
                int num = Console.pipeIn.read();
                if (num==0) Console.pOut.write(habitat.num_of_workers); 
                else Console.pOut.write(habitat.num_of_drones); 
            } catch (IOException ex) {}
    	}
    }
};
