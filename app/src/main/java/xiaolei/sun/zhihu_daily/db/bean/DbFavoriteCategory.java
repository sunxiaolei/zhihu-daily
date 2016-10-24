package xiaolei.sun.zhihu_daily.db.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class DbFavoriteCategory extends DataSupport {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
