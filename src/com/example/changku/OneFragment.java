package com.example.changku;

import com.example.changku.gongyingshang.Addgongyingshang;
import com.example.changku.gongyingshang.Findgongyingshang;
import com.example.changku.kehu.Addkehu;
import com.example.changku.kehu.Findkehu;
import com.example.changku.shangpin.Addshangpin;
import com.example.changku.shangpin.Deleteshangpin;
import com.example.changku.shangpin.Findshangpin;
import com.example.changku.shangpin.Updateshangpin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class OneFragment extends Fragment implements OnClickListener {
	/**
	 * 商品信息
	 */
	private Button shangpinxinxi;
	/**
	 * 供应商信息
	 */
	private Button gongyingshangxinxi;
	/**
	 * 客户信息
	 */
	private Button kehuxinxi;

	/**
	 * 添加商品
	 */
	private Button addshangpin;
	/**
	 * 查询商品
	 */
	private Button findshangpin;
	/**
	 * 更新商品
	 */
	private Button updateshangpin;
	/**
	 * 删除商品
	 */
	private Button delshangpin;
	/**
	 * 添加客户
	 */
	private Button addkehu;
	/**
	 * 查询客户
	 */
	private Button findkehu;

	/**
	 * 添加供应商
	 */
	private Button addgongyingshang;
	/**
	 * 查询供应商
	 */
	private Button findgongyingshang;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_one, container, false);
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		shangpinxinxi.setOnClickListener(this);
		gongyingshangxinxi.setOnClickListener(this);
		kehuxinxi.setOnClickListener(this);

		addshangpin.setOnClickListener(this);
		findshangpin.setOnClickListener(this);
		updateshangpin.setOnClickListener(this);
		delshangpin.setOnClickListener(this);

		addkehu.setOnClickListener(this);
		findkehu.setOnClickListener(this);

		addgongyingshang.setOnClickListener(this);
		findgongyingshang.setOnClickListener(this);
	}

	private void initView() {
		shangpinxinxi = (Button) view.findViewById(R.id.shangpinxinxi);
		gongyingshangxinxi = (Button) view
				.findViewById(R.id.gongyingshangxinxi);
		kehuxinxi = (Button) view.findViewById(R.id.kehuxinxi);

		addshangpin = (Button) view.findViewById(R.id.addshangpin);
		findshangpin = (Button) view.findViewById(R.id.findshangpin);
		updateshangpin = (Button) view.findViewById(R.id.updateshangpin);
		delshangpin = (Button) view.findViewById(R.id.deleteshangpin);

		addkehu = (Button) view.findViewById(R.id.addkehu);
		findkehu = (Button) view.findViewById(R.id.findkehu);

		addgongyingshang = (Button) view.findViewById(R.id.addgongyingshang);
		findgongyingshang = (Button) view.findViewById(R.id.findgongyingshang);
	}

	@Override
	public void onClick(View v) {
		doClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.addshangpin:
			intent = new Intent(getActivity(), Addshangpin.class);
//			startActivity(intent1);
			break;
		case R.id.findshangpin:
			intent = new Intent(getActivity(), Findshangpin.class);
//			startActivity(intent2);
			break;
		case R.id.updateshangpin:
			intent = new Intent(getActivity(), Updateshangpin.class);
//			startActivity(intent3);
			break;
		case R.id.deleteshangpin:
			intent = new Intent(getActivity(), Deleteshangpin.class);
			break;

		case R.id.addkehu:
			 intent = new Intent(getActivity(), Addkehu.class);
			break;
		case R.id.findkehu:
			intent = new Intent(getActivity(), Findkehu.class);
			break;
		case R.id.addgongyingshang:
			 intent = new Intent(getActivity(), Addgongyingshang.class);
			break;
		case R.id.findgongyingshang:
			intent = new Intent(getActivity(), Findgongyingshang.class);
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
		case R.id.kehuxinxi:
			addkehu.setVisibility(View.VISIBLE);
			findkehu.setVisibility(View.VISIBLE);
			break;
		case R.id.shangpinxinxi:
			addshangpin.setVisibility(View.VISIBLE);
			findshangpin.setVisibility(View.VISIBLE);
			updateshangpin.setVisibility(View.VISIBLE);
			delshangpin.setVisibility(View.VISIBLE);
			break;

		case R.id.gongyingshangxinxi:
			addgongyingshang.setVisibility(View.VISIBLE);
			findgongyingshang.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	private void visibilityView() {

		addshangpin.setVisibility(View.INVISIBLE);
		findshangpin.setVisibility(View.INVISIBLE);
		updateshangpin.setVisibility(View.INVISIBLE);
		delshangpin.setVisibility(View.INVISIBLE);

		addkehu.setVisibility(View.INVISIBLE);
		findkehu.setVisibility(View.INVISIBLE);

		addgongyingshang.setVisibility(View.INVISIBLE);
		findgongyingshang.setVisibility(View.INVISIBLE);
	}

}
