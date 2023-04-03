/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.jusk0003.heartbeat;

import java.io.Serializable;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The entity class of the application. It resides within the business layer of
 * the application, and stores information in or retrieves information from
 * the database, in the form of Heart entities. Every heart has an ID, a size,
 * x and y values, a contractedSize, and a beatCount. As a result, all heart entities
 * are persistent and persist outside of the runtime of the application in the 
 * database.
 * 
 * @author Daniel Juskevicius
 */
@Entity
@XmlRootElement
public class Heart implements Serializable {
    
    /**
     * GIT TEST HERE
     */
    
    static final int INITIAL_SIZE = 25;
    static final int CHANGE_RATE = 1;
    static final int CONTRACTION_DECREMENT = 1;
    static final int BEAT_INCREMENT = 5;
    static final int SHRINK_DECREMENT = 5;
    static final int BEATS_TO_EXHAUSTION = 100;
    static final int STOP_SIZE = 5;
    static final int SIZE_MAX = 500;
    static final int X_MAX = 500;
    static final int Y_MAX = 500;
    static final int CONTRACTED_MAX = 100;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    @Min(value = 0)
    @Max(value = SIZE_MAX)
    private Integer size;
    @Min(value = 0)
    @Max(value = X_MAX)
    private Integer x;
    @Min(value = 0)
    @Max(value = Y_MAX)
    private Integer y;
    @Min(value = 1)
    @Max(value = CONTRACTED_MAX)
    private Integer contractedSize;
    @Min(value = 0)
    @Max(value = BEATS_TO_EXHAUSTION)
    private Integer beatCount;
    
    /**
     * Default constructor to make sure that values that cannot be null are 
     * initialized if no values are entered for them.
     */
    public Heart() {
        this.size = INITIAL_SIZE;
        this.contractedSize = INITIAL_SIZE;
        this.beatCount = 0;
    }
    
    /**
     * Gets the ID of the Heart.
     * 
     * @return The ID of the Heart.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the Heart.
     * 
     * @param id The value to change ID to.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the size of the Heart.
     * 
     * @return The size of the Heart.
     */
    public Integer getSize() {
        return size;
    }
    
    /**
     * Sets the size of the Heart.
     * 
     * @param size The value to change size to.
     */
    public void setSize(Integer size) {
        this.size = size;
    }
    
    /**
     * Gets the current X coordinate of the Heart.
     * 
     * @return The current X coordinate of the Heart.
     */
    public Integer getX() {
        return x;
    }
    
    /**
     * Sets the X coordinate of the Heart.
     * 
     * @param x The x coordinate to change to.
     */
    public void setX(Integer x) {
        this.x = x;
    }
    
    /**
     * Gets the Y coordinate of the Heart.
     * 
     * @return The current Y coordinate of the Heart.
     */
    public Integer getY() {
        return y;
    }
    
    /**
     * Sets the Y coordinate of the Heart.
     * 
     * @param y The Y coordinate to change to.
     */
    public void setY(Integer y) {
        this.y = y;
    }
    
    /**
     * Gets the current contractedSize of the Heart.
     * 
     * @return The current contractedSize of the Heart.
     */
    public Integer getContractedSize() {
        return contractedSize;
    }
    
    /**
     * Sets the contractedSize of the Heart.
     * 
     * @param contractedSize The contractedSize to change to.
     */
    public void setContractedSize(Integer contractedSize) {
        this.contractedSize = contractedSize;
    }
    
    /**
     * Gets the current beatCount of the Heart.
     * 
     * @return The current beatCount of the Heart.
     */
    public Integer getBeatCount() {
        return beatCount;
    }
    
    /**
     * Sets the beatCount of the Heart.
     * 
     * @param beatCount the beatCount to change to. 
     */
    public void setBeatCount(Integer beatCount) {
        this.beatCount = beatCount;
    }
    
    /**
     * Updates an existing Heart object with the values from a new Heart object,
     * either via
     * 
     * @param oldHeart 
     */
    public void updates(Heart oldHeart) {
        if (this.size != null) oldHeart.setSize(this.size);
        if (this.x != null) oldHeart.setX(this.x);
        if (this.y != null) oldHeart.setY(this.y);
        if (this.contractedSize != null) oldHeart.setContractedSize(this.contractedSize);
        if (this.beatCount != null) oldHeart.setBeatCount(this.beatCount); 
    }
    
    /**
     * Updates the properties to simulate the passing of one unit of time.
     */
    public void advanceOneTimeIncrement() {
        if (stillBeating()) {               // if still beating
            if (finishedCurrentBeat()) {        // if size has decreased to contracted size
                newBeat();                          // suddenly increase size to begin new beat
                setBeatCount(getBeatCount() + 1);   // increment beat count
                if (exhausted()) {                  // if beat count has reached exhaused level
                    shrink();                           // decrease contracted size
                    setBeatCount(0);                    // now not exhausted - reset beat count
                }
            } else {                        // else
                continueContracting();          // continue the contracting phase of a beat
            }
        }
    }
    
    /**
     * Returns true if the Heart has not yet stopped and is still beating.
     * @return True if the Heart has not yet stopped and is still beating; otherwise False.
     */
    private boolean stillBeating() {
        return getContractedSize() > STOP_SIZE; 
    }
    
    /**
     * Returns true if the size of the heart is less than or equal to the contractedSize of that Heart.
     * @return true or false.
     */
    private boolean finishedCurrentBeat() {
        return getSize() <= getContractedSize();
    }
    
    /**
     * Increases the size of a Heart by BEAT_INCREMENT.
     */
    private void newBeat() {
        setSize(getSize() + BEAT_INCREMENT);
    }
    
    /**
     * Returns true if the beatCount of the Heart is greater than or equal to BEATS_TO_EXHAUSTION.
     * @return true or false.
     */
    private boolean exhausted() {
        return getBeatCount() >= BEATS_TO_EXHAUSTION;
    }
    
    /**
     * Shrinks a Heart by decreasing it's contractedSize by SHRINK_DECREMENT.
     */
    private void shrink() {
        setContractedSize(getContractedSize() - SHRINK_DECREMENT);
    }
    
    /**
     * Causes a Heart to continue contracting by decreasing it's size by CONTRACTION_DECREMENT.
     */
    private void continueContracting() {
        setSize(getSize() - CONTRACTION_DECREMENT);
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Heart)) {
            return false;
        }
        Heart other = (Heart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.jusk0003.heartbeat.Heart[ id=" + id + " ]";
    }
    
}
