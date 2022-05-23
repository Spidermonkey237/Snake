package processpack;


import java.awt.Point;
import java.util.*;
import java.util.stream.IntStream;


public class AL implements  Dynamics  {

//---------------------------- Variables --------------------------------------------------------

	
	
	//number of blocs on the Width and on the height: widthB= width/blockSize ; heightB= width/blockSize
     private int widthB=30, heightB=30 ;
	
	//Long of snake-blocks 
	 private final int blockSize=20;  
	 
	 private List<Point> snakeXY = new ArrayList<>( Arrays.asList( new Point(10,5),new Point(11,5),
			 													   new Point(12,5),new Point(13,5) ) );
	 
	 private int[] fruitXY=new int[]{randomCoordinateWithExclusion(snakeXY).x,
			 						 randomCoordinateWithExclusion(snakeXY).y};
	
	//direction vector
	 private int[] dirX= new int[]{0,0,1,-1};   //down,up,right,left
	 private int[] dirY= new int[]{1,-1,0,0};  //down,up,right,left
	 
	//indexes for dirX[] and dirY[]
	 private int dir=3;
	 
	//choose the screen Menu
	 private int gameOver=2;              
	 
	 private int score;
	 private boolean eatingSound=false;
	 private boolean collisionSound=false;
	 	 
	 
//------------------------------------- Interface implementations -----------------------------------------------
	
	 @Override
	 public int getBlockSize() {
		 return this.blockSize;
	 }
	 
	 @Override
	 public int getSnakeX(int index) {
		 assert(index < snakeXY.size()):"Out of Bound in snakeXY";
		 return snakeXY.get(index).x *blockSize;
	 }
	 
	 @Override
	 public int getSnakeY(int index) {
		 assert(index < snakeXY.size()):"Out of Bound in snakeXY";
		 return snakeXY.get(index).y* blockSize;
     }
     
    @Override 
	public int getWidthB(){
		return widthB;
	}
	
    @Override 
	public int getHeigthB() {
		return heightB;
	}
    
    @Override 
	public int getFruitX() {
		return fruitXY[0]*blockSize;
	}
	
	@Override
	public int getFruitY() {
		return fruitXY[1]*blockSize;
	}
		
	@Override
	public void addSnakeXY(int x, int y) {
		snakeXY.add(new Point(x,y));
	}
	
	@Override
	public void removeSnakeXY(int index) {
		assert(index < snakeXY.size()):"Out of Bound in snakeXY";
		this.snakeXY.remove(index);
	}
	
	@Override
	public void setWidthB(int widthB ) {
		this.widthB= widthB;
	}
	
	@Override
	public void setHeightB(int heightB) {
		this.heightB= heightB;
	}
	
	@Override
	public int sizeOfSnake() {
		return snakeXY.size();
	}
	 
	@Override
	public int getDirX(int index) {
		assert( index <dirX.length ):"Out of bound in dirX";
		return dirX[index];
	}
	
	@Override
	public int getDirY(int index) {
		assert( index <dirX.length ):"Out of bound in dirY";
		return dirY[index];
	}

	@Override
	public int getDir() {
		return dir;
	}

	@Override
	public void setDir(int value) {
		if(value<4 && value >=0) {
			avoidTurningBack(value);
		}
	}
	
	@Override
	public int getGameOver() {
		return gameOver;
	}

	@Override
	public void setGameOver(int gameOver) {
		this.gameOver= gameOver;
	}

	@Override
	public int getScore() {
		return score;
	}
	
	@Override
	public boolean getEatingSound() {
		  return this.eatingSound;
	}
	  
	 @Override
	 public boolean getCollisionSound() {
		  return this.collisionSound;
	 }
	 
	 @Override
		public void move(){
			int x= (snakeXY.get(0).x)+ dirX[dir];
			int y= (snakeXY.get(0).y)+ dirY[dir];
			snakeXY.add( 0, new Point(x,y));
	        growSnake();
	        CheckBorderCollision();
	        selfEat(snakeXY);
	        toString();
		 }
	 
	 @Override
		public void restart() {

			snakeXY = new ArrayList<>(Arrays.asList(new Point(10,5),new Point(11,5),
					 new Point(12,5),new Point(13,5)));
			fruitXY[0]= randomCoordinateWithExclusion(snakeXY).x;
		    fruitXY[1]= randomCoordinateWithExclusion(snakeXY).y; 
			gameOver=0;     
			dir=3;
			score=0;
		}
	 
//--------------------------------------------------------------------------------------------------
	 
   //To erase the tail
	private void eraseTail() {
	snakeXY.remove(snakeXY.size()-1);
}

   //snake must grow when he eat a fruit
	private void growSnake() {
		if( (getSnakeX(0)==getFruitX()) && (getSnakeY(0)==getFruitY()) ){
			eatingSound=true;
			fruitXY[0]= randomCoordinateWithExclusion(snakeXY).x;
		    fruitXY[1]= randomCoordinateWithExclusion(snakeXY).y; 
		    score++;
					
			}
		else {
			eraseTail();
			eatingSound=false;
		}
	}
	
   //check collision with the border
	private void CheckBorderCollision() {
		
		if( ( snakeXY.get(0).x<0)||( snakeXY.get(0).x>29)||( snakeXY.get(0).y<2)||( snakeXY.get(0).y>29) ) {
			gameOver=1;
			collisionSound=true;
		}	
		else {
			collisionSound=false;
		}
	}

	//Avoid 180 degree turning-back
	@Override
	public void avoidTurningBack(int newDir) {
		
		 if( !( (dir==0 && newDir==1)||(dir==1 && newDir==0)|| (dir==2 && newDir==3)||(dir==3 && newDir==2)|| (dir==newDir) ) ) {
			dir = newDir;
		}
	}

	//don't eat  yourself	
	public void selfEat(List<Point> snakeXY) {
		
		IntStream.range(1, snakeXY.size()).forEach(index ->{
			if( (snakeXY.get(0).x==snakeXY.get(index).x) && (snakeXY.get(0).y==snakeXY.get(index).y) ) {
				gameOver =1;
				collisionSound=true;
			}
			});
		
	}

  // to generate random Points for the fruit ,that are different from the snake coordinate
  @Override
  public Point randomCoordinateWithExclusion(List<Point> arrayList) {
	  
	   Random random= new Random();
	  List<Point> pool = new ArrayList<>();
	  //all possible positions
	  for(int i=0;i<30;i++) {
		  for(int j=2;j<30;j++) {
				  pool.add(new Point(i,j));
		  }
	  }

//	  for(Point element: arrayList) {
//		  Iterator<Point> i = pool.iterator();
//		  while (i.hasNext()) {
//			  Point r= i.next();
//		         if ( element.equals(r) ) {
//		            i.remove();
//		            break;
//		         }
//		      }
//	  }
      
	  pool.removeAll(arrayList);	  
	  if(pool.size()==0) {
		  gameOver=1;
		  return(new Point(0,0));
		  
	  }
	 
	  else {
		  return pool.get( random.nextInt(pool.size()) );
	  }
	  
  }

  @Override
  public String toString(){
	  
	  System.out.print("Fruit coordinate: ");
	  System.out.println("["+fruitXY[0]+","+fruitXY[1]+"]");
	  System.out.println("Snake coordinates : ");
	  for(Point element:snakeXY) {
		  System.out.println(element.toString());
	  }
	  System.out.print(" Score : ");
	  System.out.println(score);
	  System.out.println("_____________________");
	  return "";
  }
  
}
