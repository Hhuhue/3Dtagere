package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.security.InvalidParameterException;
import org.junit.Test;
import model.ShelfPoint;
import test.TestClass;

public class ShelfPointTests extends TestClass{

    //#region Contructors
    @Test
    public void ShelfPoint_Constructor_Should_SetXAndYValues(){
        //Arrange
        final float X = 30.0f;
        final float Y = 20.0f;

        //Action
        ShelfPoint result = new ShelfPoint(X, Y);

        //Assert
        assertEquals(X, result.x, DELTA);
        assertEquals(Y, result.y, DELTA);
    }
    
    @Test
    public void ShelfPoint_CopyConstructor_Should_SetXAndYValuesFromGivePoint(){
        //Arrange
        final float X = 30.0f;
        final float Y = 20.0f;
        ShelfPoint pointToCopy = new ShelfPoint(X, Y);

        //Action
        ShelfPoint result = new ShelfPoint(pointToCopy);

        //Assert
        assertEquals(X, result.x, DELTA);
        assertEquals(Y, result.y, DELTA);
    }
    //#endregion

    //#region Add
    @Test
    public void ShelfPoint_add_Should_ThrowException_If_AddedPointIsNull(){
        //Arrange
        final float X = 30.0f;
        final float Y = 20.0f;
        boolean exceptionThrown = false;
        ShelfPoint testPoint = new ShelfPoint(X, Y);

        //Action
        try{
            testPoint.add(null);
        } catch (InvalidParameterException e){
            exceptionThrown = true;
        } 

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void ShelfPoint_add_Should_SumPointCoordinates(){
        //Arrange
        final float EXPECTED_X = 1;
        final float EXPECTED_Y = 8.1f;

        ShelfPoint testPoint = new ShelfPoint(3.1f, 5.3f);
        ShelfPoint addedPoint = new ShelfPoint(-2.1f, 2.8f);

        //Action
        testPoint.add(addedPoint);

        //Assert
        assertEquals(EXPECTED_X, testPoint.x, DELTA);
        assertEquals(EXPECTED_Y, testPoint.y, DELTA);
    }
    //#endregion

    //#region Equals
    @Test
    public void ShelfPoint_equals_Should_ThrowException_If_ComparedPointIsNull(){
        //Arrange
        final float X = 30.0f;
        final float Y = 20.0f;
        boolean exceptionThrown = false;
        ShelfPoint testPoint = new ShelfPoint(X, Y);

        //Action
        try{
            testPoint.equals(null);
        } catch (InvalidParameterException e){
            exceptionThrown = true;
        } 

        //Assert
        assertTrue(exceptionThrown);
    }

    @Test
    public void ShelfPoint_equals_Should_TellIfPointCoordinatesAreTheSame(){
        //Arrange
        ShelfPoint point1 = new ShelfPoint(4, 3);
        ShelfPoint point2 = new ShelfPoint(5, 2);
        ShelfPoint cloneOfPoint1 = new ShelfPoint(4, 3);

        //Action
        boolean areTheSame = point1.equals(cloneOfPoint1);
        boolean areDifferent = !point1.equals(point2);

        //Assert
        assertTrue(areTheSame);
        assertTrue(areDifferent);
    }

    @Test
    public void ShelfPoint_equals_Should_ReturnTrue_If_DifferenceIsLowerThanErrorMargin(){
        //Arrange
        ShelfPoint point1 = new ShelfPoint(3.9999999f, 2.999999f);
        ShelfPoint point2 = new ShelfPoint(3.998f, 2.997f);
        ShelfPoint cloneOfPoint1 = new ShelfPoint(4, 3);

        //Action
        boolean areTheSame = point1.equals(cloneOfPoint1);
        boolean areDifferent = !point1.equals(point2);

        //Assert
        assertTrue(areTheSame);
        assertTrue(areDifferent);
    }
    //#endregion
}