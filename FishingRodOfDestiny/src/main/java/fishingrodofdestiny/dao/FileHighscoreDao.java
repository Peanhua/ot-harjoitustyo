/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.ScoreBasedHighscore;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author joyr
 */
public class FileHighscoreDao implements HighscoreDao {
    
    private HashMap<Highscore.Type, List<Highscore>> highscores;
    private String filenameBase;
    
    public FileHighscoreDao(String filenameBase) {
        this.highscores   = new HashMap<>();
        this.filenameBase = filenameBase;
        for (Highscore.Type type : Highscore.Type.values()) {
            this.load(type);
        }
    }
    
    private String getFilename(Highscore.Type type) {
        return this.filenameBase + "-" + type;
    }
    
    private List<Highscore> getHighscores(Highscore.Type type) {
        List<Highscore> list = this.highscores.get(type);
        if (list == null) {
            list = new ArrayList<>();
            this.highscores.put(type, list);
        }
        return list;
    }
    
    private final void load(Highscore.Type type) {
        List<Highscore> list = this.getHighscores(type);
        list.clear();
        
        try {
            Scanner reader = new Scanner(new File(this.getFilename(type)));
            while (reader.hasNextLine()) {
                String name   = reader.nextLine();
                int    points = Integer.parseInt(reader.nextLine());
                LocalDateTime timestamp = LocalDateTime.parse(reader.nextLine());
                
                Highscore hs = null;
                
                switch (type) {
                    case SCORE:
                        hs = new ScoreBasedHighscore(name, points, timestamp);
                        break;
                }
                
                if (hs != null) {
                    list.add(hs);
                }
            }
        } catch (Exception e) {
        }
    }
    
    private final void save(Highscore.Type type) {
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
        List<Highscore> list = this.getHighscores(type);
        list.add(highscore);
        Collections.sort(list);
        this.save(type);
        return highscore;
    }

    @Override
    public List<Highscore> getByType(Highscore.Type type) {
        return this.getHighscores(type);
    }

    @Override
    public void delete(Highscore.Type type, Highscore highscore) {
        List<Highscore> list = this.getHighscores(type);
        list.remove(highscore);
        this.save(type);
    }
}
