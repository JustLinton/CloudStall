package com.berrysdu.cloudstall.func;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.berrysdu.cloudstall.R;
import com.berrysdu.cloudstall.objects.Item;
import com.berrysdu.cloudstall.objects.Stall;

public class DemoContentProvider {
    int type;
    Resources resources;
    public DemoContentProvider(int type_,Resources res){
        type=type_;
        resources=res;
    }
    public Item getItem(int position){
        switch (type){
            case Stall.TYPE_BOOK:
                return getPositionAtItemBook(position);
                case Stall.TYPE_FOOD:
                    return getPositionAtItemFood(position);
                    case Stall.TYPE_GROCERY:
                        return getPositionAtItemGrocery(position);
            default:
                return null;
        }
    }

    private Item getPositionAtItemBook(int position){
        Item item=new Item(1,"标题示例","描述示例",1);
        switch (position){
            case 1:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book1));
                item.setTitle("三毛悄悄说什么");
                item.setDescription("来聆听三毛的传奇故事");
                item.setBalance(20);
                return item;
            case 2:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book2));
                item.setTitle("黑痴白痴的故事");
                item.setDescription("属于丁丁当当的故事");
                item.setBalance(24);
                return item;
            case 3:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book3));
                item.setTitle("矛盾文集");
                item.setDescription("矛盾，文学大家的世界");
                item.setBalance(35);
                return item;
            case 4:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book4));
                item.setTitle("海鸥乔纳森");
                item.setDescription("一只海鸥的故事");
                item.setBalance(18);
                return item;
            case 5:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book5));
                item.setTitle("宝宝爱说话——词汇扩展");
                item.setDescription("儿童启蒙书，打折销售");
                item.setBalance(199);
                return item;
            case 6:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book6));
                item.setTitle("猜猜我是谁");
                item.setDescription("儿童启蒙连环画");
                item.setBalance(98);
                return item;
            case 7:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book7));
                item.setTitle("拇指班长（17）");
                item.setDescription("适合五到六年级，打折出售");
                item.setBalance(44);
                return item;
            case 8:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book8));
                item.setTitle("深度休息的哲学");
                item.setDescription("你的身心需要放松");
                item.setBalance(27);
                return item;
            case 9:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book9));
                item.setTitle("我们仨");
                item.setDescription("来自乡村的哲学");
                item.setBalance(92);
                return item;
            case 10:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book24));
                item.setTitle("草房子");
                item.setDescription("为人的智慧,尽在书中所现");
                item.setBalance(15);
                return item;
            case 11:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book28));
                item.setTitle("十岁那年");
                item.setDescription("老师学校推荐，5年级教育部数目推荐");
                item.setBalance(11);
                return item;
            case 12:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book29));
                item.setTitle("十二岁的旅程");
                item.setDescription("多买优惠，送著名文学家写作课");
                item.setBalance(34);
                return item;
            case 13:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book30));
                item.setTitle("论中国");
                item.setDescription("中国的智慧，在书中即见");
                item.setBalance(54);
                return item;
            case 14:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book32));
                item.setTitle("奈特人体解剖学图谱");
                item.setDescription("知名科技丛书");
                item.setBalance(250);
                return item;
            default:
                item.setPic(BitmapFactory.decodeResource(resources,R.drawable.book27));
                item.setTitle("（示例图书）亲爱的汉修先生");
                item.setDescription("仅供示例");
                item.setBalance(999);
                return item;
        }
    }
    private Item getPositionAtItemFood(int position){
        Item item=new Item(1,"标题示例","描述示例",1);
        switch (position) {
            case 1:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food4));
                item.setTitle("辣椒豆角");
                item.setDescription("独此一家");
                item.setBalance(22);
                return item;
            case 2:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food2));
                item.setTitle("鲜果时蔬拼盘");
                item.setDescription("健康美味~~~~~");
                item.setBalance(19);
                return item;
            case 3:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.material_design_1));
                item.setTitle("必胜客蔬菜沙拉");
                item.setDescription("健康减肥就看它");
                item.setBalance(30);
                return item;
            case 4:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food4));
                item.setTitle("鸡蛋牛油果");
                item.setDescription("美容养颜");
                item.setBalance(49);
                return item;
            case 5:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food5));
                item.setTitle("草莓冰激凌");
                item.setDescription("草莓的美味，回味无穷。");
                item.setBalance(12);
                return item;
            case 6:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food6));
                item.setTitle("扁豆意大利面");
                item.setDescription("纯正欧洲风味！");
                item.setBalance(66);
                return item;
            case 7:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food9));
                item.setTitle("西冷牛排（黑椒风味）");
                item.setDescription("纯正牛排，厚切满足");
                item.setBalance(89);
                return item;
            case 8:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.material_design_1));
                item.setTitle("抹茶果仁");
                item.setDescription("果仁与抹茶的完美交融，激发你的味蕾");
                item.setBalance(32);
                return item;
            case 9:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food9));
                item.setTitle("青青草原牛排");
                item.setDescription("青草的香味，独此一家。仅售108。");
                item.setBalance(108);
                return item;
            case 10:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food10));
                item.setTitle("草莓雪糕和车厘子");
                item.setDescription("双重美味，谁人能经得起诱惑");
                item.setBalance(19);
                return item;
            case 11:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food11));
                item.setTitle("熔岩奶酪蛋糕");
                item.setDescription("流体容颜，美容养颜");
                item.setBalance(55);
                return item;
            case 12:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food12));
                item.setTitle("草莓双皮奶");
                item.setDescription("双皮奶的醇香，草莓的清香相交融！");
                item.setBalance(21);
                return item;
            default:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.food19));
                item.setTitle("(示例) 草莓奶昔");
                item.setDescription("仅供示例，好喝的草莓奶昔。");
                item.setBalance(31);
                return item;
        }

    }

    private Item getPositionAtItemGrocery(int position){
        Item item=new Item(1,"标题示例","描述示例",1);
        switch (position) {
            case 1:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery1));
                item.setTitle("针织枕头套");
                item.setDescription("柔软舒适");
                item.setBalance(12);
                return item;
            case 2:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.material_design_11));
                item.setTitle("鲜玫瑰花束");
                item.setDescription("女朋友的心动。");
                item.setBalance(12);
                return item;
            case 3:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery3));
                item.setTitle("五彩花束");
                item.setDescription("美妙动人，芬芳清香！");
                item.setBalance(38);
                return item;
            case 4:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery4));
                item.setTitle("夜来香");
                item.setDescription("花如其名，夜来芸香");
                item.setBalance(42);
                return item;
            case 5:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery5));
                item.setTitle("牡丹花瓶");
                item.setDescription("上档次就完事了");
                item.setBalance(104);
                return item;
            case 6:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery10));
                item.setTitle("Nixco润唇膏");
                item.setDescription("还你热辣红唇");
                item.setBalance(202);
                return item;
            case 8:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery12));
                item.setTitle("NAHB洁面膏");
                item.setDescription("提亮光泽");
                item.setBalance(149);
                return item;
            case 9:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery13));
                item.setTitle("香奈儿睫毛刷");
                item.setDescription("柔软舒适");
                item.setBalance(495);
                return item;
            case 10:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery14));
                item.setTitle("小城故事粉饼");
                item.setDescription("用了以后blingbling的");
                item.setBalance(299);
                return item;
            case 11:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery17));
                item.setTitle("羊毛沙发");
                item.setDescription("柔软舒适");
                item.setBalance(872);
                return item;
            case 12:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery18));
                item.setTitle("黑金刀叉");
                item.setDescription("RMB的味道！");
                item.setBalance(4949);
                return item;
            default:
                item.setPic(BitmapFactory.decodeResource(resources, R.drawable.grocery19));
                item.setTitle("（仅供示例） 羊毛地毯");
                item.setDescription("柔软的羊毛，像驰骋在草原");
                item.setBalance(39);
                return item;
        }
    }

    public Bitmap getCover(int position){
        switch (type){
            case Stall.TYPE_UNKOWN:
                if(position==99){
                    return BitmapFactory.decodeResource(resources,R.drawable.stall1);
                }else {
                    return BitmapFactory.decodeResource(resources,R.drawable.stall310);
                }
            case Stall.TYPE_BOOK:
                if(position==99){
                    return BitmapFactory.decodeResource(resources,R.drawable.stall28);
                }else {
                    return BitmapFactory.decodeResource(resources,R.drawable.stall313);
                }
            case Stall.TYPE_FOOD:
                if(position==99){
                    return BitmapFactory.decodeResource(resources,R.drawable.stall311);
                }else {
                    return BitmapFactory.decodeResource(resources,R.drawable.stall312);
                }
            case Stall.TYPE_GROCERY:
                if(position==99){
                    return BitmapFactory.decodeResource(resources,R.drawable.stall39);
                }else {
                    return BitmapFactory.decodeResource(resources,R.drawable.stall315);
                }
            default:
                return BitmapFactory.decodeResource(resources,R.drawable.stall2);

        }
    }
}
