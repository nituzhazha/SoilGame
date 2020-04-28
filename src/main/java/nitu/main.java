package nitu;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import nitu.Entity.seller;
import nitu.Task.CheckTask;
import nitu.copy.copy;
import nitu.event.eventListener;
import nitu.event.goods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class main extends PluginBase {

    public void onLoad() {

        File rooms = new File(this.getDataFolder() + "/Rooms");
        File CmdDynamicData = new File(this.getDataFolder() + "/CmdDynamicData.yml");

        if (!CmdDynamicData.exists()) {
            ConfigSection list = new ConfigSection();

            list.put("选择牌子", false);
            list.put("游戏房间", null);

            new Config(CmdDynamicData, 2, list);

            list.clear();
            this.getServer().getLogger().info("[SoilGame]指令动态文件初始化成功！");
        }


        if (!rooms.exists()) {
            rooms.mkdirs();
        }

        if (Objects.requireNonNull(rooms.listFiles()).length == 0) {
            this.getServer().getLogger().info("[SoilGame]当前无游戏房间");
        } else {

            for (File room : Objects.requireNonNull(rooms.listFiles())) { //一个一个列出rooms里的所有房间

                String rom = room.getName().trim();

                File level = new File(this.getDataFolder() + "/Rooms/" + rom + "/GameSetting.yml");
                File GameDynamicData = new File(this.getDataFolder() + "/Rooms/" + rom + "/GameDynamicData.yml");
                File PlayerInventoryData = new File(this.getDataFolder() + "/Rooms/" + rom + "/PlayerInventoryData.yml");


                if (!level.exists()) {
                    ConfigSection list = new ConfigSection();

                    HashMap<String, Double> blue = new HashMap<>();//蓝队
                    blue.put("x", null);
                    blue.put("y", null);
                    blue.put("z", null);

                    HashMap<String, Double> red = new HashMap<>();//红队
                    red.put("x", null);
                    red.put("y", null);
                    red.put("z", null);

                    HashMap<String, Double> bluesr = new HashMap<>();//蓝队商人
                    bluesr.put("x", null);
                    bluesr.put("y", null);
                    bluesr.put("z", null);

                    HashMap<String, Double> redsr = new HashMap<>();//红队商人
                    redsr.put("x", null);
                    redsr.put("y", null);
                    redsr.put("z", null);

                    HashMap<String, HashMap<String, Double>> bluedt = new HashMap<>();//蓝队的堆泥土区域

                    HashMap<String, Double> bdt1 = new HashMap<>();
                    bdt1.put("x", null);
                    bdt1.put("y", null);
                    bdt1.put("z", null);
                    HashMap<String, Double> bdt2 = new HashMap<>();
                    bdt2.put("x", null);
                    bdt2.put("y", null);
                    bdt2.put("z", null);

                    bluedt.put("first", bdt1);
                    bluedt.put("second", bdt2);


                    HashMap<String, HashMap<String, Double>> reddt = new HashMap<>();//红队的堆泥土区域

                    HashMap<String, Double> rdt1 = new HashMap<>();
                    rdt1.put("x", null);
                    rdt1.put("y", null);
                    rdt1.put("z", null);
                    HashMap<String, Double> rdt2 = new HashMap<>();
                    rdt2.put("x", null);
                    rdt2.put("y", null);
                    rdt2.put("z", null);

                    reddt.put("first", rdt1);
                    reddt.put("second", rdt2);

                    HashMap<String, HashMap<String, Double>> std = new HashMap<>();//刷新泥土的区域

                    HashMap<String, Double> std1 = new HashMap<>();
                    std1.put("x", null);
                    std1.put("y", null);
                    std1.put("z", null);
                    HashMap<String, Double> std2 = new HashMap<>();
                    std2.put("x", null);
                    std2.put("y", null);
                    std2.put("z", null);

                    std.put("first", std1);
                    std.put("second", std2);


                    HashMap<String, HashMap<String, Double>> bst = new HashMap<>();//蓝队刷新泥土的区域

                    HashMap<String, Double> bst1 = new HashMap<>();
                    bst1.put("x", null);
                    bst1.put("y", null);
                    bst1.put("z", null);
                    HashMap<String, Double> bst2 = new HashMap<>();
                    bst2.put("x", null);
                    bst2.put("y", null);
                    bst2.put("z", null);

                    bst.put("first", bst1);
                    bst.put("second", bst2);


                    HashMap<String, HashMap<String, Double>> rst = new HashMap<>();//红队刷新泥土的区域

                    HashMap<String, Double> rst1 = new HashMap<>();
                    rst1.put("x", null);
                    rst1.put("y", null);
                    rst1.put("z", null);
                    HashMap<String, Double> rst2 = new HashMap<>();
                    rst2.put("x", null);
                    rst2.put("y", null);
                    rst2.put("z", null);

                    rst.put("first", rst1);
                    rst.put("second", rst2);

                    HashMap<String, Double> dd = new HashMap<>();//等待时的位置
                    dd.put("x", null);
                    dd.put("y", null);
                    dd.put("z", null);

                    HashMap<String, Double> pz = new HashMap<>();//进入游戏点击的牌子的位置
                    pz.put("x", null);
                    pz.put("y", null);
                    pz.put("z", null);

                    list.put("游戏地图名字", rom);
                    list.put("最大玩家数量", 2);
                    list.put("最小玩家数量", 1);
                    list.put("等待时间", 20);
                    list.put("游戏时间", 60);
                    list.put("等待区域", dd);
                    list.put("蓝队出生点", blue);
                    list.put("红队出生点", red);
                    list.put("蓝队商人出生点", bluesr);
                    list.put("红队商人出生点", redsr);
                    list.put("蓝队堆土点", bluedt);
                    list.put("红队堆土点", reddt);
                    list.put("刷新泥土区域", std);
                    list.put("蓝队刷新泥土区域", bst);
                    list.put("红队刷新泥土区域", rst);
                    list.put("牌子的位置", pz);
                    list.put("牌子的地图", null);

                    new Config(level, 2, list);

                    list.clear();
                    this.getServer().getLogger().info("[SoilGame]房间" + rom + "的配置文件初始化成功！");
                }


                if (!GameDynamicData.exists()) {
                    ConfigSection list = new ConfigSection();

                    ArrayList<String> waitplayers = new ArrayList<>();
                    ArrayList<String> players = new ArrayList<>();
                    ArrayList<String> bplayers = new ArrayList<>();
                    ArrayList<String> rplayers = new ArrayList<>();

                    list.put("等待玩家", waitplayers);
                    list.put("玩家", players);
                    list.put("蓝方队员", bplayers);
                    list.put("红方队员", rplayers);
                    list.put("蓝方分数", 0);
                    list.put("红方分数", 0);
                    list.put("游戏是否开始", false);

                    new Config(GameDynamicData, 2, list);

                    list.clear();
                    this.getServer().getLogger().info("[SoilGame]房间" + rom + "的游戏动态文件初始化成功！");
                }


                if (!PlayerInventoryData.exists()) {
                    new Config(PlayerInventoryData);
                    this.getServer().getLogger().info("[SoilGame]房间" + rom + "的玩家背包储存文件初始化成功！");
                }


            }
        }
    }

    public Config GS(String r) {
        File level = new File(this.getDataFolder() + "/Rooms/" + r + "/GameSetting.yml");
        return new Config(level);
    }

    public Config GD(String r) {
        File GameDynamicData = new File(this.getDataFolder() + "/Rooms/" + r + "/GameDynamicData.yml");
        return new Config(GameDynamicData);
    }

    public Config IV(String r) {
        File PlayerInventoryData = new File(this.getDataFolder() + "/Rooms/" + r + "/PlayerInventoryData.yml");
        return new Config(PlayerInventoryData);
    }

    public Config CD() {
        File CmdDynamicData = new File(this.getDataFolder() + "/CmdDynamicData.yml");
        return new Config(CmdDynamicData);
    }

    public File RMS() {
        return new File(this.getDataFolder() + "/Rooms");
    }


    public ArrayList<String> ROS() {
        ArrayList<String> list = new ArrayList<>();

        for (File room : Objects.requireNonNull(RMS().listFiles())) {
            list.add(room.getName().trim());
        }

        return list;
    }


    public void onEnable() {


        File rooms = RMS();


        if (Objects.requireNonNull(rooms.listFiles()).length == 0) {
            this.getServer().getLogger().info("[SoilGame]当前无游戏房间未加载任何游戏地图");
        } else {

            for (File room : Objects.requireNonNull(rooms.listFiles())) {

                this.getServer().loadLevel(room.getName().trim());
                this.getServer().getLogger().info("[SoilGame]成功加载游戏地图" + room.getName().trim());


                File level = new File(this.getDataFolder() + "/Rooms/" + room.getName().trim() + "/" + room.getName().trim());

                if (!level.isDirectory() || !level.exists()) {
                    copy cy = new copy();
                    String from = this.getServer().getDataPath() + "worlds/" + room.getName().trim();
                    String to = this.getDataFolder() + "/Rooms/" + room.getName().trim() + "/" + room.getName().trim();
                    cy.copyDir(from, to);
                    this.getServer().getLogger().info("[SoilGame]地图" + room.getName().trim() + "存储成功");
                }

                this.getServer().getScheduler().scheduleRepeatingTask(new CheckTask(this, room.getName().trim()), 20);//每秒检测
            }
        }


        Entity.registerEntity("seller", seller.class);
        this.getServer().getPluginManager().registerEvents(new eventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new goods(), this);
        this.getServer().getLogger().info("加载成功  by:nitu QQ:1010340249");
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player pl = (Player) sender;
        String en = pl.getLevel().getName().trim();

        if ("soilgame".equals(command.getName())) {


            if (args.length > 0) {


                if (!"set".equals(args[0]) && !"quit".equals(args[0]) && !"help".equals(args[0])) {
                    sender.sendMessage("不存在这个选项");
                } else {


                    if ("quit".equals(args[0])) {

                        if (ROS().contains(en)) {

                            Config list = GD(en);
                            ArrayList<String> arr = (ArrayList<String>) list.get("等待玩家");
                            ArrayList<String> ar = (ArrayList<String>) list.get("玩家");
                            ArrayList<String> arr1 = (ArrayList<String>) GD(en).get("蓝方队员");
                            ArrayList<String> arr2 = (ArrayList<String>) GD(en).get("红方队员");


                            if (arr.contains(pl.getName())) {

                                arr.remove(pl.getName());
                                list.set("等待玩家", arr);

                                list.save();

                                Level lv = this.getServer().getLevelByName(GS(en).get("牌子的地图").toString());
                                pl.teleport(lv.getSafeSpawn());

                                pl.sendMessage("退出成功");

                                pl.getInventory().clearAll();


                                if (IV(en).get(pl.getName()) != null) {
                                    Config llv = IV(en);
                                    HashMap<Integer, HashMap<String, Object>> all = (HashMap<Integer, HashMap<String, Object>>) llv.get(pl.getName());

                                    for (int i = 0; i < 36; i++) {

                                        HashMap<String, Object> single = all.get(i);

                                        Item item;

                                        if (Integer.parseInt(single.get("id").toString()) != 0) {
                                            item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()));

                                            if (!single.get("nbt").toString().equals("无")) {
                                                item = Item.get(Integer.parseInt(single.get("id").toString()), Integer.parseInt(single.get("damage").toString()), Integer.parseInt(single.get("count").toString()), single.get("nbt").toString().getBytes());
                                            }

                                            pl.getInventory().addItem(item);
                                        }

                                        single.clear();
                                    }

                                    llv.remove(pl.getName());
                                    llv.save();
                                    pl.sendMessage("你的背包已经归还");
                                }

                            }

                            if (ar.contains(pl.getName())) {

                                ar.remove(pl.getName());
                                list.set("玩家", ar);


                                if (arr1.contains(pl.getName())) {
                                    arr1.remove(pl.getName());
                                    list.set("蓝方队员", arr1);

                                }


                                if (arr2.contains(pl.getName())) {
                                    arr2.remove(pl.getName());
                                    list.set("红方队员", arr2);
                                }


                                list.save();

                                Level lv = this.getServer().getLevelByName(GS(en).get("牌子的地图").toString());
                                pl.teleport(lv.getSafeSpawn());

                                pl.sendMessage("退出成功");

                                pl.getInventory().clearAll();


                                if (IV(en).getKeys().contains(pl.getName())) {
                                    HashMap<Integer, Item> items = (HashMap<Integer, Item>) IV(en).get(pl.getName());
                                    pl.getInventory().setContents(items);
                                    Config iv = IV(en);
                                    iv.remove(pl.getName());
                                    iv.save();
                                    pl.sendMessage("你的背包已经归还");
                                }

                            }

                        } else {
                            pl.sendMessage("你不能在非游戏地图以外的地图执行这个指令");
                            return false;
                        }
                    }


                    if (pl.isOp()) {

                        if ("help".equals(args[0])) {
                            sender.sendMessage("设置等待区域  /soilgame set wait" +
                                    "\n设置蓝队出生点  /soilgame set blueb" +
                                    "\n设置红队出生点  /soilgame set redb" +
                                    "\n设置蓝队商人出生点  /soilgame set blues" +
                                    "\n设置红队商人出生点  /soilgame set reds" +
                                    "\n设置蓝队堆土一号点  /soilgame set blue1" +
                                    "\n设置蓝队堆土二号点  /soilgame set blue2" +
                                    "\n设置红队堆土一号点  /soilgame set red1" +
                                    "\n设置红队堆土二号点  /soilgame set red2" +
                                    "\n设置刷新泥土一号点  /soilgame set soil1" +
                                    "\n设置刷新泥土二号点  /soilgame set soil2" +
                                    "\n设置刷新蓝队泥土一号点  /soilgame set blues1" +
                                    "\n设置刷新蓝队泥土二号点  /soilgame set blues2" +
                                    "\n设置刷新红队泥土一号点  /soilgame set reds1" +
                                    "\n设置刷新红队泥土二号点  /soilgame set reds2" +
                                    "\n设置进入游戏的牌子  /soilgame set pz 游戏房间名字" +
                                    "\n退出游戏  /soilgame quit");
                        }


                        if ("set".equals(args[0])) {

                            if (args.length > 1) {


                                if (args[1].equals("pz")) {

                                    Config cd = CD();

                                    if (args.length > 2) {

                                        if (ROS().contains(args[2].toString())) {

                                            if (!Boolean.parseBoolean(cd.get("选择牌子").toString())) {

                                                cd.set("选择牌子", true);
                                                cd.set("游戏房间", args[2].toString());
                                                cd.save();

                                                pl.sendMessage("快去点击一块牌子吧");
                                                return true;

                                            } else {
                                                pl.sendMessage("请勿重复输入此选项你还没有选择牌子");
                                                return false;
                                            }

                                        } else {
                                            pl.sendMessage("不存在此房间");
                                            return false;
                                        }


                                    } else {
                                        pl.sendMessage("你还没有输入是哪个游戏房间");
                                        return false;
                                    }
                                }//牌子


                                if (ROS().contains(en)) {

                                    Config pz = GS(en);//游戏配置


                                    if (args[1].equals("wait")) {

                                        HashMap<String, Object> list = (HashMap<String, Object>) pz.get("等待区域");

                                        if (list.get("x") == null && list.get("y") == null && list.get("z") == null) {

                                            HashMap<String, Double> dd = new HashMap<>();
                                            dd.put("x", pl.getX());
                                            dd.put("y", pl.getY());
                                            dd.put("z", pl.getZ());

                                            pz.set("等待区域", dd);
                                            pz.save();

                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//wait


                                    if (args[1].equals("blueb")) {

                                        HashMap<String, Object> list = (HashMap<String, Object>) pz.get("蓝队出生点");

                                        if (list.get("x") == null && list.get("y") == null && list.get("z") == null) {

                                            HashMap<String, Double> dd = new HashMap<>();
                                            dd.put("x", pl.getX());
                                            dd.put("y", pl.getY());
                                            dd.put("z", pl.getZ());

                                            pz.set("蓝队出生点", dd);
                                            pz.save();

                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//blueborn


                                    if (args[1].equals("redb")) {

                                        HashMap<String, Object> list = (HashMap<String, Object>) pz.get("红队出生点");

                                        if (list.get("x") == null && list.get("y") == null && list.get("z") == null) {

                                            HashMap<String, Double> dd = new HashMap<>();
                                            dd.put("x", pl.getX());
                                            dd.put("y", pl.getY());
                                            dd.put("z", pl.getZ());

                                            pz.set("红队出生点", dd);
                                            pz.save();

                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//redborn


                                    if (args[1].equals("blues")) {

                                        HashMap<String, Object> list = (HashMap<String, Object>) pz.get("蓝队商人出生点");

                                        if (list.get("x") == null && list.get("y") == null && list.get("z") == null) {

                                            HashMap<String, Double> dd = new HashMap<>();
                                            dd.put("x", pl.getX());
                                            dd.put("y", pl.getY());
                                            dd.put("z", pl.getZ());

                                            pz.set("蓝队商人出生点", dd);
                                            pz.save();

                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//blueseller


                                    if (args[1].equals("reds")) {

                                        HashMap<String, Object> list = (HashMap<String, Object>) pz.get("红队商人出生点");

                                        if (list.get("x") == null && list.get("y") == null && list.get("z") == null) {

                                            HashMap<String, Double> dd = new HashMap<>();
                                            dd.put("x", pl.getX());
                                            dd.put("y", pl.getY());
                                            dd.put("z", pl.getZ());

                                            pz.set("红队商人出生点", dd);
                                            pz.save();

                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//redseller


                                    if (args[1].equals("blue1")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("蓝队堆土点");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list1.get("x") == null && list1.get("y") == null && list1.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list2.get("x"));
                                            dd2.put("y", list2.get("y"));
                                            dd2.put("z", list2.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd1);
                                            dd.put("second", dd2);

                                            pz.set("蓝队堆土点", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//blue1


                                    if (args[1].equals("blue2")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("蓝队堆土点");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list2.get("x") == null && list2.get("y") == null && list2.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list1.get("x"));
                                            dd2.put("y", list1.get("y"));
                                            dd2.put("z", list1.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd2);
                                            dd.put("second", dd1);

                                            pz.set("蓝队堆土点", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//blue2


                                    if (args[1].equals("red1")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("红队堆土点");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list1.get("x") == null && list1.get("y") == null && list1.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list2.get("x"));
                                            dd2.put("y", list2.get("y"));
                                            dd2.put("z", list2.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd1);
                                            dd.put("second", dd2);

                                            pz.set("红队堆土点", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//red1


                                    if (args[1].equals("red2")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("红队堆土点");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list2.get("x") == null && list2.get("y") == null && list2.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list1.get("x"));
                                            dd2.put("y", list1.get("y"));
                                            dd2.put("z", list1.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd2);
                                            dd.put("second", dd1);

                                            pz.set("红队堆土点", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//red2


                                    if (args[1].equals("soil1")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list1.get("x") == null && list1.get("y") == null && list1.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list2.get("x"));
                                            dd2.put("y", list2.get("y"));
                                            dd2.put("z", list2.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd1);
                                            dd.put("second", dd2);

                                            pz.set("刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//soil1


                                    if (args[1].equals("soil2")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list2.get("x") == null && list2.get("y") == null && list2.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list1.get("x"));
                                            dd2.put("y", list1.get("y"));
                                            dd2.put("z", list1.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd2);
                                            dd.put("second", dd1);

                                            pz.set("刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//soil2


                                    if (args[1].equals("blues1")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("蓝队刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list1.get("x") == null && list1.get("y") == null && list1.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list2.get("x"));
                                            dd2.put("y", list2.get("y"));
                                            dd2.put("z", list2.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd1);
                                            dd.put("second", dd2);

                                            pz.set("蓝队刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//bsoil1


                                    if (args[1].equals("blues2")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("蓝队刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list2.get("x") == null && list2.get("y") == null && list2.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list1.get("x"));
                                            dd2.put("y", list1.get("y"));
                                            dd2.put("z", list1.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd2);
                                            dd.put("second", dd1);

                                            pz.set("蓝队刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//bsoil2


                                    if (args[1].equals("reds1")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("红队刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list1.get("x") == null && list1.get("y") == null && list1.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list2.get("x"));
                                            dd2.put("y", list2.get("y"));
                                            dd2.put("z", list2.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd1);
                                            dd.put("second", dd2);

                                            pz.set("红队刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//bsoil1


                                    if (args[1].equals("reds2")) {

                                        HashMap<String, HashMap<String, Object>> list = (HashMap<String, HashMap<String, Object>>) pz.get("红队刷新泥土区域");
                                        HashMap<String, Object> list1 = list.get("first");
                                        HashMap<String, Object> list2 = list.get("second");


                                        if (list2.get("x") == null && list2.get("y") == null && list2.get("z") == null) {

                                            HashMap<String, Object> dd1 = new HashMap<>();
                                            dd1.put("x", pl.getX());
                                            dd1.put("y", pl.getY());
                                            dd1.put("z", pl.getZ());

                                            HashMap<String, Object> dd2 = new HashMap<>();
                                            dd2.put("x", list1.get("x"));
                                            dd2.put("y", list1.get("y"));
                                            dd2.put("z", list1.get("z"));


                                            HashMap<String, HashMap<String, Object>> dd = new HashMap<>();
                                            dd.put("first", dd2);
                                            dd.put("second", dd1);

                                            pz.set("红队刷新泥土区域", dd);
                                            pz.save();

                                            dd1.clear();
                                            dd2.clear();
                                            dd.clear();
                                            pl.sendMessage("配置成功");

                                        } else {
                                            pl.sendMessage("你已经配置过了");
                                            return false;
                                        }

                                    }//bsoil2


                                } else {
                                    pl.sendMessage("你不能在非游戏地图以外的地图执行这个指令");
                                    return false;
                                }


                            } else {
                                pl.sendMessage("你没有填写要执行的操作");
                                return false;
                            }


                        }


                    }

                }
            } else {
                pl.sendMessage("你没有写第一个选项");
                return false;
            }

        } else {
            pl.sendMessage("你没有权限用这个指令");
            return false;
        }
        return true;
    }


}