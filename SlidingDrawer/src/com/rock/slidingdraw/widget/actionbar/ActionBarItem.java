package com.rock.slidingdraw.widget.actionbar;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionBarItem {
    public final static int TYPE_TEXT = 0;
    public final static int TYPE_IMAGE = 1;
    public final static int TYPE_TEXT_IMAGE_LEFT = 2;
    public final static int TYPE_TEXT_IMAGE_TOP = 3;
    public final static int TYPE_TEXT_IMAGE_RIGHT = 4;
    public final static int TYPE_TEXT_IMAGE_BOTTOM = 5;

    private int itemType;
    private String title;
    private int drawableId;

    private Context context;

    private OnClickListener onClickListener;

    View view = null;

    public ActionBarItem( Context context , String title , OnClickListener onClickListener ) {
        this.context = context;
        this.title = title;
        this.itemType = TYPE_TEXT;
        this.onClickListener = onClickListener;
    }

    public ActionBarItem( Context context , int drawableId , OnClickListener onClickListener ) {
        this.context = context;
        this.drawableId = drawableId;
        this.itemType = TYPE_IMAGE;
        this.onClickListener = onClickListener;
    }

    public ActionBarItem( Context context , int itemType , String title , int drawableId ,
            OnClickListener onClickListener ) {
        this.context = context;
        this.itemType = itemType;
        this.title = title;
        this.drawableId = drawableId;
        this.onClickListener = onClickListener;
    }

    public View getView() {
        try {
            if( itemType == TYPE_TEXT ) {
                TextView textView = new TextView( context );
                textView.setText( title );
                view = textView;
            } else if( itemType == TYPE_TEXT_IMAGE_LEFT ) {
                TextView textView = new TextView( context );
                textView.setText( title );

                textView.setCompoundDrawablesWithIntrinsicBounds( drawableId, 0, 0, 0 );

                view = textView;
            } else if( itemType == TYPE_TEXT_IMAGE_TOP ) {
                TextView textView = new TextView( context );
                textView.setText( title );
                textView.setCompoundDrawablesWithIntrinsicBounds( 0, drawableId, 0, 0 );
                view = textView;
            } else if( itemType == TYPE_TEXT_IMAGE_RIGHT ) {
                TextView textView = new TextView( context );
                textView.setText( title );
                textView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, drawableId, 0 );
                view = textView;
            } else if( itemType == TYPE_TEXT_IMAGE_BOTTOM ) {
                TextView textView = new TextView( context );
                textView.setText( title );
                textView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, drawableId );
                view = textView;
            } else if( itemType == TYPE_IMAGE ) {
                ImageView imageView = new ImageView( context );
                imageView.setImageResource( drawableId );
                view = imageView;
            }
            if( view != null ) {
                //TODO
                view.setPadding( 5, 5, 5, 5 );
                if( onClickListener != null ) {
                    view.setOnClickListener( onClickListener );
                }
            }
        } catch ( Exception e ) {
            view = null;
        }
        return view;
    }
}
