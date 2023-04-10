/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.jusk0003.tests;

import cst8218.jusk0003.heartbeat.Heart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Browser
 */
public class TestHeart {
    
    public TestHeart() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    // TODO add test methods here.
    @Test
    public void testHeartInitialization() {
        Heart heart = new Heart();
        
        assertEquals(heart.getSize(),heart.INITIAL_SIZE);
        assertEquals(heart.getContractedSize(),heart.INITIAL_SIZE);
        assertEquals(heart.getBeatCount(),0);
    }
    
    @Test
    public void testStillBeating() {
        Heart heart = new Heart();
        
        heart.setContractedSize(50);
        assertEquals(heart.stillBeating(), true);
        
        heart.setContractedSize(0);
        assertEquals(heart.stillBeating(), false);
    }
    
    @Test
    public void testFinishedCurrentBeat() {
        Heart heart = new Heart();
        
        heart.setSize(50);
        heart.setContractedSize(45);
        assertEquals(heart.finishedCurrentBeat(), false);
        
        heart.setSize(30);
        heart.setContractedSize(50);
        assertEquals(heart.finishedCurrentBeat(), true);
    }
    
    @Test
    public void testNewBeat() {
        Heart heart = new Heart();
        
        int oldSize = heart.getSize();
        heart.newBeat();
        int newSize = heart.getSize();
        assertEquals(newSize, oldSize+heart.BEAT_INCREMENT);
    }
    
    @Test
    public void testExhuasted() {
        Heart heart = new Heart();
        
        heart.setBeatCount(heart.BEATS_TO_EXHAUSTION + 1);
        assertEquals(heart.exhausted(), true);
        
        heart.setBeatCount(heart.BEATS_TO_EXHAUSTION - 1);
        assertEquals(heart.exhausted(), false);
    }
    
    @Test
    public void testShrink() {
        Heart heart = new Heart();
        
        int oldContractedSize = heart.getContractedSize();
        heart.shrink();
        int newContractedSize = heart.getContractedSize();
        
        assertEquals(newContractedSize, oldContractedSize - heart.SHRINK_DECREMENT);
    }
    
    @Test
    public void testContinueContracting() {
        Heart heart = new Heart();
        
        int oldSize = heart.getSize();
        heart.continueContracting();
        int newSize = heart.getSize();
        
        assertEquals(newSize, oldSize - heart.CONTRACTION_DECREMENT);
    }
}
