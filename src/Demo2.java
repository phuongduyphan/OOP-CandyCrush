



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Demo2 extends Application {
	//var board setup
	private static final int WIDTH= 80;
	private static final int HEIGHT= 80;
	private static final int numberOfColumn = 7;
	private static final int numberOfRow = 7;
	private static final int Screen_WIDTH = 640;
	private static final int Screen_HEIGHT = 800;
	private static final int gameInfoHeight = 80;
	
	//var cell
	private boolean isPressed= false;
	private boolean isCombo= false;
	private boolean needShuffle= false;
	private int columnAnte,rowAnte;
	private Integer imageCandy[] = new Integer[] {1,2,3,4,5,6,7};

	public Image getCandyImage(int i) {
		Image image=null; 
		switch (i) {
         
		 case 1: image = new Image("File:images/Quang.JPG");// get image
             break;     
         case 2: image = new Image("File:images/Chau.JPG");
             break;        
         case 3: image = new Image("File:images/Duy.PNG");
        	 break;
         case 4: image = new Image("File:images/pink.png");
             break;
         case 5: image = new Image("File:images/star.png");
        	 break;
         case 6: image = new Image("File:images/yellowcandy.png");
        	 break;
         case 7: image = new Image("File:images/red.jpg");
        	 break;	 
         default:
             System.out.println("Can't load Image");
             break;
		 }
	    return image;
	}
	
	// creating the board
	Pane root = new Pane();
	Rectangle rectArray[][]= new Rectangle[numberOfColumn][numberOfRow];
	
	@Override
	public void start (Stage board) {
		
		
	// init the board
		for (int column = 0, x=1; column < numberOfColumn; column++,x+=WIDTH+1) {
			for (int row =0,y= gameInfoHeight; row < numberOfRow; row++, y += HEIGHT+1) { 
					rectArray[column][row]= createCell(x,y,column,row);  
				 	 root.getChildren().add(rectArray[column][row]);

            }
		}
		checkBoard();// check to make sure there are no combo at the start
		setGameInfo();
    //init Scene  
		Scene scene = new Scene(root, Screen_WIDTH, Screen_HEIGHT);
	    board.setTitle("Candy Crush");
	    board.setScene(scene);
	    board.show();
	    board.setResizable(true);

    }
	
	public void setGameInfo() {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(2);// distance bt columns
        grid.setVgap(2);// rows
        
        Text playerTitle = new Text("Spielername: ");
        playerTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 14));
        Text levelTitle = new Text("Nivel: ");
        levelTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 12));
        Text scoreTitle = new Text("Diem so: ");
        scoreTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 12));
        Text timeTitle = new Text("Shijian: ");
        timeTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 14));
        Label playername = new Label("Huy");
        Label level = new Label("1");
        Label score = new Label("10000 ");
        Image image = new Image("File:images/Candy Crush.jpg");// get image
        ImageView imageView = new ImageView(image);// transfer to node
        imageView.setFitHeight(60);
        imageView.setFitWidth(120);
       
        
        grid.add(imageView,4,0,1,4); //must have ImageView como a node
        grid.add(playerTitle,0,0,2,1); 
        grid.add(playername, 2, 0);
        grid.add(levelTitle, 0, 1);
        grid.add(level,1,1);
        grid.add(scoreTitle, 0, 2);
        grid.add(score,1,2);
        grid.add(timeTitle, 1, 3);
        grid.setGridLinesVisible(false);
        root.getChildren().add(grid);
	}
	
	

	public Rectangle createCell(int _x, int _y,int _column, int _row) { 		
		Rectangle rect = new Rectangle();
		int i =new Random().nextInt(imageCandy.length);
		rect.setId(String.valueOf(i));// id varies from 0 to 6, used to assign number of the color
        rect.setX(_x);
        rect.setY(_y);
        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT);
        rect.setFill(new ImagePattern(getCandyImage(imageCandy[i])));// set image to the rectangle
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setOnMouseClicked(new EventHandler<MouseEvent>()
        	 {
          @Override
          public void handle(MouseEvent t) {
        	  //first click and indicate 4 positions that can be clicked next
        	  if (isPressed==false) {
        		 rect.setStroke(Color.BLACK);
                 rect.setStrokeWidth(3);
                 columnAnte=_column;
                 rowAnte=_row;
                 setPossibleMove(_column,_row);
                 isPressed=true;
                 System.out.println( "cell first click is at "+_column+" "+_row);
        	  }
        	  else {
        		  //second click
        		  
        		  //click on 4 surrounding squares
        		  if ((_column == columnAnte+1 && _row==rowAnte)||
        			  (_column == columnAnte-1 && _row==rowAnte)||
        			  (_column == columnAnte && _row==rowAnte+1)||
        			  (_column == columnAnte && _row==rowAnte-1)) {				  
        			  //check if swap is valid
        			  swap(_column, _row ,columnAnte,rowAnte);
        			  checkBoard();
        			  
        			  
        			  checkValid();//check if there are any possible move	
        			  if(needShuffle==true) {
        				  shuffle();
        			  }
        		  }
        		  //if click on other cells or the first cell => end turn
        		  setClearAllBorder();
        		  isPressed=false;
        		  System.out.println("end turn");
        	  }
        	  updateScore();
          }
        });
        return rect;
	}
	
	public void setPossibleMove(int x,int y){
		if(x>0) {
			rectArray[x-1][y].setStroke(Color.AQUAMARINE);
			rectArray[x-1][y].setStrokeWidth(3);
		}
		if(x< numberOfColumn-1) {
			rectArray[x+1][y].setStroke(Color.AQUAMARINE);
			rectArray[x+1][y].setStrokeWidth(3);
		}
		if (y>0 ) {
			rectArray[x][y-1].setStroke(Color.AQUAMARINE);
			rectArray[x][y-1].setStrokeWidth(3);
		}
		if ( y<numberOfRow-1) {
			rectArray[x][y+1].setStroke(Color.AQUAMARINE);
			rectArray[x][y+1].setStrokeWidth(3);
		}
	}
	public void swap(int x1,int y1, int x2, int y2){
		Paint image= rectArray[x2][y2].getFill();
		rectArray[x2][y2].setFill(rectArray[x1][y1].getFill());
		rectArray[x1][y1].setFill(image);
		//switch id
		String id=rectArray[x2][y2].getId();
		rectArray[x2][y2].setId(rectArray[x1][y1].getId());
		rectArray[x1][y1].setId(id);
	}
	
	public void checkBoard() {
		
		//check vertically and send the list of cell in combo
		List<Rectangle> destruir = new ArrayList<>();
		destruir= checkCombo(destruir,1);// 1 for vertically ,2 for horizontally
		// check horizontally and send the list of cell in combo
		List <Rectangle> destruir2 = new ArrayList<>();
		destruir2= checkCombo(destruir2,2);
		//print the total number of cells in combo in each direction
		System.out.println("Total vertical cells abt to be destroyed: "+destruir.size());
		System.out.println("Total horizontal cells abt to be destroyed: "+destruir2.size());
		if(destruir.size()!=0) {
			isCombo=true;
			crush(destruir);
			skyFall(destruir);
			destruir.clear();
		}
		if(destruir2.size()!= 0) {
			isCombo=true;
			crush(destruir2);
			skyFall(destruir2);
			destruir.clear();
		}
		// make sure after the skyFall there is no additional combo 
		if(isCombo==true) {
			isCombo=false;
//			try {
//				Thread.sleep(100);
//			}
//			catch(InterruptedException e){
//				System.err.println(e.getMessage());
//			}
			checkBoard();
		}
	}
	public List<Rectangle> checkCombo(List<Rectangle> list, int checkDirection) {
		int ComboNum=0;
		int staticIndex=0;
		int alterIndex=0;
		int listIndex=0;
		if (checkDirection == 1) {// check horizontally
			staticIndex = numberOfColumn;
		    alterIndex = numberOfRow;
		}
		if (checkDirection == 2) {// check vertically
			staticIndex = numberOfRow;
			alterIndex = numberOfColumn;
		}
		//method: first, pick out the image need to be compared by id of each cell
		// second, if we are checking vertically, then we take each column and check downward
		// for each same image cell we add 1 to ComboNum, otherwise the next cell doesnt have same image the ComboNum will be 0 
		// finally, if ComboNum manages to get three or more in a row, we add to list of rectangle for crush func
		
		for (int imgIndex=0; imgIndex<imageCandy.length; imgIndex++) {
			for (int i = 0; i< staticIndex; i++ ) {
				for (int j = 0; j< alterIndex; j++ ) {
					int rectId=0;
					if (checkDirection == 1) {
						rectId= Integer.parseInt(rectArray[i][j].getId());
						// change String to Integer to get the id from 0 to 6
					}
					if (checkDirection == 2) {
						rectId= Integer.parseInt(rectArray[j][i].getId());
					}
					
					if (imgIndex == rectId) {
						ComboNum++;
						if (ComboNum>=3 && j==alterIndex-1) { // in case the current cell is the last cell of the column/row and it's still in a combo
							for (int k=j-ComboNum+1;k<j+1;k++){
								if (checkDirection == 1) {
									list.add(listIndex,rectArray[i][k]);
								}
								if (checkDirection == 2) {
									list.add(listIndex,rectArray[k][i]);
								}	
							}
							listIndex++;
						}
					
					}
					else {
						if (ComboNum >=3 ) {//at this point the current is not in the combo of those previous cells
							for (int k=j-ComboNum;k<j;k++){
								if (checkDirection == 1) {
									list.add(listIndex,rectArray[i][k]);
								}
								if (checkDirection == 2) {
									list.add(listIndex,rectArray[k][i]);
								}
							}
							listIndex++;
						}
						ComboNum=0;
					}	
				}
				ComboNum=0;// set to zero and change to next column/row
			}//change the comparing image
		}
		return list;
	}
	
	public void crush(List<Rectangle> list) {
		
		for(int i =0 ; i<list.size();i++) {
			Rectangle rect= list.get(i);
			FadeTransition fadeTransition = 
		            new FadeTransition(Duration.millis(2000), rect);
		        fadeTransition.setFromValue(0.0f);
		        fadeTransition.setToValue(1.0f);
		        
//		    RotateTransition rotateTransition = 
//		            new RotateTransition(Duration.millis(2000), rect);
//		        rotateTransition.setByAngle(360f); 
		    ParallelTransition parallelTransition = new ParallelTransition();
		        parallelTransition.getChildren().addAll(
		                fadeTransition
//		                rotateTransition
		        );
		        parallelTransition.play();

//		     FillTransition ft = new FillTransition(Duration.millis(2000), rect,
//		        				Color.AQUA, Color.TRANSPARENT);
//		     ft.setCycleCount(1);
//		     ft.setAutoReverse(false);
//		 
//		     ft.play();
		        rect.setFill(Color.TRANSPARENT);
		}	
		
	}
	
	public void skyFall(List<Rectangle> list) {
		
		Rectangle crushrect;
		for(int index =list.size()-1 ; index>=0;index--) {// have to minus because list is a stack list
			// which means when we get an element from the list it will get the last element to the very first one
			crushrect= list.get(index);
			if (crushrect.getFill() == Color.TRANSPARENT) {// in case that vertical and horizontal combo share the same cell
				int column=(int) crushrect.getX()/WIDTH;
				int row=(int) (crushrect.getY()- gameInfoHeight )/HEIGHT; 
				System.out.println("index "+ index + " Array " +column+" "+row +" paint: "+rectArray[column][row].getFill() );
				for(int j =row;;j--) {
					if ((j==0)) {
						break;
					}
					else {
						swap(column,j,column,j-1);
					}
				}
			}
		}
		
		//se crea nuevo bloque que no tiene color
		for(int column=0;column<numberOfColumn;column++) {
			for(int row = 0; row<numberOfRow; row++) {
				if (rectArray[column][row].getFill()==Color.TRANSPARENT) {
					int i =new Random().nextInt(imageCandy.length);
					rectArray[column][row].setId(String.valueOf(i));// id varies from 0 to 6
					rectArray[column][row].setFill(new ImagePattern(getCandyImage(imageCandy[i])));
				}
			}
		}
	}
	
	public void checkValid() {
		
	}
	public void shuffle() {

	}
	public void setClearAllBorder() {
		for (int column = 0; column < numberOfColumn; column++) {
			for (int row =0; row < numberOfRow; row++) { 
				 	rectArray[column][row].setStroke(Color.TRANSPARENT); 
            }
		}
	}
	
	public void updateScore() {
		
	}
	//init the game
	public static void main(String[] args) {
		 launch(args);
    }
}
