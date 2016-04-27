package com.example.changku;


import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import android.content.Context;

public abstract class Volleyinterface {
	
	public Context mContext;
	public static ErrorListener mErrorlistener;
	public static Listener<String> mListener;

	public Volleyinterface(Context context, Listener<String> listener,
			ErrorListener errorlistener) {
		this.mContext = context;
		this.mListener = listener;
		this.mErrorlistener = errorlistener;
	}
	
	
	public abstract void onMySuccess(String result);
	public abstract void onMyError(VolleyError error);

	public Listener<String> loadingListener() {
		mListener = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				//弹出成功加载�?
				onMySuccess(response);
			}
		};
		return mListener;
		
	}

	public ErrorListener errorListener() {
		mErrorlistener = new ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				//返回失败
				onMyError(error);
			}
		};
		
		return mErrorlistener;
	}
}
