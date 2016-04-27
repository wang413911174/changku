package com.example.changku;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelpLiteOpenHelper extends SQLiteOpenHelper {
	/*
	 * �������
	 */
	
	
	// �����û���
	/*
	 * �û��������롢ְ����
	 */
		String createUserTable = "create table user_info(_id int auto_increment,username char(20),"
				+ "password char(20),telephone char(40),primary key('_id'));";
	/* ������Ʒ��
	 * ��Ʒ���ơ���Ʒ�����Ʒ��λ
	 */
	String creatProductsTable = "create table products(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),primary key('_id'));";

	/*�����ͻ���
	 *
	 * ��˾���ơ���ϵ�ˡ���˾��ַ���������ơ��������롢��ϵ�绰��������롢��˾��ҳ��
	 */
	String creatCustomerTable = "create table customers(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),companyaddress char(40),city char(40),email char(20),postalcode char(20),telephone char(40),fax char(20),homepageurl char(20),primary key('_id'));";

	/*��ӹ�Ӧ��
	 *
	 * ��˾���ơ���ϵ�ˡ���˾��ַ���������ơ��������롢��ϵ�绰��������롢��˾��ҳ��
	 */
	String  creatSupplierTable ="create table supplier(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),companyaddress char(40),city char(40),email char(20),postalcode char(20),telephone char(40),fax char(20),homepageurl char(20),primary key('_id'));";
	/*������
	 *
	 * ��˾���ơ���ϵ�ˡ���ϵ�绰����Ʒ���ơ���Ʒ�����Ʒ��λ�����۸��������������
	 */
	String  creatInboundTable ="create table inbounds(_id int auto_increment,"
				+ "companyname char(40),dutyperson char(20),telephone char(40),pname char(40),pguige char(20),pdanwei char(20), cost int,amount int ,date char(40),primary key('_id'));";
	/*��ӳ���
	 *
	 * ��˾���ơ���ϵ�ˡ���ϵ�绰����Ʒ���ơ���Ʒ�����Ʒ��λ������۸񡢳�������������
	 */
	String  creatOutboundTable ="create table outbounds(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),telephone char(40),pname char(40),pguige char(20),pdanwei char(20), cost int,amount int ,date char(40),primary key('_id'));";

	//inventory
	/*
	 * ���
	 * ��Ʒ���ơ���Ʒ�����Ʒ��λ������
	 */
	String  creatInventoryTable ="create table inventory(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),amount int,primary key('_id'));";

	
	// ������Ʒ���
	String insertProducts = "insert into products values(?,?,?,?);";
	// ����˿ͱ�
	String insertCustomer ="insert into customers values(?,?,?,?,?,?,?,?,?,?);";
	// ���빩Ӧ��
	String insertSupplier ="insert into supplier values(?,?,?,?,?,?,?,?,?,?);";
	// �����û�
	String insertUserinfo ="insert into user_info values(?,?,?,?);";
	public SqlHelpLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SqlHelpLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ������Ʒ��
		
		// String[] products = {"0","����","׿ԽN5010","5400"};
		// db.execSQL(insertProducts, products);
		db.execSQL(createUserTable);
		db.execSQL(creatCustomerTable);
		db.execSQL(creatProductsTable);
		db.execSQL(creatSupplierTable);
		db.execSQL(creatInboundTable);
		db.execSQL(creatOutboundTable);
		db.execSQL(creatInventoryTable);
		String[] userinfo = {"0","admin","123456","13008080800"};
		db.execSQL(insertUserinfo,userinfo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
