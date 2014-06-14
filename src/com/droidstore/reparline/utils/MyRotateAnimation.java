package com.droidstore.reparline.utils;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyRotateAnimation extends Animation {
	private float mFromDegrees;
	private float mToDegrees;

	private int mPivotXType = ABSOLUTE;
	private int mPivotYType = ABSOLUTE;
	private float mPivotXValue = 0.0f;
	private float mPivotYValue = 0.0f;

	private float mPivotX;
	private float mPivotY;

	public MyRotateAnimation(float fromDegrees, float toDegrees) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mPivotX = 0.0f;
		mPivotY = 0.0f;
	}

	public MyRotateAnimation(float fromDegrees, float toDegrees, float pivotX,
			float pivotY) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;

		mPivotXType = ABSOLUTE;
		mPivotYType = ABSOLUTE;
		mPivotXValue = pivotX;
		mPivotYValue = pivotY;
	}

	public MyRotateAnimation(float fromDegrees, float toDegrees,
			int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;

		mPivotXValue = pivotXValue;
		mPivotXType = pivotXType;
		mPivotYValue = pivotYValue;
		mPivotYType = pivotYType;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float degrees = mFromDegrees
				+ ((mToDegrees - mFromDegrees) * interpolatedTime);

		if (mPivotX == 0.0f && mPivotY == 0.0f) {
			t.getMatrix().setRotate(degrees);
		} else {
			t.getMatrix().setRotate(degrees, mPivotX, mPivotY);
		}
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
		mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
	}

	/**
	 * @return the mFromDegrees
	 */
	public synchronized float getmFromDegrees() {
		return mFromDegrees;
	}

	/**
	 * @param mFromDegrees
	 *            the mFromDegrees to set
	 */
	public synchronized void setmFromDegrees(float mFromDegrees) {
		this.mFromDegrees = mFromDegrees;
	}

	/**
	 * @return the mToDegrees
	 */
	public synchronized float getmToDegrees() {
		return mToDegrees;
	}

	/**
	 * @param mToDegrees
	 *            the mToDegrees to set
	 */
	public synchronized void setmToDegrees(float mToDegrees) {
		this.mToDegrees = mToDegrees;
	}

	/**
	 * @return the mPivotXType
	 */
	public synchronized int getmPivotXType() {
		return mPivotXType;
	}

	/**
	 * @param mPivotXType
	 *            the mPivotXType to set
	 */
	public synchronized void setmPivotXType(int mPivotXType) {
		this.mPivotXType = mPivotXType;
	}

	/**
	 * @return the mPivotYType
	 */
	public synchronized int getmPivotYType() {
		return mPivotYType;
	}

	/**
	 * @param mPivotYType
	 *            the mPivotYType to set
	 */
	public synchronized void setmPivotYType(int mPivotYType) {
		this.mPivotYType = mPivotYType;
	}

	/**
	 * @return the mPivotXValue
	 */
	public synchronized float getmPivotXValue() {
		return mPivotXValue;
	}

	/**
	 * @param mPivotXValue
	 *            the mPivotXValue to set
	 */
	public synchronized void setmPivotXValue(float mPivotXValue) {
		this.mPivotXValue = mPivotXValue;
	}

	/**
	 * @return the mPivotYValue
	 */
	public synchronized float getmPivotYValue() {
		return mPivotYValue;
	}

	/**
	 * @param mPivotYValue
	 *            the mPivotYValue to set
	 */
	public synchronized void setmPivotYValue(float mPivotYValue) {
		this.mPivotYValue = mPivotYValue;
	}

	/**
	 * @return the mPivotX
	 */
	public synchronized float getmPivotX() {
		return mPivotX;
	}

	/**
	 * @param mPivotX
	 *            the mPivotX to set
	 */
	public synchronized void setmPivotX(float mPivotX) {
		this.mPivotX = mPivotX;
	}

	/**
	 * @return the mPivotY
	 */
	public synchronized float getmPivotY() {
		return mPivotY;
	}

	/**
	 * @param mPivotY
	 *            the mPivotY to set
	 */
	public synchronized void setmPivotY(float mPivotY) {
		this.mPivotY = mPivotY;
	}

}
