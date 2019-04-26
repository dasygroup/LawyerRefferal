package com.videodasy.OLHS_backend.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Messages {

	
	  public static final String error = "Error occured. Try Again";

	  
	
	 public static String save = "Saved Successfully";
	    public static String update = "Updated Successfully";
	    public static String delete = "Deleted Successfully";
	    public static String not_found = "Records not found";
	    public static String unknown_request = "UNKNOWN REQUEST";
	    public static String exist = "Records already exist";
	    public static String token_name = "olhs_token";

	    public static int SUCCESS_CODE = 200;
	    public static int ERROR_CODE = 300;
	    public static int ERROR_NETWORK_CODE = 400;
	    public static int TOKEN_NOT_FOUND = 500;
	    public static int INCORRECT_TOKEN = 600;
	    public static int NULLS_FOUND = 700;
	    public static int NOT_FOUND = 404;
	    
	    public static String  token="OLHS" + new SimpleDateFormat("dd-MM-yyyy").format(new Date());

	    public static String myum_url = "http://localhost:8002";
	    public static String olhs_token_name = "olhs_token";
}
