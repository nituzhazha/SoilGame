package nitu.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;

public class shop{

    public static final int SHOP = 448293485;
    public static final int BLOCK = 342432234;
    public static final int WEAPON = 556452522;
    public static final int TOOL =  349283492;
    public static final int ARMOR = 485783453;
    public static final int ITEM = 490589843;


    public void send(Player pl, FormWindow window, int id) {
        pl.showFormWindow(window, id);
    }


    public void sendShop(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");


                window.addButton(new ElementButton("§1方块"));
                window.addButton(new ElementButton("§2武器"));
                window.addButton(new ElementButton("§6工具"));
                window.addButton(new ElementButton("§7护甲"));
                window.addButton(new ElementButton("§e道具"));

                send(pl,window,SHOP);
    }

    public void block(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");

        window.addButton(new ElementButton("§5玻璃x16   §c2个泥土" ));
        window.addButton(new ElementButton("§1羊毛x16   §c4个泥土"));
        window.addButton(new ElementButton("§2木板x16   §c6个泥土"));
        window.addButton(new ElementButton("§3沙石x16   §c6个泥土"));
        window.addButton(new ElementButton("§4砖块x16   §c8个泥土"));

        send(pl,window,BLOCK);
    }

    public void weapom(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");


        window.addButton(new ElementButton("§1木剑  §c5个泥土"));
        window.addButton(new ElementButton("§2石剑  §c15个泥土"));
        window.addButton(new ElementButton("§3铁剑  §c5草方块5黏土块10个泥土"));
        window.addButton(new ElementButton("§4击退大棒子  §c5草方块"));
        window.addButton(new ElementButton("§5火焰附加木剑  §c6黏土块"));
        window.addButton(new ElementButton("§6弓   §c20草方块10黏土块"));
        window.addButton(new ElementButton("§7箭x8   §c3草方块"));

        send(pl,window,WEAPON);
    }

    public void tool(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");


        window.addButton(new ElementButton("§1木铲  §c8个泥土"));
        window.addButton(new ElementButton("§2石铲  §c16个泥土"));
        window.addButton(new ElementButton("§3精准木铲  §c20个泥土"));
        window.addButton(new ElementButton("§4木稿  §c5个泥土"));
        window.addButton(new ElementButton("§5石稿  §c5草方块"));
        window.addButton(new ElementButton("§6精准木稿   §c6黏土块"));
        window.addButton(new ElementButton("§7木斧  §c8个泥土"));
        window.addButton(new ElementButton("§e石斧  §c16个泥土"));

        send(pl,window,TOOL);
    }



    public void armor(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");


        window.addButton(new ElementButton("§1皮甲一套  §c10个泥土"));
        window.addButton(new ElementButton("§2金甲一套  §c20个泥土"));
        window.addButton(new ElementButton("§3锁甲一套  §c10草方块5个泥土"));
        window.addButton(new ElementButton("§4铁甲一套  §c20黏土块"));
        window.addButton(new ElementButton("§5钻石胸甲  §c10草方块10黏土块"));

        send(pl,window,ARMOR);
    }

    public void item(Player pl){

        FormWindowSimple window = new FormWindowSimple("§4SoilGame§3商店" , "§5欢迎使用");


        window.addButton(new ElementButton("§1箱子   §c10个泥土"));
        window.addButton(new ElementButton("§2回城胶囊  §c5草方块"));
        window.addButton(new ElementButton("§3面包x4   §c2个泥土"));
        window.addButton(new ElementButton("§4金苹果   §c5黏土块"));

        send(pl,window, ITEM);
    }

}