package xiaolei.sun.zhihu_daily.network.entity.zhihu;

import java.io.Serializable;
import java.util.List;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class NewsBean implements Serializable{

    /**
     * date : 20160921
     * stories : [{"images":["http://pic3.zhimg.com/800792d19b709bdea4ce5436bc33e19e.jpg"],"type":0,"id":8816472,"ga_prefix":"092122","title":"小事 · 被祸害的童年"},{"images":["http://pic1.zhimg.com/4e419c32831768f2df69c16667fd6cd4.jpg"],"type":0,"id":8815737,"ga_prefix":"092121","title":"「结婚并不是终点，也不是逃避之途」"},{"images":["http://pic2.zhimg.com/af8884ddb341d6736b08144a7b52cfe1.jpg"],"type":0,"id":8816618,"ga_prefix":"092120","title":"上班族该如何计划和打理阳台绿化？"},{"images":["http://pic4.zhimg.com/5902e05fc2c867dcafd2e5d5bcae17d3.jpg"],"type":0,"id":8792845,"ga_prefix":"092119","title":"水是生命，卫生是尊严，那这水费，该怎么收才合理？"},{"title":"中国也有了米其林餐厅 ：这些我喜欢， 那些我可能不会去","ga_prefix":"092118","images":["http://pic1.zhimg.com/b0f3de6a9617195d7456256fd8bdcc98.jpg"],"multipic":true,"type":0,"id":8815855},{"images":["http://pic1.zhimg.com/7b34da249c7f5cf1495315fc868bbf60.jpg"],"type":0,"id":8812568,"ga_prefix":"092117","title":"知乎好问题 Plus · 国庆怎么玩，加量不加价"},{"images":["http://pic3.zhimg.com/b6ccc46b3211528fc505bbae0ef0fd1e.jpg"],"type":0,"id":8815523,"ga_prefix":"092116","title":"关于「育儿理念分歧」，可以试试这几条通用的沟通原则"},{"images":["http://pic3.zhimg.com/bdc7ca3df04a10161bc1a12ef8a8d8c6.jpg"],"type":0,"id":8815031,"ga_prefix":"092115","title":"你可能自己不知道，但你是没法控制眼球扫视的速度的"},{"images":["http://pic2.zhimg.com/8bb2593989e6e6295cf2dab37ff546d5.jpg"],"type":0,"id":8815218,"ga_prefix":"092114","title":"兰博基尼也有人贷款买，0% 利率的车贷该这么用"},{"images":["http://pic2.zhimg.com/5bf331aee528e49f9a94a34a176d11a5.jpg"],"type":0,"id":8812870,"ga_prefix":"092113","title":"欧盟要罚钱，苹果不服，日本要罚钱，苹果默默地交了"},{"images":["http://pic2.zhimg.com/2c40871d53d2506211dd99fe06b4fba1.jpg"],"type":0,"id":8810138,"ga_prefix":"092112","title":"大误 · 地球上只有牡蛎一种智慧生命"},{"images":["http://pic3.zhimg.com/c1f509bdf34e5094068ec73a1d375bde.jpg"],"type":0,"id":8813066,"ga_prefix":"092111","title":"如何正确地培养天才，长达 45 年的神童研究报告这么说"},{"images":["http://pic4.zhimg.com/4efabc5e7a0e56a575246493f1f6ac3f.jpg"],"type":0,"id":8814007,"ga_prefix":"092110","title":"涨价 5500% 的药都能有，说说美国药企和医保的那点事儿"},{"images":["http://pic1.zhimg.com/b7d60eb8cce13a923effdbaa686466fc.jpg"],"type":0,"id":8805036,"ga_prefix":"092109","title":"爸妈和孩子可以嘴对嘴亲亲吗？"},{"images":["http://pic4.zhimg.com/aad9ecf7aba05a2b931d2d82c28de003.jpg"],"type":0,"id":8814021,"ga_prefix":"092108","title":"思考的时候，感觉大脑里有个人在跟自己说话，是谁？"},{"images":["http://pic3.zhimg.com/2cbc39edbcac5e69e889a0106b800d36.jpg"],"type":0,"id":8813828,"ga_prefix":"092107","title":"人行天桥的台阶之痛：「跨两步太远，走一步太短」"},{"images":["http://pic3.zhimg.com/e6484718ea285ae81c46d49874389e12.jpg"],"type":0,"id":8813049,"ga_prefix":"092107","title":"Apple Watch 升级到了 Series 2，功能还是很被动"},{"title":"亲身逛了亚马逊的实体书店，被「人为数据打工」惊呆了","ga_prefix":"092107","images":["http://pic3.zhimg.com/3987d72346cad6464adf1e11dedf0ada.jpg"],"multipic":true,"type":0,"id":8805534},{"images":["http://pic1.zhimg.com/e6208ae7529201c17857d8ac4e031688.jpg"],"type":0,"id":8813899,"ga_prefix":"092107","title":"读读日报 24 小时热门 TOP 5 · 「首善」还是「首骗」"},{"images":["http://pic3.zhimg.com/29cb438fbaf6e4f64a4b56392183daa6.jpg"],"type":0,"id":8809230,"ga_prefix":"092106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/615c27fe9835358297bed972e52ed791.jpg","type":0,"id":8815855,"ga_prefix":"092118","title":"中国也有了米其林餐厅 ：这些我喜欢， 那些我可能不会去"},{"image":"http://pic3.zhimg.com/b312b41ef51be11f3aee446dbe5b67a2.jpg","type":0,"id":8812568,"ga_prefix":"092117","title":"知乎好问题 Plus · 国庆怎么玩，加量不加价"},{"image":"http://pic2.zhimg.com/73e7351e55196624c48e04f3ef9923c9.jpg","type":0,"id":8815523,"ga_prefix":"092116","title":"关于「育儿理念分歧」，可以试试这几条通用的沟通原则"},{"image":"http://pic4.zhimg.com/fa2e759bebe3d5863331473b086caf0f.jpg","type":0,"id":8813899,"ga_prefix":"092107","title":"读读日报 24 小时热门 TOP 5 · 「首善」还是「首骗」"},{"image":"http://pic3.zhimg.com/84ef825ab9c461769d5f8d91f0811b66.jpg","type":0,"id":8812870,"ga_prefix":"092113","title":"欧盟要罚钱，苹果不服，日本要罚钱，苹果默默地交了"}]
     */

    private String date;

    private List<StoriesBean> stories;

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }


}
