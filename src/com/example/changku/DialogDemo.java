package com.example.changku;

import android.app.AlertDialog;
import android.content.Context;

public class DialogDemo {
	//������Ϣ�Ի���
	public static void buidler(Context context,String title,String message){
		AlertDialog.Builder  builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("ȷ��", null);
		builder.create();
		builder.show();
	}
}