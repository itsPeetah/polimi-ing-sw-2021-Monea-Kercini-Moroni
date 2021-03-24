package it.polimi.ingsw.model.general;

import java.util.HashMap;

class NotEnoughResourcesException extends Exception {

}

public class Resources {
    private HashMap<ResourceType, Integer> amounts;

    /**
     * Initialize an empty Resources
     */
    public Resources() {
        amounts = new HashMap<>();
    }

    /**
     *
     * @param resourceType
     * @return number of resources of the specified type
     */
    public Integer getAmountOf(ResourceType resourceType) {
        Integer temp = amounts.get(resourceType);
        return (temp == null) ? 0 : temp;
    }

    /**
     *
     * @param other
     * @return true if this contains more elements for each type contained in other
     */
    public Boolean isGreaterThan(Resources other) {
        int additional_amounts = 0;
        for(ResourceType resource: other.amounts.keySet()) {
            if(getAmountOf(resource) < other.getAmountOf(resource)) return false;
        }
        return true;
    }

    /**
     *
     * @return total number of elements contained in this
     */
    public Integer getTotalAmount() {
        return amounts.size();
    }

    /**
     * Add a certain amount of resources of a specified type
     * @param resourceType
     * @param amount
     */
    public void add(ResourceType resourceType, Integer amount) {
        amounts.put(resourceType, amount + getAmountOf(resourceType));
    }

    /**
     * Remove a certain amount of resources of a specified type
     * @param resourceType
     * @param amount
     * @throws NotEnoughResourcesException if the current resources are not enough
     */
    public void remove(ResourceType resourceType, Integer amount) throws NotEnoughResourcesException {
        Integer temp = getAmountOf(resourceType);
        if(temp < amount) throw new NotEnoughResourcesException();
        amounts.put(resourceType, temp - amount);
    }

    /**
     * Add all the elements of other to this
     * @param other
     */
    public void add(Resources other) {
        for(ResourceType resource: other.amounts.keySet()) {
            add(resource, other.getAmountOf(resource));
        }
    }

    /**
     * Remove all the elements of other from this
     * @param other
     * @throws NotEnoughResourcesException if the current resources are not enough
     */
    public void remove(Resources other) throws NotEnoughResourcesException {
        if(!isGreaterThan(other)) throw new NotEnoughResourcesException();
        for(ResourceType resource: other.amounts.keySet()) {
            remove(resource, other.getAmountOf(resource));
        }
    }
}
