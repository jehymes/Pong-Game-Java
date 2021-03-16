package entidades;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public boolean up, down;
	public double x, y;
	private final double MIN_TOP = 0;
	private final double MAX_BOT= 120;
	public int width, height;
	public static int score = 0;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 05;
		this.height = 40;
	}
	
	public void tick() {
		if(up) {
			y--;
			if(y <= MIN_TOP+2.5) {
				y = MIN_TOP+2.5;
			}			
		}else if(down) {
			y++;
			if(y >= MAX_BOT-40.5) {
				y = MAX_BOT-40.5;
			}	
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
//		g.fillRect(240-10, 80/2, 05, 40);
		g.fillRect((int)x, (int)y, width, height);
	}
}
