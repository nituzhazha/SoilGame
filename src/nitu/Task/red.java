package nitu.Task;

import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import nitu.main;

import java.io.File;
import java.util.HashMap;

public class red extends AsyncTask {

    public main se;

    public red(main s) {
        se = s;
    }

    public Config GS() {
        File level = new File(se.getDataFolder() + "/GameSetting.yml");
        return new Config(level);
    }


    public Config GD() {
        File GameDynamicData = new File(se.getDataFolder() + "/GameDynamicData.yml");
        return new Config(GameDynamicData);
    }

    public String levelName() {
        return GS().get("游戏地图名字").toString();
    }


    public void rednum(){
        Config gd = GD();
        int num = Integer.parseInt(gd.get("红方分数").toString());
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

        if(smx != null && smz != null && smy != null && bix != null && biz != null && biy != null) {

            for (Double x = smx; x <= bix; x++) {

                for (Double z = smz; z <= biz; z++) {

                    for (Double y = smy; y <= biy; y++) {


                        if(se.getServer().getLevelByName(levelName()).getBlockIdAt(x.intValue() , y.intValue() ,z.intValue()) == 2){

                            gd.set("红方分数" , num+2);
                            gd.save();

                        }

                        if(se.getServer().getLevelByName(levelName()).getBlockIdAt(x.intValue() , y.intValue() ,z.intValue()) == 3){

                            gd.set("红方分数" , num+1);
                            gd.save();

                        }

                        if(se.getServer().getLevelByName(levelName()).getBlockIdAt(x.intValue() , y.intValue() ,z.intValue()) == 82){

                            gd.set("红方分数" , num+3);
                            gd.save();

                        }



                    }
                }

            }
        }
    }



    @Override
    public void onRun() {

        rednum();
    }

}