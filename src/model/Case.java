package model;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Class containing the dimentions, the position and the plank of a case
 */
public class Case{
    
    public enum Side{
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        SECOND_LEFT,
        SECOND_RIGHT,
        SECOND_BOTTOM
    }

    private HashMap<Side, Plank> sides;
    private float width;
    private float height;
    private ShelfPoint origin;

    /**
     * Initializes an instance of the Case class
     * @param width The with of the case
     * @param height The height of the case
     * @param origin The position of th case
     * @throws InvalidParameterException Thrown when the origin is null
     */
    public Case(float width, float height, ShelfPoint origin){
        if(origin == null) throw new InvalidParameterException("The case origin must not be null");

        this.width = width;
        this.height = height;
        this.origin = origin;

        ShelfPoint leftOrigin = new ShelfPoint(origin.x, origin.y + Config.PLANK_THICKNESS);
        ShelfPoint rightOrigin = new ShelfPoint(origin.x + width - Config.PLANK_THICKNESS, origin.y + Config.PLANK_THICKNESS);
        ShelfPoint bottomOrigin = new ShelfPoint(origin.x + Config.PLANK_THICKNESS, origin.y + height - Config.PLANK_THICKNESS);

        Plank topPlank = new Plank(width, Config.PLANK_THICKNESS, origin);
        Plank leftPlank = new Plank(Config.PLANK_THICKNESS, height - Config.PLANK_THICKNESS, leftOrigin);
        Plank rightPlank = new Plank(Config.PLANK_THICKNESS, height -  Config.PLANK_THICKNESS, rightOrigin);
        Plank bottomPlank = new Plank(width - 2 * Config.PLANK_THICKNESS, Config.PLANK_THICKNESS, bottomOrigin);

        sides = new HashMap<Case.Side,Plank>();
        sides.put(Side.TOP, topPlank);
        sides.put(Side.LEFT, leftPlank);
        sides.put(Side.RIGHT, rightPlank);
        sides.put(Side.BOTTOM, bottomPlank);
        sides.put(Side.SECOND_LEFT, null);
        sides.put(Side.SECOND_RIGHT, null);
        sides.put(Side.SECOND_BOTTOM, null);
    }

    /**
     * Gives the width of the case
     * @return The width of the case
     */
    public float getWidth(){
        return this.width;
    }

    /**
     * Gives the height of the case
     * @return The height of the case
     */
    public float getHeight(){
        return this.height;
    }

    /**
     * Gives the position of the case
     * @return The position of the case
     */
    public ShelfPoint getOrigin(){
        return this.origin;
    }

    /**
     * Gives the plank at the given case side
     * @return The plank at the given case side
     */
    public Plank getPlank(Side side){
        return this.sides.get(side);
    }

    /**
     * Adds or removes a secondary plank at the given side
     * @param side The at which a plank is added or removed
     * @param enabled Whether the plank must be added or removed
     * @throws InvalidParameterException Thrown when the chosen side is TOP
     */
    public void toggleSecondPlank(Side side, boolean enabled){
        if(side == Side.TOP) throw new InvalidParameterException("Can't add secondary plank at the top");

        if(!enabled){
            removeSecondaryPlank(side);
        } else {
            createSecondaryPlank(side);
        }
    }
    
    /**
    * Sets the width of the Case and changes its plank accordingly.
    *@param newWidth The new Case width
    *@param direction The direction in which the Case will grow or shrink
    */
    public void setWidth(float newWidth, Side direction){
        if(direction == Side.TOP || direction == Side.BOTTOM){
            throw new InvalidParameterException("The direction must be horizontal");
        }
    }
    
    /**
    * Sets the height of the Case and changes its plank accordingly.
    *@param newHeight The new Case height
    *@param direction The direction in which the Case will grow or shrink
    */
    public void setHeight(float newHeight, Side direction){
        
    }

    /**
     * Revmoves the secondary plank at the given side
     * @param side The side where a plank must be removed
     */
    private void removeSecondaryPlank(Side side){
        switch(side){
            case LEFT:
                sides.put(Side.SECOND_LEFT, null);
                break;

            case RIGHT:
                sides.put(Side.SECOND_RIGHT, null);
                break;

            case BOTTOM:
                sides.put(Side.SECOND_BOTTOM, null);
                break;

            default:
                break;
        }        
    }

    /**
     * Creates a secondary plank at the given side
     * @param side The side where a plank must be created
     */
    private void createSecondaryPlank(Side side){
        Plank plank = sides.get(side);
        Plank secondaryPlank = new Plank(plank);

        switch(side){
            case LEFT:
                secondaryPlank.setWidth(Config.PLANK_THICKNESS / 2);
                plank.getOrigin().add(Config.PLANK_THICKNESS / 2, 0);
                plank.setWidth(Config.PLANK_THICKNESS / 2);
                sides.put(Side.SECOND_LEFT, secondaryPlank);
                break;

            case RIGHT:
                secondaryPlank.setWidth(Config.PLANK_THICKNESS / 2);
                secondaryPlank.getOrigin().add(Config.PLANK_THICKNESS / 2, 0);
                plank.setWidth(Config.PLANK_THICKNESS / 2);
                sides.put(Side.SECOND_RIGHT, secondaryPlank);
                break;

            case BOTTOM:
                secondaryPlank.setHeight(Config.PLANK_THICKNESS / 2);
                secondaryPlank.getOrigin().add(0, Config.PLANK_THICKNESS / 2);
                plank.setHeight(Config.PLANK_THICKNESS / 2);
                sides.put(Side.SECOND_BOTTOM, secondaryPlank);
                break;   

            default:
                break;
        }        
    }
}