package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.EnchantmentFireAspect;

public class hyj extends EnchantmentFireAspect{

    public Item item() {
        Item item = new Item(268, 0, 1);
        String name = "§3火焰剑";
        item.setCustomName(name);
        item.addEnchantment(this);
        return item;
    }

}