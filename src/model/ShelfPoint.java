package model;

import java.security.InvalidParameterException;

/**
 * Point class with float coordinates and utilities
 */
public class ShelfPoint{
    public float x;
    public float y;

    /**
     * Initializes a new instance of the ShelfPoint class.
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public ShelfPoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a copy of the given ShelfPoint.
     * @param p The point to copy
     */
    public ShelfPoint(ShelfPoint p){
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Adds the coordinates of from an other point.
     * @param p The coordinates to add
     * @throws InvalidParameterException Thrown when the point to add is null
     */
    public void add(ShelfPoint p){
        if(p == null) throw new InvalidParameterException("The point to add must not be null");

        this.x += p.x;
        this.y += p.y;
    }

    /**
     * Tells whether or not the point has the same coordinates than an another point.
     * A certain error margin is allowed.
     * @param p The point to compare
     * @throws InvalidParameterException Thrown if the point to compare is null
     */
    public boolean equals(ShelfPoint p){
        if(p == null) throw new InvalidParameterException("The point to compare must not be null");

        float xDelta = absoluteValue(this.x - p.x);
        float yDelta = absoluteValue(this.y - p.y);

        return xDelta <= Config.ERROR_MARGIN && yDelta <= Config.ERROR_MARGIN;
    }

    /**
     * Calculates the absolute value of a float.
     * @return The absolute value of the given float
     */
    private float absoluteValue(float value){
        if(value < 0){
            value = value * -1;
        }

        return value;
    }
}