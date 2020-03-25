package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.protection.EnchantmentProtectionAll;
import cn.nukkit.nbt.tag.CompoundTag;

public class bitem{

    public Item item() {
        Item item = new Item(351, 6, 1);
        String name = "§3蓝队";
        EnchantmentProtectionAll en = new EnchantmentProtectionAll();
        CompoundTag tag = (new CompoundTag()).putString("color" , "blue");
        item.setNamedTag(tag);
        item.setCustomName(name);
        item.addEnchantment(new Enchantment[]{en});
        return item;
    }

}