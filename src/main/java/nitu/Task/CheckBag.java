package nitu.Task;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import nitu.main;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class CheckBag extends AsyncTask {

    public main se;
    public File Rooms;
    public Player player;

    public CheckBag(main s, File r, Player n) {
        Rooms = r;
        se = s;
        player = n;
    }


    public Config IV(String r) {
        File PlayerInventoryData = new File(se.getDataFolder() + "/Rooms/" + r + "/PlayerInventoryData.yml");
        return new Config(PlayerInventoryData);
    }

    public void CheckBag() {

        if (Objects.requireNonNull(Rooms.listFiles()).length != 0) {

            for (File room : Objects.requireNonNull(Rooms.listFiles())) {

                if (IV(room.getName().trim()).get(player.getName()) != null) {
                    Config llv = IV(room.getName().trim());
                    HashMap<Integer, HashMap<String, Object>> all = (HashMap<Integer, HashMap<String, Object>>) llv.get(player.getName());

                    for (int i = 0; i < 36; i++) {

                        HashMap<String, Object> single = all.get(i);

                        Item item;

                        if (Integer.parseInt(single.get("id").toString()) != 0) {
                            item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()));

                            if (!single.get("nbt").toString().equals("无")) {
                                item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()), single.get("nbt").toString().getBytes());
                            }

                            player.getInventory().addItem(item);
                        }

                        single.clear();
                    }

                    llv.remove(player.getName());
                    llv.save();
                    player.sendMessage("你的背包已经归还");
                }


            }

        }
    }


    @Override
    public void onRun() {
        CheckBag();
    }

}