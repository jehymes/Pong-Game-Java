package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import pongGame.Game;

public class Ball {

	public boolean up, down;
	public double x, y;
	private double directionX;
	private double directionY;
	private double speed = 1.2;
	private int WIDTH = 3;
	private int HEIGHT = 3;
	public boolean start;
	
	public Ball(double x, double y) {
		this.start = false;
		this.x = x;
		this.y = y;
		
		int angle = new Random().nextInt(80 - 20) + 20 + 1;
		directionX = Math.cos(Math.toRadians(angle));
		directionY = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() {
		if(y + (directionY*speed) + HEIGHT >= Game.HEIGHT) {
			directionY *= -1;
		}else if(y + (directionY*speed) < 0) {
			directionY *= -1;
		}
		
		if(x < 0) {
//			System.out.println("Ponto da Jogador");
			Player.score += 1;
			new Game();
			return;
		}else if(x > Game.WIDTH){
//			System.out.println("Ponto da AI");
			PlayerAI.score += 1;
			new Game();
			return;
		}
		
		if(start) {
			x += speed*directionX;
			y += speed*directionY;
		}
		
		Rectangle bounds = new Rectangle((int)(x+(directionX*speed)), (int)(y+(directionY*speed)), WIDTH, HEIGHT);
		
		Rectangle boundsPlayer = new Rectangle((int)Game.player.x, (int)Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsAI = new Rectangle((int)Game.playerAI.x, (int)Game.playerAI.y, Game.playerAI.width, Game.playerAI.height);
		
		if(bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(80 - 20) + 20 + 1;
			directionX = Math.cos(Math.toRadians(angle));
			directionY = Math.sin(Math.toRadians(angle));
			if(directionX > 0)
				directionX *= -1;				
		}else if(bounds.intersects(boundsAI)) {
			int angle = new Random().nextInt(80 - 20) + 20 + 1;
			directionX = Math.cos(Math.toRadians(angle));
			directionY = Math.sin(Math.toRadians(angle));
			if(directionX < 0)
				directionX *= -1;						
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
	}
	
}
