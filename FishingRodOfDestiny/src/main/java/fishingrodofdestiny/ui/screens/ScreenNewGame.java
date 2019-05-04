/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.ui.widgets.PointDistributor;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenNewGame extends Screen {
    
    private TextField        name;
    private ToggleGroup      rescueToggleGroup;
    private PointDistributor pointDistributor;
    private TextField        randomSeed;
    private final Random     random;
    private Button           startButton;
    
    public ScreenNewGame(Screen parent, Stage stage) {
        super(parent, stage);
        this.name              = null;
        this.rescueToggleGroup = null;
        this.pointDistributor  = null;
        this.randomSeed        = null;
        this.random            = new Random();
        this.startButton       = null;
    }
    

    @Override
    protected Node createUserInterface() {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(UserInterfaceFactory.createTitle("START NEW GAME"));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.name = this.createNameField();
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Name:", this.name));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        this.pointDistributor = new PointDistributor(new Player(), 20); 
        vb.getChildren().add(this.pointDistributor.createUserInterface());
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        vb.getChildren().add(this.setupRescueTarget());
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));

        this.randomSeed = new TextField("" + this.random.nextInt(1000000));
        vb.getChildren().add(UserInterfaceFactory.createLabeledInput("Random seed:", this.randomSeed));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        
        vb.getChildren().add(this.setupButtons());
        
        return vb;
    }
    
    
    private TextField createNameField() {
        TextField field = new TextField();
        field.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (this.startButton != null) {
                this.startButton.setDisable(this.name.getText().length() == 0);
            }
        });
        return field;
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
        this.startButton = buttons.get(1);
        this.startButton.setOnAction(e-> this.startNewGame());
        this.startButton.setDisable(true);
        return rv;
    }
    
    
    private void startNewGame() {
        Player defaultPlayer = new Player();
        Player player = new Player(
                this.name.getText(),
                this.pointDistributor.getPoints(PointDistributor.PointType.ATTACK)            + defaultPlayer.getAttack(),
                this.pointDistributor.getPoints(PointDistributor.PointType.DEFENCE)           + defaultPlayer.getDefence(),
                this.pointDistributor.getPoints(PointDistributor.PointType.HITPOINTS)         + defaultPlayer.getMaxHitpoints(),
                this.pointDistributor.getPoints(PointDistributor.PointType.CARRYING_CAPACITY) + defaultPlayer.getCarryingCapacity()
        );
        
        this.close();
        
        Game.RescueTarget rtarget = (Game.RescueTarget) this.rescueToggleGroup.getSelectedToggle().getUserData();
        Game game = new Game(this.parseRandomSeedNumber(), player, rtarget);
        Screen plot = new ScreenPlot(game, this.getParent(), this.getStage());
        plot.show();
    }
    
    private long parseRandomSeedNumber() {
        try {
            return Long.parseLong(this.randomSeed.getText());
        } catch (Exception e) {
            return this.randomSeed.getText().hashCode();
        }
    }
}
