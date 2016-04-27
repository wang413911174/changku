package com.example.changku;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelpLiteOpenHelper extends SQLiteOpenHelper {
	/*
	 * 创建语句
	 */
	
	
	// 创建用户表
	/*
	 * 用户名、密码、职工号
	 */
		String createUserTable = "create table user_info(_id int auto_increment,username char(20),"
				+ "password char(20),telephone char(40),primary key('_id'));";
	/* 创建商品表
	 * 商品名称、商品规格、商品单位
	 */
	String creatProductsTable = "create table products(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),primary key('_id'));";

	/*创建客户表
	 *
	 * 公司名称、联系人、公司地址、地区名称、邮政编码、联系电话、传真号码、公司主页、
	 */
	String creatCustomerTable = "create table customers(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),companyaddress char(40),city char(40),email char(20),postalcode char(20),telephone char(40),fax char(20),homepageurl char(20),primary key('_id'));";

	/*添加供应商
	 *
	 * 公司名称、联系人、公司地址、地区名称、邮政编码、联系电话、传真号码、公司主页、
	 */
	String  creatSupplierTable ="create table supplier(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),companyaddress char(40),city char(40),email char(20),postalcode char(20),telephone char(40),fax char(20),homepageurl char(20),primary key('_id'));";
	/*添加入库
	 *
	 * 公司名称、联系人、联系电话、商品名称、商品规格、商品单位、入库价格、入库数量、日期
	 */
	String  creatInboundTable ="create table inbounds(_id int auto_increment,"
				+ "companyname char(40),dutyperson char(20),telephone char(40),pname char(40),pguige char(20),pdanwei char(20), cost int,amount int ,date char(40),primary key('_id'));";
	/*添加出库
	 *
	 * 公司名称、联系人、联系电话、商品名称、商品规格、商品单位、出库价格、出库数量、日期
	 */
	String  creatOutboundTable ="create table outbounds(_id int auto_increment,"
			+ "companyname char(40),dutyperson char(20),telephone char(40),pname char(40),pguige char(20),pdanwei char(20), cost int,amount int ,date char(40),primary key('_id'));";

	//inventory
	/*
	 * 库存
	 * 商品名称、商品规格、商品单位、总量
	 */
	String  creatInventoryTable ="create table inventory(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),amount int,primary key('_id'));";

	
	// 插入商品语句
	String insertProducts = "insert into products values(?,?,?,?);";
	// 插入顾客表
	String insertCustomer ="insert into customers values(?,?,?,?,?,?,?,?,?,?);";
	// 插入供应商
	String insertSupplier ="insert into supplier values(?,?,?,?,?,?,?,?,?,?);";
	// 插入用户
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
		// 创建商品表
		
		// String[] products = {"0","电脑","卓越N5010","5400"};
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
