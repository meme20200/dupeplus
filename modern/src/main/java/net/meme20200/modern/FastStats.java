package net.meme20200.modern;

import dev.faststats.bukkit.BukkitContext;
import org.bukkit.plugin.java.JavaPlugin;

public class FastStats {
    BukkitContext fastStatsMetrics;


    public FastStats(JavaPlugin plugin, String version) {
        fastStatsMetrics = new BukkitContext.Factory(plugin, "9f44bf01b4e697d6ee8b3144f77d55cd")
                .metrics(factory -> factory.addMetric(dev.faststats.data.Metric.string("config_version", () -> version)).create())
                .create();

        fastStatsMetrics.ready();
    }

    public void close() {
        if (fastStatsMetrics != null) {
            fastStatsMetrics.shutdown();
            fastStatsMetrics = null;
        }
    }


}
