package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.EnchantmentSilkTouch;

public class jzmg extends EnchantmentSilkTouch{

    public Item item() {
        Item item = new Item(270, 0, 1);
        String name = "§3精准木稿";
        item.setCustomName(name);
        item.addEnchantment(this);
        return item;
    }

}