package com.aj.model;

public enum Role {
	  ROLE_USER,
	    ROLE_ADMIN,
	    ROLE_SUPERADMIN;
	    
	    
	    @Override
	    public String toString() {
	        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase(); // Converts "TODO" to "Todo"
	    }

		public boolean isBlank() {
			// TODO Auto-generated method stub
			return false;
		}

		
}
