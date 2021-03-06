package test.model;

import model.Config;
import model.Plank;
import model.ShelfPoint;
import test.TestClass;
import java.security.InvalidParameterException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlankTests extends TestClass{

    //#region Getters    
    @Test
    public void Plank_getWidth_Should_ReturnPlankWidth(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testPlank.getWidth();

        //Assert
        assertEquals(result, WIDTH, DELTA);
    }

    @Test
    public void Plank_getHeight_Should_ReturnPlankHeight(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        float result = testPlank.getHeight();

        //Assert
        assertEquals(HEIGHT, result, DELTA);
    }

    @Test
    public void Plank_getOrigin_Should_ReturnPlankOrigin(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        ShelfPoint resultOrigin = testPlank.getOrigin();

        //Assert
        assertTrue(resultOrigin.equals(ORIGIN));
    }
    //#endregion

    //#region Setters
    @Test
    public void Plank_setWidth_Should_ChangePlankWidthToMinimumValue_If_GivenValueIsTooSmall(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float SMALL_WIDTH = Config.MIN_PLANK_THICKNESS - 0.5f;

        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setWidth(SMALL_WIDTH);
        float result = testPlank.getWidth();

        //Assert
        assertEquals(Config.MIN_PLANK_THICKNESS, result, DELTA);
    }

    @Test
    public void Plank_setWidth_Should_ChangePlankWidthValue(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float NEW_WIDTH = 15;

        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setWidth(NEW_WIDTH);
        float result = testPlank.getWidth();

        //Assert
        assertEquals(NEW_WIDTH, result, DELTA);
    }

    @Test
    public void Plank_setHeight_Should_ChangePlankHeightToMinimumValue_If_GivenValueIsTooSmall(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float SMALL_HEIGHT = Config.MIN_PLANK_THICKNESS - 0.5f;

        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setHeight(SMALL_HEIGHT);
        float result = testPlank.getHeight();

        //Assert
        assertEquals(Config.MIN_PLANK_THICKNESS, result, DELTA);
    }

    @Test
    public void Plank_setHeight_Should_ChangePlankHeightValue(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final float NEW_HEIGHT = 15;

        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setHeight(NEW_HEIGHT);
        float result = testPlank.getHeight();

        //Assert
        assertEquals(NEW_HEIGHT, result, DELTA);
    }

    @Test
    public void Plank_setOrigin_Should_ThrowException_If_GivenOriginIsNull(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        boolean exceptionThrown = false;

        //Action
        try{
            testPlank.setOrigin(null);
        } catch(InvalidParameterException e) {
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void Plank_setOrigin_Should_ChangePlankOriginValue(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        final ShelfPoint NEW_ORIGIN = new ShelfPoint(20, 10);

        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        testPlank.setOrigin(NEW_ORIGIN);
        ShelfPoint result = testPlank.getOrigin();

        //Assert
        assertTrue(result.equals(NEW_ORIGIN));
    }
    //#endregion

    //#region Constructors
    @Test
    public void Plank_Constructor_Should_ThrowException_If_GivenOriginIsNull(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = null;

        boolean exceptionThrown = false;

        //Action
        try{
            new Plank(WIDTH, HEIGHT, ORIGIN);
        } catch(InvalidParameterException e) {
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void Plank_Constructor_Should_SetDimentionsToMinimum_If_GivenDimentionsAreTooSmall(){
        //Arrange
        final float WIDTH = Config.MIN_PLANK_THICKNESS - 0.001f;
        final float HEIGHT = Config.MIN_PLANK_THICKNESS - 1000f;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);

        //Action
        Plank result = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Assert
        assertEquals(Config.MIN_PLANK_THICKNESS, DELTA, result.getWidth());
        assertEquals(Config.MIN_PLANK_THICKNESS, result.getHeight(), DELTA);
    }

    @Test
    public void Plank_Constructor_Should_SetDimentionsAndOrigin(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);

        //Action
        Plank result = new Plank(WIDTH, HEIGHT, ORIGIN);
        ShelfPoint resultOrigin = result.getOrigin();

        //Assert
        assertEquals(WIDTH, result.getWidth(), DELTA);
        assertEquals(HEIGHT, result.getHeight(), DELTA);
        assertTrue(resultOrigin.equals(ORIGIN));
    }
    
    @Test
    public void Plank_CopyConstructor_Should_ThrowException_If_PlankToCopyIsNull(){
        //Arrange
        boolean exceptionThrown = false;

        //Action
        try{
            new Plank(null);
        } catch (InvalidParameterException e){
            exceptionThrown = true;
        }

        //Assert
        assertTrue(exceptionThrown);
    }
    
    @Test
    public void Plank_CopyConstructor_Should_CopyGivenPlankDimentionsAndOrigin(){
        //Arrange
        final float WIDTH = 30;
        final float HEIGHT = 40;
        final ShelfPoint ORIGIN = new ShelfPoint(40,30);
        Plank testPlank = new Plank(WIDTH, HEIGHT, ORIGIN);

        //Action
        Plank result = new Plank(testPlank);
        ShelfPoint resultOrigin = result.getOrigin();

        //Assert
        assertEquals(testPlank.getWidth(), result.getWidth(), DELTA);
        assertEquals(testPlank.getHeight(), result.getHeight(), DELTA);
        assertTrue(resultOrigin.equals(testPlank.getOrigin()));
    }
    //#endregion
}