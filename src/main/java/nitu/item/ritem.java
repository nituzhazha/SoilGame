package nitu.item;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.protection.EnchantmentProtectionAll;
import cn.nukkit.nbt.tag.CompoundTag;

public class ritem{

    public Item item() {
        Item item = new Item(351, 1, 1);
        String name = "§4红队";
        EnchantmentProtectionAll en = new EnchantmentProtectionAll();
        CompoundTag tag = (new CompoundTag()).putString("color" , "red");
        item.setNamedTag(tag);
        item.setCustomName(name);
        item.addEnchantment(new Enchantment[]{en});
        return item;
    }

}