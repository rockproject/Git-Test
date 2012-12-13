package com.rock.slidingdraw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rock.slidingdraw.widget.SlidingDrawer;
import com.rock.slidingdraw.widget.actionbar.ActionBar;
import com.rock.slidingdraw.widget.actionbar.ActionBarSubMenu;

public class SlidingDrawerActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private SlidingDrawer slidingDraw;
	private ActionBarSubMenu subMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		slidingDraw = new SlidingDrawer(this);

		setContentView(slidingDraw);

		setviews();

	}

	private void setviews() {
		slidingDraw.getMiddleContainer().addView(
				getLayoutInflater().inflate(R.layout.middle_layout, null));
		ActionBar actionBar = (ActionBar) slidingDraw
				.findViewById(R.id.actionbar);

		actionBar.getLeftActionBar().addView(
				getLayoutInflater().inflate(R.layout.actionbar_left_layout,
						null));
		actionBar.getCenterActionBar().addView(
				getLayoutInflater().inflate(R.layout.actionbar_middle_layout,
						null));
		actionBar.getRightActionBar().addView(
				getLayoutInflater().inflate(R.layout.actionbar_right_layout,
						null));

		findViewById(R.id.actionbar_open_left).setOnClickListener(this);
		findViewById(R.id.actionbar_open_menu).setOnClickListener(this);
		findViewById(R.id.actionbar_open_right).setOnClickListener(this);

		View containtLeftView = getLayoutInflater().inflate(
				R.layout.container_left_layout, null);
		View containtRightView = getLayoutInflater().inflate(
				R.layout.container_right_layout, null);

		slidingDraw.getLeftContainer().addView(containtLeftView);
		RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		slidingDraw.getRightContainer().addView(containtRightView, llParams);

		findViewById(R.id.left_1).setOnClickListener(this);
		findViewById(R.id.left_2).setOnClickListener(this);
		findViewById(R.id.left_3).setOnClickListener(this);
		findViewById(R.id.left_4).setOnClickListener(this);

		findViewById(R.id.right_1).setOnClickListener(this);
		findViewById(R.id.right_2).setOnClickListener(this);
		findViewById(R.id.right_3).setOnClickListener(this);
		findViewById(R.id.right_4).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int viewid = v.getId();
		switch (viewid) {
		case R.id.actionbar_open_left:
			if (slidingDraw.isNormalState()) {
				slidingDraw.showRight();
			}
			break;
		case R.id.actionbar_open_right:
			if (slidingDraw.isNormalState()) {
				slidingDraw.showLeft();
			}
			break;
		case R.id.actionbar_open_menu:
			if (slidingDraw.isNormalState()) {
				openMenu();
			}
			break;
		case R.id.left_1:
		case R.id.left_2:
		case R.id.left_3:
		case R.id.left_4:

		case R.id.right_1:
		case R.id.right_2:
		case R.id.right_3:
		case R.id.right_4:

			if (v instanceof TextView) {
				Toast.makeText(this, ((TextView) v).getText(),
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}
	}

	private void openMenu() {
		if (subMenu != null) {
			return;
		}

		subMenu = new ActionBarSubMenu(this);
		subMenu.setContentViewId(R.layout.actionbar_menu_layout);
		subMenu.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.transparent_background));
		subMenu.showSubMenuDropDown(findViewById(R.id.actionbar_open_menu));
		subMenu.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				subMenu = null;
			}
		});
	}
}