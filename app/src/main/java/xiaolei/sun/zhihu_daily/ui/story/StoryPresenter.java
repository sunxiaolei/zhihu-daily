package xiaolei.sun.zhihu_daily.ui.story;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.db.DbManager;
import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteCategory;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.api.ApiNews;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteResponse;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StoryBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryPresenter extends RxPresenter<StoryContract.View> implements StoryContract.Presenter {

    public static final int FAVORITE_SUCCESS = 0;
    public static final int FAVORITE_FAILED = 1;

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

    @Override
    public void favorite(String category,String storyId) {

        FavoriteRequest request = new FavoriteRequest();
        request.setCategory(category);
        request.setStoryId(storyId);
        request.setUserId(ZhihuDailyApplication.user.getObjectId());
        LeanCloudRequest.doFavorite(request)
                .subscribe(new Subscriber<FavoriteResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.favoriteResult(FAVORITE_FAILED);
                    }

                    @Override
                    public void onNext(FavoriteResponse favoriteResponse) {
                        mView.favoriteResult(FAVORITE_SUCCESS);
                    }
                });

        //本地保存到数据库---暂时不用本地数据库
//        DbStory storyBean = new DbStory();
//        storyBean.setStoryId(bean.getId());
//        storyBean.setTitle(bean.getTitle());
//        storyBean.setImage(bean.getImage());
//        storyBean.setImage_source(bean.getImage_source());
//        storyBean.setShare_url(bean.getShare_url());
//        storyBean.setBody(adjustBody);
//        storyBean.setFavoriteCategory(favoriteName);
//        storyBean.save();
//
//        List<String> listCategory = DbManager.getFavorateCategory();
//        if (!listCategory.contains(favoriteName)) {
//            DbFavoriteCategory categoryBean = new DbFavoriteCategory();
//            categoryBean.setName(favoriteName);
//            categoryBean.save();
//        }
//        mView.favorite("保存成功");
    }

    @Override
    public void getFavorateCategory() {

        //符合查询
        //{"$or":[{"pubUserCertificate":{"$gt":2}},{"pubUserCertificate":{"$lt":3}}]}
//        LeanCloudRequest.getFavoriteCategory()

        //获取数据库数据
//        List<String> stringList = DbManager.getFavorateCategory();
//        mView.setFavorateCategory(stringList);
    }

    public String adjustCss(String string) {
        String str = string.replace("height: 200px;", "height: 0px;");
        str = str.replace("margin: 20px 0;", "margin: 50px 0 20px;");
        return "<style type=\"text/css\">" + str + "</style>" + bean.getBody();
    }
}
