package nitu.Task;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.LavaDripParticle;
import cn.nukkit.level.particle.WaterParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import nitu.copy.copy;
import nitu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameTask extends Task {

    public main se;
    public int time;
    public long i1;
    public long i2;
    public String RoomName;

    public GameTask(main s, int t, long i, long ii, String r) {
        se = s;
        time = t;
        i1 = i;
        i2 = ii;
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


    public void pl(int t) {
        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);
        ArrayList<String> arr1 = (ArrayList<String>) GD().get("蓝方队员");
        ArrayList<String> arr2 = (ArrayList<String>) GD().get("红方队员");

        for (String pn : pls) {

            if (arr1.contains(pn)) {
                se.getServer().getPlayer(pn).sendTip("§c你所在的队伍:" + "§3蓝队" + "  " + "§2倒计时" + "§e" + String.valueOf(t));
            }

            if (arr2.contains(pn)) {
                se.getServer().getPlayer(pn).sendTip("§c你所在的队伍:" + "§4红队" + "  " + "§2倒计时" + "§e" + String.valueOf(t));
            }

        }

    }

    public void pll(int t) {
        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);

        for (String pn : pls) {

            if (t == 1) {
                se.getServer().getPlayer(pn).sendMessage("§3蓝队获胜");
            }

            if (t == 2) {
                se.getServer().getPlayer(pn).sendMessage("§3红队获胜");
            }

            if (t == 3) {
                se.getServer().getPlayer(pn).sendMessage("§3本场平局");
            }


        }

    }

    public void tp() {

        String[] pls = ((ArrayList<String>) GD().get("玩家")).toArray(new String[0]);

        for (String pn : pls) {

            Level lv = se.getServer().getLevelByName(GS().get("牌子的地图").toString());
            se.getServer().getPlayer(pn).teleport(lv.getSafeSpawn());
            se.getServer().getPlayer(pn).setSpawn(lv.getSafeSpawn());
            se.getServer().getPlayer(pn).getInventory().clearAll();
            se.getServer().getPlayer(pn).sendMessage("游戏结束返回大厅");
            se.getServer().getPlayer(pn).setNameTag("§f" + se.getServer().getPlayer(pn).getNameTag());

        }
    }


    public void change() {

        se.getServer().unloadLevel(se.getServer().getLevelByName(RoomName));
        File level = new File(se.getServer().getDataPath() + "worlds/" + RoomName);
        delall(level);
        copy cy = new copy();
        String to = se.getServer().getDataPath() + "worlds/" + RoomName;
        String from = se.getDataFolder() + "/Rooms/" + RoomName + "/" + RoomName;
        cy.copyDir(from, to);
    }


    public void delall(File path) {

        if (path.isFile()) {
            path.delete();
            return;
        } else {
            File[] files = path.listFiles();
            for (File file : files) {
                delall(file);
            }
        }
    }


    public void block() {

        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("刷新泥土区域");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");

        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }


        int ra1 = ThreadLocalRandom.current().nextInt(smx.intValue(), bix.intValue() + 1);
        int ra2 = ThreadLocalRandom.current().nextInt(smy.intValue(), biy.intValue() + 1);
        int ra3 = ThreadLocalRandom.current().nextInt(smz.intValue(), biz.intValue() + 1);

        HashMap<Integer, Block> blocks = new HashMap<>();
        blocks.put(0, Block.get(2));
        blocks.put(1, Block.get(3));
        blocks.put(2, Block.get(82));

        int ra4 = ThreadLocalRandom.current().nextInt(0, 3);
        Block block = blocks.get(ra4);

        Vector3 vec3 = new Vector3(ra1, ra2, ra3);


        se.getServer().getLevelByName(RoomName).setBlock(vec3, block);

    }


    public void bblock() {

        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("蓝队刷新泥土区域");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");

        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }


        Block block = Block.get(3);

        int ra1 = ThreadLocalRandom.current().nextInt(smx.intValue(), bix.intValue() + 1);
        int ra2 = ThreadLocalRandom.current().nextInt(smy.intValue(), biy.intValue() + 1);
        int ra3 = ThreadLocalRandom.current().nextInt(smz.intValue(), biz.intValue() + 1);

        Vector3 vec3 = new Vector3(ra1, ra2, ra3);


        se.getServer().getLevelByName(RoomName).setBlock(vec3, block);

    }


    public void rblock() {

        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("红队刷新泥土区域");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");

        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }


        Block block = Block.get(3);

        int ra1 = ThreadLocalRandom.current().nextInt(smx.intValue(), bix.intValue() + 1);
        int ra2 = ThreadLocalRandom.current().nextInt(smy.intValue(), biy.intValue() + 1);
        int ra3 = ThreadLocalRandom.current().nextInt(smz.intValue(), biz.intValue() + 1);

        Vector3 vec3 = new Vector3(ra1, ra2, ra3);


        se.getServer().getLevelByName(RoomName).setBlock(vec3, block);

    }


    public void blueparticle() {
        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("蓝队堆土点");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");

        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }

        if (smx != null && smz != null && smy != null) {

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new WaterParticle(new Vector3(x, smy, z)));

                }

            }

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new WaterParticle(new Vector3(x, biy, z)));

                }

            }
        }

    }

    public void blueparticle2() {
        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("蓝队堆土点");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");

        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }

        if (smx != null && smz != null && smy != null) {

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new WaterParticle(new Vector3(x, biy, z)));

                }

            }
        }

    }


    public void redparticle() {
        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("红队堆土点");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");


        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }

        if (smx != null && smz != null && smy != null) {

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new LavaDripParticle(new Vector3(x, smy, z)));

                }

            }

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new LavaDripParticle(new Vector3(x, biy, z)));

                }

            }
        }

    }

    public void redparticle2() {
        HashMap<String, HashMap<String, Double>> list = (HashMap<String, HashMap<String, Double>>) GS().get("红队堆土点");
        HashMap<String, Double> f = list.get("first");
        HashMap<String, Double> s = list.get("second");

        Double x1 = f.get("x");//x first
        Double x2 = s.get("x");
        Double y1 = f.get("y");
        Double y2 = s.get("y");
        Double z1 = f.get("z");
        Double z2 = s.get("z");


        Double smx = null;
        Double bix = null;
        Double smy = null;
        Double biy = null;
        Double smz = null;
        Double biz = null;

        if (x1 > x2) {
            smx = x2;
            bix = x1;
        }

        if (x1 < x2) {
            smx = x1;
            bix = x2;
        }

        if (x1.equals(x2)) {
            smx = x1;
            bix = x2;
        }

        if (y1 > y2) {
            smy = y2;
            biy = y1;
        }

        if (y1 < y2) {
            smy = y1;
            biy = y2;
        }

        if (y1.equals(y2)) {
            smy = y1;
            biy = y2;
        }

        if (z1 > z2) {
            smz = z2;
            biz = z1;
        }

        if (z1 < z2) {
            smz = z1;
            biz = z2;
        }

        if (z1.equals(z2)) {
            smz = z1;
            biz = z2;
        }

        if (smx != null && smz != null && smy != null) {

            for (double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    se.getServer().getLevelByName(RoomName).addParticle(new LavaDripParticle(new Vector3(x, biy, z)));

                }

            }
        }

    }


    public void remove(long id, Player pl) {
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = id;
        pl.dataPacket(pk);
    }


    @Override
    public void onRun(int i) {


        redparticle();//红队堆土点提示粒子
        redparticle2();
        blueparticle();//蓝队堆土点提示粒子
        blueparticle2();
        block();
        rblock();
        bblock();


        if (((ArrayList) GD().get("玩家")).size() == 0) {

            Config gd = GD();
            ArrayList<String> arr1 = (ArrayList<String>) GD().get("玩家");
            ArrayList<String> arr2 = (ArrayList<String>) GD().get("蓝方队员");
            ArrayList<String> arr3 = (ArrayList<String>) GD().get("红方队员");
            arr1.removeAll(arr1);
            arr2.removeAll(arr2);
            arr3.removeAll(arr3);
            gd.set("玩家", arr1);
            gd.set("游戏是否开始", false);
            gd.set("蓝方队员", arr2);
            gd.set("红方队员", arr3);
            gd.set("蓝方分数", 0);
            gd.set("红方分数", 0);
            gd.save();

            for (Player pl : se.getServer().getOnlinePlayers().values().toArray(new Player[0])) {
                remove(i1, pl);
                remove(i2, pl);
            }

            se.getServer().getScheduler().scheduleRepeatingTask(new CheckTask(se, RoomName), 20);

            change();
            se.getServer().loadLevel(RoomName);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_ENTITY_DROPS, true);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_TILE_DROPS, true);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.PVP, true);
            this.getHandler().cancel();
        }//无人重开


        if (time > 1) {
            pl(time);
        }


        time--;


        if (time == 2) {
            se.getServer().getScheduler().scheduleAsyncTask(se, new blue(se, RoomName));
            se.getServer().getScheduler().scheduleAsyncTask(se, new red(se, RoomName));
        }


        if (time == 1) {

            if (Integer.parseInt(GD().get("蓝方分数").toString()) > Integer.parseInt(GD().get("红方分数").toString())) {
                pll(1);
            }

            if (Integer.parseInt(GD().get("蓝方分数").toString()) < Integer.parseInt(GD().get("红方分数").toString())) {
                pll(2);
            }

            if (Integer.parseInt(GD().get("蓝方分数").toString()) == Integer.parseInt(GD().get("红方分数").toString())) {
                pll(3);
            }

            tp();
            se.getServer().getScheduler().scheduleAsyncTask(se, new bag(se, RoomName));
        }

        if (time == 0) {

            Config gd = GD();
            ArrayList<String> arr1 = (ArrayList<String>) GD().get("玩家");
            ArrayList<String> arr2 = (ArrayList<String>) GD().get("蓝方队员");
            ArrayList<String> arr3 = (ArrayList<String>) GD().get("红方队员");
            arr1.removeAll(arr1);
            arr2.removeAll(arr2);
            arr3.removeAll(arr3);
            gd.set("玩家", arr1);
            gd.set("游戏是否开始", false);
            gd.set("蓝方队员", arr2);
            gd.set("红方队员", arr3);
            gd.set("蓝方分数", 0);
            gd.set("红方分数", 0);
            gd.save();

            for (Player pl : se.getServer().getOnlinePlayers().values().toArray(new Player[0])) {
                remove(i1, pl);
                remove(i2, pl);
            }


            se.getServer().getScheduler().scheduleRepeatingTask(new CheckTask(se, RoomName), 20);

            change();
            se.getServer().loadLevel(RoomName);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_ENTITY_DROPS, true);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.DO_TILE_DROPS, true);
            se.getServer().getLevelByName(RoomName).gameRules.setGameRule(GameRule.PVP, true);
            this.getHandler().cancel();
        }

    }


}



