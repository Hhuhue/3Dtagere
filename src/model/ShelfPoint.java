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
     * Adds the coordinates from an other point.
     * @param p The coordinates to add
     * @throws InvalidParameterException Thrown when the point to add is null
     */
    public void add(ShelfPoint p){
        if(p == null) throw new InvalidParameterException("The point to add must not be null");

        this.x += p.x;
        this.y += p.y;
    }

    /**
     * Adds the given coordinates.
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public void add(float x, float y){
        this.x += x;
        this.y += y;
    }

    /**
     * Sums the coordinates from two points.
     * @param p1 The first point to sum
     * @throws InvalidParameterException Thrown when one or both points to sum are null
     */
    public static ShelfPoint sum(ShelfPoint p1, ShelfPoint p2){
        if(p1 == null || p2 == null) throw new InvalidParameterException("The points to sum must not be null");

        return new ShelfPoint(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Adds coordinates to the given point.
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public static ShelfPoint sum(ShelfPoint p, float x, float y){
        if(p == null) throw new InvalidParameterException("The point to add must not be null");
         
        return new ShelfPoint(p.x + x,p.y +  y);
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