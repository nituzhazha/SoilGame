package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.EnchantmentSilkTouch;

public class jzmc extends EnchantmentSilkTouch{

    public Item item() {
        Item item = new Item(269, 0, 1);
        String name = "§3精准木铲";
        item.setCustomName(name);
        item.addEnchantment(this);
        return item;
    }

}