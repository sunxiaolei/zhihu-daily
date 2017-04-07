package xiaolei.sun.zhihu_daily.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteCategory;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;

/**
 * Created by sunxl8 on 2016/10/24.
 */

public class DbManager {

    /**
     * 获取收藏类别列表
     * @return
     */
    public static List<String> getFavorateCategory() {
        List<DbFavoriteCategory> list = DataSupport.findAll(DbFavoriteCategory.class);
        List<String> stringList = new ArrayList<>();
        for (DbFavoriteCategory item : list) {
            stringList.add(item.getName());
        }
        return stringList;
    }

    /**
     * 根据收藏类别获取收藏文章列表
     * @param category
     * @return
     */
    public static List<DbStory> getStoriesByFavorateCategory(String category) {
        List<DbStory> list = DataSupport.where("favoriteCategory = ?", category).find(DbStory.class);
        return list;
    }

    /**
     * 根据标题获取收藏文章
     * @param title
     * @return
     */
    public static List<DbStory> getStoriesByTitle(String title) {
        List<DbStory> list = DataSupport.where("title = ?", title).find(DbStory.class);
        return list;
    }

    /**
     * 获取已收藏文章id列表
     * @return
     */
    public static List<Integer> getFavoriteStoryIds() {
        List<DbStory> listStories = DataSupport.findAll(DbStory.class);
        List<Integer> listIds = new ArrayList<>();
        for (DbStory story : listStories) {
            listIds.add(story.getStoryId());
        }
        return listIds;
    }

    /**
     * 根据id判断是否已收藏
     * @param id
     * @return
     */
    public static boolean isFavorite(int id){
        List<Integer> listIds = getFavoriteStoryIds();
        if (listIds.contains(id)){
            return true;
        }else {
            return false;
        }
    }

}
