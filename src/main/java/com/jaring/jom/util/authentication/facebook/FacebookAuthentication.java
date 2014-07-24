package com.jaring.jom.util.authentication.facebook;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.Optional;
import com.jaring.jom.util.common.PropertyLoaderUtil;
import com.jaring.jom.util.impl.IOAuthImpl;

public class FacebookAuthentication implements IOAuthImpl{
	private final String ACCESS_TOKEN_KEY = "access_token";
	
	private final HttpTransport HTTP_TRANSPORT;
	private final AuthorizationCodeFlow FLOW;
	private final GenericUrl GEN_URL;
	private final GenericUrl USER_URL;
	private final String CALLBACK_URI;
	
	private final String CLIENT_SECRET_FILE = "/facebook.properties";
	private final String FACEBOOK_URL = "https://www.facebook.com/dialog/oauth";
	private final String FACEBOOK_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
	
	private final String USER_INFO_URL = "https://graph.facebook.com/me";
	
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	private final List<String> SCOPES = Arrays.asList("public_profile", "email");
	
	private final String PROFILE_INFO = "id,name,email,first_name,gender,link";	
	
	public FacebookAuthentication(final String CALLBACK_URI) 
		throws IllegalAccessException, GeneralSecurityException, IOException, ClassNotFoundException{
			
		this.CALLBACK_URI = CALLBACK_URI;
		
	    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    FacebookBean fbBean = new FacebookBean();
	    new PropertyLoaderUtil().loadProperty(CLIENT_SECRET_FILE, fbBean);
	    
	    // set up authorization code flow
	    FLOW = new AuthorizationCodeFlow.Builder(
	    		BearerToken.authorizationHeaderAccessMethod(),
	    		HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(FACEBOOK_TOKEN_URL),
	    		 new ClientParametersAuthentication(fbBean.getClientId(), fbBean.getClientSecret()), fbBean.getClientId(), 
	    		 FACEBOOK_URL)
	    .setScopes(SCOPES)
	    .build();
	    
	    // set up facebook token verifier
	    GEN_URL = new GenericUrl(FACEBOOK_TOKEN_URL);
	    GEN_URL.put("client_id", fbBean.getClientId());
	    GEN_URL.put("client_secret", fbBean.getClientSecret());
	    GEN_URL.put("redirect_uri", CALLBACK_URI);
	    
	    // set up user requester
	    USER_URL = new GenericUrl(USER_INFO_URL);
	    USER_URL.put("fields", PROFILE_INFO);
	}
	
	/**
	 * Generates a secure state token 
	 */
	@Override
	public String generateStateToken(){
		SecureRandom sr1 = new SecureRandom();
		return FACEBOOK_KEY+sr1.nextInt();
	}

	/**
	 * Generates an url to be called for user.
	 */
	@Override
	public Optional<String> getGeneratedOAuthURL(String stateToken){
		
		if (isConnectable() == false || stateToken==null || stateToken.isEmpty())
			return Optional.absent();

		final AuthorizationCodeRequestUrl url = FLOW.newAuthorizationUrl();
		
		return Optional.fromNullable(url.setRedirectUri(CALLBACK_URI).setState(stateToken).build());
	}
	
	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 */
	@Override
	public Optional<String> getUserInfoJson(final String authCode) throws IOException {
		return getUserInfoJson(authCode, null);
	}
	
	@Override
	public Optional<String> getUserInfoJson(final String authCode, final String userId) throws IOException {
		
		if(isConnectable() == false || authCode == null || authCode.isEmpty())
			return Optional.absent();
		
		String jsonIdentity = null;
		
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
		GEN_URL.put("code", authCode);
		
		final HttpRequest request = requestFactory.buildGetRequest(GEN_URL);
		final HttpResponse response = request.execute();
		
		final HashMap<String, String> hashMap = getAccessToken(response);

		if(hashMap.containsKey(ACCESS_TOKEN_KEY)){
			USER_URL.put(ACCESS_TOKEN_KEY, hashMap.get(ACCESS_TOKEN_KEY));
			final HttpRequest userInfoRequest = requestFactory.buildGetRequest(USER_URL);
			request.getHeaders().setContentType("application/json");
			jsonIdentity = userInfoRequest.execute().parseAsString();
		}
		
		return Optional.fromNullable(jsonIdentity);
	}
	
	private HashMap<String, String> getAccessToken(HttpResponse response) throws IOException {
		final HashMap<String, String> hashMap = new HashMap<String, String>(2);
		
		final String authJsonIdentity = response.parseAsString();
		
		if(response.getStatusCode() == 200){
			String[] keyValues = authJsonIdentity.split("&");
			for(String keyValue: keyValues){
				String[] splitKey = keyValue.split("=");
				hashMap.put(splitKey[0], splitKey[1]);
			}
		}else{
			throw new IOException("Error:"+authJsonIdentity);
		}
		
		return hashMap;
	}
	
	@Override
	public Optional<FacebookUserInfoEntity> convertJsonToObj(String json){
		if(json == null || json.isEmpty())
			return Optional.absent();
		
		FacebookUserInfoEntity fbInfoEntity =  new FacebookUserInfoEntity.Builder().setJSON(json).build();
		return Optional.fromNullable(fbInfoEntity);
	}

	private boolean isConnectable(){
		if(FLOW == null || HTTP_TRANSPORT == null)
			return false;
		return true;
	}
	
}
