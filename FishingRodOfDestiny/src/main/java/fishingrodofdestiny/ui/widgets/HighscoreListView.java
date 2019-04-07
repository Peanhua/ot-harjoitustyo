/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.HighscoreList;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author joyr
 */
public class HighscoreListView extends Widget {
    private final HighscoreList highscoreList;
    private final List<Text> names;
    private final List<Text> points;
    private final List<Text> timestamps;
    
    public HighscoreListView(HighscoreList highscoreList) {
        this.highscoreList = highscoreList;
        this.names         = new ArrayList<>();
        this.points        = new ArrayList<>();
        this.timestamps    = new ArrayList<>();
    }

    @Override
    public Node createUserInterface() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);

        int row = 0;
        String[] headers = { "#", "Name", "Points", "Date/time" };
        for (int i = 0; i < headers.length; i++) {
            Text label = UserInterfaceFactory.createLargeText(headers[i]);
            label.setUnderline(true);
            GridPane.setConstraints(label, i, row);
            grid.getChildren().add(label);
        }
        row++;
        
        for (int i = 0; i < this.highscoreList.getMaximumNumberOfEntries(); i++) {
            Text pos = UserInterfaceFactory.createText("" + (i + 1));
            GridPane.setConstraints(pos, 0, row);
            
            Text nam = UserInterfaceFactory.createText("");
            GridPane.setConstraints(nam, 1, row);
            this.names.add(nam);

            Text pnt = UserInterfaceFactory.createText("");
            GridPane.setConstraints(pnt, 2, row);
            this.points.add(pnt);

            Text tst = UserInterfaceFactory.createText("");
            GridPane.setConstraints(tst, 3, row);
            this.timestamps.add(tst);
            
            grid.getChildren().addAll(pos, nam, pnt, tst);
            
            row++;
        }
        this.refresh();
        return grid;
    }

    @Override
    public void refresh() {
        for (int i = 0; i < this.highscoreList.getMaximumNumberOfEntries(); i++) {
            String nam = "";
            String pts = "";
            String tst = "";
            
            Highscore hs = this.highscoreList.get(i);
            if (hs != null) {
                nam = hs.getName();
                pts = "" + hs.getPoints();
                tst = hs.getEndTimestamp().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
            
            this.names.get(i).setText(nam);
            this.points.get(i).setText(pts);
            this.timestamps.get(i).setText(tst);
        }
    }
}
