package processpack;

import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;


 
public class DI extends PApplet {

	public static void main(String[] args) {
		PApplet.runSketch(new String[]{""}, new DI());
	}

	Dynamics antrieb = new AL();            
	int blockSize=antrieb.getBlockSize();
	PImage bg;
	PImage apple;
	SoundFile game;
    SoundFile eatApple;
    SoundFile collision;
    
	
	
	public void settings() {
		size(600,600);
		
	}
	
	public void setup() {
		
		bg = loadImage("Feld.png");
		apple = loadImage("apple.png");
		game = new SoundFile( this,"game.wav");
		collision = new SoundFile( this,"collision.wav");
		game.play((float) 1.0,(float) 0.1);
		game.loop();
		eatApple=new SoundFile( this,"eat.wav");
		frameRate(30);

	}

	public void draw() {
		
		if( antrieb.getGameOver()==0 ){
			game.stop();
		if(frameCount % 4 == 0) { 
				antrieb.move();
				background(bg);
				menuBar();
				drawFruit();
				drawSnake();
				
				if(antrieb.getEatingSound()) {
					eatApple.play();
				}
				if(antrieb.getCollisionSound()) {
					collision.play();
				}
				
			}
		}
		else if(antrieb.getGameOver()==1) {
			gameOver();
			reset();
		}
		else if(antrieb.getGameOver()==2) {
			startScreen();
		}

	}

	//draw the snake on the Screen
	public void drawSnake() {

		for(int i=0; i<antrieb.sizeOfSnake(); i++) {

			int x=antrieb.getSnakeX(i);
			int y=antrieb.getSnakeY(i);
			noStroke();
			fill(18, 38, 22);
			rect(x,y,20,20);
		}
	}

	public void drawFruit() {
		
		int x= antrieb.getFruitX();
		int y=antrieb.getFruitY();
		image(apple, x, y, 20, 20);
	}
    
	//controls
	public void keyPressed() {

		switch(keyCode) {
		
		case LEFT :
			antrieb.setDir(3);
			break;
			
		case RIGHT :
			antrieb.setDir(2);
			break;
			
		case UP :
			antrieb.setDir(1);
			break;
			
		case DOWN :
			antrieb.setDir(0);
			break;
		}

	}
	
	public void reset() {
		if(keyCode == ENTER) {
			antrieb.restart();
		}
		keyCode=DELETE;
	}
	
	//gameOver Screen
	public void gameOver() {
		background(32, 36, 51);
		fill(255);
		textAlign(CENTER);
		textSize(40);
		text("GAME OVER !",width/2,height/2);
		textSize(30);
		text("score:"+antrieb.getScore(),width/2,(height/2)+50);
		textSize(20);
		text( "press Enter to restart",width/2 , (height/2)+100);
	}
	
	
	public void startScreen() {
		background(32, 36, 51);
		fill(255);
		textAlign(CENTER);
		textSize(40);
		text("SNAKE",width/2,height/2);
		textAlign(CENTER);
		textSize(20);
		text( "press Enter to start the Game",width/2 , (height/2)+50);
		if(keyCode == ENTER) {
			antrieb.setGameOver(0);
		}
		keyCode=DELETE;
	}
	
	public void menuBar() {
		fill(32, 36, 51, 90);
		noStroke();
		rect(0,0,width,blockSize*2);
		image(apple, 70,12, 20, 20);
		fill(255);
		text(antrieb.getScore()+" X ",30,30);
	}
	
			
	

}
