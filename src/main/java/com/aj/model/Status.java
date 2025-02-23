package com.aj.model;

public enum Status {

	ACTIVE, MUTE, UNMUTE, BLOCK , UNBLOCK;
	

	@Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase(); 
    }

}
