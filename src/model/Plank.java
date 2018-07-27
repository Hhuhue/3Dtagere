package model;

import java.security.InvalidParameterException;

/**
 * Class containing the dimentions and the position of a plank
 */
public class Plank{
    private float width;
    private float height;
    private ShelfPoint origin;

    /**
     * Intitializes a new instance of the Plank class.
     * @param width The width of the plank
     * @param height The height of the plank
     * @param origin The point that determines the position of the plank
     * @throws InvalidParameterException Thrown if the given origin is null
     */
    public Plank(float width, float height, ShelfPoint origin){
        if(origin == null) throw new InvalidParameterException("Origin must not be null");        
        this.origin = origin;
        setWidth(width);
        setHeight(height);
    }
    
    /**
     * Redefines the width of the plank.
     * @param width The new width
     */
    public void setWidth(float width){
        if(width < Config.MIN_PLANK_THICKNESS){
            this.width = Config.MIN_PLANK_THICKNESS;
        } else {
            this.width = width;
        }
    }

    /**
     * Redefines the height of the plank.
     * @param height The new height
     */
    public void setHeight(float height){
        if(height < Config.MIN_PLANK_THICKNESS){
            this.height = Config.MIN_PLANK_THICKNESS;
        } else {
            this.height = height;
        }
    }

    /**
     * Gives the position of the plank.
     * @return The position of the plank
     */
    public ShelfPoint getOrigin(){
        return this.origin;
    }

    /**
     * Gives the width of the plank.
     * @return The width of the plank
     */
    public float getWidth(){
        return this.width;
    }

    /**
     * Gives the height of the plank.
     * @return The height of the plank
     */
    public float getHeight(){
        return this.height;
    }
}
