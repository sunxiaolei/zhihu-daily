package xiaolei.sun.zhihu_daily.ui.story;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import xiaolei.sun.zhihu_daily.BaseApplication;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.ZhihuDailyRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRelationResponse;
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
    public static final int FAVORITE_YET = 2;//已收藏

    private StoryBean bean;

    public StoryPresenter(StoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void getNews(int storyId) {
        ZhihuDailyRequest.getNews(storyId, new Subscriber<StoryBean>() {
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
        ZhihuDailyRequest.getCss(params, new Subscriber<String>() {
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

    FavoriteRequest request;

    @Override
    public void favorite(final String category, final String storyId) {

        //判断是否已收藏
        //{'$or':[{'pubUserCertificate':{'$gt':2}},{'pubUserCertificate':{'$lt':3}}]}
        Map<String, String> map = new HashMap<>();
        JsonObject obj = new JsonObject();
        JsonObject obj1 = new JsonObject();
        JsonObject obj2 = new JsonObject();
        JsonArray arr = new JsonArray();
        obj1.addProperty("userId", BaseApplication.user.getObjectId());
        obj2.addProperty("storyId", storyId + "");
        arr.add(obj1);
        arr.add(obj2);
        obj.add("$and", arr);
        map.put("where", obj.toString());
//        LeanCloudRequest.getFavoriteRelation(map)
//                .subscribe(new Subscriber<FavoriteRelationResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.favoriteResult(FAVORITE_FAILED);
//                    }
//
//                    @Override
//                    public void onNext(FavoriteRelationResponse response) {
//                        if (response.getResults() != null && response.getResults().size() > 0) {
//                            mView.favoriteResult(FAVORITE_YET);
//                        } else {
//                            doFavorite(category, storyId);
//                        }
//                    }
//                });

        request = new FavoriteRequest();
        request.setCategory(category);
        request.setStoryId(bean.getId() + "");
        request.setUserId(BaseApplication.user.getObjectId());
        request.setStoryTitle(bean.getTitle());
        LeanCloudRequest.getFavoriteRelation(map)
                .flatMap(new Func1<FavoriteRelationResponse, Observable<FavoriteResponse>>() {
                    @Override
                    public Observable<FavoriteResponse> call(FavoriteRelationResponse response) {
                        if (response.getResults() != null && response.getResults().size() > 0) {
                            mView.favoriteResult(FAVORITE_YET);
                            return null;
                        } else {
                            return LeanCloudRequest.doFavorite(request);
                        }
                    }
                })
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

    private void doFavorite(String category, String storyId) {
        FavoriteRequest request = new FavoriteRequest();
        request.setCategory(category);
        request.setStoryId(storyId);
        request.setUserId(BaseApplication.user.getObjectId());
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
    }

    @Override
    public void getFavorateCategory() {

        //符合查询
        Map<String, String> map = new HashMap<>();
        JsonObject obj = new JsonObject();
        obj.addProperty("userId", BaseApplication.user.getObjectId());
        map.put("where", obj.toString());
        LeanCloudRequest.getFavoriteRelation(map)
                .subscribe(new Subscriber<FavoriteRelationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(FavoriteRelationResponse response) {
                        if (response.getResults() != null && response.getResults().size() > 0) {
                            List<String> listCategory = new ArrayList<String>();
                            HashSet set = new HashSet();
                            for (FavoriteRelationResponse.ResultsBean item : response.getResults()) {
                                set.add(item.getCategory());
                            }
                            listCategory.addAll(set);
                            mView.setFavorateCategory(listCategory);
                        } else {
                        }
                    }
                });

        //获取数据库数据
//        List<String> stringList = DbManager.getFavorateCategory();
//        mView.setFavorateCategory(stringList);
    }

    public String adjustCss(String string) {
        String str = string.replace("height: 200px;", "height: 0px;");
        str = str.replace("margin: 20px 0;", "margin: 50px 0 20px;");
//        str = str.replace("content { color: #444;", "content { color: #039BE5;");//查看知乎讨论
        str = str.replace("question-title {\n" +
                "  line-height: 1.4em;\n" +
                "  color: #000;", "question-title {\n" +
                "  line-height: 1.4em;\n" +
                "  color: #039BE5;");//查看知乎讨论
        return "<style type=\"text/css\">" + str + "</style>" + bean.getBody();
    }
}
