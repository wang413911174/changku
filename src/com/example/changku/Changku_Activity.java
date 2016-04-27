package com.example.changku;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Changku_Activity extends FragmentActivity implements
		OnClickListener,OnPageChangeListener {
	private ViewPager mViewPager;
	List<Fragment> tabFragment = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private List<ChangeIconWithOrText> mtabs = new ArrayList<ChangeIconWithOrText>();
	SqlHelpLiteOpenHelper db;
	SQLiteDatabase mDatabase = null;
	String username="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		setContentView(R.layout.activity_changku);
		initView();
		initData();
		mViewPager.setAdapter(mAdapter);
		initEvent();
		db = new SqlHelpLiteOpenHelper(getApplicationContext(),"changku.db", null, 1);
	}
	private void initEvent() {
		mViewPager.setOnPageChangeListener(this);
	}
	private void initData() {
		OneFragment one = new OneFragment();
		TwoFragment two = new TwoFragment();
		ThreeFragment three = new ThreeFragment();
		FourFragment four = new FourFragment();
		Bundle bundle = new Bundle();
		bundle.putString(four.USERNAME, username);
		Log.i("username", username);
		four.setArguments(bundle);
		
		FiveFragment five = new FiveFragment();
		tabFragment.add(one);
		tabFragment.add(two);
		tabFragment.add(three);
		tabFragment.add(four);
		tabFragment.add(five);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return tabFragment.get(arg0);
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return tabFragment.size();
			}
		};
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.changku_viewpager);
		ChangeIconWithOrText one = (ChangeIconWithOrText) findViewById(R.id.changku_tab_one);
		ChangeIconWithOrText two = (ChangeIconWithOrText) findViewById(R.id.changku_tab_two);
		ChangeIconWithOrText three = (ChangeIconWithOrText) findViewById(R.id.changku_tab_three);
		ChangeIconWithOrText four = (ChangeIconWithOrText) findViewById(R.id.changku_tab_four);
		ChangeIconWithOrText five = (ChangeIconWithOrText) findViewById(R.id.changku_tab_five);
		mtabs.add(one);
		mtabs.add(two);
		mtabs.add(three);
		mtabs.add(four);
		mtabs.add(five);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		one.setIconAlpha(1.0f);
	}

	@Override
	public void onClick(View v) {
		ClickTab(v);
		
	}
	
	/**
	 * 将doClick()事件传递给相应fragment
	 * @param v
	 *//*
	public void doKeHuXinXi(View v) {
		 ((OneFragment) tabFragment.get(0)).doKeHuXinXi(v);
	}*/

	private void ClickTab(View v) {
		resetOtherTabs();
		switch(v.getId()){
		case R.id.changku_tab_one:
			mtabs.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0,false);
			break;
		case R.id.changku_tab_two:
			mtabs.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1,false);
			break;
		case R.id.changku_tab_three:
			mtabs.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2,false);
			break;
		case R.id.changku_tab_four:
			mtabs.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3,false);
			break;
		case R.id.changku_tab_five:
			mtabs.get(4).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(4,false);
			break;
		}
	}

	private void resetOtherTabs() {
		for(int i=0;i< mtabs.size();i++){
			mtabs.get(i).setIconAlpha(0);
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if(arg1>0){
			ChangeIconWithOrText left = mtabs.get(arg0);
			ChangeIconWithOrText right = mtabs.get(arg0 + 1);
			left.setIconAlpha(1-arg1);
			right.setIconAlpha(arg1);
		}
	}
	@Override
	public void onPageSelected(int arg0) {
		
	}
}
