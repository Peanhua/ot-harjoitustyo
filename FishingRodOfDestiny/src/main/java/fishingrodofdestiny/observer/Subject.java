/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Subject {
    private final List<Observer> observers;
    
    public Subject() {
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
    
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.update());
    }
}
