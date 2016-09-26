package xiaolei.sun.zhihu_daily.ui.main;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/22.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoriesItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {

        } else {
            outRect.top = 30;
        }
    }
}