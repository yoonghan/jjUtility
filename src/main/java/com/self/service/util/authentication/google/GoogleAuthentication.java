package com.self.service.util.authentication.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.Optional;
import com.self.service.util.common.PropertyLoaderUtil;
import com.self.service.util.impl.IOAuthImpl;

/**
 * Google API for google authentication.
 * Configuration to be done at, https://code.google.com/apis/console
 * @author yoong.han
 */
public class GoogleAuthentication implements IOAuthImpl{
	
	private final String CLIENT_SECRET_FILE = "/client_secrets.json";
	//private final String GMAIL_OAUTH_FOLDER = "gmail/oauth2";
	private final String USER = "user";
	
	//private final java.io.File DATA_STORE_DIR = new java.io.File(CommonProperties.getWritableDirectory(), GMAIL_OAUTH_FOLDER);
	
	//private final FileDataStoreFactory DATASTORE_FACTORY;
	private final HttpTransport HTTP_TRANSPORT;
	private final GoogleAuthorizationCodeFlow FLOW;
	private final String CALLBACK_URI;
	
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private final List<String> SCOPES = Arrays.asList(
		      "https://www.googleapis.com/auth/userinfo.profile",
		      "https://www.googleapis.com/auth/userinfo.email");
	
	public GoogleAuthentication(final String CALLBACK_URI) 
		throws IllegalAccessException, GeneralSecurityException, IOException{
		
		this.CALLBACK_URI = CALLBACK_URI;
		
		GoogleClientSecrets clientSecrets = loadClientSecrets();
	    	    
	    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    //DATASTORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
	    
	    // set up authorization code flow
	    FLOW = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	    //.setCredentialDataStore(DATASTORE_FACTORY)
	    .build();

	}
	
	/**
	 * Generates a secure state token 
	 */
	@Override
	public String generateStateToken(){
		SecureRandom sr1 = new SecureRandom();
		return GOOGLE_KEY+sr1.nextInt();
	}

	/**
	 * Generates an url to be called for user.
	 */
	@Override
	public Optional<String> getGeneratedOAuthURL(String stateToken){
		
		if (isConnectable() == false || stateToken==null || stateToken.isEmpty())
			return Optional.absent();

		final GoogleAuthorizationCodeRequestUrl url = FLOW.newAuthorizationUrl();
		
		return Optional.fromNullable(url.setRedirectUri(CALLBACK_URI).setState(stateToken).build());
	}

	private GoogleClientSecrets loadClientSecrets() throws IllegalAccessException{
		GoogleClientSecrets clientSecrets = null;
		try{
			
			InputStream inputStream = new PropertyLoaderUtil().fileLoader(CLIENT_SECRET_FILE);
			
			clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
		        new InputStreamReader(inputStream));
		}catch(Exception error){
			throw new IllegalAccessException("Unable to detect "+CLIENT_SECRET_FILE);
		}
		return clientSecrets;
	}
	
	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 */
	public Optional<String> getUserInfoJson(final String authCode) throws IOException {
		return getUserInfoJson(authCode, USER);
	}
	
	@Override
	public Optional<String> getUserInfoJson(final String authCode, final String userId) throws IOException {
		
		if(isConnectable() == false || authCode == null || authCode.isEmpty())
			return Optional.absent();
		
		final GoogleTokenResponse response = FLOW.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		final Credential credential = FLOW.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();

		return Optional.fromNullable(jsonIdentity);
	}
	
	@Override
	public Optional<GoogleUserInfoEntity> convertJsonToObj(String json){
		if(json == null || json.isEmpty())
			return Optional.absent();
		
		GoogleUserInfoEntity googleInfoEntity =  new GoogleUserInfoEntity.Builder().setJSON(json).build();
		return Optional.fromNullable(googleInfoEntity);
	}
	
	private boolean isConnectable(){
		if(FLOW == null || HTTP_TRANSPORT == null)
			return false;
		return true;
	}
}
