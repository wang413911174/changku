package com.example.changku;


import com.example.changku.shangpinchuku.Addchuku;
import com.example.changku.shangpinchuku.Findchuku;
import com.example.changku.shangpinruku.Addruku;
import com.example.changku.shangpinruku.Findruku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

public class TwoFragment extends Fragment implements OnClickListener{
	 /**
     * 商品入库
     */
    private Button shangpinruku;
    /**
     * 商品出库
     */
    private Button shangpinchuku;
	 /**
     * 添加入库
     */
    private Button addruku;
    /**
     * 查询入库
     */
    private Button findruku;

    /**
     * 添加出库
     */
    private Button addchuku;
    /**
     * 查询出库
     */
    private Button findchuku;
    View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_two, container,false);
		initView();
		initEvent();
		return view;
	}
	
	private void initEvent() {
		shangpinruku.setOnClickListener(this);
		shangpinchuku.setOnClickListener(this);

		addruku.setOnClickListener(this);
		findruku.setOnClickListener(this);

		addchuku.setOnClickListener(this);
		findchuku.setOnClickListener(this);

	}

	private void initView() {
		shangpinruku = (Button) view.findViewById(R.id.shangpinruku);
		shangpinchuku = (Button) view
				.findViewById(R.id.shangpinchuku);

		addruku = (Button) view.findViewById(R.id.addruku);
		findruku = (Button) view.findViewById(R.id.findruku);

		addchuku = (Button) view.findViewById(R.id.addchuku);
		findchuku = (Button) view.findViewById(R.id.findchuku);

	}

	@Override
	public void onClick(View v) {
		doClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.addruku:
			intent = new Intent(getActivity(), Addruku.class);
//			startActivity(intent1);
			break;
		case R.id.findruku:
			intent = new Intent(getActivity(), Findruku.class);
//			startActivity(intent2);
			break;
		case R.id.addchuku:
			 intent = new Intent(getActivity(), Addchuku.class);
			break;
		case R.id.findchuku:
			intent = new Intent(getActivity(), Findchuku.class);
			break;
		default:
			break;
		}
		if(intent!=null)
		startActivity(intent);
	}

	public void doClick(View v) {
		visibilityView();
		switch (v.getId()) {
		case R.id.shangpinruku:
			addruku.setVisibility(View.VISIBLE);
			findruku.setVisibility(View.VISIBLE);
			break;
		case R.id.shangpinchuku:
			addchuku.setVisibility(View.VISIBLE);
			findchuku.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	private void visibilityView() {

		addruku.setVisibility(View.INVISIBLE);
		findruku.setVisibility(View.INVISIBLE);

		addchuku.setVisibility(View.INVISIBLE);
		findchuku.setVisibility(View.INVISIBLE);

	}




}
