package cn.wode490390.nukkit.biomefixer;

import cn.nukkit.plugin.PluginBase;

public class BiomeFixer extends PluginBase  {

    @Override
    public void onEnable() {
        this.getServer().getCommandMap().register("biomefixer", new FixBiomeCommand(this));
        try {
            new MetricsLite(this);
        } catch (Exception ignore) {

        }
    }
}
