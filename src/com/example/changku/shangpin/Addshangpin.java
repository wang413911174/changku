package com.example.changku.shangpin;

import com.example.changku.Changku_Activity;
import com.example.changku.DialogDemo;
import com.example.changku.R;
import com.example.changku.SqlHelpLiteOpenHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts.Intents.Insert;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Addshangpin extends Activity {
	private Button submit;
	private Button cancel;
	private TextView tvPName;
	private TextView tvPGuiGe;
	private TextView tvPCost;
	private EditText etPName;
	private EditText etPGuiGe;
	private EditText etPCost;
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangpinadd);
		/*
		 * WindowManager m = getWindowManager(); Display d =
		 * m.getDefaultDisplay(); //Ϊ��ȡ��Ļ���� LayoutParams p =
		 * getWindow().getAttributes(); //��ȡ�Ի���ǰ�Ĳ���ֵ p.height = (int)
		 * (d.getHeight() *0.8); //�߶�����Ϊ��Ļ��1.0 p.width = (int) (d.getWidth() *
		 * 0.7); //�������Ϊ��Ļ��0.8 p.alpha = 1.0f; //���ñ���͸���� p.dimAmount = 0.3f;
		 * //���úڰ��� //p.setTitle("�����Ʒ��Ϣ"); getWindow().setAttributes(p);
		 */
		initView();
		db = new SqlHelpLiteOpenHelper(getApplicationContext(), "changku.db", null, 1);
		mDatabase = db.getWritableDatabase();
	}

	private void initView() {
		submit = (Button) findViewById(R.id.adds_submit);
		cancel = (Button) findViewById(R.id.adds_cancel);
		etPName = (EditText) findViewById(R.id.adds_ev_pname);
		etPGuiGe = (EditText) findViewById(R.id.adds_ev_pguige);
		etPCost = (EditText) findViewById(R.id.adds_ev_pcost);
	}

	public void doSubmit(View v) {
		
		String pName = etPName.getText().toString();
		//�ж��������Ʒ�����Ƿ�Ϊ��
		
		if (pName.trim().equals("") || pName == null) {
			DialogDemo.buidler(Addshangpin.this, "��ʾ", "��������Ʒ��");
		} else {
			//��Ʒ���Ʋ�Ϊ��,ִ�����
			String PGuiGe = etPGuiGe.getText().toString();
			String pCost = etPCost.getText().toString();
			String selectStr = "select pname,pguige,pdanwei from products;";
			Cursor pCursor = mDatabase.rawQuery(selectStr, null);
			pCursor.moveToFirst();
			
			String namestr = null;
			String guigestr = null;
			String coststr = null;
			do {
				try {
					namestr = pCursor.getString(0);
					guigestr = pCursor.getString(1);
					coststr = pCursor.getString(2);
				} catch (Exception e) {
					namestr = "";
					guigestr = "";
					coststr = "";
				}
				if (namestr.equals(pName) && guigestr.equals(PGuiGe)
						&& coststr.equals(pCost)) {
					DialogDemo.buidler(Addshangpin.this, "������Ϣ", "����Ʒ�Ѿ�����");
					pCursor.close();
					break;
				}
				//�жϹ����һ���Ƿ�������
			} while (pCursor.moveToNext());
			if(pCursor!=null){
				pCursor.close();
			}
			if (!(namestr.equals(pName) && guigestr.equals(PGuiGe) && coststr
						.equals(pCost))) {
					// ����id
					int id = 0;
					String selectid = "select max(_id) from products;";
					Cursor idCousor = mDatabase.rawQuery(selectid, null);
					try {
						//����ƶ���һ����¼�� �ȵ�id ��������1
						idCousor.moveToFirst();
						id = Integer.valueOf(idCousor.getString(0));
						id += 1;
					} catch (Exception e) {
						id = 0;
					}
					//ִ�в������
					mDatabase.execSQL("Insert into products values('"  + id
							+ "','" + pName + "','" + PGuiGe + "','" + pCost
							+ "');");
					//��ӳɹ���ӡtoast
					Toast.makeText(Addshangpin.this, "��ӳɹ�", Toast.LENGTH_SHORT)
							.show();
					idCousor.close();
				}
		} 
	}
	public void doCancel(View v) {
		Addshangpin.this.finish();
	}
}
