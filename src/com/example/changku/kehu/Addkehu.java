package com.example.changku.kehu;

import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;
import com.example.changku.shangpin.Addshangpin;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Addkehu extends Activity {
	/**
	 * 公司名称
	 */
	private EditText etCompanyName;
	/**
	 * 项目 负责人
	 */

	private EditText etDutyPerson;
	/**
	 * 公司地址
	 */
	private EditText etCompanyAddress;
	/**
	 * 城市
	 */
	private EditText etCity;
	/**
	 * 邮箱
	 */
	private EditText etEmail;
	/**
	 * 邮编编号
	 */
	private EditText etPostalcode;
	/**
	 * 电话号
	 */
	private EditText etTelephone;
	/**
	 * 传真
	 */
	private EditText etFax;
	/**
	 * 公司地址
	 */
	private EditText etHomePageUrl;
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kehuadd);
		initView();
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();

	}

	private void initView() {
		etCompanyName = (EditText) findViewById(R.id.addk_et_companyname);
		etDutyPerson = (EditText) findViewById(R.id.addk_et_dutyperson);
		etCompanyAddress = (EditText) findViewById(R.id.addk_et_companyaddress);
		etCity = (EditText) findViewById(R.id.addk_et_city);
		etEmail = (EditText) findViewById(R.id.addk_et_email);
		etPostalcode = (EditText) findViewById(R.id.addk_et_postalcode);
		etTelephone = (EditText) findViewById(R.id.addk_et_telephone);
		etFax = (EditText) findViewById(R.id.addk_et_fax);
		etHomePageUrl = (EditText) findViewById(R.id.addk_et_homepageurl);
	}

	public void doSubmit(View v) {
		String companyName = etCompanyName.getText().toString().trim();
		if (companyName.trim().equals("") || companyName == null) {
			DialogDemo.buidler(this, "错误提示", "请输入公司名称");
		} else {
			String dutyPerson = etDutyPerson.getText().toString().trim();
			String companyAddress = etCompanyAddress.getText().toString()
					.trim();
			String city = etCity.getText().toString().trim();
			String email = etEmail.getText().toString().trim();
			String postalcode = etPostalcode.getText().toString().trim();
			String telephone = etTelephone.getText().toString().trim();
			String fax = etFax.getText().toString().trim();
			String homePageUrl = etHomePageUrl.getText().toString().trim();
			String customerStr = "select * from customers;";
			Cursor mCursor = mDatabase.rawQuery(customerStr, null);
			mCursor.moveToFirst();
			int count = mCursor.getCount();
			int i = 0;
			String str0 =null;
			String str1 = null;
			String str2 = null;
			String str3 = null;
			String str4 = null;
			String str5 = null;
			String str6 = null;
			String str7 = null;
			String str8 = null;
			String str9 = null;
			do {
				if (count > 0) {
					try {
						str0 = mCursor.getInt(0)+"";
						str1 = mCursor.getString(1);
						str2 = mCursor.getString(2);
						str3 = mCursor.getString(3);
						str4 = mCursor.getString(4);
						str5 = mCursor.getString(5);
						str6 = mCursor.getString(6);
						str7 = mCursor.getString(7);
						str8 = mCursor.getString(8);
						str9 = mCursor.getString(9);
						i++;
					} catch (Exception e) {
						Log.i("e", e.toString());
						str1 =   "";
						str2 =  "";
						str3 =  "";
						str4 =  "";
						str5 =  "";
						str6 = "";
						str7 =  "";
						str8 = "";
						str9 =  "";
					}
					if (companyName.equals(str1) && dutyPerson.equals(str2)
							&& companyAddress.equals(str3) && city.equals(str4)
							&& email.equals(str5) && postalcode.equals(str6)
							&& telephone.equals(str7) && fax.equals(str8)
							&& homePageUrl.equals(str9)) {
						DialogDemo.buidler(Addkehu.this, "错误信息", "该客户已经存在");
						mCursor.close();
						break;
					}
				}
			} while (mCursor.moveToNext());
			// 关闭光标
			if(mCursor!=null){
				mCursor.close();
			}
			if (!(companyName.equals(str1) && dutyPerson.equals(str2)
					&& companyAddress.equals(str3) && city.equals(str4)
					&& email.equals(str5) && postalcode.equals(str6)
					&& telephone.equals(str7) && fax.equals(str8) && homePageUrl
						.equals(str9))) {
				// 获取即将插入的数据的id
				int id = 0;
				String customerid = "select max(_id) from customers;";
				Cursor mCursorid = mDatabase.rawQuery(customerid, null);
				mCursorid.moveToFirst();
				if (mCursorid.getCount() == 0) {
					id = 1;
				} else {
					id = mCursorid.getInt(0);
					id += 1;
				}
				// 插入语句
				String insertStr = "insert into customers values('" + id
						+ "','" + companyName + "','" + dutyPerson + "','"
						+ companyAddress + "' ,'" + city + "' ,'" + email
						+ "','" + postalcode + "','" + telephone + "','" + fax
						+ "','" + homePageUrl + "');";
				mDatabase.execSQL(insertStr);
				// 添加成功打印toast
				Toast.makeText(Addkehu.this, "添加成功", Toast.LENGTH_SHORT).show();
				mCursorid.close();
			}
		}
	}

	public void doCancel(View v) {
		Addkehu.this.finish();
	}
}
