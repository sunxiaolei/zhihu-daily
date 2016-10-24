package xiaolei.sun.zhihu_daily.ui.story;

import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Subscriber;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.db.DbManager;
import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteCategory;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.network.api.ApiNews;
import xiaolei.sun.zhihu_daily.network.entity.BmobStoryBean;
import xiaolei.sun.zhihu_daily.network.entity.BmobUserBean;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;

import static android.R.attr.id;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryPresenter extends RxPresenter<StoryContract.View> implements StoryContract.Presenter {

    private ApiNews api;
    private StoryBean bean;

    public StoryPresenter(StoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void getNews(int storyId) {
        api = new ApiNews();
        api.getNews(storyId, new Subscriber<StoryBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError" + e.toString());
            }

            @Override
            public void onNext(StoryBean storyBean) {
                bean = storyBean;
                getCss();
                mView.setImageUrl(bean.getImage());
                mView.setTitle(storyBean.getSection().getName());
            }
        });

    }

    private String adjustBody;

    @Override
    public void getCss() {
        String[] strs = bean.getCss().get(0).split("[?]");
        String[] strs2 = strs[1].split("=");
        Map<String, String> params = new HashMap<>();
        params.put(strs2[0], strs2[1]);
        api.getCss(params, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError" + e.toString());
            }

            @Override
            public void onNext(String string) {
                adjustBody = adjustCss(string);
                mView.loadData(adjustBody);
            }
        });
    }

    private BmobStoryBean storyBean;
    private BmobRelation relation;

    @Override
    public void favorite(String favoriteName) {
//        if (!ZhihuDailyApplication.isLogin) {
//            mView.favorite("收藏失败：请先登录");
//            return;
//        }
//        BmobUserBean user = BmobUser.getCurrentUser(BmobUserBean.class);
//        relation = new BmobRelation();
//        relation.add(user);
//
//        storyBean = new BmobStoryBean();
//        storyBean.setId(bean.getId());
//        storyBean.setTitle(bean.getTitle());
//        storyBean.setImage(bean.getImage());
//        storyBean.setImage_source(bean.getImage_source());
//        storyBean.setShare_url(bean.getShare_url());
//        storyBean.setBody(adjustBody);
//        storyBean.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    storyBean.setFavorite(relation);
//                    storyBean.setObjectId(s);
//                    storyBean.update(new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                mView.favorite("收藏成功");
//                            } else {
//                                mView.favorite("收藏失败：" + e.getMessage());
//                            }
//                        }
//                    });
//                }else {
//                    mView.favorite("保存失败：" + e.getMessage());
//                }
//            }
//        });

        //本地保存到数据库
        DbStory storyBean = new DbStory();
        storyBean.setId(bean.getId());
        storyBean.setTitle(bean.getTitle());
        storyBean.setImage(bean.getImage());
        storyBean.setImage_source(bean.getImage_source());
        storyBean.setShare_url(bean.getShare_url());
        storyBean.setBody(adjustBody);
        storyBean.setFavoriteName(favoriteName);


        List<String> stringList = DbManager.getFavorateCategory();
        if (!stringList.contains(favoriteName)) {
            DbFavoriteCategory categoryBean = new DbFavoriteCategory();
            categoryBean.setName(favoriteName);
            categoryBean.save();
        }


        if (storyBean.save()) {
            mView.favorite("保存成功");
        } else {
            mView.favorite("保存失败");
        }

    }

    @Override
    public void getFavorateCategory() {
        List<String> stringList = DbManager.getFavorateCategory();
        mView.setFavorateCategory(stringList);
    }

    public String adjustCss(String string) {
        String str = string.replace("height: 200px;", "height: 0px;");
        str = str.replace("margin: 20px 0;", "margin: 50px 0 20px;");
        return "<style type=\"text/css\">" + str + "</style>" + bean.getBody();
    }
}
