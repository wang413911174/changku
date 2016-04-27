package com.example.changku;

import com.example.changku.kucunxinxi.Kucuninfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ThreeFragment extends Fragment {
	private Button kucuninfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_three, container,false);
		kucuninfo = (Button) view.findViewById(R.id.kuchuchaxun);
		initEvent();
		return view;
	}
	private void initEvent() {
		kucuninfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),Kucuninfo.class);
				startActivity(intent);
			}
		});
	}
	
}
