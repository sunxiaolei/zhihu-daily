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

    public static List<String> getFavorateCategory() {
        List<DbFavoriteCategory> list = DataSupport.findAll(DbFavoriteCategory.class);
        List<String> stringList = new ArrayList<>();
        for (DbFavoriteCategory item : list) {
            stringList.add(item.getName());
        }
        return stringList;
    }

    public static List<DbStory> getStoriesByFavorateCategory(String category) {
        List<DbStory> list = DataSupport.where("favoriteCategory = ?", category).find(DbStory.class);
        return list;
    }

    public static List<Integer> getStoryIds() {
        List<DbStory> listStories = DataSupport.findAll(DbStory.class);
        List<Integer> listIds = new ArrayList<>();
        for (DbStory story : listStories) {
            listIds.add(story.getId());
        }
        return listIds;
    }

}
