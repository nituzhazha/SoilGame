package nitu.Task;

import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import nitu.Entity.seller;
import nitu.item.bitem;
import nitu.item.ritem;
import nitu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class WaitTask extends Task {

    public main se;

    public int time;

    public int givetime;

    public String RoomName;


    public WaitTask(main s , int t , String r){
        se = s;
        time = t;
        givetime = t-5;
        RoomName = r;
    }

    public Config GD() {
        File GameDynamicData = new File(se.getDataFolder() + "/Rooms/" + RoomName + "/GameDynamicData.yml");
        return new Config(GameDynamicData);
    }

    public Config GS() {
        File level = new File(se.getDataFolder() + "/Rooms/" + RoomName + "/GameSetting.yml");
        return new Config(level);
    }


    public void pl(int t){
        String[] pls = ((ArrayList<String>) GD().get("等待玩家")).toArray(new String[0]);

        for(String pn : pls){
            se.getServer().getPlayer(pn).sendTip("等待倒计时  " + "§7" + String.valueOf(t));
        }

    }

    public void pll(String s){
        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);

        for(String pn : pls){
            se.getServer().getPlayer(pn).sendMessage(s);
        }

    }

    public void plll(){
        String[] pls = ((ArrayList<String>) GD().get("等待玩家")).toArray(new String[0]);

        for(String pn : pls){
            se.getServer().getPlayer(pn).sendMessage("有人退出人数不足游戏重新进入等待状态");
            se.getServer().getPlayer(pn).getInventory().clearAll();
        }

    }


    public void give(){
        String[] pls = ((ArrayList<String>) GD().get("等待玩家")).toArray(new String[0]);

        for(String pn : pls){
            se.getServer().getPlayer(pn).getInventory().addItem(new Item[]{new ritem().item() , new bitem().item()});
            se.getServer().getPlayer(pn).sendTip("选择队伍的物品已经发放至你的背包");
        }

    }

    public BlockEntitySign bes(){
        HashMap<String , Double> pos = (HashMap<String, Double>) GS().get("牌子的位置");
        if(pos.get("x") != null) {
            return (BlockEntitySign) se.getServer().getLevelByName(GS().get("牌子的地图").toString()).getBlockEntity(new Vector3(Double.parseDouble(pos.get("x").toString()), Double.parseDouble(pos.get("y").toString()), Double.parseDouble(pos.get("z").toString())));
        }else{
            return null;
        }
    }

    public String[] name(){
        ArrayList<String> list = (ArrayList<String>) GD().get("等待玩家");
        return new String[]{"§7Soil§5Game" , "§e房间" + "§3" + RoomName , "游戏中"};
    }


    public void choose(){
        String[] pls = ((ArrayList<String>) GD().get("等待玩家")).toArray(new String[0]);
        ArrayList<String> bt = (ArrayList<String>) GD().get("蓝方队员");
        ArrayList<String> rt = (ArrayList<String>) GD().get("红方队员");
        Config gd = GD();

        for(String pn : pls){

            if(!bt.contains(pn) && !rt.contains(pn)){

                HashMap<Integer, String> teams = new HashMap<>();
                teams.put(0 , "红方队员");
                teams.put(1 , "蓝方队员");

                int random = ThreadLocalRandom.current().nextInt(0, 2);
                String team = teams.get(random);

                ArrayList<String> weizhi = (ArrayList<String>) GD().get(team);

                weizhi.add(pn);

                gd.set(team , weizhi);
                gd.save();

                se.getServer().getPlayer(pn).sendTitle("你随机成为了" + team);
                se.getServer().getPlayer(pn).getInventory().clearAll();
                
            }

        }

    }

    public long sellerSet(String name){

        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);

        HashMap<String , Double> p = (HashMap<String, Double>) GS().get(name + "出生点");

        Position pos = new Position(p.get("x") , p.get("y") , p.get("z") , se.getServer().getLevelByName(RoomName));

        CompoundTag tag = Entity.getDefaultNBT(new Vector3(p.get("x") , p.get("y") , p.get("z")));

        FullChunk chunk = pos.getChunk();

        seller sell = new seller(chunk , tag);

        sell.setHealth(20);

        sell.setNameTag(name);

        sell.setScale((float) 1.0);

        sell.setNameTagAlwaysVisible();

        sell.spawnToAll();

        return sell.getId();
    }



    public void tp(){
        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);
        ArrayList<String> arr1 = (ArrayList<String>)GD().get("蓝方队员");
        ArrayList<String> arr2 = (ArrayList<String>)GD().get("红方队员");
        HashMap<String , Double> arr3 = (HashMap<String, Double>) GS().get("蓝队出生点");
        HashMap<String , Double> arr4 = (HashMap<String, Double>) GS().get("红队出生点");


        for(String pn : pls){

            if(arr1.contains(pn)){
                se.getServer().getPlayer(pn).teleport(new Position(arr3.get("x") , arr3.get("y") , arr3.get("z") , se.getServer().getLevelByName(RoomName)));
                se.getServer().getPlayer(pn).setSpawn(new Vector3(arr3.get("x") , arr3.get("y") , arr3.get("z")));
            }

            if(arr2.contains(pn)){
                se.getServer().getPlayer(pn).teleport(new Position(arr4.get("x") , arr4.get("y") , arr4.get("z") , se.getServer().getLevelByName(RoomName)));
                se.getServer().getPlayer(pn).setSpawn(new Vector3(arr4.get("x") , arr4.get("y") , arr4.get("z")));
            }

        }

    }

    @Override
    public void onRun(int i) {


        if(((ArrayList<String>)GD().get("等待玩家")).size() < Integer.parseInt(GS().get("最小玩家数量").toString())){

            plll();

            Config gd = GD();

            ArrayList<String> arr2 = (ArrayList<String>)GD().get("蓝方队员");
            ArrayList<String> arr3 = (ArrayList<String>)GD().get("红方队员");
            arr2.removeAll(arr2);
            arr3.removeAll(arr3);

            gd.set("游戏是否开始" , false);
            gd.set("蓝方队员" , arr2);
            gd.set("红方队员" , arr3);
            gd.save();
            se.getServer().getScheduler().scheduleRepeatingTask(new CheckTask(se , RoomName) , 20);
            this.getHandler().cancel();
        }

        if(time == givetime){
            give();
        }


        if(time == 1){
            Config gd = GD();
            ArrayList<String> arr = (ArrayList<String>)GD().get("等待玩家");
            gd.set("玩家" , arr);
            gd.save();
            choose();
        }

         pl(time);
         time--;


         if(time == 0){

             Config gd = GD();
             ArrayList<String> arr = (ArrayList<String>)GD().get("等待玩家");
             arr.removeAll(arr);
             gd.set("等待玩家" , arr);
             gd.set("游戏是否开始" ,true);
             gd.save();

             bes().setText(name());

             pll("游戏开始");
             tp();
             se.getServer().getScheduler().scheduleRepeatingTask(new GameTask(se , Integer.parseInt(GS().get("游戏时间").toString()) , sellerSet("红队商人") , sellerSet("蓝队商人") , RoomName) , 20);
             this.getHandler().cancel();
         }

    }



}