package nitu.Task;

import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import nitu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckTask extends Task {

    public main se;
    public String RoomName;

    public CheckTask(main s, String r) {
        se = s;
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

    public BlockEntitySign bes() {
        HashMap<String, Double> pos = (HashMap<String, Double>) GS().get("牌子的位置");
        if (pos.get("x") != null) {
            return (BlockEntitySign) se.getServer().getLevelByName(GS().get("牌子的地图").toString()).getBlockEntity(new Vector3(Double.parseDouble(pos.get("x").toString()), Double.parseDouble(pos.get("y").toString()), Double.parseDouble(pos.get("z").toString())));
        } else {
            return null;
        }
    }

    public String[] name() {
        ArrayList<String> list = (ArrayList<String>) GD().get("等待玩家");
        return new String[]{"§7Soil§5Game", "§e房间" + "§3" + RoomName, "§4点击加入", String.valueOf(list.size()) + "/" + GS().get("最大玩家数量").toString()};
    }

    public String[] name1() {
        ArrayList<String> list = (ArrayList<String>) GD().get("等待玩家");
        return new String[]{"§7Soil§5Game", "§e房间" + "§3" + RoomName, "§4点击加入", "等待中"};
    }


    @Override
    public void onRun(int i) {

        if (bes() != null) {
            bes().setText(name());
        }

        if (((ArrayList<String>) GD().get("等待玩家")).size() == Integer.parseInt(GS().get("最小玩家数量").toString())) {

            bes().setText(name1());

            se.getServer().getScheduler().scheduleRepeatingTask(new WaitTask(se, Integer.parseInt(GS().get("等待时间").toString()), RoomName), 20);
            this.getHandler().cancel();
        }


    }


}