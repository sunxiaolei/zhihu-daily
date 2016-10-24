package xiaolei.sun.zhihu_daily.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteCategory;

/**
 * Created by sunxl8 on 2016/10/24.
 */

public class DbManager {

    public static List<String> getFavorateCategory(){
        List<DbFavoriteCategory> list = DataSupport.findAll(DbFavoriteCategory.class);
        List<String> stringList = new ArrayList<>();
        for (DbFavoriteCategory item : list) {
            stringList.add(item.getName());
        }
        return stringList;
    }
}
