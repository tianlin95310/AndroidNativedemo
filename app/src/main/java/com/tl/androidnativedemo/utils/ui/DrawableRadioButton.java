package com.tl.androidnativedemo.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.tl.androidnativedemo.R;


/**
 * 能设置图片大小的RadioButton
 */

public class DrawableRadioButton extends AppCompatRadioButton
{

    Drawable drawableLeft = null,
            drawableTop = null,
            drawableRight = null,
            drawableBottom = null;
    Drawable drawableLeftSelected = null,
            drawableTopSelected = null,
            drawableRightSelected = null,
            drawableBottomSelected = null;
    private int mDrawableSize = 50;

    public DrawableRadioButton(Context context)
    {
        super(context);
    }

    public DrawableRadioButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableRadioButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

    }

    private void init(Context context, AttributeSet attrs)
    {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DrawableRadioButton);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.DrawableRadioButton_drawableSize:
                    mDrawableSize = a.getDimensionPixelSize(R.styleable.DrawableRadioButton_drawableSize, 50);
                    break;
                case R.styleable.DrawableRadioButton_drawableTop:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableRight:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableBottom:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableLeft:
                    drawableLeft = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableTopSelected:
                    drawableTopSelected = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableRightSelected:
                    drawableRightSelected = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableBottomSelected:
                    drawableBottomSelected = a.getDrawable(attr);
                    break;
                case R.styleable.DrawableRadioButton_drawableLeftSelected:
                    drawableLeftSelected = a.getDrawable(attr);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        boolean checked = isChecked();
        if (checked)
        {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeftSelected, drawableTopSelected, drawableRightSelected, drawableBottomSelected);
        } else
        {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)
    {
        if (left != null)
        {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null)
        {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null)
        {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null)
        {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setChecked(boolean checked)
    {
        super.setChecked(checked);
        if (checked)
        {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeftSelected, drawableTopSelected, drawableRightSelected, drawableBottomSelected);
        } else
        {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }
}
