package com.example.changku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ChangeIconWithOrText extends View {

	private int mColor = 0xFF45C01A;
	private String mText = "基本信息";
	private Bitmap mIconBitmap;
	private int mText_size = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Bitmap mBitmap;
	private float mAlpha;

	private Paint mPaint;
	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;

	public ChangeIconWithOrText(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ChangeIconWithOrText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ChangeIconWithOrText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeIconWithOrText);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.ChangeIconWithOrText_icons:
				BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;
			case R.styleable.ChangeIconWithOrText_colors:
				mColor = a.getColor(attr, 0xFF45C01A);
				break;
			case R.styleable.ChangeIconWithOrText_text:
				mText = a.getString(attr);
				break;
			case R.styleable.ChangeIconWithOrText_text_size:
				mText_size = (int) a.getDimension(attr, TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
								getResources().getDisplayMetrics()));
				break;
			}
		}
		a.recycle();
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mText_size);
		mTextPaint.setColor(0XFF555555);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth)
				/ 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
		int alpha = (int) Math.ceil(255 * mAlpha);
		// 内存去准备mBitmap ,setalpha,纯色,xfermode,图标
		setupTargetBitmap(alpha);
		// canvas.drawBitmap(mBitmap, 0, 0, null);
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
	}

	/**
	 * 绘制新TEXT
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height()/2;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 绘制原Text
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setColor(0xFF333333);
		mTextPaint.setAlpha(255 - alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height()/2;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 绘制图片
	 * 
	 * @param alpha
	 */
	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setDither(true);
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mBitmap, null, mIconRect, mPaint);
	}

	private final static String INSTANCE_STATE = "instance_state";
	private final static String STATE_VALUE = "state_value";

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putFloat(STATE_VALUE, mAlpha);
		return bundle;

	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATE_VALUE);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
			return;
		}
		super.onRestoreInstanceState(state);
	}
	public void setIconAlpha(float alpha){
		this.mAlpha = alpha;
		invalidateView();
	}

	private void invalidateView() {
		// TODO Auto-generated method stub
		if(Looper.myLooper() == Looper.getMainLooper()){
			invalidate();
		}else{
			postInvalidate();
		}
	}
}
