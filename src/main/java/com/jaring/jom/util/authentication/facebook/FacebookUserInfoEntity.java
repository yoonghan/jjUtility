package com.jaring.jom.util.authentication.facebook;

import com.jaring.jom.util.gson.CustomGson;

public class FacebookUserInfoEntity {
	private String  id;
	private String  email;
	private String  name;
	private String  link;
	private String  gender;
	
	public FacebookUserInfoEntity(String id, 
			String email,  
			String name, 
			String link,
			String gender){
		this.setId(id);
		this.setEmail(email);
		this.setName(name);
		this.setLink(link);
		this.setGender(gender);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id != null){
			id = id.length() > 1000?
					id.substring(0, 1000):
						id;
		}
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email != null){
			email = email.length() > 500?
					email.substring(0, 500):
						email;
		}
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if(gender != null){
			gender = gender.length() > 200?
					gender.substring(0, 200):
						gender;
		}
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name != null){
			name = name.length() > 1000?
					name.substring(0, 1000):
						name;
		}
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		if(link != null){
			link = link.length() > 1000?
					link.substring(0, 1000):
						link;
		}
		this.link = link;
	}

	public static class Builder{
		
		private String jsonString;
		
		public Builder setJSON(String jsonString){
			this.jsonString = jsonString;
			return this;
		}
		
		public FacebookUserInfoEntity build(){
			return CustomGson.fromGson(jsonString, FacebookUserInfoEntity.class);
		}
	}

}
