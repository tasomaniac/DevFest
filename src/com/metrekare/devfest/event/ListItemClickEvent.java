package com.metrekare.devfest.event;



public class ListItemClickEvent {

	private String id;

	public ListItemClickEvent() {
		super();
		this.id = null;
	}

	/**
	 * @return the position
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param position the position to set
	 */
	public ListItemClickEvent setId(String id) {
		this.id = id;
		return this;
	}
}
