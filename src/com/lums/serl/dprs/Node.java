package com.lums.serl.dprs;

import java.util.ArrayList;

public class Node extends TreeObject {
	public static enum TYPE {
		ROOT, PROJECT, DP, INSTANCE, CLASS, METHOD;
	}
	private ArrayList children;
	private TYPE type;
	private double score;
	private String category;
	private String description;
	private String sourcePath;
	private ArrayList<String> topics;
	
	public Node(String name, TYPE type) {
		super(name);
		children = new ArrayList();
		this.type = type;
	}
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	public TreeObject [] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}
	public TYPE getType() {
		return type;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getTopics() {
		return topics;
	}
	public void setTopics(ArrayList<String> topics) {
		this.topics = topics;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
}
