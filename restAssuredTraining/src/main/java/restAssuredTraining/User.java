package restAssuredTraining;

import java.beans.Transient;

public class User {
	private int id;
	private String first_name;
	private String last_name;
	private String avatar;
	private String createdAt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public void printUser() {
		System.out.println("User ID: " + this.id);
		System.out.println("User First Name: " + this.first_name);
		System.out.println("User Last Name: " + this.last_name);
		System.out.println("User Avatar: " + this.avatar);
	}
	@Transient
	public String getCreatedAt() {
		return createdAt;
	}
	@Transient
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
