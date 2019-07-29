package cn.wode490390.nukkit.biomefixer;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Position;
import cn.nukkit.level.biome.BiomeSelector;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.plugin.Plugin;

public class FixBiomeCommand extends Command implements PluginIdentifiableCommand {

    private final Plugin plugin;

    public FixBiomeCommand(Plugin plugin) {
        super("fixbiome", "Fixes the biome of the chunk where the player is located", "/fixbiome", new String[]{"fb"});
        this.setPermission("biomefixer.command");
        this.getCommandParameters().clear();
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.plugin.isEnabled() || !this.testPermission(sender)) {
            return false;
        }
        if (sender.isPlayer()) {
            FullChunk chunk = ((Position) sender).getChunk();
            BiomeSelector selector = new BiomeSelector(new NukkitRandom(chunk.getProvider().getSeed()));
            int x = chunk.getX() << 4;
            int z = chunk.getZ() << 4;
            for (int cx = 0; cx < 16; cx++) {
                for (int cz = 0; cz < 16; cz++) {
                    chunk.setBiome(cx, cz, selector.pickBiome(x | cx, z | cz));
                }
            }
            sender.sendMessage("The biome in the current chunk has been successfully fixed.");
        } else {
            sender.sendMessage(new TranslationContainer("%commands.generic.ingame"));
        }
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
