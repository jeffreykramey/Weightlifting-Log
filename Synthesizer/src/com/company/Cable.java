package com.company;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Cable{
    protected Line line = new Line();

   public Cable(WidgetBase input, WidgetBase output){
       input.getAudioComponent().connectInput(output.getAudioComponent());
       //line = new Line(output.getOutputJack().getCenterX(),output.getOutputJack().getCenterY(), input.getInputJack().getCenterX(), input.getInputJack().getCenterY());

//       line.setStartX(output.getOutputJack().getCenterX());
//       line.setStartY(output.getOutputJack().getCenterY());
//
//       line.setEndX(input.getInputJack().getCenterX());
//       line.setEndY(input.getInputJack().getCenterY());
//       output.getWidget().getChildren().add(line);




//       output.getWidget().getParent().setOnMouseClicked(event -> {
//           System.out.println("click");
//           line.setStartX(output.getOutputJack().getCenterX());
//           line.setStartY(output.getOutputJack().getCenterY());
//           output.getWidget().getChildren().add(line);
//       });
//
//       output.getWidget().getParent().setOnMouseReleased(event -> {
//           System.out.println("release");
//           line.setEndX(output.getOutputJack().getCenterX());
//           line.setEndY(output.getOutputJack().getCenterY());
//       });






   }

    //listen for click, line's start point begins on outputJack.getCenterX() and .getCenterY(), set WidgetBase output
    //if mouse released, grab nearest input jack x,y set equal to WidgetBase input
}
