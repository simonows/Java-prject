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
        JMenu condition=new JMenu ("Файл");
        //condition.setFont(Style.text_style1);
        add(condition);
        JMenuItem saving=new  JMenuItem("сохранить");
        JMenuItem reading=new  JMenuItem("загрузить");
        condition.add(saving);
        condition.add(reading);
        JMenu time=new JMenu ("время");
        JMenuItem time_starting=new  JMenuItem("показать время");
        JMenuItem time_ending=new  JMenuItem("скрыть время");
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
