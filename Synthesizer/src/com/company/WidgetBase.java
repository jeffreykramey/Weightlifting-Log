package com.company;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class WidgetBase {
    protected BorderPane pane;
    private Text label;
    private VBox rBox;
    private VBox tBox;
    private Button closeButton;
    private Circle outputJack, inputJack;
    private GridPane grid;
    private AudioComponent audioComponent;
    private Boolean dragging = false;



    public WidgetBase(String title){


        pane = new BorderPane();

        grid = new GridPane();
        grid.setMaxWidth(300);
        grid.setMaxHeight(100);
        pane.setCenter(grid);

        label = new Text(title);
        grid.add(label, 1, 0);

        rBox = new VBox();
        pane.setRight(rBox);
        rBox.setAlignment(Pos.BOTTOM_RIGHT);

        tBox = new VBox();
        pane.setTop(tBox);
        tBox.setAlignment(Pos.TOP_RIGHT);

        closeButton = new Button("X");
        closeButton.setStyle("-fx-base: #cb1b1b;");
        tBox.getChildren().add(closeButton);

        inputJack = new Circle(10);

        outputJack = new Circle(10);
        outputJack.setFill(Color.LIME);
        rBox.getChildren().add(outputJack);

        pane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: #0022ff;");


        closeButton.setOnAction(event -> {
            pane.setStyle(null);
            pane.getChildren().clear();
        });

        pane.setOnMousePressed(startDrag ->{
            if (startDrag.getTarget() != outputJack && startDrag.getTarget() != inputJack){
                dragging = true;
            }
            else if (startDrag.getTarget() == outputJack){

            }
        });

        pane.setOnMouseDragged(drag ->{
            if(dragging) {
                pane.setLayoutX(drag.getSceneX());
                pane.setLayoutY(drag.getSceneY());
            }
        });

        pane.setOnMouseReleased(stopDrag->{
            dragging = false;
        });
    }

    protected void addToGUI(Node controlToAdd){
        grid.add(controlToAdd, grid.getColumnCount(), 0);
    }

    protected void buildInputJack(){
        inputJack.setFill(Color.LIGHTBLUE);
        VBox lBox = new VBox();
        pane.setLeft(lBox);
        lBox.getChildren().add(inputJack);
        lBox.setAlignment(Pos.BOTTOM_LEFT);
    }

    public BorderPane getWidget(){
        return pane;
    }

    public AudioComponent getAudioComponent(){
        return audioComponent;
    }

    public void setAudioComponent(AudioComponent update){
        audioComponent = update;
    }

    public Circle getOutputJack(){
        return outputJack;
    }

    public Circle getInputJack(){
        return inputJack;
    }
}
