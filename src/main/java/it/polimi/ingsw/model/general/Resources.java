package it.polimi.ingsw.model.general;

import java.util.HashMap;

public class Resources {
    private HashMap<ResourceType, Integer> amounts;
    public static final String NOT_ENOUGH_RESOURCES = "Not enough resources";

    /**
     * Initialize an empty Resources.
     */
    public Resources() {
        amounts = new HashMap<>();
    }

    /**
     * Initialize a non-empty Resources from an existing HashMap.
     */
    public Resources(HashMap<ResourceType, Integer> source) {
        amounts = source;
    }

    /**
     * @param resourceType
     * @return number of resources of the specified type.
     */
    public Integer getAmountOf(ResourceType resourceType) {
        Integer temp = amounts.get(resourceType);
        return (temp == null) ? 0 : temp;
    }

    /**
     * @param other
     * @return true if this contains more elements for each type contained in other.
     */
    public Boolean isGreaterThan(Resources other) {
        int additional_amounts = 0;
        if(other.amounts.isEmpty()) return true;
        for(ResourceType resource: ResourceType.values()) {
            if(resource != ResourceType.CHOICE && getAmountOf(resource) < other.getAmountOf(resource)) // checking that ONLY normal resources are in a greater amount
                return false;
            additional_amounts += getAmountOf(resource) - other.getAmountOf(resource); // removing CHOICE amount
        }
        // if we are here normal resources are good, but we still need to check if we have enough additional resources for CHOICE
        return additional_amounts >= 0;
    }

    /**
     * @return total number of elements contained in this.
     */
    public Integer getTotalAmount() {
        return amounts.values().parallelStream().reduce(0, Integer::sum);
    }

    /**
     * Add a certain amount of resources of a specified type.
     * @param resourceType type of the added resource.
     * @param amount type of the added resource.
     */
    public Resources add(ResourceType resourceType, Integer amount) {
        amounts.put(resourceType, amount + getAmountOf(resourceType));
        return this;

    }

    /**
     * Remove a certain amount of resources of a specified type.
     * @param resourceType type of the removed resource.
     * @param amount amount of the removed resource.
     * @throws ResourcesException if the current resources are not enough.
     */
    public Resources remove(ResourceType resourceType, Integer amount) throws ResourcesException {
        Integer temp = getAmountOf(resourceType);
        if(temp < amount) throw new ResourcesException(NOT_ENOUGH_RESOURCES);
        amounts.put(resourceType, temp - amount);
        return this;
    }

    /**
     * Add all the elements of other to this.
     * @param other resources to be added to this.
     */
    public Resources add(Resources other) {
        for(ResourceType resource: other.amounts.keySet()) {
            add(resource, other.getAmountOf(resource));
        }
        return this;
    }

    /**
     * Remove all the elements of other from this.
     * @param other resources to be removed from this.
     * @throws ResourcesException if the current resources are not enough.
     */
    public Resources remove(Resources other) throws ResourcesException {
        if(!isGreaterThan(other)) throw new ResourcesException(NOT_ENOUGH_RESOURCES);
        for(ResourceType resource: other.amounts.keySet()) {
            remove(resource, other.getAmountOf(resource));
        }
        return this;
    }

    /**
     * Remove all the elements of other from this. If other has more elements, the amount is fixed at zero.
     * @param other resources to be removed from this.
     */
    public Resources removeWithoutException(Resources other) {
        for(ResourceType resource: other.amounts.keySet()) {
            try {
                remove(resource, Math.min(getAmountOf(resource), other.getAmountOf(resource)));
            } catch (ResourcesException e) {
                // The exception will never be thrown
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Checks if two resources are equal
     * @param r the other resource which you are comparing to the current
     * @return true if so
     */
    public boolean equals(Resources r){
        return this.isGreaterThan(r) && r.isGreaterThan(this);
    }

    /**
     * Method that replaces white
     * @param r the resource which will have it's white replaced
     * @param type the resource type you want the white to be replaced with
     */

    public void replaceWhite(Resources r, ResourceType type){
        int white = r.getAmountOf(ResourceType.BLANK);
        try {
            r.remove(ResourceType.BLANK, white);
        } catch (Exception e){
            e.printStackTrace();
        }
        r.add(type, white);
    }
}
