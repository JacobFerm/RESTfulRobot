package com.jayway.restfulrobot.domain;

public class Robot {

    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;

    private int robotId;
    private String name;
    private int xPos;
    private int yPos;
    private Room room;
    private int dir;

    public Robot(int id, String name, Room room) {
        this.robotId = id;
        this.name = name;
        xPos = 1;
        yPos = 1;
        this.room = room;
        dir = NORTH;
    }

    public int getRobotId() {
        return robotId;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getDir() {
        return dir;
    }

    public void turnLeft() {
        dir = (dir + 1) % 4;
    }

    public void turnRight() {
        dir = (dir + 3) % 4;
    }

    public boolean move() {
        int newX = 0;
        int newY = 0;
        switch (dir) {

            case NORTH:
                newX = xPos;
                newY = yPos + 1;
                break;
            case WEST:
                newX = xPos - 1;
                newY = yPos;
                break;
            case SOUTH:
                newX = xPos;
                newY = yPos - 1;
                break;
            case EAST:
                newX = xPos + 1;
                newY = yPos;
                break;
        }

        if (room.contains(newX, newY)) {
            xPos = newX;
            yPos = newY;
            return true;
        } else {
            return false;
        }
    }

    public boolean canMove() {
        int newX = 0;
        int newY = 0;

        switch (dir) {

            case NORTH:
                newX = xPos;
                newY = yPos + 1;
                break;
            case WEST:
                newX = xPos - 1;
                newY = yPos;
                break;
            case SOUTH:
                newX = xPos;
                newY = yPos - 1;
                break;
            case EAST:
                newX = xPos + 1;
                newY = yPos;
                break;
        }
        return room.contains(newX, newY);
    }

}
