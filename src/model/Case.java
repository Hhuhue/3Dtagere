package model;

import java.util.HashMap;

public class Case{
    public enum Side{
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }

    private HashMap<Side, Plank> sides;
    private float width;
    private float height;
    private ShelfPoint origin;

    public Case(float width, float height, ShelfPoint origin){
        this.width = width;
        this.height = height;
        this.origin = origin;

        ShelfPoint leftOrigin = new ShelfPoint(origin.x, origin.y + Config.PLANK_THICKNESS);
        ShelfPoint rightOrigin = new ShelfPoint(origin.x + width - Config.PLANK_THICKNESS, origin.y + Config.PLANK_THICKNESS);
        ShelfPoint bottomOrigin = new ShelfPoint(origin.x + Config.PLANK_THICKNESS, origin.y + height - Config.PLANK_THICKNESS);

        Plank topPlank = new Plank(width, Config.PLANK_THICKNESS, origin);
        Plank leftPlank = new Plank(Config.PLANK_THICKNESS, height - Config.PLANK_THICKNESS, leftOrigin);
        Plank rightPlank = new Plank(Config.PLANK_THICKNESS,height -  Config.PLANK_THICKNESS, rightOrigin);
        Plank bottomPlank = new Plank(width - 2 * Config.PLANK_THICKNESS, Config.PLANK_THICKNESS, bottomOrigin);

        sides = new HashMap<Case.Side,Plank>();
        sides.put(Side.TOP, topPlank);
        sides.put(Side.LEFT, leftPlank);
        sides.put(Side.RIGHT, rightPlank);
        sides.put(Side.BOTTOM, bottomPlank);
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }

    public ShelfPoint getOrigin(){
        return this.origin;
    }

    public Plank getPlank(Side side){
        return this.sides.get(side);
    }
}