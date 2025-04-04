package com.aj.vo;

import java.sql.Timestamp;

public class BranchVo {

	  private long id;
	    
	    private String branchName;
	    
	    private String branchLocation;
	    
	    private String createdAt;

	    private String updatedAt;

	    private String createdBy;

	    private String updatedBy;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		public String getBranchLocation() {
			return branchLocation;
		}

		public void setBranchLocation(String branchLocation) {
			this.branchLocation = branchLocation;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public String getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}
	    
	    
}
