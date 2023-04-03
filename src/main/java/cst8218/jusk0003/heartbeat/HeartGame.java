/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.jusk0003.heartbeat;

import static cst8218.jusk0003.heartbeat.Heart.CHANGE_RATE;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * The class HeartGame contains the implementation to create a thread to run the
 * game, and continues when the web application may not be open. It will advance time by one
 * increment for each Heart object then sleep for a predetermined amount of time,
 * based off of the CHANGE_RATE constant set in the Heart class.
 * 
 * @author Daniel Juskevicius
 */
@Singleton
@Startup
public class HeartGame {

    @EJB
    private cst8218.jusk0003.heartbeat.HeartFacade heartFacade;
    
    private List<Heart> hearts;

    /**
     * Creates the thread for the application that runs indefinitely. It advances
     * time by one increment for each Heart in the database engine and then sleeps
     * for the amount of time described by CHANGE_RATE.
     */
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                // the game runs indefinitely
                while (true) {
                    // update all the hearts and save the changes to the database
                    hearts = heartFacade.findAll();
                    for (Heart heart : hearts) {
                        heart.advanceOneTimeIncrement();
                        heartFacade.edit(heart);
                    }
                    // sleep while waiting to process the next frame of the animation
                    try {
                        // wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long)(1.0/CHANGE_RATE*1000));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
