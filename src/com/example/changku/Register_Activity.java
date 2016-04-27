package com.example.changku;

import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_Activity extends Activity implements OnClickListener {
	private Button mRegister;
	private EditText mUserName;
	private EditText mPassWord;
	private EditText mPhoneNum;
	private Button mValidation;
	String APPKEY = "122a6469b13b6";
	String APPSECRETE = "a3cf5d83f54eae9148956c9ee6164ad5";
	Boolean flag = false;
	String phone = "";
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase=null;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//db.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		SMSSDK.initSDK(this, APPKEY, APPSECRETE);
		setContentView(R.layout.activity_register);
		initView();
		initEvent();
	}

	private void initEvent() {
		mRegister.setOnClickListener(this);
		mValidation.setOnClickListener(this);
	}

	private void initView() {
		mRegister = (Button) findViewById(R.id.but_register);
		mUserName = (EditText) findViewById(R.id.re_username);
		mPassWord = (EditText) findViewById(R.id.re_password);
		mPhoneNum = (EditText) findViewById(R.id.re_phonenum);
		mValidation = (Button) findViewById(R.id.validation);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.validation:
			// ��ע��ҳ��
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// ����ע����
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						phone = (String) phoneMap.get("phone");
						// �ύ�û���Ϣ
						if (phone != null)
							registerUser(country, phone);
						flag = true;
						mPhoneNum.setText(phone);
					} else if (result == SMSSDK.RESULT_ERROR) {
						try {
							Throwable throwable = (Throwable) data;
							throwable.printStackTrace();
							JSONObject object = new JSONObject(throwable
									.getMessage());
							String des = object.optString("detail");// ��������
							int status = object.optInt("status");// �������
							if (status > 0 && !TextUtils.isEmpty(des)) {
								Toast.makeText(Register_Activity.this, des,
										Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (Exception e) {
							// do something
							Toast.makeText(Register_Activity.this,
									e.toString(), Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
			registerPage.show(this);
			break;
		case R.id.but_register:
			String username = mUserName.getText().toString();
			String password = mPassWord.getText().toString();
			String phonenum = mPhoneNum.getText().toString();
			if (flag == true && phonenum.equals(phone)) {
				if (!(username.equals("") && !(password.equals("")))) {
					if (addUser(username, password, phonenum)) {
						DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 跳转到登录界面
								Intent in = new Intent();
								in.setClass(Register_Activity.this,
										Login_Activity.class);
								startActivity(in);
								// 销毁当前activity
								Register_Activity.this.onDestroy();
							}
						};
						new AlertDialog.Builder(Register_Activity.this)
								.setTitle("注册成功").setMessage("注册成功")
								.setPositiveButton("确定", ss).show();

					} else {
						new AlertDialog.Builder(Register_Activity.this)
								.setTitle("注册失败").setMessage("注册失败")
								.setPositiveButton("确定", null).show();
					}
				} else {
					new AlertDialog.Builder(Register_Activity.this)
							.setTitle("帐号密码不能为空").setMessage("帐号密码不能为空")
							.setPositiveButton("确定", null).show();
				}
			}else if(flag == true && (phonenum.equals(phone)!=true)){
				new AlertDialog.Builder(Register_Activity.this)
				.setTitle("手机号填写错误").setMessage("手机号填写错误")
				.setPositiveButton("确定", null).show();
			}else if(flag != true){
				new AlertDialog.Builder(Register_Activity.this)
				.setTitle("手机号未验证").setMessage("请先验证手机号")
				.setPositiveButton("确定", null).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * �ύ�û���Ϣ
	 * 
	 * @param country
	 * @param phone
	 */
	public void registerUser(String country, String phone) {
		Random r = new Random();
		String uid = Math.abs(r.nextInt()) + "";
		String nickname = "changku";
		SMSSDK.submitUserInfo(uid, nickname, null, country, phone);
	}

	// 添加用户
	public Boolean addUser(String name, String password, String phonenum) {
		
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
		String selectid ="select max(_id) from user_info;";
		Cursor cursorid = mDatabase.rawQuery(selectid, null);
		cursorid.moveToFirst();
		int id=1;
		do{
			if(cursorid.getCount()>0){
				try{
					id=cursorid.getInt(0);
					id+=1;
				}catch(Exception e){
					Toast.makeText(Register_Activity.this, "Register_Activity SQLiteException", Toast.LENGTH_SHORT).show();
				}
			}
		}while(cursorid.moveToNext());
		if(cursorid!=null){
			cursorid.close();
		}
		String str = "insert into user_info values('"+id +"',?,?,?); ";
		try {
			mDatabase.execSQL(str, new String[] { name, password, phonenum});
			return true;
		} catch (Exception e) {
			Toast.makeText(Register_Activity.this, "Register_Activity2 SQLiteException", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
}
