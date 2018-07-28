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
     * Creates a copy of the given Plank.
     * @param plank The plank to copy
     * @throws InvalidParameterException Thrown if the given Plank is null
     */
    public Plank(Plank plank){
        if(plank == null) throw new InvalidParameterException("Plank must not be null");

        this.origin = plank.origin;
        setWidth(plank.width);
        setHeight(plank.height);
    }
    
    /**
     * Redefines the width of the plank.
     * @param newWidth The new width of the plank
     */
    public void setWidth(float newWidth){
        if(newWidth < Config.MIN_PLANK_THICKNESS){
            this.width = Config.MIN_PLANK_THICKNESS;
        } else {
            this.width = newWidth;
        }
    }

    /**
     * Redefines the height of the plank.
     * @param newHeight The new height of the plank
     */
    public void setHeight(float newHeight){
        if(newHeight < Config.MIN_PLANK_THICKNESS){
            this.height = Config.MIN_PLANK_THICKNESS;
        } else {
            this.height = newHeight;
        }
    }

    /**
     * Redefines the position of the plank.
     * @param newOrigin The new position of the plank
     */
    public void setOrigin(ShelfPoint newOrigin){
        if(newOrigin == null) throw new InvalidParameterException("The new origin must not be null");
        
        this.origin = newOrigin;
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
