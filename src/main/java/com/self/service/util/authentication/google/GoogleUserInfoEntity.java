package com.self.service.util.authentication.google;

import com.self.service.util.gson.CustomGson;

public class GoogleUserInfoEntity {
	
	private String  id;
	private String  email;
	private boolean verified_email;
	private String  name;
	private String  link;
	private String  family_name;
	private String  given_name;
	private String  picture;
	
	public GoogleUserInfoEntity(String id, 
			String email, boolean verified_email, 
			String name, String family_name, String given_name,
			String link, String picture){
		this.setId(id);
		this.setEmail(email);
		this.setVerified_email(verified_email);
		this.setName(name);
		this.setFamily_name(family_name);
		this.setGiven_name(given_name);
		this.setLink(link);
		this.setPicture(picture);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id.length() > 1000?
				id.substring(0, 1000):
					id;
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		email = email.length() > 500?
				email.substring(0, 500):
					email;
		this.email = email;
	}

	public boolean isVerified_email() {
		return verified_email;
	}

	public void setVerified_email(boolean verified_email) {
		this.verified_email = verified_email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name.length() > 1000?
				name.substring(0, 1000):
					name;
		this.name = name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		family_name = family_name.length() > 300?
				family_name.substring(0, 300):
					family_name;
		this.family_name = family_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		link = link.length() > 1000?
				link.substring(0, 1000):
					link;
		this.link = link;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		given_name = given_name.length() > 300?
				given_name.substring(0, 300):
					given_name;
		this.given_name = given_name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		picture = picture.length() > 1000?
				picture.substring(0, 1000):
					picture;
		this.picture = picture;
	}

	public static class Builder{
		
		private String jsonString;
		
		public Builder setJSON(String jsonString){
			this.jsonString = jsonString;
			return this;
		}
		
		public GoogleUserInfoEntity build(){
			return CustomGson.fromGson(jsonString, GoogleUserInfoEntity.class);
		}
	}
	
	
}
