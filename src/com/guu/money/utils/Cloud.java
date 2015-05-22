package com.guu.money.utils;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SignUpCallback;

public class Cloud {
	
	//注册
	public static void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
	    AVUser user = new AVUser();
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    user.signUpInBackground(signUpCallback);
	}
	
	//登录
	public static void login(String username, String password,LogInCallback loginCallback) {
	    AVUser user = new AVUser();
	    user.setUsername(username);
	    user.setPassword(password);
	    user.logInInBackground(username, password, loginCallback);
	}	
	
	//重置密码申请
	public static void resetPsw(String email,RequestPasswordResetCallback resetCallback) {
		AVUser user = new AVUser();
		user.setEmail(email);
		user.requestPasswordResetInBackground(email, resetCallback);
	}
}
