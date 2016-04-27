package com.example.changku.yonghu;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.changku.DialogDemo;
import com.example.changku.Login_Activity;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateUserinfo extends Activity {
	/**
	 * 原来密码
	 */
	private EditText etoldpassword;
	/**
	 * 新密码
	 */
	private EditText etnewpassword;
	/**
	 * 确认密码
	 */
	private EditText ettruepassword;

	/**
	 * 电话号
	 */
	String username = "";

	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase=null;
	String oldpws = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		setContentView(R.layout.updateuserpassword);
		initView();
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();

		try {
			String selectStr = "select password from  user_info where username='"
					+ username + "';";
			Cursor userCursor = mDatabase.rawQuery(selectStr, null);
			userCursor.moveToFirst();

			oldpws = userCursor.getString(0);
			if (userCursor != null) {
				userCursor.close();
			}
		} catch (Exception e) {
			Toast.makeText(UpdateUserinfo.this, "UpdateUserinfo Exception",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initView() {
		etoldpassword = (EditText) findViewById(R.id.user_et_oldpassword);
		etnewpassword = (EditText) findViewById(R.id.user_et_newpassword);
		ettruepassword = (EditText) findViewById(R.id.user_et_truepassword);
	}

	public void doSubmit(View v) {

		String oldpassword = etoldpassword.getText().toString().trim();
		if (!(oldpassword.equals(oldpws)) || oldpassword == null) {
			DialogDemo.buidler(this, "错误提示", "原密码不正确");
		} else {
			String newpassword = etnewpassword.getText().toString().trim();
			String truepassword = ettruepassword.getText().toString().trim();

			if (ispasswordinfo(newpassword, truepassword)) {
				try {
					String updateStr = "update user_info set password='"
							+ newpassword + "' where username='" + username
							+ "';";
					mDatabase.execSQL(updateStr);
				} catch (SQLException e) {
					Toast.makeText(UpdateUserinfo.this,
							"UpdateUserinfo SQLException", Toast.LENGTH_SHORT)
							.show();
				}
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserinfo.this);
			builder.setTitle("错误").setMessage("修改密码成功,退出后生效！");
			builder.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UpdateUserinfo.this.finish();
				}
			});
			builder.create().show();
		}
	}

	private boolean ispasswordinfo(String newpassword, String truepassword) {
		if (null == newpassword || newpassword.trim().equals("")) {
			new AlertDialog.Builder(UpdateUserinfo.this).setTitle("错误")
					.setMessage("请输入新密码！").setPositiveButton("确定", null).show();
			return false;
		} else if (null == truepassword || truepassword.trim().equals("")) {
			new AlertDialog.Builder(UpdateUserinfo.this).setTitle("错误")
					.setMessage("请输入确认密码！").setPositiveButton("确定", null)
					.show();
			return false;
		} else if (!newpassword.equals(truepassword)) {
			new AlertDialog.Builder(UpdateUserinfo.this).setTitle("错误")
					.setMessage("两次密码不正确！").setPositiveButton("确定", null)
					.show();
			return false;
		} else if (newpassword.equals(truepassword)) {
			return true;
		}
		return false;
	}

	public void doCancel(View v) {
		UpdateUserinfo.this.finish();
	}
}
