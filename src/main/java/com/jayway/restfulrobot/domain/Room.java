package com.jayway.restfulrobot.domain;

import org.springframework.hateoas.ResourceSupport;

public class Room extends ResourceSupport {

	private int roomId;
	private int width;
	private int height;

	public Room(int roomId, int width, int height) {
		this.roomId = roomId;
		this.width = width;
		this.height = height;
	}

	public int getRoomId() {
		return roomId;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean contains(int newX, int newY) {
		if (Math.abs(newX) < width && Math.abs(newY) < height) {
			return true;
		}
		return false;
	}

}
