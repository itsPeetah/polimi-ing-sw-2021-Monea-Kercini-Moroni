package it.polimi.ingsw.model.general;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class ProductionTest {

    @Test
    public void testproductionTest() {
        ResourceType resType = ResourceType.STONES;
        ResourceType resType2 = ResourceType.SHIELDS;
        int n = 3;
        int n2 = 4;

        HashMap<ResourceType, Integer> resHashMap = new HashMap<>();
        resHashMap.put(resType, n);
        resHashMap.put(resType2, n2);
        Resources output = new Resources(resHashMap);
        Resources input = new Resources();

        Production prod = new Production(input, output);

        assertEquals(0, prod.getInput().getTotalAmount());
        assertEquals(n, prod.getOutput().getAmountOf(resType));
        assertEquals(n2, prod.getOutput().getAmountOf(resType2));
        assertEquals(n + n2, output.getTotalAmount());
    }
}
