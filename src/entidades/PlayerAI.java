package entidades;

import java.awt.Color;
import java.awt.Graphics;

import pongGame.Game;

public class PlayerAI {

	public boolean up, down;
	public double x, y;
	private final double MIN_TOP = 0;
	private final double MAX_BOT= 120;
	public int width, height;
	public static int score = 0;
	
	public PlayerAI(double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 05;
		this.height = 40;
	}
	
	public void tick() {
		y += (Game.ball.y - (y + 20))  * 0.04;
		if(y <= MIN_TOP+2.5) {
			y = MIN_TOP+2.5;
		}
		if(y >= MAX_BOT-40.5) {
			y = MAX_BOT-40.5;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int)x, (int)y, width, height);
	}
	
}
