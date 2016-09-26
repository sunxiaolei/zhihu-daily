package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.entity.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.TopStoriesBean;
import xiaolei.sun.zhihu_daily.ui.story.StoryActivity;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/22.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoriesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<StoriesBean> listStories;
    private List<TopStoriesBean> listTop;
    private List<SimpleDraweeView> listImage;
    private String date;

    enum ITEM_TYPE {
        NORMAL_ITEM, IMAGE_ITEM, TITLE_ITEM;
    }

    public StoriesRecyclerAdapter(Context mContext, List<StoriesBean> listStories, List<TopStoriesBean> listTop, String date) {
        this.mContext = mContext;
        this.listStories = listStories;
        this.listTop = listTop;
        this.date = date;
        listImage = new ArrayList<>();
        for (TopStoriesBean topBean : listTop) {
            SimpleDraweeView image = new SimpleDraweeView(mContext);
            image.setImageURI(topBean.getImage());
            listImage.add(image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.IMAGE_ITEM.ordinal()) {
            ImageViewHolder holder =
                    new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_topimg, parent, false));
            return holder;
        } else if (viewType == ITEM_TYPE.TITLE_ITEM.ordinal()) {
            TitleViewHolder holder =
                    new TitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_title, parent, false));
            return holder;
        } else {
            StoriesViewHolder holder =
                    new StoriesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_story, parent, false));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StoriesViewHolder) {
            StoriesBean story = listStories.get(position - 2);
            ((StoriesViewHolder) holder).tvTitle.setText(story.getTitle());
            ((StoriesViewHolder) holder).ivImage.setImageURI(story.getImages().get(0));
        } else if (holder instanceof ImageViewHolder) {
//            ((ImageViewHolder)holder).
//            ((ImageViewHolder)holder).
        } else if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).tvTitle.setText(
                    date.substring(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日");
//            ((ImageViewHolder)holder).
        }
    }

    @Override
    public int getItemCount() {
        return listStories.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.IMAGE_ITEM.ordinal() :
                position == 1 ? ITEM_TYPE.TITLE_ITEM.ordinal() : ITEM_TYPE.NORMAL_ITEM.ordinal();
    }

    class StoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        SimpleDraweeView ivImage;

        public StoriesViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_story);
            ivImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_item_story);
//            ShadowViewHelper.bindShadowHelper(
//                    new ShadowProperty()
//                            .setShadowColor(0x77000000)
//                            .setShadowDy(1)
//                            .setShadowRadius(2)
//                    , itemView.findViewById(R.id.rl_item_story));
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, StoryActivity.class);
            intent.putExtra("STORY_ID", listStories.get(getAdapterPosition() - 1).getId());
            mContext.startActivity(intent);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ViewPager vpTop;
        CircleIndicator indicator;

        public ImageViewHolder(View itemView) {
            super(itemView);
            vpTop = (ViewPager) itemView.findViewById(R.id.vp_main_top);
            indicator = (CircleIndicator) itemView.findViewById(R.id.indicator_main);
            vpTop.setAdapter(new PagerAdapter() {

                @Override
                public int getCount() {
                    return listImage.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    SimpleDraweeView view = listImage.get(position);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, StoryActivity.class);
                            intent.putExtra("STORY_ID", listTop.get(position).getId());
                            mContext.startActivity(intent);
                        }
                    });
                    container.addView(view);
                    return listImage.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(listImage.get(position));
                }
            });
            indicator.setViewPager(vpTop);
        }

    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_main);
        }

    }

}
