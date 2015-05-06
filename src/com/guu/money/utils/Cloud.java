package com.guu.money.utils;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

public class Cloud {
	
	//зЂВс
	public static void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
	    AVUser user = new AVUser();
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.signUpInBackground(signUpCallback);
	}

}
