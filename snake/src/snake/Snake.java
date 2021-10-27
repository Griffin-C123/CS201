package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Snake extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 1280;
	static final int SCREEN_HEIGHT = 720;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 175;
	final int snakePosX[] = new int[GAME_UNITS];
	final int snakePosY[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int score;
	int posAppleX;
	int posAppleY;
	char direction = 'L';
	boolean running = false;
	Timer timer;
	Random random;
	
	Snake(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	// Method that starts the snake moving and places the first apple
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	//instantiate method that allows for the on screen graphics to have color
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}
	
	//Draws the graphics on the screen like score, the apple, and the snakes body
	public void draw(Graphics graphics) {
		
		if(running) {
			graphics.setColor(Color.red);
			graphics.fillOval(posAppleX, posAppleY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					graphics.setColor(Color.green);
					graphics.fillRect(snakePosX[i], snakePosY[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					graphics.setColor(new Color(45,180,0));
					graphics.fillRect(snakePosX[i], snakePosY[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			graphics.setColor(Color.red);
			graphics.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: "+score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+score))/2, graphics.getFont().getSize());
		}
		else {
			gameOver(graphics);
		}
	}
	
	//Gives the coordinates to the new apple position
	public void newApple(){
		posAppleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		posAppleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	//statement the moves the snake and reads the given keyboard input
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			snakePosX[i] = snakePosX[i-1];
			snakePosY[i] = snakePosY[i-1];
		}
		
		switch(direction) {
		case 'U':
			snakePosY[0] = snakePosY[0] - UNIT_SIZE;
			break;
		case 'D':
			snakePosY[0] = snakePosY[0] + UNIT_SIZE;
			break;
		case 'L':
			snakePosX[0] = snakePosX[0] - UNIT_SIZE;
			break;
		case 'R':
			snakePosX[0] = snakePosX[0] + UNIT_SIZE;
			break;
		}
	}
	
	//Checks if the apple collided with the snake head
	public void checkApple() {
		if((snakePosX[0] == posAppleX) && (snakePosY[0] == posAppleY)) {
			bodyParts++;
			score++;
			newApple();
		}
	}
	
	//checks for all snake collisions that would result in a game over
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = bodyParts;i>0;i--) {
			if((snakePosX[0] == snakePosX[i])&& (snakePosY[0] == snakePosY[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(snakePosX[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(snakePosX[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(snakePosY[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(snakePosY[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	//creates the gameover screen
	public void gameOver(Graphics graphics) {
		//Score
		graphics.setColor(Color.red);
		graphics.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: "+score, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+score))/2, graphics.getFont().getSize());
		//Game Over text
		graphics.setColor(Color.red);
		graphics.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent event) {
			switch(event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}