package com.robot.mkss2;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private long id;
    private Position position;
    private int energy;
    private List<String> inventory = new ArrayList<>();
    private List<String> actions = new ArrayList<>();

    public Robot(long id, Position position, int energy)
    {
        setId(id);
        setPosition(position);
        setEnergy(energy);
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        if(id < 0)
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        this.id = id;
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Integer getEnergy(){
        return this.energy;
    }

    public void setEnergy(Integer energy){
        this.energy = energy;
    }

    public List<String> getInventory(){
        return this.inventory;
    }

    public void setInventory(List<String> inventory){
        this.inventory = inventory;
    }

    public List<String> getActions() {
        return this.actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public void move(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                position.setY(position.getY() + 1);
                break;
            case "down":
                position.setY(position.getY() - 1);
                break;
            case "left":
                position.setX(position.getX() - 1);
                break;
            case "right":
                position.setX(position.getX() + 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
        actions.add("Moved " + direction);
    }

    public void pickUpItem(Integer itemId) {
        inventory.add(itemId.toString());
        actions.add("Picked up item " + itemId);
    }

    public void putDownItem(Integer itemId) {
        inventory.remove(itemId.toString());
        actions.add("Put down item " + itemId);
    }

    public void attack(Robot target) {
        if (this.energy >= 5) {
            this.energy -= 5;
            target.takeDamage();
            actions.add("Attacked robot " + target.getId());
        } else {
            throw new IllegalStateException("Not enough energy to attack");
        }
    }

    public void takeDamage() {
        this.energy = Math.max(this.energy - 10, 0);
    }

}
