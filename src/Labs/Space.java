package Labs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Space extends JPanel{
	
	public Space(){
		super();
		setPreferredSize(new Dimension(Style.space_width, Style.space_height));
		setBackground(Style.space_background);
		setOpaque(true);
        setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g){
		synchronized(Singleton.getList()){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Style.space_width, Style.space_height);
			for(Bee i: Singleton.getList()){
				//synchronized(i){
					g.drawImage(i.getImage(), i.pos_x, i.pos_y, null);
				//}
			}
		}
	}
}
