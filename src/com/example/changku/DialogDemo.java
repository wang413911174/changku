package com.example.changku;

import android.app.AlertDialog;
import android.content.Context;

public class DialogDemo {
	//错误消息对话框
	public static void buidler(Context context,String title,String message){
		AlertDialog.Builder  builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("确定", null);
		builder.create();
		builder.show();
	}
}
