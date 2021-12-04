package com.pepedevs.corelib.main;

import com.pepedevs.corelib.plugin.PluginAdapter;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreLib extends PluginAdapter {

    private static Metrics metrics;
    private static final int SERVICE_ID = 11582;

    @Override
    protected boolean setUp() {
        this.getLogger().info("CoreLib loaded!");
        Bukkit.getServicesManager()
                .register(CoreLib.class, this, this, ServicePriority.Normal);
        initMetrics(this);
        return true;
    }

    public static void initMetrics(JavaPlugin plugin) {
        metrics = new Metrics(plugin, SERVICE_ID);
        metrics.addCustomChart(new SingleLineChart("servers", () -> 1));
    }

}
