package it.polimi.ingsw.model.general;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ResourcesTest {

    @Test
    public void testgetAmountOfTest() {
        ResourceType resType = ResourceType.STONES;
        int numStones = 5;

        // Test on empty res
        Resources emptyRes = new Resources();
        assertEquals(0, emptyRes.getAmountOf(resType));

        // Test on non-empty res
        HashMap<ResourceType, Integer>  resHashMap = new HashMap<>();
        resHashMap.put(resType, numStones);
        Resources nonEmptyRes = new Resources(resHashMap);
        assertEquals(numStones, nonEmptyRes.getAmountOf(resType));
    }

    @Test
    public void testgetTotalAmountTest() {
        ResourceType resType1 = ResourceType.STONES;
        int numRes1 = 5;
        ResourceType resType2 = ResourceType.SHIELDS;
        int numRes2 = 3;

        // Test on empty res
        Resources emptyRes = new Resources();
        assertEquals(0, emptyRes.getTotalAmount());

        // Test on non-empty res
        HashMap<ResourceType, Integer>  resHashMap = new HashMap<>();
        resHashMap.put(resType1, numRes1);
        resHashMap.put(resType2, numRes2);
        Resources nonEmptyRes = new Resources(resHashMap);
        assertEquals(numRes1 + numRes2, nonEmptyRes.getTotalAmount());
    }

    @Test
    public void testisGreaterThanTest() {
        int smallNum = 3;
        int bigNum = 5;

        // Create the first Resources
        HashMap<ResourceType, Integer>  resHashMap1 = new HashMap<>();
        resHashMap1.put(ResourceType.STONES, bigNum);
        resHashMap1.put(ResourceType.SHIELDS, smallNum);
        Resources firstRes = new Resources(resHashMap1);

        // Test on equal
        Resources equalRes = new Resources(resHashMap1);
        assertTrue(firstRes.isGreaterThan(equalRes));

        // Test true is returned
        HashMap<ResourceType, Integer>  resHashMap2 = new HashMap<>();
        resHashMap2.put(ResourceType.STONES, smallNum);
        Resources secondRes = new Resources(resHashMap2);
        assertTrue(firstRes.isGreaterThan(secondRes));

        // Test false is returned
        HashMap<ResourceType, Integer>  resHashMap3 = new HashMap<>();
        resHashMap3.put(ResourceType.SHIELDS, bigNum);
        Resources thirdRes = new Resources(resHashMap3);
        assertFalse(firstRes.isGreaterThan(thirdRes));

        // Test cases with CHOICE resources
        HashMap<ResourceType, Integer>  resHashMap4 = new HashMap<>();
        resHashMap4.put(ResourceType.CHOICE, bigNum + smallNum);
        Resources fourthRes = new Resources(resHashMap4);
        assertTrue(firstRes.isGreaterThan(fourthRes));

        resHashMap4.put(ResourceType.CHOICE, bigNum + smallNum + 1);
        Resources fifthRes = new Resources(resHashMap4);
        assertFalse(firstRes.isGreaterThan(fifthRes));

    }

    @Test
    public void testaddTest() {
        ResourceType resType = ResourceType.STONES;
        ResourceType resType2 = ResourceType.SHIELDS;
        int n = 3;
        int n2 = 4;

        // Test add with single type
        Resources res = new Resources();
        res.add(resType, n);
        assertEquals(n, res.getAmountOf(resType));

        // Test add with other res
        HashMap<ResourceType, Integer>  resHashMap = new HashMap<>();
        resHashMap.put(resType, n);
        resHashMap.put(resType2, n2);
        Resources addedRes = new Resources(resHashMap);
        Resources res2 = new Resources();
        res2.add(addedRes);
        assertEquals(n, res2.getAmountOf(resType));
        assertEquals(n2, res2.getAmountOf(resType2));
    }

    @Test
    public void removeTest() {
        ResourceType resType = ResourceType.STONES;
        ResourceType resType2 = ResourceType.SHIELDS;
        int n = 3;
        int n2 = 4;

        // Test remove with single type
        Resources res = new Resources();
        res.add(resType, n2);
        try {
            res.remove(resType, n);
            assertEquals(n2 - n, res.getAmountOf(resType));
        } catch (ResourcesException e) {
            fail();
        }

        // Test remove with other res
        HashMap<ResourceType, Integer>  resHashMap = new HashMap<>();
        resHashMap.put(resType, n);
        resHashMap.put(resType2, n2);
        Resources addedRes = new Resources(resHashMap);
        Resources res2 = new Resources();
        res2.add(addedRes);
        try {
            res2.remove(addedRes);
            assertEquals(0, res2.getTotalAmount());
        } catch (ResourcesException e) {
            fail();
        }
    }

    @Test
    public void testremoveWithExceptionTest() {
        Resources res = new Resources();
        assertThrows(ResourcesException.class, () -> res.remove(ResourceType.STONES, 1));
    }

    @Test
    public void testremoveWithoutExceptionTest() {
        Resources res = new Resources();
        res.add(ResourceType.STONES, 1);

        Resources resToRemove1 = new Resources();
        resToRemove1.add(ResourceType.COINS, 1);
        Resources resToRemove2 = new Resources();
        resToRemove2.add(ResourceType.STONES, 2);

        // Remove COINS and test that no exception is thrown and Resources has not changed
        res.removeWithoutException(resToRemove1);
        assertEquals(1, res.getAmountOf(ResourceType.STONES));

        // Remove more STONES than their actual amount in res
        res.removeWithoutException(resToRemove2);
        assertEquals(0, res.getAmountOf(ResourceType.STONES));

    }

    @Test
    public void testequalsTest() {
        int smallNum = 3;
        int bigNum = 5;

        // Create the first Resources
        HashMap<ResourceType, Integer>  resHashMap1 = new HashMap<>();
        resHashMap1.put(ResourceType.STONES, bigNum);
        resHashMap1.put(ResourceType.SHIELDS, smallNum);
        Resources firstRes = new Resources(resHashMap1);

        // Test on equal
        Resources equalRes = new Resources(resHashMap1);
        assertTrue(firstRes.equals(equalRes));

        // Test false is returned
        HashMap<ResourceType, Integer>  resHashMap2 = new HashMap<>();
        resHashMap2.put(ResourceType.STONES, smallNum);
        Resources secondRes = new Resources(resHashMap2);
        assertFalse(firstRes.equals(secondRes));
    }
}
