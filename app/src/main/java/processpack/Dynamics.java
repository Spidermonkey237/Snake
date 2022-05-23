package processpack;

import java.awt.Point;
import java.util.List;

public interface Dynamics {

	public int getBlockSize();
	public int getSnakeX(int index);
	public int getSnakeY(int index);
	public int getWidthB();
	public int getHeigthB();
	public int getFruitX();
	public int getFruitY();
	public int sizeOfSnake();
	public int getDir();
	public int getDirX(int index);
	public int getDirY(int index);
	public void move();
	public int getGameOver();
	public void setGameOver(int gameOver);
	public void addSnakeXY(int x,int y);
	public void removeSnakeXY(int index);
	public void setWidthB(int widthB );
	public void setHeightB(int heightB);
	public void setDir(int value);
	public void selfEat(List <Point> snakeXY);
	public void restart();
	public int getScore();
	public boolean getEatingSound();
	public boolean getCollisionSound();
	public void avoidTurningBack(int newDir);
	public Point randomCoordinateWithExclusion(List<Point> arrayList);
	
}
