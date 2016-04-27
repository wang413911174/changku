package com.example.changku;

import com.example.changku.yonghu.UpdateUserinfo;
import com.example.changku.yonghu.Userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FourFragment extends Fragment implements OnClickListener {
	/**
	 * 用户管理键
	 */
	private Button userinfo;
	/**
	 * 修改密码键
	 */
	private Button change_password;
	
	View view;

	
	public final static String USERNAME ="username";
	String username = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(getArguments()!=null){
			username = getArguments().getString(USERNAME);
		}
		view = inflater.inflate(R.layout.fragment_four, container, false);
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		userinfo.setOnClickListener(this);
		change_password.setOnClickListener(this);
	}

	private void initView() {
		userinfo = (Button) view.findViewById(R.id.userinfo);
		change_password = (Button) view.findViewById(R.id.change_password);

	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch(v.getId()){
			case R.id.userinfo:
				intent = new Intent(getActivity(),Userinfo.class);
				
				break;
			case R.id.change_password:
				intent = new Intent(getActivity(),UpdateUserinfo.class);
				Bundle bundle = new Bundle();
				bundle.putString("username", username);
				intent.putExtras(bundle);
				break;
			default:
				break;
		}
		if(intent!=null){
			startActivity(intent);
		}
	}
}
