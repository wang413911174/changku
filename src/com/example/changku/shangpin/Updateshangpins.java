package com.example.changku.shangpin;

import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Updateshangpins extends Activity {
	private Button submit;
	private Button cancle;
	private EditText etname;
	private EditText etguige;
	private EditText etdanwei;
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase=null;
	String pidStr="";
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangpinadd);
		setTitle("�޸���Ʒ");
		//���ǰitem���������ݿ��е�id
		intent = getIntent();
		pidStr = intent.getStringExtra("pid");
		initView();
		
		//�������ݿ��ѯ�� id=pid����������
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
		String selectStr= "select _id,pname,pguige,pdanwei from products where _id="+pidStr+";";
		Cursor pCursor = mDatabase.rawQuery(selectStr, null);
		//����ƶ�����һ������
		pCursor.moveToFirst();
		/*
		 * ����Ĵ������ֱ��д��
		 *  pname.setText(pCursor.getString(1));
		 *  pguige.setText(pCursor.getString(2));
		 *  pguige.setText(pCursor.getString(3));
		 */
		int nid=0;
		String nPname="";
		String nPguige="";
		String nPdanwei="";
		do{
			if(pCursor.getCount() == 1){
				try{
					nid=pCursor.getInt(0);
					nPname=pCursor.getString(1);
					nPguige=pCursor.getString(2);
					nPdanwei=pCursor.getString(3);
				}catch(Exception e){
					nid=0;
					nPname="";
					nPguige="";
					nPdanwei="";
				}
			}
		//�ж���һ���Ƿ�������
		}while(pCursor.moveToNext());
		etname.setText(nPname);
		etguige.setText(nPguige);
		etdanwei.setText(nPdanwei);
		pCursor.close();
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		etname= (EditText) findViewById(R.id.adds_ev_pname);
		etguige = (EditText) findViewById(R.id.adds_ev_pguige);
		etdanwei = (EditText) findViewById(R.id.adds_ev_pcost);
	}
	
	
	/**
	 * �޸����ݡ�����
	 * @param v
	 */
	public void doSubmit(View v){
		String pName=etname.getText().toString();
		//�����Ʒ����Ϊ�գ���ʾ�����Ʒ����
		if(pName.trim().equals("") || pName ==null){
			DialogDemo.buidler(Updateshangpins.this, "������ʾ", "��Ʒ���Ʋ���Ϊ��");
		}else{
			String pGuige = etguige.getText().toString();
			String pDanwei = etdanwei.getText().toString();
			
			String selectStr = "select pname,pguige,pdanwei from products;";
			Cursor mCursor = mDatabase.rawQuery(selectStr, null);
		
			mCursor.moveToFirst();
			String rName="";
			String rGuige="";
			String rDanwei="";
			do{
				try{
					rName =mCursor.getString(0);
					rGuige = mCursor.getString(1);
					rDanwei = mCursor.getString(2);
				}catch(Exception e){
					rName ="";
					rGuige ="";
					rDanwei ="";
				}
				if(pName.equals(rName) && pGuige.equals(rGuige) &&pDanwei.equals(rDanwei)){
					DialogDemo.buidler(Updateshangpins.this, "������ʾ", "����Ʒ��Ϣ�Ѵ���");
					mCursor.close();
					break;
				}
			}while(mCursor.moveToNext());
			//��������
			if(!(pName.equals(rName) && pGuige.equals(rGuige) &&pDanwei.equals(rDanwei))){
				String updateStr="update  products set pname =' "+ pName +"',pguige='" + pGuige + "',pdanwei='" + pDanwei +"' where _id='"+pidStr+"';";
				mDatabase.execSQL(updateStr);
				Toast.makeText(Updateshangpins.this, "�޸���Ʒ�ɹ�", Toast.LENGTH_SHORT).show();
				mCursor.close();
				Updateshangpins.this.finish();
			}
		}
	}
	/**
	 * ȡ��
	 * @param v
	 */
	public void doCancel(View v){
		intent = new Intent(Updateshangpins.this,Updateshangpin.class);
		startActivity(intent);
		Updateshangpins.this.finish();
	}
}
