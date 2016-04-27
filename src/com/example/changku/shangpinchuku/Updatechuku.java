package com.example.changku.shangpinchuku;

import android.app.Activity;

import java.util.Calendar;

import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Updatechuku extends Activity {

	/*
	 * 联系人
	 */
	private EditText etDutyPerson;
	/*
	 * 商品规格
	 */
	private EditText etPGuige;
	/*
	 * 联系人电话
	 */
	private EditText etTelephone;
	/*
	 * 商品单位
	 */
	private EditText etPDanwei;
	/*
	 * 进货价格
	 */
	private EditText etcost;
	/*
	 * 进货数量
	 */
	private EditText etamount;
	/*
	 * 公司名称
	 */
	private EditText sCompanyName;
	/*
	 * 商品名称
	 */
	private EditText sPName;
	/*
	 * 日期
	 */
	private DatePicker date;
	private SqlHelpLiteOpenHelper db;
	private SQLiteDatabase mDatabase = null;
	int year;
	int month;
	int day;
	String datestyle;
	String companyList[];
	String companyid[];
	String productList[];
	String productid[];
	String comid;
	String comname;
	String proname;
	String proid;
	Calendar calendar;
	String outboundsid=null;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chukuupdate);
		initView();
		Intent intent = getIntent();
		outboundsid= intent.getStringExtra("outboundsid");
		initDate();
	}


	private void initDate() {
		String selectinbouds ="select * from outbounds where _id='" + outboundsid +"';";
		
		Cursor inboundcursor = mDatabase.rawQuery(selectinbouds, null);
		inboundcursor.moveToFirst();
		do {
			if(inboundcursor.getCount() > 0){
				sCompanyName.setText(inboundcursor.getString(1));
				etDutyPerson.setText(inboundcursor.getString(2));
				etTelephone.setText(inboundcursor.getString(3));
				sPName.setText(inboundcursor.getString(4));
				etPGuige.setText(inboundcursor.getString(5));
				etPDanwei.setText(inboundcursor.getString(6));
				etcost.setText(inboundcursor.getString(7));
				etamount.setText(inboundcursor.getString(8));
			}else{
				Toast.makeText(Updatechuku.this, "无数据", Toast.LENGTH_SHORT).show();
			}
		} while (inboundcursor.moveToNext());
		if(inboundcursor!=null){
			inboundcursor.close();
		}
		/*sCompanyName.setInputType(InputType.TYPE_NULL);
		etDutyPerson.setInputType(InputType.TYPE_NULL);
		etTelephone.setInputType(InputType.TYPE_NULL);
		sPName.setInputType(InputType.TYPE_NULL);
		etPGuige.setInputType(InputType.TYPE_NULL);
		etPDanwei.setInputType(InputType.TYPE_NULL);*/
	}

	private void initView() {
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db",
				null, 1);
		mDatabase = db.getWritableDatabase();
		sCompanyName = (EditText) findViewById(R.id.addc_companyname);
		etDutyPerson = (EditText) findViewById(R.id.addc_et_dutyperson);
		etTelephone = (EditText) findViewById(R.id.addc_et_telephone);
		sPName = (EditText) findViewById(R.id.addc_pname);
		etPGuige = (EditText) findViewById(R.id.addc_et_pguige);
		etPDanwei = (EditText) findViewById(R.id.addc_et_pdanwei);
		etcost = (EditText) findViewById(R.id.addc_et_cost);
		etamount = (EditText) findViewById(R.id.addc_et_amount);
		
		
		date = (DatePicker) findViewById(R.id.addc_date);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH + 1);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		// 显示当前日期、时间
		datestyle = year + "年" + (month + 1) + "月" + day + "日";

		date.init(year, month, day, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				Updatechuku.this.year = year;
				Updatechuku.this.month = monthOfYear;
				Updatechuku.this.day = dayOfMonth;
			}
		});
	}

	public void doSubmit(View v) {
		if (etcost.getText().toString().equals("")
				&& etamount.getText().toString().equals("")) {
			DialogDemo.buidler(Updatechuku.this, "错误提示", "请输入单价和数量");
		} else {
			String companyname = sCompanyName.getText().toString();
			String dutyperson = etDutyPerson.getText().toString();
			String telephone = etTelephone.getText().toString();
			String pname = sPName.getText().toString();
			String pguige = etPGuige.getText().toString();
			String pdanwei = etPDanwei.getText().toString();
			String cost = etcost.getText().toString();
			String amount = etamount.getText().toString();

			int id = 1;
			String selectid = "select max(_id) from outbounds;";
			Cursor cursorid = mDatabase.rawQuery(selectid, null);

			try {
				cursorid.moveToFirst();
				id = cursorid.getInt(0);
				id += 1;
			} catch (Exception e) {
				Toast.makeText(Updatechuku.this,
						"Updatechuku doSubmit Exception", Toast.LENGTH_SHORT)
						.show();
			}
			if(cursorid!=null){
				cursorid.close();
			}
			String updateStr ="update outbounds set companyname= '"
			+ companyname + "',dutyperson='" + dutyperson + "',telephone='" + telephone + "',pname='"
			+ pname + "',pguige ='" + pguige + "',pdanwei ='" + pdanwei + "',cost ='" + cost
			+ "',amount ='" + amount + "',date='" + datestyle + "' where _id='"+ outboundsid +"';";
			mDatabase.execSQL(updateStr);
			Toast.makeText(Updatechuku.this, "修改成功", Toast.LENGTH_SHORT).show();
			Updatechuku.this.finish();
		}
	}

	public void doCancel(View v) {
		Updatechuku.this.finish();
	}
}
