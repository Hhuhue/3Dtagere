package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;

import org.junit.Test;
import model.Case;
import model.Config;
import model.Plank;
import model.ShelfPoint;
import model.Case.Side;
import test.TestClass;

public class CaseTests extends TestClass{

    //#region Getters
    @Test
    public void Case_getWidth_Should_ReturnCaseWidth(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);
        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testCase.getWidth();
        
        //Assert
        assertEquals(WIDTH, result, DELTA);
    }
    
    @Test
    public void Case_getHeight_Should_ReturnCaseHeight(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);
        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testCase.getHeight();
        
        //Assert
        assertEquals(HEIGHT, result, DELTA);
    }
    
    @Test
    public void Case_getOrigin_Should_ReturnCaseOrigin(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);
        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);
        
        //Action
        ShelfPoint result = testCase.getOrigin();
        
        //Assert
        assertEquals(ORIGIN.x, result.x, DELTA);
        assertEquals(ORIGIN.y, result.y, DELTA);
    }
    
    @Test
    public void Case_getPlank_Should_ReturnPlankAtGivenSide(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);

        ShelfPoint expectedTopOrigin = new ShelfPoint(ORIGIN);
        ShelfPoint expectedLeftOrigin = ShelfPoint.sum(ORIGIN, 0, Config.PLANK_THICKNESS);
        ShelfPoint expectedRightOrigin = ShelfPoint.sum(ORIGIN,WIDTH - Config.PLANK_THICKNESS, Config.PLANK_THICKNESS);
        ShelfPoint expectedBottomOrigin = ShelfPoint.sum(ORIGIN, Config.PLANK_THICKNESS, HEIGHT - Config.PLANK_THICKNESS);

        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);
        
        //Action
        Plank topPlank = testCase.getPlank(Side.TOP);
        Plank leftPlank = testCase.getPlank(Side.LEFT);
        Plank rightPlank = testCase.getPlank(Side.RIGHT);
        Plank bottomPlank = testCase.getPlank(Side.BOTTOM);
        
        //Assert
        assertTrue(expectedTopOrigin.equals(topPlank.getOrigin()));
        assertTrue(expectedLeftOrigin.equals(leftPlank.getOrigin()));
        assertTrue(expectedRightOrigin.equals(rightPlank.getOrigin()));
        assertTrue(expectedBottomOrigin.equals(bottomPlank.getOrigin()));
    }
    //#endregion

    //#region Constructors
    @Test
    public void Case_Constructor_Should_ThrowException_If_GivenOriginIsNull(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = null;

        boolean exceptionThrown = false;
        
        //Action
        try{
            new Case(WIDTH, HEIGHT, ORIGIN);
        } catch (InvalidParameterException e){
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void Case_Constructor_Should_SetDimentionsAndOrigin(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);
        
        //Action
        Case result = new Case(WIDTH, HEIGHT, ORIGIN);
        
        //Assert
        assertEquals(WIDTH, result.getWidth(), DELTA);
        assertEquals(HEIGHT, result.getHeight(), DELTA);
        assertTrue(result.getOrigin().equals(ORIGIN));
    }

    @Test
    public void Case_Constructor_Should_CreatePlanksAccordingToDimentions(){
        //Arrange
        final float WIDTH = 20.0f;
        final float HEIGHT = 30.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(10, 15);

        ShelfPoint expectedTopOrigin = new ShelfPoint(ORIGIN);
        ShelfPoint expectedLeftOrigin = ShelfPoint.sum(ORIGIN, 0, Config.PLANK_THICKNESS);
        ShelfPoint expectedRightOrigin = ShelfPoint.sum(ORIGIN,WIDTH - Config.PLANK_THICKNESS, Config.PLANK_THICKNESS);
        ShelfPoint expectedBottomOrigin = ShelfPoint.sum(ORIGIN, Config.PLANK_THICKNESS, HEIGHT - Config.PLANK_THICKNESS);
        
        //Action
        Case result = new Case(WIDTH, HEIGHT, ORIGIN);
        Plank topPlank = result.getPlank(Side.TOP);
        Plank leftPlank = result.getPlank(Side.LEFT);
        Plank rightPlank = result.getPlank(Side.RIGHT);
        Plank bottomPlank = result.getPlank(Side.BOTTOM);
        
        //Assert
        assertTrue(expectedTopOrigin.equals(topPlank.getOrigin()));
        assertTrue(expectedLeftOrigin.equals(leftPlank.getOrigin()));
        assertTrue(expectedRightOrigin.equals(rightPlank.getOrigin()));
        assertTrue(expectedBottomOrigin.equals(bottomPlank.getOrigin()));
        
        assertEquals(WIDTH, topPlank.getWidth(), DELTA);
        assertEquals(Config.PLANK_THICKNESS, topPlank.getHeight(), DELTA);
        assertEquals(Config.PLANK_THICKNESS, leftPlank.getWidth(), DELTA);
        assertEquals(HEIGHT - Config.PLANK_THICKNESS, leftPlank.getHeight(), DELTA);
        assertEquals(Config.PLANK_THICKNESS, rightPlank.getWidth(), DELTA);
        assertEquals(HEIGHT - Config.PLANK_THICKNESS, rightPlank.getHeight(), DELTA);
        assertEquals(WIDTH - 2 * Config.PLANK_THICKNESS, bottomPlank.getWidth(), DELTA);
        assertEquals(Config.PLANK_THICKNESS, bottomPlank.getHeight(), DELTA);
    }
    //#endregion

    //#region Setters
    @Test
    public void Case_setWidth_Should_ThrowException_If_DirectionIsNotHorizontal(){
        //Arrange
        final float WIDTH = 25.0f;
        final float HEIGHT = 25.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(0, 0);
        boolean exceptionThrown = false;

        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);

        //Action
        try{
            testCase.setWidth(30.0f, Side.TOP);
        } catch(InvalidParameterException e){
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void Case_setWidth_Should_SetWidthToMinimumValue_If_GivenValueIsTooSmall(){
        //Arrange
        final float WIDTH = 25.0f;
        final float HEIGHT = 25.0f;
        final ShelfPoint ORIGIN = new ShelfPoint(0, 0);
        final float NEW_WIDTH = 30.0f;

        Case testCase = new Case(WIDTH, HEIGHT, ORIGIN);

        //Action
            testCase.setWidth(NEW_WIDTH, Side.TOP);

        //Assert
        assertEquals(NEW_WIDTH, testCase.getWidth(), DELTA);
    }
    //#endregion
}