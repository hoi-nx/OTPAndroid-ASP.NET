package com.mteam.android_professional.anninhmangotp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;




/**
 * Created by Heart Of Dead on 9/6/2017.
 */

public class PasswordEditText extends AppCompatEditText {
    private Drawable eye, eyeStrike;
    private boolean visible;
    private boolean useStrike;
    private Drawable mDrawable;
    //public static final int ALPHA = (int) (255 * .50f);
    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, 0, 0);
            useStrike = typedArray.getBoolean(R.styleable.PasswordEditText_useStrike, false);
            typedArray.recycle();
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_visibility_on).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_visibility_off).mutate();




        initEditText();
    }

    private void initEditText() {
        setInputType(InputType.TYPE_CLASS_TEXT |(visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
       //nếu visible là true thì InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ele InputType.TYPE_TEXT_VARIATION_PASSWORD

        Drawable[] drawables = getCompoundDrawables();//trái trên phải dưới
        mDrawable = useStrike && !visible? eyeStrike : eye;

        //nêu usetrke là true và visibal=fale thì esytril else eye
        //mDrawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], mDrawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //nếu người dùng nhấn vào màn hình và tọa độ x >=(getRight lấy tọa độ x bên phải trừ tọa độ x của drawable
        if(event.getAction() == MotionEvent.ACTION_UP && event.getX() >= (getRight() - mDrawable.getBounds().width()) ){
            visible = !visible;
           initEditText();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
