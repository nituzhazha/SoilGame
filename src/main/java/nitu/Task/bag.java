package nitu.Task;

import cn.nukkit.item.Item;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import nitu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class bag extends AsyncTask {

    public main se;
    public String RoomName;

    public bag(main s , String r){
    RoomName = r;
    se = s;
    }

    public Config GD() {
        File GameDynamicData = new File(se.getDataFolder() + "/Rooms/" + RoomName + "/GameDynamicData.yml");
        return new Config(GameDynamicData);
    }


    public Config IV () {
        File PlayerInventoryData = new File(se.getDataFolder() + "/Rooms/" + RoomName + "/PlayerInventoryData.yml");
        return new Config(PlayerInventoryData);
    }

    public void ReturnBag(){

        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);

        for (String pn : pls) {

            Config llv = IV();
            HashMap<Integer, HashMap<String, Object>> all = (HashMap<Integer, HashMap<String, Object>>) llv.get(pn);

            for (int i = 0; i < 36 ; i++) {

                HashMap<String, Object> single = all.get(i);

                Item item;

                if(Integer.parseInt(single.get("id").toString())  != 0) {
                    item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()));

                    if(!single.get("nbt").toString().equals("无")){
                        item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()), single.get("nbt").toString().getBytes());
                    }

                    se.getServer().getPlayer(pn).getInventory().addItem(item);
                }

                single.clear();
            }

            llv.remove(pn);
            llv.save();
            se.getServer().getPlayer(pn).sendMessage("你的背包已经归还");

        }
    }



    @Override
    public void onRun() {
        ReturnBag();
    }

}