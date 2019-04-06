/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.ui.widgets.PointDistributor;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenNewGame extends Screen {
    
    private Player           player;
    private TextField        name;
    private ToggleGroup      rescueToggleGroup;
    private PointDistributor pointDistributor;
    
    public ScreenNewGame(Screen parent, Stage stage) {
        super(parent, stage);
        this.player            = new Player();
        this.name              = null;
        this.rescueToggleGroup = null;
        this.pointDistributor  = null;
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);
        vb.setMinWidth(scene.getWidth());

        vb.getChildren().add(UserInterfaceFactory.createTitle("START NEW GAME"));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.name = new TextField();
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Name:", this.name));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.pointDistributor = new PointDistributor(this.player, 20); 
        vb.getChildren().add(this.pointDistributor.createUserInterface());
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        vb.getChildren().add(this.setupRescueTarget());
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));

        TextField randseed = new TextField();
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Random seed:", randseed));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        vb.getChildren().add(this.setupButtons());
        
        root.getChildren().add(vb);
    }

    private Node setupRescueTarget() {
        String[]            labels  = { "Rescue princess:",         "Rescue prince:"         };
        Game.RescueTarget[] targets = { Game.RescueTarget.PRINCESS, Game.RescueTarget.PRINCE };
        
        List<RadioButton> buttons = new ArrayList<>();
        this.rescueToggleGroup = new ToggleGroup();
        Node node = UserInterfaceFactory.createRadiobuttonGrid(labels, rescueToggleGroup, buttons);
        for (int i = 0; i < labels.length; i++) {
            buttons.get(i).setUserData(targets[i]);
        }
        return node;
    }

    private Node setupButtons() {
        String[] labels = { "Cancel", "Start New Game" };
        List<Button> buttons = new ArrayList<>();
        Node rv = UserInterfaceFactory.createButtonRow(labels, buttons);
        buttons.get(0).setOnAction(e-> this.close());
        buttons.get(1).setOnAction(e-> this.startNewGame());
        return rv;
    }
    
    
    private void startNewGame() {
        player.setName(this.name.getText());
        player.adjustAttack(this.pointDistributor.getPoints(PointDistributor.PointType.ATTACK));
        player.adjustDefence(this.pointDistributor.getPoints(PointDistributor.PointType.DEFENCE));
        player.adjustMaxHitpoints(this.pointDistributor.getPoints(PointDistributor.PointType.HITPOINTS));
        player.getInventory().adjustWeightLimit(this.pointDistributor.getPoints(PointDistributor.PointType.CARRYING_CAPACITY));
        
        this.close();
        
        Game.RescueTarget rtarget = (Game.RescueTarget) this.rescueToggleGroup.getSelectedToggle().getUserData();
        Game game = new Game(player, rtarget);
        Screen plot = new ScreenPlot(game, this.getParent(), this.getStage());
        plot.show();
    }
}
