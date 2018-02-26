package com.lums.serl.dprs;

import org.eclipse.core.runtime.IAdaptable;

public class TreeObject implements IAdaptable {
	private String name;
	private Node parent;
	
	public TreeObject(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return parent;
	}
	@Override
	public String toString() {
		return getName();
	}
	@Override
	public <T> T getAdapter(Class<T> key) {
		return null;
	}
}
