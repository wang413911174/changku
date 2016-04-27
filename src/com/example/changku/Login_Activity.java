package com.example.changku;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends Activity implements OnClickListener {
	private EditText username;
	private EditText password;
	private Button denglu;
	private Button register;
	private CheckBox checkbox;
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase=null;
	SharedPreferences pref;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
		initView();
		initEvent();
		pref = getSharedPreferences("uesrInfos", MODE_PRIVATE);
		editor = pref.edit();
		String uname = pref.getString("username", "");
		String upassword = pref.getString("password", "");
		if (uname == null) {
			checkbox.setChecked(false);
		} else {
			checkbox.setChecked(true);
			username.setText(uname);
			password.setText(upassword);
		}

	}

	private void initEvent() {
		denglu.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	private void initView() {
		username = (EditText) findViewById(R.id.login_uesrname);
		password = (EditText) findViewById(R.id.login_password);
		denglu = (Button) findViewById(R.id.login_denglu);
		register = (Button) findViewById(R.id.login_register);
		checkbox = (CheckBox) findViewById(R.id.login_issave);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent =null;
		switch (v.getId()) {
		case R.id.login_denglu:
			String uname = username.getText().toString().trim();
			String upassword = password.getText().toString().trim();
			if (uname.equals("") || upassword.equals("")) {
				// 弹出消息框
				new AlertDialog.Builder(Login_Activity.this).setTitle("错误")
						.setMessage("帐号或密码不能空").setPositiveButton("确定", null)
						.show();
			} else {
				if (isUserinfo(uname, upassword)) {
					if (checkbox.isChecked()) {
						editor.putString("username", uname);
						editor.putString("password", upassword);
						editor.commit();
						intent = new Intent(Login_Activity.this,
								Changku_Activity.class);
						
						Bundle bundle = new Bundle();
						bundle.putString("username", uname);
						intent.putExtras(bundle);
						
						startActivity(intent);
						Login_Activity.this.finish();
					} else {
						editor.remove("username");
						editor.remove("password");
						editor.commit();
						intent = new Intent(Login_Activity.this,
								Changku_Activity.class);
						
						Bundle bundle = new Bundle();
						bundle.putString("username", uname);
						intent.putExtras(bundle);
						
						startActivity(intent);
						Login_Activity.this.finish();
					}
				}
			}
			break;
		case R.id.login_register:
			intent =  new Intent(Login_Activity.this,
					Register_Activity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	public Boolean isUserinfo(String uname, String upassword) {
		try {
			String str = "select * from user_info where username=? and password=?";
			Cursor cursor =mDatabase.rawQuery(str, new String[] { uname, upassword });
			if (cursor.getCount() <= 0) {
				new AlertDialog.Builder(Login_Activity.this).setTitle("错误")
						.setMessage("帐号或密码错误！").setPositiveButton("确定", null)
						.show();
				return false;
			} else {
				/*new AlertDialog.Builder(Login_Activity.this).setTitle("正确")
						.setPositiveButton("确定", null).setMessage("成功登录")
						.show();*/
				Toast.makeText(Login_Activity.this, "成功登录", Toast.LENGTH_SHORT).show();
				return true;
			}
		} catch (SQLiteException e) {
			Toast.makeText(Login_Activity.this, "Login_Activity SQLiteException", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
}
