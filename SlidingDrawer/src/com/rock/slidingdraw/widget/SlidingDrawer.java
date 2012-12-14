package com.rock.slidingdraw.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlidingDrawer extends RelativeLayout implements
		android.view.GestureDetector.OnGestureListener {

	private IntercepteRelativeLayout leftContainer;
	private IntercepteRelativeLayout middleContainer;
	private IntercepteRelativeLayout rightContainer;
	private GestureDetector detector;

	private static final int STATE_NORMAL = 0;
	private static final int STATE_SHOW_LEFT = 1;
	private static final int STATE_SHOW_RIGHT = 2;

	private boolean moving;
	private int currentState = STATE_NORMAL;

	private int currentTranslateX = 0;
	private float leftRoate = 0.7f; // left show roate
	private float rightRoate = 0.36f; // right show roate
	private static final String TAG = "SlidingDraw";
	private final List<MotionEvent> motionEvents = new ArrayList<MotionEvent>();

	private boolean enableShowRight = true;
	private boolean enableShowLeft = true;

	public SlidingDrawer(Context context) {
		super(context);
		init();
	}

	public SlidingDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		leftContainer = new IntercepteRelativeLayout(getContext());
		middleContainer = new IntercepteRelativeLayout(getContext());
		rightContainer = new IntercepteRelativeLayout(getContext());
		RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		leftContainer.setBackgroundColor(Color.BLACK);
		TextView textView = new TextView(getContext());
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(20f);
		textView.setText("left");
		leftContainer.addView(textView, llParams);

		middleContainer.setBackgroundColor(Color.WHITE);
		textView = new TextView(getContext());
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(20f);
		textView.setText("middle");
		// middleContainer.addView(textView, llParams);

		rightContainer.setBackgroundColor(Color.BLUE);
		textView = new TextView(getContext());
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(20f);
		textView.setText("right");
		rightContainer.addView(textView, llParams);

		this.setLayoutParams(llParams);

		addView(leftContainer, llParams);
		addView(rightContainer, llParams);
		addView(middleContainer, llParams);

		detector = new GestureDetector(this);

		middleContainer.setOnTouchListener(new OnTouchListener() {
			float startX;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				motionEvents.add(MotionEvent.obtain(event));
				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN) {
					motionEvents.clear();
					motionEvents.add(MotionEvent.obtain(event));
					startX = event.getRawX();
				} else if (action == MotionEvent.ACTION_UP
						|| action == MotionEvent.ACTION_CANCEL) {

					int[] location = new int[2];
					middleContainer.getLocationOnScreen(location);
					int x = (int) event.getRawX();
					int y = (int) event.getRawY();
					int scrollWidth = 0;
					if (currentState == STATE_SHOW_LEFT) {
						scrollWidth = ((int) (middleContainer.getWidth() * leftRoate));
					} else {
						scrollWidth = -((int) (middleContainer.getWidth() * rightRoate));
					}
					Rect rect = new Rect(location[0] + scrollWidth,
							location[1], location[0]
									+ middleContainer.getWidth() + scrollWidth,
							location[1] + middleContainer.getHeight());
					boolean contains = rect.contains(x, y);
					Log.w(TAG, "onTouch contains: " + contains + " moving: "
							+ moving);
					if (!contains && !moving) {
						return dispantchOnTouch();
					} else {
						int distantX = (int) (event.getRawX() - startX);
						if (isInvalidValidTouch(distantX)) {
							return true;
						}
						Log.d(TAG, "onTouch action_up");
						showWhitchSide(distantX);
					}

					moving = false;
				}
				return detector.onTouchEvent(event);
			}

		});

	}

	/**
	 * is a invalid touch
	 *
	 * @param distantX
	 * @return
	 */
	private boolean isInvalidValidTouch(int distantX) {
		if (currentState == STATE_SHOW_LEFT) {
			// cant right again
			if (distantX > 0) {
				return true;
			}
		} else if (currentState == STATE_SHOW_RIGHT) {
			if (distantX < 0) {
				// cant left again
				return true;
			}
		}
		return false;
	}

	/**
	 * dispantch touch event
	 *
	 * @return
	 */
	private boolean dispantchOnTouch() {
		if (currentState == STATE_SHOW_LEFT && enableShowLeft) {
			Log.d(TAG, "onTouch dispatchTouchEvent  Left");
			leftContainer.requestFocus();
			for (MotionEvent motionEvent : motionEvents) {
				leftContainer.dispatchTouchEvent(motionEvent);
			}
			return true;
		} else if (currentState == STATE_SHOW_RIGHT && enableShowRight) {
			Log.d(TAG, "onTouch dispatchTouchEvent  right");
			rightContainer.requestFocus();
			for (MotionEvent motionEvent : motionEvents) {
				rightContainer.dispatchTouchEvent(motionEvent);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent event) {
		// TODO
		Log.i(TAG, "onDown");

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	private void showWhitchSide(int distanceX) {

		boolean showLeft = distanceX > 0;
		int max = getMaxScrollSize(distanceX);
		distanceX = Math.abs(distanceX);
		Log.w(TAG, "max: " + max + " distanceX: " + distanceX + " scrollLeft: "
				+ showLeft);
		if (currentState == STATE_NORMAL) {
			if (distanceX > max / 2) {
				if (showLeft) {
					Log.w(TAG, "scrollWhere 1");
					showLeft();
				} else {
					Log.w(TAG, "scrollWhere 2");
					showRight();
				}
			} else {
				resetToNormal();
			}
		} else if (currentState == STATE_SHOW_LEFT) {
			if (distanceX < max / 2) {
				showLeft();
			} else {
				resetToNormal();
			}
		} else if (currentState == STATE_SHOW_RIGHT) {
			if (distanceX < max / 2) {
				showRight();
			} else {
				resetToNormal();
			}
		} else {
			Log.w(TAG, "scrollWhere 3");
			resetToNormal();
		}
	}

	public void showRight() {
		Log.w(TAG, "showRight");
		currentState = STATE_SHOW_RIGHT;

		startTranslateAnimation(currentTranslateX,
				-((int) (middleContainer.getWidth() * rightRoate)));
		currentTranslateX = -((int) (middleContainer.getWidth() * rightRoate));
		Log.d(TAG, "scrollLeft currentTranslateX: " + currentTranslateX);

		leftContainer.setVisibility(View.GONE);
		rightContainer.setVisibility(View.VISIBLE);

	}

	public void showLeft() {
		Log.w(TAG, "showLeft");
		currentState = STATE_SHOW_LEFT;

		startTranslateAnimation(currentTranslateX,
				((int) (middleContainer.getWidth() * leftRoate)));
		currentTranslateX = ((int) (middleContainer.getWidth() * leftRoate));
		Log.d(TAG, "scrollRight currentTranslateX: " + currentTranslateX);

		leftContainer.setVisibility(View.VISIBLE);
		rightContainer.setVisibility(View.GONE);

	}

	@Override
	public void onLongPress(MotionEvent e) {

		Log.d(TAG, "onLongPress");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d(TAG, "onScroll pre distanceX: " + distanceX);

		if (!isScrollable(e1, e2)) {
			return true;
		}

		moving = true;
		int max = 0;

		int distanceTotalX = (int) (e2.getRawX() - e1.getRawX());
		max = getMaxScrollSize(distanceTotalX);

		if (max > Math.abs((int) (e2.getRawX() - e1.getRawX()))) {
			int startX = currentTranslateX;
			currentTranslateX += -distanceX;
			Log.d(TAG, "onScroll state: " + currentState + "  max: " + max
					+ "diff: " + (int) (e2.getRawX() - e1.getRawX())
					+ " start:  " + startX + " end: " + currentTranslateX
					+ "  distanceX: " + distanceX);

			TranslateAnimation translateAnimation = new TranslateAnimation(
					startX, currentTranslateX, 0, 0);
			translateAnimation.setFillAfter(true);
			middleContainer.startAnimation(translateAnimation);

		}
		return false;
	}

	private int getMaxScrollSize(int distanceTotalX) {
		int max = 0;
		if (currentState == STATE_NORMAL) {
			if (distanceTotalX > 0) {
				max = (int) (middleContainer.getWidth() * leftRoate);
			} else {
				max = (int) (middleContainer.getWidth() * rightRoate);
			}
		} else if (currentState == STATE_SHOW_LEFT) {
			max = (int) (middleContainer.getWidth() * leftRoate);
		} else if (currentState == STATE_SHOW_RIGHT) {
			max = (int) (middleContainer.getWidth() * rightRoate);
		} else {
			max = (int) (middleContainer.getWidth() * (rightRoate > leftRoate ? leftRoate
					: rightRoate));
		}
		return max;
	}

	private boolean isScrollable(MotionEvent e1, MotionEvent e2) {
		if (currentState == STATE_NORMAL) {
			if (!enableShowLeft && !enableShowRight) {
				return false;
			} else if ((int) (e2.getRawX() - e1.getRawX()) > 0) {
				if (!enableShowLeft) {
					return false;
				}
			} else if ((int) (e2.getRawX() - e1.getRawX()) < 0) {
				if (!enableShowRight) {
					return false;
				}
			}

			if (e2.getRawX() - e1.getRawX() > 0) {
				leftContainer.setVisibility(View.VISIBLE);
				rightContainer.setVisibility(View.GONE);
			} else {
				leftContainer.setVisibility(View.GONE);
				rightContainer.setVisibility(View.VISIBLE);
			}
		} else {
			int distantX = (int) (e2.getRawX() - e1.getRawX());

			if (isInvalidValidTouch(distantX)) {
				return false;
			}

		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.i(TAG, "onShowPress");

	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		Log.i(TAG, "onSingleTapUp");
		resetToNormal();
		return false;
	}

	public void resetToNormal() {
		Log.i(TAG, "resetToNormal");
		currentState = STATE_NORMAL;
		startTranslateAnimation(currentTranslateX, 0);

		TranslateAnimation translateAnimation = new TranslateAnimation(
				currentTranslateX, 0, 0, 0);
		translateAnimation.setDuration(300);
		translateAnimation.setFillAfter(true);

		middleContainer.startAnimation(translateAnimation);
		currentTranslateX = 0;
	}

	private void startTranslateAnimation(int start, int end) {
		TranslateAnimation translateAnimation = new TranslateAnimation(start,
				end, 0, 0);

		translateAnimation.setDuration(300);
		translateAnimation.setFillAfter(true);
		middleContainer.startAnimation(translateAnimation);
	}

	public IntercepteRelativeLayout getLeftContainer() {
		return leftContainer;
	}

	public IntercepteRelativeLayout getMiddleContainer() {
		return middleContainer;
	}

	public IntercepteRelativeLayout getRightContainer() {
		return rightContainer;
	}

	public boolean isNormalState() {
		return currentState == STATE_NORMAL;
	}

	public void setEnableShowRight(boolean enableShowRight) {
		this.enableShowRight = enableShowRight;
	}

	public void setEnableShowLeft(boolean enableShowLeft) {
		this.enableShowLeft = enableShowLeft;
	}

	public float getLeftRoate() {
		return leftRoate;
	}

	public void setLeftRoate(float leftRoate) {
		if (leftRoate > 0.1 && leftRoate < 1) {
			this.leftRoate = leftRoate;
		}
	}

	public float getRightRoate() {
		return rightRoate;
	}

	public void setRightRoate(float rightRoate) {
		if (rightRoate > 0.1 && rightRoate < 1) {
			this.rightRoate = rightRoate;
		}
	}

}
