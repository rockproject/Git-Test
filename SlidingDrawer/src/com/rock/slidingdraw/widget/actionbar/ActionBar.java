package com.rock.slidingdraw.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionBar extends RelativeLayout {

    private LinearLayout mBarLeftContainer;
    private LinearLayout mBarRightContainer;
    private LinearLayout mBarCenterContainer;

    public ActionBar( Context context ) {
        super( context );

        setViews();
    }

    public ActionBar( Context context , AttributeSet attrs ) {
        super( context, attrs );

        setViews();
    }

    public ActionBar( Context context , AttributeSet attrs , int defStyle ) {
        super( context, attrs, defStyle );

        setViews();
    }

    private void setViews() {

        mBarLeftContainer = new LinearLayout( getContext() );
        mBarCenterContainer = new LinearLayout( getContext() );
        mBarRightContainer = new LinearLayout( getContext() );

        RelativeLayout.LayoutParams llpLayoutParams = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT );
        llpLayoutParams.addRule( RelativeLayout.ALIGN_PARENT_LEFT );
        llpLayoutParams.addRule( RelativeLayout.CENTER_VERTICAL );

        addView( mBarLeftContainer, llpLayoutParams );

        llpLayoutParams = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
        llpLayoutParams.addRule( RelativeLayout.CENTER_IN_PARENT );
        llpLayoutParams.addRule( RelativeLayout.CENTER_VERTICAL );

        addView( mBarCenterContainer, llpLayoutParams );

        llpLayoutParams = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
        llpLayoutParams.addRule( RelativeLayout.ALIGN_PARENT_RIGHT );
        llpLayoutParams.addRule( RelativeLayout.CENTER_VERTICAL );

        addView( mBarRightContainer, llpLayoutParams );
    }

    public void addLeftActionItem( ActionBarItem item ) {
        if( item != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarLeftContainer.addView( item.getView(), llpLayoutParams );
        }
    }

    public void addRightActionItem( ActionBarItem item ) {
        if( item != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarRightContainer.addView( item.getView(), llpLayoutParams );
        }
    }

    public void addCenterActionItem( ActionBarItem item ) {
        if( item != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarCenterContainer.addView( item.getView(), llpLayoutParams );
        }
    }

    public void addLeftActionItem( View view ) {
        if( view != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarLeftContainer.addView( view, llpLayoutParams );
        }
    }

    public void addRightActionItem( View view ) {
        if( view != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarRightContainer.addView( view, llpLayoutParams );
        }
    }

    public void addCenterActionItem( View view ) {
        if( view != null ) {
            LinearLayout.LayoutParams llpLayoutParams = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT );
            llpLayoutParams.gravity = Gravity.CENTER;
            mBarCenterContainer.addView( view, llpLayoutParams );
        }
    }

    public void setCenterActionItemsStyle( int style ) {
        int childCount = mBarCenterContainer.getChildCount();
        View childView;
        for(int i=0; i<childCount; i++) {
            childView = mBarCenterContainer.getChildAt( i );
            if( childView instanceof TextView) {
                ( (TextView) childView ).setTextAppearance( getContext(), style );
            }
        }
    }

    public void setOrientation( int orientation ) {
        if( orientation == LinearLayout.VERTICAL) {
            mBarLeftContainer.setOrientation( LinearLayout.VERTICAL );
            mBarCenterContainer.setOrientation( LinearLayout.VERTICAL );
            mBarRightContainer.setOrientation( LinearLayout.VERTICAL );
        }else {
            mBarLeftContainer.setOrientation( LinearLayout.HORIZONTAL );
            mBarCenterContainer.setOrientation( LinearLayout.HORIZONTAL );
            mBarRightContainer.setOrientation( LinearLayout.HORIZONTAL );
        }
    }

    public LinearLayout getLeftActionBar( ) {
        return mBarLeftContainer;
    }

    public LinearLayout getCenterActionBar(  ) {
       return mBarCenterContainer;
    }

    public LinearLayout getRightActionBar( ) {
        return mBarRightContainer;
    }

}
