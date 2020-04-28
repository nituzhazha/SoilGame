package nitu.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import nitu.Entity.seller;
import nitu.Task.CheckBag;
import nitu.Task.bag;
import nitu.gui.shop;
import nitu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class eventListener implements Listener {

    public main se;

public eventListener(main s){
    se = s;
}


    public Config GS (String r) {
        File level = new File(se.getDataFolder() + "/Rooms/" + r + "/GameSetting.yml");
        return new Config(level);
    }

    public Config GD (String r) {
        File GameDynamicData = new File(se.getDataFolder() + "/Rooms/" + r + "/GameDynamicData.yml");
        return new Config(GameDynamicData);
    }

    public Config IV (String r) {
        File PlayerInventoryData = new File(se.getDataFolder() + "/Rooms/" + r + "/PlayerInventoryData.yml");
        return new Config(PlayerInventoryData);
    }

    public Config CD() {
        File CmdDynamicData = new File(se.getDataFolder() + "/CmdDynamicData.yml");
        return new Config(CmdDynamicData);
    }

    public File RMS () {
        return new File(se.getDataFolder() + "/Rooms");
    }


    public ArrayList<String> ROS(){
        ArrayList<String> list = new ArrayList<>();

        for(File room : Objects.requireNonNull(RMS().listFiles())) {
            list.add(room.getName().trim());
        }

        return list;
    }


   @EventHandler(priority = EventPriority.HIGH)
   public void Interact(PlayerInteractEvent ev){

       Player pl = ev.getPlayer();
       String en = pl.getLevel().getName().trim();
       Block bl = ev.getBlock();
       Item it = ev.getItem();
       Level lv = pl.getLevel();
       Vector3 vec = new Vector3(bl.getX() , bl.getY() , bl.getZ());
       BlockEntity bk = lv.getBlockEntity(vec);
       String[] pzz = ROS().toArray(new String[0]);

       if(bk instanceof BlockEntitySign) {
           for (String pzn : pzz) {

               HashMap<String, Double> pz = (HashMap<String, Double>) GS(pzn).get("牌子的位置");

               if (pz.get("x") != null && pz.get("y") != null && pz.get("z") != null) {

                   if (en.equals(GS(pzn).get("牌子的地图").toString()) && Double.parseDouble(pz.get("x").toString()) == bl.getX() && Double.parseDouble(pz.get("y").toString()) == bl.getY() && Double.parseDouble(pz.get("z").toString()) == bl.getZ()) {


                       ArrayList<String> arr = (ArrayList<String>) GD(pzn).get("等待玩家");
                       HashMap<String, Double> pos = (HashMap<String, Double>) GS(pzn).get("等待区域");

                       if (!arr.contains(pl.getName())) {

                           if (!Boolean.parseBoolean(GD(pzn).get("游戏是否开始").toString())) {

                               if (arr.size() >= Integer.parseInt(GS(pzn).get("最大玩家数量").toString())) {
                                   pl.sendMessage("人数已满");
                               } else {
                                   Config gd = GD(pzn);

                                   Config iv = IV(pzn);

                                   HashMap<Integer , HashMap<String , Object>> all = new HashMap<>();


                                   for(int i = 0 ; i < 36 ; i++) {

                                       HashMap<String , Object> single = new HashMap<>();
                                       int id = pl.getInventory().getItem(i).getId();
                                       int damage = pl.getInventory().getItem(i).getDamage();
                                       int count = pl.getInventory().getItem(i).getCount();
                                       String nbt = pl.getInventory().getItem(i).hasCompoundTag() ? new String(pl.getInventory().getItem(i).getCompoundTag()) : "无";
                                       single.put("id" , id);
                                       single.put("damage" , damage);
                                       single.put("count" , count);
                                       single.put("nbt" , nbt);
                                       all.put(i , single);
                                   }

                                   iv.set(pl.getName(), all);
                                   iv.save();

                                   arr.add(pl.getName());
                                   gd.set("等待玩家", arr);
                                   gd.save();



                                   Position po = new Position(Double.parseDouble(pos.get("x").toString()), Double.parseDouble(pos.get("y").toString()), Double.parseDouble(pos.get("z").toString()), se.getServer().getLevelByName(pzn));
                                   pl.teleport(po);
                                   pl.getInventory().clearAll();

                                   pl.setMaxHealth(20);
                                   pl.setHealth(20);
                                   pl.setFoodEnabled(true);

                                   arr.clear();

                                   pl.sendMessage("已将您传送至等待区");
                                   break;
                               }
                           } else {
                               pl.sendMessage("游戏已经开始你无法进入");
                           }
                       } else {
                           pl.sendMessage("你已经在游戏房间了");
                       }
                   }
               }
           }
       }


       if(ROS().contains(en)) {

           if (!Boolean.parseBoolean(GD(en).get("游戏是否开始").toString())) {

               Config list = GD(en);
               ArrayList<String> arr = (ArrayList<String>) list.get("等待玩家");

               if (arr.contains(pl.getName())) {

                   if (it.hasCompoundTag() && it.hasCustomName()) {


                       ArrayList<String> rteam = (ArrayList<String>) list.get("红方队员");
                       ArrayList<String> bteam = (ArrayList<String>) list.get("蓝方队员");

                       if (it.getNamedTag().getString("color").equals("red")) {
                           rteam.add(pl.getName());
                           pl.getInventory().clearAll();
                           pl.sendTip("你选择了红队");
                           list.set("红方队员", rteam);
                           list.save();
                       }


                       if (it.getNamedTag().getString("color").equals("blue")) {
                           bteam.add(pl.getName());
                           pl.getInventory().clearAll();
                           pl.sendTip("你选择了蓝队");
                           list.set("蓝方队员", bteam);
                           list.save();
                       }

                   }

               }
           }
       }






    if(Boolean.parseBoolean(CD().get("选择牌子").toString())) {

        String fj = CD().get("游戏房间").toString();

        if(bk instanceof BlockEntitySign){

            String[] text = {"§7Soil§5Game" , "§e房间" + "§3" + fj , "§4点击加入" , "0" + "/" + GS(fj).get("最大玩家数量").toString()};
            HashMap<String , Double> list = new HashMap<>();

            Config gs = GS(fj);
            Config cd = CD();

            ((BlockEntitySign) bk).setText(text);

            list.put("x" , bl.getX());
            list.put("y" , bl.getY());
            list.put("z" , bl.getZ());

            gs.set("牌子的地图" , pl.getLevel().getName());
            gs.set("牌子的位置" , list);
            gs.save();

            cd.set("选择牌子" , false);
            cd.set("游戏房间" , null);
            cd.save();

            list.clear();
            pl.sendMessage("设置成功");

        }


    }




    if(ev.getItem().getCustomName().equals("§3回城胶囊")){
        pl.teleport(pl.getSpawn());
        pl.sendMessage("成功回家");
        pl.getInventory().removeItem(ev.getItem());
    }
    

   }


    @EventHandler
    public void changeWorld(PlayerTeleportEvent ev) {

        Player pl = ev.getPlayer();
        Location loc = ev.getTo();
        String levelname = loc.getLevel().getName();


        if (ROS().contains(levelname)) {

            ArrayList<String> arr = (ArrayList<String>) GD(levelname).get("等待玩家");
            ArrayList<String> arr1 = (ArrayList<String>) GD(levelname).get("玩家");

            if (arr.contains(pl.getName()) || arr1.contains(pl.getName()) || pl.isOp()){
                ev.setCancelled(false);
            } else {
                ev.setCancelled(true);
                pl.sendMessage("游戏地图禁止前往");
            }

            arr.clear();
        }
    }



    @EventHandler
    public void blockbreak(BlockBreakEvent ev){

    Player pl = ev.getPlayer();
    String levelname = pl.getLevel().getName();

    if(ROS().contains(levelname)){

        ArrayList<String> arr = (ArrayList<String>) GD(levelname).get("等待玩家");

        if(arr.contains(pl.getName())){
            ev.setCancelled(true);
            pl.sendMessage("请不要破坏地图");
        }
    }



    }




    @EventHandler
    public void quit(PlayerQuitEvent ev){

        String name = ev.getPlayer().getName();
        File rooms = RMS();


        if(Objects.requireNonNull(rooms.listFiles()).length != 0) {


            for(File room : Objects.requireNonNull(rooms.listFiles())) {

                ArrayList<String> arr = (ArrayList<String>) GD(room.getName().trim()).get("等待玩家");
                ArrayList<String> arr1 = (ArrayList<String>) GD(room.getName().trim()).get("玩家");


                if (arr.contains(name)) {

                    Config gd = GD(room.getName().trim());

                    arr.remove(name);
                    gd.set("等待玩家", arr);
                    gd.save();
                    break;
                }

                if (arr1.contains(name)) {

                    Config gd = GD(room.getName().trim());

                    arr1.remove(name);
                    gd.set("玩家", arr1);
                    gd.save();
                    break;
                }
            }
        }
    }


    @EventHandler
    public void join(PlayerJoinEvent ev) {

        Player pl = ev.getPlayer();
        String lv = ev.getPlayer().getLevel().getName();
        String name = ev.getPlayer().getName();


        File rooms = RMS();


        if (Objects.requireNonNull(rooms.listFiles()).length != 0) {


            for (File room : Objects.requireNonNull(rooms.listFiles())) {


                ArrayList<String> arr2 = (ArrayList<String>) GD(room.getName().trim()).get("红方队员");
                ArrayList<String> arr3 = (ArrayList<String>) GD(room.getName().trim()).get("蓝方队员");
                ArrayList<String> ar = (ArrayList<String>) GD(room.getName().trim()).get("玩家");
                Config gd = GD(room.getName().trim());


                if (Boolean.parseBoolean(gd.get("游戏是否开始").toString())) {

                    if (arr2.contains(name) || arr3.contains(name)) {

                        ar.add(name);
                        gd.set("玩家", ar);
                        gd.save();

                        pl.sendMessage("断线重连成功!");
                        break;


                    }
                }
                }
                }



                    if (ROS().contains(lv)) {
                        if (!Boolean.parseBoolean(GD(lv).get("游戏是否开始").toString())) {
                            pl.sendMessage("你的位置异常已送你返回大厅");
                            pl.getInventory().clearAll();
                            pl.setSpawn(se.getServer().getLevelByName(GS(lv).get("牌子的地图").toString()).getSafeSpawn());
                            pl.kill();
                        }
                    }

                   se.getServer().getScheduler().scheduleAsyncTask(se , new CheckBag(se , rooms , pl));

    }


    @EventHandler
    public void drop(PlayerDropItemEvent ev){
    Item item = ev.getItem();

    if(item.hasCompoundTag()) {
        if (item.getNamedTag().getString("color").equals("red") || item.getNamedTag().getString("color").equals("blue")) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage("禁止丢弃此物品");
        }
    }
    }


    @EventHandler
    public void damage(EntityDamageByEntityEvent ev) {
        if (!(ev.getDamager() instanceof Player)) {
            return;
        }
        Player pl = (Player) ev.getDamager();
        Entity shopper = ev.getEntity();
        String name = pl.getName();
        String lv = pl.getLevel().getName();


        if (ROS().contains(lv)) {

            ArrayList<String> arr = (ArrayList<String>) GD(lv).get("等待玩家");
            ArrayList<String> ar = (ArrayList<String>) GD(lv).get("玩家");
            ArrayList<String> arr2 = (ArrayList<String>) GD(lv).get("蓝方队员");
            ArrayList<String> arr3 = (ArrayList<String>) GD(lv).get("红方队员");


            if (arr.contains(name)) {
                ev.setCancelled(true);
                pl.sendTip("禁止攻击别人");
            }

            if (ar.contains(name)) {
                if (shopper instanceof seller) {
                    shop sp = new shop();
                    sp.sendShop(pl);
                    ev.setCancelled(true);
                }
            }

            if (arr2.contains(pl) && arr2.contains(shopper)) {
                ev.setCancelled(true);
            }

            if (arr3.contains(pl) && arr3.contains(shopper)) {
                ev.setCancelled(true);
            }

        }
    }



        @EventHandler
        public void death(PlayerDeathEvent ev) {

            Player pl = ev.getEntity();
            String name = pl.getName();
            String lv = pl.getLevel().getName();

            if (ROS().contains(lv)) {
                ArrayList<String> arr = (ArrayList<String>) GD(lv).get("等待玩家");
                HashMap<String, Double> pos = (HashMap<String, Double>) GS(lv).get("等待区域");


                if (arr.contains(name)) {
                    Position po = new Position(Double.parseDouble(pos.get("x").toString()), Double.parseDouble(pos.get("y").toString()), Double.parseDouble(pos.get("z").toString()), se.getServer().getLevelByName(lv));
                    pl.teleport(po);
                }
            }
        }


    @EventHandler
    public void nametag(PlayerMoveEvent ev) {


        Player pl = ev.getPlayer();
        String na = pl.getName();
        String lv = pl.getLevel().getName();

        if (ROS().contains(lv)) {
            ArrayList<String> arr2 = (ArrayList<String>) GD(lv).get("蓝方队员");
            ArrayList<String> arr3 = (ArrayList<String>) GD(lv).get("红方队员");

            if (arr2.contains(na)) {
                pl.setNameTag("§3" + na);
            }

            if (arr3.contains(na)) {
                pl.setNameTag("§4" + na);
            }

        }

    }

}