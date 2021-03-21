package it.polimi.ingsw.module.general;

import java.util.HashMap;

class NotEnoughResourcesException extends Exception {

}

public class Resources {
    private HashMap<ResourceType, Integer> amounts;

    public Resources() {
        amounts = new HashMap<>();
    }

    public Integer getAmountOf(ResourceType resourceType) {
        Integer temp = amounts.get(resourceType);
        return (temp == null) ? 0 : temp;
    }

    public Boolean isGreaterThan(Resources other) {
        for(ResourceType resource: other.amounts.keySet()) {
            if(getAmountOf(resource) < other.getAmountOf(resource)) return false;
        }
        return true;
    }

    public HashMap getAmounts() {
        return new HashMap(amounts);
    }

    public void add(ResourceType resourceType, Integer amount) {
        amounts.put(resourceType, amount + getAmountOf(resourceType));
    }

    public void remove(ResourceType resourceType, Integer amount) throws NotEnoughResourcesException {
        Integer temp = getAmountOf(resourceType);
        if(temp < amount) throw new NotEnoughResourcesException();
        amounts.put(resourceType, temp - amount);
    }

    public void add(Resources other) {
        for(ResourceType resource: other.amounts.keySet()) {
            add(resource, other.getAmountOf(resource));
        }
    }

    public void remove(Resources other) throws NotEnoughResourcesException {
        if(!isGreaterThan(other)) throw new NotEnoughResourcesException();
        for(ResourceType resource: other.amounts.keySet()) {
            remove(resource, other.getAmountOf(resource));
        }
    }

}
