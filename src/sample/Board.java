package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Board {
    private Stage window;
    private BorderPane layout;
    private GridPane gameBoard;

    private VBox rightMenu;
    private Button next;
    private Button restart;
    private Label alive;
    private Label dead;

    private int deadNum,aliveNum;
    private boolean isStarted;
    private int rows,columns;

    private Cell[][] cells;
    private Button[][] house;

    public Board(int rows,int columns) {
        this.rows=rows;
        this.columns=columns;
        layout=new BorderPane();
        deadNum=rows*columns;
        aliveNum=0;
        isStarted=false;

        setRightMenu();
        setBoard(rows,columns);
        layout.setCenter(gameBoard);
        Scene scene=new Scene(layout,53*columns+150,53*rows+50);
        window=new Stage();
        window.setScene(scene);
        window.show();
    }

    private void setRightMenu() {
        rightMenu=new VBox();
        dead=new Label("Deads : "+deadNum);
        alive=new Label("Alives : "+aliveNum);

        //Buttons :

        next=new Button("Start");
        next.setMinWidth(75);
        next.setOnAction(event -> playGame());
        restart=new Button("Restart");
        restart.setMinWidth(75);
        restart.setOnAction(event -> gameRestart());
        restart.setVisible(false);

        rightMenu.getChildren().addAll(dead,alive,next,restart);
        rightMenu.setSpacing(10);
        rightMenu.setPadding(new Insets(20,20,10,10));
        layout.setRight(rightMenu);
    }

    private void setBoard(int rows, int columns) {
        house=new Button[rows+10][columns+10];
        cells=new Cell[rows+10][columns+10];
        gameBoard=new GridPane();
        gameBoard.setVgap(3);
        gameBoard.setHgap(3);
        gameBoard.setPadding(new Insets(20,20,20,20));

        for(int i=0;i<=rows+1;i++){
            for(int j=0;j<=columns+1;j++){
                house[i][j]=new Button("none");
                if(i>0&&i<=rows&&j>0&&j<=columns){
                    cells[i][j]=new Cell(i,j);
                    house[i][j].setMinWidth(50);
                    house[i][j].setMinHeight(50);
                    house[i][j].setMaxWidth(50);
                    house[i][j].setMaxHeight(50);
                    int x=i,y=j;
                    setHouse(i,j,"Dead");
                    house[i][j].setOnAction(event -> clickOnCell(x,y));
                    GridPane.setConstraints(house[i][j],j,i);
                    gameBoard.getChildren().addAll(house[i][j]);
                }

            }
        }

    }

    private void clickOnCell(int x, int y) {
        if(isStarted)
            return;
        cells[x][y].toOpposite();
        setHouse(x,y,cells[x][y].getAliveOrDead());
        if(cells[x][y].getAliveOrDead().equals("Alive")){
            aliveNum++;
            deadNum--;
        }else {
            aliveNum--;
            deadNum++;
        }
        updateLabel();
    }

    private void setHouse(int x,int y,String aliveOrDead){
        house[x][y].setText(aliveOrDead);
        if(aliveOrDead.equals("Alive"))
            house[x][y].setStyle("-fx-background-color:yellow");
        else
            house[x][y].setStyle("-fx-background-color:black");
    }

    private void updateLabel() {
        alive.setText("Alives : "+aliveNum);
        dead.setText("Deads : "+deadNum);
    }

    private void gameRestart() {
        isStarted=false;
        restart.setVisible(false);
        next.setText("Start");
        for(int i=1;i<=rows;i++){
            for(int j=1;j<=columns;j++){
                cells[i][j].setAliveOrDead("Dead");
                setHouse(i,j,"Dead");
            }
        }
        aliveNum=0;
        deadNum=rows*columns;
        updateLabel();
    }

    private void playGame() {
       if(next.getText().equals("Start")){
           next.setText("Next");
           isStarted=true;
           restart.setVisible(true);
           return;
       }
       nextStep();
    }

    private void nextStep() {
        for(int i=1;i<=rows;i++){
            for(int j=1;j<=columns;j++){
                checkCell(i,j);
            }
        }
        updateCells();
    }

    private void checkCell(int i, int j) {
        int aroundAlive=0;
        //counting alive neighbors
        if(aliveCheck(i-1,j-1))
            aroundAlive++;
        if(aliveCheck(i-1,j))
            aroundAlive++;
        if(aliveCheck(i-1,j+1))
            aroundAlive++;
        if(aliveCheck(i,j-1))
            aroundAlive++;
        if(aliveCheck(i,j+1))
            aroundAlive++;
        if(aliveCheck(i+1,j-1))
            aroundAlive++;
        if(aliveCheck(i+1,j))
            aroundAlive++;
        if(aliveCheck(i+1,j+1))
            aroundAlive++;

        if(aroundAlive<2){
            cells[i][j].setNext("Dead");
        }
        else if(aroundAlive>3){
            cells[i][j].setNext("Dead");
        }
        else if(aroundAlive==3){
            cells[i][j].setNext("Alive");
        }
        else {
            cells[i][j].setNext(cells[i][j].getAliveOrDead());
        }
        System.out.println(i+" "+j+" "+aroundAlive+" "+cells[i][j].getNext());
    }

    private void updateCells() {
            for(int i=1;i<=rows;i++){
                for(int j=1;j<=columns;j++){
                    if(cells[i][j].getAliveOrDead()!=cells[i][j].getNext()){
                        if(cells[i][j].getNext().equals("Alive")){
                            aliveNum++;
                            deadNum--;
                        }else {
                            aliveNum--;
                            deadNum++;
                        }
                    }
                    cells[i][j].setAliveOrDead(cells[i][j].getNext());
                    System.out.println(i+" "+j+" to "+cells[i][j].getAliveOrDead());
                    setHouse(i,j,cells[i][j].getAliveOrDead());
                }
            }
            updateLabel();
    }

    private boolean aliveCheck(int i,int j){
        if(house[i][j].getText().equals("Alive"))
            return true;
        return false;
    }

}
