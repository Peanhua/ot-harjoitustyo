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
package fishingrodofdestiny.ui;

import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.ui.screens.ScreenMainMenu;
import fishingrodofdestiny.ui.screens.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class FishingRodOfDestinyUi extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Fishing Rod Of Destiny");
        primaryStage.setResizable(false);
        
        Screen mainmenu = new ScreenMainMenu(null, primaryStage);
        mainmenu.show();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    
        SettingsCache.getInstance().save();
    }
}
