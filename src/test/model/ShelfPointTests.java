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

    //#region Addition
    @Test
    public void ShelfPoint_add_withPoint_Should_ThrowException_If_AddedPointIsNull(){
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
    public void ShelfPoint_add_withPoint_Should_SumPointCoordinates(){
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

    @Test
    public void ShelfPoint_add_withCoordinates_Should_SumPointCoordinates(){
        //Arrange
        final float EXPECTED_X = 1;
        final float EXPECTED_Y = 8.1f;
        final float ADDED_X = -2.1f;
        final float ADDED_Y =  2.8f;

        ShelfPoint testPoint = new ShelfPoint(3.1f, 5.3f);

        //Action
        testPoint.add(ADDED_X, ADDED_Y);

        //Assert
        assertEquals(EXPECTED_X, testPoint.x, DELTA);
        assertEquals(EXPECTED_Y, testPoint.y, DELTA);
    }
    
    @Test
    public void ShelfPoint_sum_withPoints_Should_ThrowException_If_AddedPointsAreNull(){
        //Arrange
        final float X = 30.0f;
        final float Y = 20.0f;
        boolean exceptionThrownWithP1Null = false;
        boolean exceptionThrownWithP2Null = false;
        boolean exceptionThrownBothPointsNull = false;
        ShelfPoint testPoint = new ShelfPoint(X, Y);

        //Action
        try{
            ShelfPoint.sum(null, testPoint);
        } catch (InvalidParameterException e){
            exceptionThrownWithP1Null = true;
        } 

        try{
            ShelfPoint.sum(testPoint, null);
        } catch (InvalidParameterException e){
            exceptionThrownWithP2Null = true;
        } 

        try{
            ShelfPoint.sum(null, null);
        } catch (InvalidParameterException e){
            exceptionThrownBothPointsNull = true;
        } 

        //Assert
        assertTrue(exceptionThrownWithP1Null);
        assertTrue(exceptionThrownWithP2Null);
        assertTrue(exceptionThrownBothPointsNull);
    }

    @Test
    public void ShelfPoint_sum_withPoints_Should_SumPointsCoordinates(){
        //Arrange
        final float EXPECTED_X = 1;
        final float EXPECTED_Y = 8.1f;

        ShelfPoint testPoint1 = new ShelfPoint(3.1f, 5.3f);
        ShelfPoint testPoint2 = new ShelfPoint(-2.1f, 2.8f);

        //Action
        ShelfPoint result = ShelfPoint.sum(testPoint1, testPoint2);

        //Assert
        assertEquals(EXPECTED_X, result.x, DELTA);
        assertEquals(EXPECTED_Y, result.y, DELTA);
    }

    @Test
    public void ShelfPoint_sum_withPointAndCoordinates_Should_ThrowException_If_AddedPointsAreNull(){
        //Arrange
        final float X = -2.1f;
        final float Y = 2.8f;
        boolean exceptionThrown = false;

        //Action
        try{
            ShelfPoint.sum(null, X, Y);
        } catch (InvalidParameterException e){
            exceptionThrown = true;
        } 

        //Assert
    assertTrue(exceptionThrown);
    }

    @Test
    public void ShelfPoint_sum_withPointAndCoordinates_Should_SumPointAndCoordinates(){
        //Arrange
        final float EXPECTED_X = 1;
        final float EXPECTED_Y = 8.1f;
        final float X = -2.1f;
        final float Y = 2.8f;

        ShelfPoint testPoint = new ShelfPoint(3.1f, 5.3f);

        //Action
        ShelfPoint result = ShelfPoint.sum(testPoint, X, Y);

        //Assert
        assertEquals(EXPECTED_X, result.x, DELTA);
        assertEquals(EXPECTED_Y, result.y, DELTA);
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