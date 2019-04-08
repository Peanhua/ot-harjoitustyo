/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.highscores.Highscore;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author joyr
 */
public class FileHighscoreDao extends HighscoreDao {
    private final String filenameBase;
    
    public FileHighscoreDao(String filenameBase) {
        super();
        this.filenameBase = filenameBase;
    }
    
    private String getFilename(Highscore.Type type) {
        return this.filenameBase + "-" + type;
    }
    
    @Override
    protected void load(Highscore.Type type) {
        List<Highscore> list = this.getHighscores(type);
        list.clear();
        
        try {
            Scanner reader = new Scanner(new File(this.getFilename(type)));
            while (reader.hasNextLine()) {
                String name   = reader.nextLine();
                int    points = Integer.parseInt(reader.nextLine());
                LocalDateTime timestamp = LocalDateTime.parse(reader.nextLine());
                
                Highscore hs = this.createFromData(null, type, name, points, timestamp);
                if (hs != null) {
                    list.add(hs);
                }
            }
        } catch (Exception e) {
        }
    }
    

    private void save(Highscore.Type type) {
        List<Highscore> list = this.getHighscores(type);
        try {
            FileWriter writer = new FileWriter(new File(this.getFilename(type)));
            for (Highscore highscore : list) {
                writer.write(highscore.getName() + "\n");
                writer.write("" + highscore.getPoints() + "\n");
                writer.write("" + highscore.getEndTimestamp() + "\n");
            }
            writer.close();
        } catch (Exception e) {
        }
    }

    @Override
    public Highscore create(Highscore.Type type, Highscore highscore) {
        super.create(type, highscore);
        this.save(type);
        return highscore;
    }

    @Override
    public void delete(Highscore.Type type, Highscore highscore) {
        super.delete(type, highscore);
        this.save(type);
    }
}
