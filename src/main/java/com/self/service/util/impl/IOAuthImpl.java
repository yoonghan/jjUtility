package com.self.service.util.impl;

import java.io.IOException;

import com.google.common.base.Optional;

public interface IOAuthImpl {
	
	public String GOOGLE_KEY = "google;";
	public String FACEBOOK_KEY = "facebook;";
	
	public String generateStateToken();
	public Optional<String> getGeneratedOAuthURL(String stateToken);
	public Optional<String> getUserInfoJson(final String authCode) throws IOException;
	public Optional<String> getUserInfoJson(final String authCode, final String userId) throws IOException;
	public Optional<?> convertJsonToObj(String json);
}
