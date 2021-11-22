package com.vasantlab.tag.model;

import java.util.HashMap;
import java.util.Map;

import com.vasantlab.tag.service.exception.TagStatus;

public class TagDataStatus {
	private boolean status;
	private Map<String, String> tagDataMemoryStatus;
	private TagStatus tagStatus;

	

	public TagDataStatus(boolean status, Map<String, String> tagDataMemoryStatus, TagStatus tagStatus) {
		super();
		this.status = status;
		this.tagDataMemoryStatus = tagDataMemoryStatus;
		this.tagStatus = tagStatus;
	}

	public TagDataStatus() {
		super();
		this.status=true;
		this.tagDataMemoryStatus= new HashMap<>();
	}

	public TagStatus getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(TagStatus tagStatus) {
		this.tagStatus = tagStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Map<String, String> getTagDataMemoryStatus() {
		return tagDataMemoryStatus;
	}

	public void setTagDataMemoryStatus(Map<String, String> tagDataMemoryStatus) {
		this.tagDataMemoryStatus = tagDataMemoryStatus;
	}

	@Override
	public String toString() {
		return "TagDataStatus [status=" + status + ", tagDataMemoryStatus=" + tagDataMemoryStatus + "]";
	}

}
