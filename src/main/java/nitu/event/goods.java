package nitu.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ModalFormResponsePacket;
import nitu.gui.shop;
import nitu.item.*;

public class goods implements Listener {

    @EventHandler
    public void data(DataPacketReceiveEvent ev) {

        if (!(ev.getPacket() instanceof ModalFormResponsePacket)) return;

        ModalFormResponsePacket ui = (ModalFormResponsePacket) ev.getPacket();
        int id = ui.formId;
        String data = ui.data.trim();
        shop s = new shop();
        Player pl = ev.getPlayer();
        Inventory inv = pl.getInventory();

        if (id == shop.SHOP) {

            if (data.equals("null")) {
                return;
            }

            switch (Integer.parseInt(data)) {

                case 0:

                    s.block(pl);

                    break;

                case 1:

                    s.weapom(pl);

                    break;


                case 2:

                    s.tool(pl);

                    break;


                case 3:

                    s.armor(pl);

                    break;


                case 4:

                    s.item(pl);

                    break;

            }
        }


        if (id == shop.BLOCK) {

            if (data.equals("null")) {
                return;
            }


            switch (Integer.parseInt(data)) {

                case 0://玻璃 2土

                    Item it1 = Item.get(3, 0, 2);
                    Item w1 = Item.get(20, 0, 16);


                    if (inv.contains(it1)) {

                        inv.addItem(w1);
                        inv.removeItem(it1);

                        pl.sendMessage("兑换成功");
                        break;
                    } else {
                        pl.sendMessage("你的泥土不足");
                    }
                    break;

                case 1://羊毛 4土

                    Item it2 = Item.get(3, 0, 4);
                    Item w2 = Item.get(35, 0, 16);


                    if (inv.contains(it2)) {

                        inv.addItem(w2);
                        inv.removeItem(it2);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 2://木板 6土

                    Item it3 = Item.get(3, 0, 6);
                    Item w3 = Item.get(5, 0, 16);


                    if (inv.contains(it3)) {

                        inv.addItem(w3);
                        inv.removeItem(it3);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 3://沙石 6土

                    Item it4 = Item.get(3, 0, 6);
                    Item w4 = Item.get(24, 0, 16);


                    if (inv.contains(it4)) {

                        inv.addItem(w4);
                        inv.removeItem(it4);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 4://砖块 8土

                    Item it5 = Item.get(3, 0, 8);
                    Item w5 = Item.get(43, 4, 16);


                    if (inv.contains(it5)) {

                        inv.addItem(w5);
                        inv.removeItem(it5);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

            }
        }


        if (id == shop.WEAPON) {

            if (data.equals("null")) {
                return;
            }


            switch (Integer.parseInt(data)) {

                case 0://木剑 5土

                    Item it1 = Item.get(3, 0, 5);
                    Item w1 = Item.get(268, 0, 1);


                    if (inv.contains(it1)) {

                        inv.addItem(w1);
                        inv.removeItem(it1);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 1://石剑 15土

                    Item it2 = Item.get(3, 0, 15);
                    Item w2 = Item.get(272, 0, 1);


                    if (inv.contains(it2)) {

                        inv.addItem(w2);
                        inv.removeItem(it2);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 2://铁剑 5草方块5粘土块10泥土

                    Item it3 = Item.get(3, 0, 10);
                    Item i3 = Item.get(2, 0, 5);
                    Item t3 = Item.get(82, 0, 5);
                    Item w3 = Item.get(267, 0, 1);


                    if (inv.contains(it3) && inv.contains(i3) && inv.contains(t3)) {

                        inv.addItem(w3);
                        inv.removeItem(it3);
                        inv.removeItem(t3);
                        inv.removeItem(i3);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 3://击退棒子 5草方块

                    Item it4 = Item.get(2, 0, 5);
                    Item w4 = new dbz().item();


                    if (inv.contains(it4)) {

                        inv.addItem(w4);
                        inv.removeItem(it4);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 4://火焰附加木剑 6粘土块

                    Item it5 = Item.get(82, 0, 6);
                    Item w5 = new hyj().item();


                    if (inv.contains(it5)) {

                        inv.addItem(w5);
                        inv.removeItem(it5);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 5://弓 20草方块10粘土块

                    Item it6 = Item.get(2, 0, 20);
                    Item i6 = Item.get(82, 0, 10);
                    Item w6 = Item.get(261, 0, 1);


                    if (inv.contains(it6)) {

                        inv.addItem(w6);
                        inv.removeItem(it6);
                        inv.removeItem(i6);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 6://箭 3草方块

                    Item it7 = Item.get(2, 0, 3);
                    Item w7 = Item.get(262, 0, 8);


                    if (inv.contains(it7)) {

                        inv.addItem(w7);
                        inv.removeItem(it7);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

            }
        }


        if (id == shop.TOOL) {

            if (data.equals("null")) {
                return;
            }


            switch (Integer.parseInt(data)) {

                case 0://木铲 8土

                    Item it1 = Item.get(3, 0, 8);
                    Item w1 = Item.get(269, 0, 1);


                    if (inv.contains(it1)) {

                        inv.addItem(w1);
                        inv.removeItem(it1);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 1://石铲 16土

                    Item it2 = Item.get(3, 0, 16);
                    Item w2 = Item.get(273, 0, 1);


                    if (inv.contains(it2)) {

                        inv.addItem(w2);
                        inv.removeItem(it2);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 2://精准木铲 20泥土

                    Item it3 = Item.get(3, 0, 20);
                    Item w3 = new jzmc().item();


                    if (inv.contains(it3)) {

                        inv.addItem(w3);
                        inv.removeItem(it3);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 3://木稿 5土

                    Item it4 = Item.get(3, 0, 5);
                    Item w4 = Item.get(270, 0, 1);


                    if (inv.contains(it4)) {

                        inv.addItem(w4);
                        inv.removeItem(it4);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 4://石稿 5草方块

                    Item it5 = Item.get(2, 0, 5);
                    Item w5 = Item.get(274, 0, 1);


                    if (inv.contains(it5)) {

                        inv.addItem(w5);
                        inv.removeItem(it5);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 5://精准木稿 6粘土块

                    Item it6 = Item.get(82, 0, 6);
                    Item w6 = new jzmg().item();


                    if (inv.contains(it6)) {

                        inv.addItem(w6);
                        inv.removeItem(it6);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 6://木斧 8泥土

                    Item it7 = Item.get(3, 0, 8);
                    Item w7 = Item.get(271, 0, 1);


                    if (inv.contains(it7)) {

                        inv.addItem(w7);
                        inv.removeItem(it7);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 7://石斧 16土

                    Item it8 = Item.get(3, 0, 16);
                    Item w8 = Item.get(275, 0, 1);


                    if (inv.contains(it8)) {

                        inv.addItem(w8);
                        inv.removeItem(it8);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

            }
        }


        if (id == shop.ARMOR) {

            if (data.equals("null")) {
                return;
            }


            switch (Integer.parseInt(data)) {

                case 0://皮甲一套 10土

                    Item it1 = Item.get(3, 0, 10);
                    Item a1 = Item.get(298, 0, 1);
                    Item b1 = Item.get(299, 0, 1);
                    Item c1 = Item.get(300, 0, 1);
                    Item d1 = Item.get(301, 0, 1);

                    if (inv.contains(it1)) {

                        inv.addItem(a1);
                        inv.addItem(b1);
                        inv.addItem(c1);
                        inv.addItem(d1);
                        inv.removeItem(it1);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 1://金甲一套 20土

                    Item it2 = Item.get(3, 0, 20);
                    Item a2 = Item.get(314, 0, 1);
                    Item b2 = Item.get(315, 0, 1);
                    Item c2 = Item.get(316, 0, 1);
                    Item d2 = Item.get(317, 0, 1);


                    if (inv.contains(it2)) {

                        inv.addItem(a2);
                        inv.addItem(b2);
                        inv.addItem(c2);
                        inv.addItem(d2);
                        inv.removeItem(it2);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 2://锁甲一套 10草方块5泥土

                    Item it3 = Item.get(2, 0, 10);
                    Item i3 = Item.get(3, 0, 15);
                    Item a3 = Item.get(302, 0, 1);
                    Item b3 = Item.get(303, 0, 1);
                    Item c3 = Item.get(304, 0, 1);
                    Item d3 = Item.get(305, 0, 1);


                    if (inv.contains(it3) && inv.contains(i3)) {

                        inv.addItem(a3);
                        inv.addItem(b3);
                        inv.addItem(c3);
                        inv.addItem(d3);
                        inv.removeItem(it3);
                        inv.removeItem(i3);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 3://铁甲一套 20粘土块

                    Item it4 = Item.get(82, 0, 20);
                    Item a4 = Item.get(307, 0, 1);
                    Item b4 = Item.get(308, 0, 1);
                    Item c4 = Item.get(309, 0, 1);
                    Item d4 = Item.get(306, 0, 1);


                    if (inv.contains(it4)) {

                        inv.addItem(a4);
                        inv.addItem(b4);
                        inv.addItem(c4);
                        inv.addItem(d4);
                        inv.removeItem(it4);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 4://钻石胸甲 10草块10粘土块

                    Item it5 = Item.get(3, 0, 10);
                    Item i5 = Item.get(82, 0, 10);
                    Item w5 = Item.get(311, 0, 1);


                    if (inv.contains(it5) && inv.contains(i5)) {

                        inv.addItem(w5);
                        inv.removeItem(it5);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

            }
        }


        if (id == shop.ITEM) {

            if (data.equals("null")) {
                return;
            }


            switch (Integer.parseInt(data)) {

                case 0://箱子 10土

                    Item it1 = Item.get(3, 0, 10);
                    Item w1 = Item.get(146, 0, 1);


                    if (inv.contains(it1)) {

                        inv.addItem(w1);
                        inv.removeItem(it1);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;

                case 1://回城 5草块

                    Item it2 = Item.get(2, 0, 5);
                    Item w2 = new hc().item();


                    if (inv.contains(it2)) {

                        inv.addItem(w2);
                        inv.removeItem(it2);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 2://面包4 2土

                    Item it3 = Item.get(3, 0, 2);
                    Item w3 = Item.get(297, 0, 4);


                    if (inv.contains(it3)) {

                        inv.addItem(w3);
                        inv.removeItem(it3);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


                case 3://金苹果 5粘土

                    Item it4 = Item.get(82, 0, 5);
                    Item w4 = Item.get(322, 0, 1);


                    if (inv.contains(it4)) {

                        inv.addItem(w4);
                        inv.removeItem(it4);
                        pl.sendMessage("兑换成功");

                    } else {
                        pl.sendMessage("你的泥土不足");
                    }

                    break;


            }
        }

    }


}