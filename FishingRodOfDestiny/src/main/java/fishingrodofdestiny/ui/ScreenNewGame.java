/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenNewGame extends Screen {
    
    private int      distPoints;
    private String[] distLabels = { "Hit Points:",
                                     "Attack:",
                                     "Defence:",
                                     "Carrying Capacity:"
    };
    private ArrayList<Integer> distValues;
    private ArrayList<Text>    distValueTexts;
    
    public ScreenNewGame(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);
        vb.setMinWidth(scene.getWidth());

        vb.getChildren().add(this.createTitle("START NEW GAME"));
        vb.getChildren().add(this.createVerticalSpacer(50));
        
        TextField name = new TextField();
        vb.getChildren().add(this.createLabeledInput("Name:", name));
        vb.getChildren().add(this.createVerticalSpacer(50));
        
        
        { // Point distribution: 
            this.distPoints = 20;
            
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(4);
            grid.setVgap(2);

            int row = 0;
            {
                Text t = this.createText("Distribute points:");
                Text v = this.createText("" + this.distPoints);

                GridPane.setConstraints(t, 0, row);
                GridPane.setConstraints(v, 1, row);
                grid.getChildren().addAll(t, v);
                row++;
            }

            this.distValues     = new ArrayList<>();
            this.distValueTexts = new ArrayList<>();
            
            for(String label : this.distLabels) {
                Text t = this.createText(label);
                Text v = this.createText("0");
                Button sub = new Button("-");
                Button add = new Button("+");
                
                this.distValues.add(0);
                this.distValueTexts.add(v);

                GridPane.setConstraints(t, 0, row);
                GridPane.setConstraints(v, 1, row);
                GridPane.setConstraints(sub, 2, row);
                GridPane.setConstraints(add, 3, row);
                grid.getChildren().addAll(t, v, sub, add);
                row++;
            }
            
            vb.getChildren().addAll(grid);
            vb.getChildren().add(this.createVerticalSpacer(50));
        }
        
        { // Saving target:
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(4);
            grid.setVgap(2);
            
            Text l1 = this.createText("Save princess:");
            Text l2 = this.createText("Save prince:");
            
            ToggleGroup group = new ToggleGroup();
            RadioButton button1 = new RadioButton();
            button1.setToggleGroup(group);
            button1.setSelected(true);
            RadioButton button2 = new RadioButton();
            button2.setToggleGroup(group);

            GridPane.setConstraints(l1, 0, 0);
            GridPane.setConstraints(l2, 0, 1);
            GridPane.setConstraints(button1, 1, 0);
            GridPane.setConstraints(button2, 1, 1);
            grid.getChildren().addAll(l1, l2, button1, button2);
            
            vb.getChildren().addAll(grid);
            vb.getChildren().add(this.createVerticalSpacer(50));
        }


        TextField randseed = new TextField();
        vb.getChildren().add(this.createLabeledInput("Random seed:", randseed));
        vb.getChildren().add(this.createVerticalSpacer(50));

        
        { // Cancel/Go -buttons:
            HBox buttons = new HBox(10);
            buttons.setAlignment(Pos.CENTER);

            Button close = new Button("Cancel");
            close.setOnAction(e-> this.close());
            buttons.getChildren().addAll(close);

            Button start = new Button("Start New Game");
            start.setOnAction(e-> this.startNewGame());
            buttons.getChildren().addAll(start);
            
            vb.getChildren().add(buttons);
        }
        
        root.getChildren().add(vb);
    }
    
    private void startNewGame() {
        this.close();
        
        Screen plot = new ScreenPlot(this.getParent(), this.getStage());
        plot.show();
    }
}
