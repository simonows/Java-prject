package Labs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;

public class MenuBar extends JMenu {     
    
	public MenuBar(String g)
    {
        super(g);
        setForeground(Style.info_background);
        setFont(Style.text_style1);
        JMenu condition=new JMenu ("����");
        //condition.setFont(Style.text_style1);
        add(condition);
        JMenuItem saving=new  JMenuItem("���������");
        JMenuItem reading=new  JMenuItem("���������");
        condition.add(saving);
        condition.add(reading);
        JMenu time=new JMenu ("�����");
        JMenuItem time_starting=new  JMenuItem("�������� �����");
        JMenuItem time_ending=new  JMenuItem("������ �����");
        time.add(time_starting);
        time.add(time_ending);
        add(time);
      
	    saving.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){   
	        	MyFile.savePopulation();
	        }
	    });
       
        reading.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyFile.loadPopulation();
			}
        });
                  
        time_ending.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){   
        	
        	}
        });
       
        time_starting.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){   
        	
        	}
        }); 
    }
}
