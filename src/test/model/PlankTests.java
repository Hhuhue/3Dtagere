package test.model;

import model.Config;
import model.Plank;
import java.awt.Point;
import java.security.InvalidParameterException;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlankTests{
    private final float DELTA = 0.0001f;

    //#region Getters    
    @Test
    public void PlankTests_getWidthShouldReturnPlankWidth(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testPlank.getWidth();

        //Assert
        assertEquals(result, WIDTH, DELTA);
    }

    @Test
    public void PlankTests_getHeightShouldReturnPlankHeight(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testPlank.getHeight();

        //Assert
        assertEquals(result, HEIGHT, DELTA);
    }

    @Test
    public void PlankTests_getOriginShouldReturnPlankOrigin(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        Point resultOrigin = testPlank.getOrigin();

        //Assert
        assertEquals(resultOrigin.x, ORIGIN.x);
        assertEquals(resultOrigin.y, ORIGIN.y);
    }
    //#endregion

    //#region Setters
    @Test
    public void PlankTests_setWidthShouldChangePlankWidthValue(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float NEW_WIDTH = 15;

        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setWidth(NEW_WIDTH);

        //Assert
        assertEquals(testPlank.getWidth(), NEW_WIDTH, DELTA);
    }

    @Test
    public void PlankTests_setWidthShouldChangePlankWidthToMinimumValueIfGivenValueIsTooSmall(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float SMALL_WIDTH = Config.MIN_PLANK_THICKNESS - 0.5f;

        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setWidth(SMALL_WIDTH);

        //Assert
        assertEquals(testPlank.getWidth(), Config.MIN_PLANK_THICKNESS, DELTA);
    }
    @Test
    public void PlankTests_setHeightShouldChangePlankHeightValue(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float NEW_HEIGHT = 15;

        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setHeight(NEW_HEIGHT);

        //Assert
        assertEquals(testPlank.getHeight(), NEW_HEIGHT, DELTA);
    }

    @Test
    public void PlankTests_setHeightShouldChangePlankHeightToMinimumValueIfGivenValueIsTooSmall(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float SMALL_HEIGHT = Config.MIN_PLANK_THICKNESS - 0.5f;

        final Point ORIGIN = new Point(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setHeight(SMALL_HEIGHT);

        //Assert
        assertEquals(testPlank.getHeight(), Config.MIN_PLANK_THICKNESS, DELTA);
    }
    //#endregion

    //#region Constructors
    @Test
    public void PlankTests_PlankConstructorShouldInitialzePlankWithGivenParameters(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final Point ORIGIN = new Point(40,30);

        //Action
        Plank result = new Plank(WIDTH, HEIGHT, ORIGIN);
        Point resultOrigin = result.getOrigin();

        //Assert
        assertEquals(result.getWidth(), WIDTH, DELTA);
        assertEquals(result.getHeight(), HEIGHT, DELTA);
        assertEquals(resultOrigin.x, ORIGIN.x);
        assertEquals(resultOrigin.y, ORIGIN.y);
    }

    @Test
    public void PlankTests_PlankConstructorShouldThrowExceptionIfOriginIsNull(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final Point ORIGIN = null;

        boolean exceptionThrown = false;

        //Action
        try{
            new Plank(WIDTH, HEIGHT, ORIGIN);
        } catch(InvalidParameterException e) {
        } finally {
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void PlankTests_PlankConstructorShouldSetDimentionsToMinimumIfGivenDimentionsAreTooSmall(){
        //Arrange
        final float WIDTH = Config.MIN_PLANK_THICKNESS - 0.001f;
        final float HEIGHT = Config.MIN_PLANK_THICKNESS - 1000f;
        final Point ORIGIN = new Point(40,30);

        //Action
        Plank result = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Assert
        assertEquals(result.getWidth(), Config.MIN_PLANK_THICKNESS, DELTA);
        assertEquals(result.getHeight(), Config.MIN_PLANK_THICKNESS, DELTA);
    }
    //#endregion
}