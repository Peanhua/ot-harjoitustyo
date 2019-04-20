/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.statistics.Statistics;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author joyr
 */
public class FileStatisticsDao implements StatisticsDao {
    private final String filename;

    public FileStatisticsDao(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void load(Statistics to) {
        try {
            Scanner reader = new Scanner(new File(this.filename));
            String line;
            
            line = reader.nextLine();
            to.setGamesPlayed(Long.parseLong(line));
            
            line = reader.nextLine();
            to.setGamesCompleted(Long.parseLong(line));
            
            line = reader.nextLine();
            to.setGoldCoinsCollected(Long.parseLong(line));
            
            line = reader.nextLine();
            to.setEnemiesKilled(Long.parseLong(line));
        } catch (Exception e) {
        }

    }

    @Override
    public void save(Statistics from) {
        try {
            FileWriter writer = new FileWriter(new File(this.filename));
            writer.write("" + from.getGamesPlayed()        + "\n");
            writer.write("" + from.getGamesCompleted()     + "\n");
            writer.write("" + from.getGoldCoinsCollected() + "\n");
            writer.write("" + from.getEnemiesKilled()      + "\n");
            writer.close();
        } catch (Exception e) {
        }
    }
}
