package pongGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import entidades.Ball;
import entidades.Player;
import entidades.PlayerAI;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, KeyListener{
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	public final static int WIDTH = 240;
	public final static int HEIGHT = 120;
	private final int SCALE = 4;
	
	private BufferedImage layer;
	
	public static Player player;
	public static PlayerAI playerAI;
	public static Ball ball;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);
		player = new Player(230, 40);
		playerAI = new PlayerAI(05, 40);
		ball = new Ball(WIDTH/2, HEIGHT/2);
		layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Pong Game - 1.0");
		frame.add(this);
		frame.setResizable(false); //false
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double AMOUNT_OF_TICKS = 60.0;
		final double NS = 1000000000 / AMOUNT_OF_TICKS;
		double delta = 0;		
		
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / NS;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		
		stop();
	}
	
	public void tick() {
		player.tick();
		playerAI.tick();
		ball.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		//Cor da Janela
		Graphics gameFrame = layer.getGraphics(); 
		gameFrame.setColor(new Color(60,60,60));
		gameFrame.fillRect(0, 0, WIDTH, HEIGHT);
		
		/**
		 * Renderização do Game
		 */
		getLine(02, 05, 20);
		getScore(String.valueOf(Player.score), (WIDTH/2)+12, 10);
		getScore(String.valueOf(PlayerAI.score), (WIDTH/2)-15, 10);
		player.render(gameFrame);
		playerAI.render(gameFrame);
		ball.render(gameFrame);
		
		/*******************************************/
		
		gameFrame.dispose();
		gameFrame = bs.getDrawGraphics();
		gameFrame.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}

	public void getScore(String text, int x, int y) {
		Graphics g = layer.getGraphics();
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
	public void getLine(int largura, int altura, int quantidade) {
		Graphics g = layer.getGraphics();
		int espacos = 05;
		for(int i = 0; i < quantidade; i++) {
			g.setColor(Color.white);
			g.fillRect(WIDTH/2, espacos, largura, altura);
			espacos += 12;
		}
	}
	
	public static void main(String[] args) {
		Game pong = new Game();
		pong.initFrame();
		pong.start();
		
		new Thread(pong).start();
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(ball.start) {
				ball.start = false;
			}else {
				ball.start = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			new Game();
			return;
		}
	}

	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
	}
}
