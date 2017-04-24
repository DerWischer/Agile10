package adp.group10.roomates;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * Created by Joshua Jungen on 22.04.2017.
 */

public class CheckableShoppingListItemLayout extends FrameLayout implements Checkable {

    private boolean checked = false;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public CheckableShoppingListItemLayout(@NonNull Context context) {
        super(context);
    }

    public CheckableShoppingListItemLayout(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableShoppingListItemLayout(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        checked = !checked;
        refreshDrawableState();
    }
}
