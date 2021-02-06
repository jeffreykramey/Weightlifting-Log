package com.company;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;


public class MyApp extends Application {
    private static ArrayList <WidgetBase> widgetArray;
    private WidgetBase out;
    private WidgetBase in;
    private int lineCounter;

    @Override
    public void start(Stage primaryStage) throws Exception {
        widgetArray = new ArrayList<WidgetBase>();
        primaryStage.setTitle("Synthesizer");
        BorderPane parentPane = new BorderPane();
        Pane widgetBox = new Pane();
        Scene scene = new Scene(parentPane, 800, 1200);

        //create all buttons
        Button play = new Button("Play");
        play.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        Button sineWaveBtn = new Button("SineWave");
        Button volumeBtn = new Button("Volume");
        Button linearRampBtn = new Button("LinearRamp");
        Button vFSineWaveBtn = new Button("VFSineWave");
        Button mixerBtn = new Button("Mixer");
        VBox filterMenu = new VBox(sineWaveBtn, volumeBtn, linearRampBtn, vFSineWaveBtn, mixerBtn);
        filterMenu.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: #000000;");

        VBox playMenu = new VBox(play);
        playMenu.setAlignment(Pos.BOTTOM_LEFT);

        parentPane.setCenter(widgetBox);
        parentPane.setRight(filterMenu);
        parentPane.setBottom(playMenu);

        primaryStage.setScene(scene);
        primaryStage.show();

        SpeakerWidget spkrWidget = new SpeakerWidget();
        widgetArray.add(spkrWidget);
        widgetBox.getChildren().add(spkrWidget.getWidget());


        sineWaveBtn.setOnAction(ae -> {
            SineWaveWidget swWidget = null;
            try {
                swWidget = new SineWaveWidget();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            widgetArray.add(swWidget);
            widgetBox.getChildren().add(swWidget.getWidget());
        });

        volumeBtn.setOnAction(ae -> {
            VolumeWidget volWidget = new VolumeWidget();
            widgetArray.add(volWidget);
            widgetBox.getChildren().add(volWidget.getWidget());
        });

        vFSineWaveBtn.setOnAction(ae ->{
            VFSineWaveWidget vfswWidget = new VFSineWaveWidget();
            widgetArray.add(vfswWidget);
            widgetBox.getChildren().add(vfswWidget.getWidget());
        });

        mixerBtn.setOnAction(ae ->{
            MixerWidget mxrWidget = new MixerWidget();
            widgetArray.add(mxrWidget);
            widgetBox.getChildren().add(mxrWidget.getWidget());
        });

        linearRampBtn.setOnAction(ae ->{
            LinearRampWidget lnrRmpWidget = new LinearRampWidget();
            widgetArray.add(lnrRmpWidget);
            widgetBox.getChildren().add(lnrRmpWidget.getWidget());
        });

        play.setOnAction(ae-> {
            Clip c = null;
            try {
                c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            AudioClip clip = spkrWidget.getAudioComponent().getClip();
            c.open(format16, clip.getByteArr(), 0, clip.getByteArr().length);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            c.start();
            System.out.println("playing");
            while(c.getFramePosition() < 88200 || c.isActive() || c.isRunning()){}
            c.close();
            System.out.println("done");
        });

        ArrayList <Line> lines = new ArrayList();

        widgetBox.setOnMousePressed(event -> {  //set output
            for(int i = 0; i < widgetArray.size(); i ++){
                if(event.getTarget() == widgetArray.get(i).getOutputJack()){
                    System.out.println("your output widget is " + i);
                    out = widgetArray.get(i);
                    Line line = new Line();
                    line.setStartX(event.getSceneX());
                    line.setStartY(event.getSceneY());
                    lines.add(line);
                    widgetBox.getChildren().add(lines.get(lineCounter));
                }
            }

        });

        widgetBox.setOnMouseReleased(event ->{  //set input
            //find location of cursor when mouse is released
            Point2D releasePoint = new Point2D(event.getSceneX(), event.getSceneY());
            for(int i = 0; i < widgetArray.size(); i ++) {
                //creates a bound that matches the inputJack circle
                //converts the coordinates from local to scene so releasePoint and ijPoint are same coordinate type
                Bounds ijPoint = widgetArray.get(i).getInputJack().localToScene(widgetArray.get(i).getInputJack().getBoundsInLocal());
                if(ijPoint.contains(releasePoint)){ //checks each to see if releasePoint is contained within the bounds of the inputJack
                    System.out.println("your input widget is " + i);
                    in = widgetArray.get(i);
                    Cable cable = new Cable(in, out);
                    lines.get(lineCounter).setEndX(event.getSceneX());
                    lines.get(lineCounter).setEndY(event.getSceneY());
                    lineCounter ++;
                }
            }
        });

        widgetBox.setOnMouseDragged(event -> {
            for(int i = 0; i < widgetArray.size(); i ++) {
                if(event.getTarget() == widgetArray.get(i).getOutputJack()){
                    lines.get(lineCounter).setEndX(event.getSceneX());
                    lines.get(lineCounter).setEndY(event.getSceneY());
                }
            }
        });


    }

    public static ArrayList <WidgetBase> getWidgetArray(){
        return widgetArray;
    }
}
