package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.EnchantmentKnockback;

public class dbz extends EnchantmentKnockback {

    public Item item() {
        Item item = new Item(369, 0, 1);
        String name = "§3击退棒子";
        item.setCustomName(name);
        item.addEnchantment(this);
        return item;
    }

}