import java.util.Random;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoardExample extends Application {
	
	//var board setup
	private static final int WIDTH= 40;
	private static final int HEIGHT= 40;
	private static final int numberOfColumn = 7;
	private static final int numberOfRow = 7;
	private static final int Screen_WIDTH = 320;
	private static final int Screen_HEIGHT = 400;
	
	//var cell
	private boolean isPressed= false;
	private boolean needShuffle= false;
	private int columnAnte,rowAnte;
	private Color[] colors = new Color[] {Color.BLUEVIOLET, Color.DARKSALMON, Color.MAGENTA,
			Color.YELLOWGREEN, Color.CHOCOLATE,Color.DARKORANGE,Color.BLUE};
	
	public Rectangle createCell(int _x, int _y,int _column, int _row) { 		
		Rectangle rect = new Rectangle();
        rect.setX(_x);
        rect.setY(_y);
        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT);
        rect.setFill(colors[new Random().nextInt(colors.length)]);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setOnMouseClicked(new EventHandler<MouseEvent>()
        	 {
          @Override
          public void handle(MouseEvent t) {
        	  if (isPressed==false) {
        		 rect.setStroke(Color.BLACK);
                 rect.setStrokeWidth(3);
                 columnAnte=_column;
                 rowAnte=_row;
                 setPossibleMove(_column,_row);
                 isPressed=true;
        	  }
        	  else {
        		  if ((_column == columnAnte+1 && _row==rowAnte)||
        			  (_column == columnAnte-1 && _row==rowAnte)||
        			  (_column == columnAnte && _row==rowAnte+1)||
        			  (_column == columnAnte && _row==rowAnte-1)) {	
        			  swap(_column, _row ,columnAnte,rowAnte);
        			  checkMove();
        			  checkValid();	
        			  if(needShuffle==true) {
        				  shuffle();
        			  }
        		  }
        		  setClearAllBorder();
        		  isPressed=false;
        	  }
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
	
	/*public void swapAnimated(int x1,int y1, int x2, int y2) {
		Path path = new Path();
		double _x1=rectArray[x1][y1].getX();
		double _y1=rectArray[x1][y1].getY();
		double _x2=rectArray[x2][y2].getX();
		double _y2=rectArray[x2][y2].getY();
		path.getElements().add(new MoveTo(_x1+20,_y1+20));
	    path.getElements().add(new LineTo(_x2+20,_y2+20));
		final PathTransition transition = generatePathTransition(rectArray[x1][y1], path);
		transition.play();
	}
	
	private PathTransition generatePathTransition(final Rectangle rect, final Path path){
	      final PathTransition pathTransition = new PathTransition();
	      pathTransition.setDuration(Duration.seconds(1.0));
	      pathTransition.setDelay(Duration.seconds(0));
	      pathTransition.setPath(path);
	      pathTransition.setNode(rect);
	      pathTransition.setAutoReverse(false);
	      return pathTransition;
	}*/
	
	public void swap(int x1,int y1, int x2, int y2){
		Paint color= rectArray[x2][y2].getFill();
		rectArray[x2][y2].setFill(Color.valueOf(rectArray[x1][y1].getFill().toString()));
		rectArray[x1][y1].setFill(color);
	}
	
	public void checkMove() {
		
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
	
	Pane root = new Pane();
	Rectangle rectArray[][]= new Rectangle[numberOfColumn][numberOfRow];
	@Override
	public void start (Stage board) {
		
		
	// init the board
		
		for (int column = 0, x=1; column < numberOfColumn; column++,x+=WIDTH+1) {
			for (int row =0,y= HEIGHT*2; row < numberOfRow; row++, y += HEIGHT+1) { 
				 	rectArray[column][row]= createCell(x,y,column,row);  
				 	 root.getChildren().add(rectArray[column][row]);
            }
		}
		
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
	
	public static void main(String[] args) {
		 launch(args);
    }
}