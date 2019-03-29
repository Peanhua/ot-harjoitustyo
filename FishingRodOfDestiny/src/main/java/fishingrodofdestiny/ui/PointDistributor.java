/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.gameobjects.Character;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author joyr
 */
public class PointDistributor {
    private int       pointsLeft;
    private Text      pointsLeftText;

    private ArrayList<Integer> pointValues;
    private ArrayList<Text>    pointValueTexts;
    
    public enum PointType {
        // TODO: move this enum to character, make querying character attributes with the enum possible, use it here
        //       OR make character attribute a separate class
        ATTACK,
        DEFENCE,
        HITPOINTS,
        CARRYING_CAPACITY;
        
        public static String getName(PointType pointType) {
            switch(pointType) {
                case ATTACK:            return "Attack";
                case DEFENCE:           return "Defence";
                case HITPOINTS:         return "Hit Points";
                case CARRYING_CAPACITY: return "Carrying Capacity";
                default:                throw new RuntimeException("Unknown PointType for PointType.getName()");
            }
        }
    };
    
    
    public PointDistributor(int pointsToDistribute) {
        this.pointsLeft = pointsToDistribute;

        this.pointValues = new ArrayList<>();
        this.pointValueTexts  = new ArrayList<>();
        for(int i = 0; i < PointType.values().length; i++) {
            this.pointValues.add(0);
            this.pointValueTexts.add(null);
        }
    }
    
    
    public Node createUserInterface() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);

        int row = 0;

        {
            Text label = UserInterfaceFactory.createText("Distribute points:");
            GridPane.setConstraints(label, 0, row);
            grid.getChildren().add(label);

            this.pointsLeftText = UserInterfaceFactory.createText("" + this.pointsLeft);
            GridPane.setConstraints(this.pointsLeftText, 1, row);
            grid.getChildren().add(this.pointsLeftText);
        }

        row++;

        for(PointType pt : PointType.values()) {
            Text label = UserInterfaceFactory.createText(PointType.getName(pt) + ":");

            Text value = UserInterfaceFactory.createText("");

            Button sub = new Button("-");
            sub.setOnAction(e -> this.onSubtractClicked(pt));

            Button add = new Button("+");
            add.setOnAction(e -> this.onAddClicked(pt));

            GridPane.setConstraints(label, 0, row);
            GridPane.setConstraints(value, 1, row);
            GridPane.setConstraints(sub, 2, row);
            GridPane.setConstraints(add, 3, row);
            grid.getChildren().addAll(label, value, sub, add);
            
            this.pointValueTexts.set(pt.ordinal(), value);
            
            row++;
        }
        
        this.refreshTexts();
        
        return grid;
    }
    
    
    public int getPoints(PointType pointType) {
        return this.pointValues.get(pointType.ordinal());
    }
    
    
    private void refreshTexts() {
        this.pointsLeftText.setText("" + this.pointsLeft);
        
        for(PointType pt : PointType.values()) {
            this.pointValueTexts.get(pt.ordinal()).setText("" + this.pointValues.get(pt.ordinal()));
        }
    }
    
    
    private void onSubtractClicked(PointType pointType) {
        int ind = pointType.ordinal();
        int cur = this.pointValues.get(ind);
        if(cur > 0) {
            this.pointsLeft += 1;
            this.pointValues.set(ind, cur - 1);
            this.refreshTexts();
        }
    }

    
    private void onAddClicked(PointType pointType) {
        int ind = pointType.ordinal();
        if(this.pointsLeft > 0) {
            this.pointsLeft -= 1;
            int cur = this.pointValues.get(ind);
            this.pointValues.set(ind, cur + 1);
            this.refreshTexts();
        }
    }
}
