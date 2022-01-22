package com.pepedevs.radium.main;

import com.pepedevs.radium.plugin.PluginAdapter;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.plugin.java.JavaPlugin;

public class Radium extends PluginAdapter {

    private static Metrics metrics;
    private static final int SERVICE_ID = 11582;

    @Override
    protected boolean setUp() {
        this.getLogger().info("Radium loaded!");
        initMetrics(this);
        return true;
    }

    public static void initMetrics(JavaPlugin plugin) {
        metrics = new Metrics(plugin, SERVICE_ID);
        metrics.addCustomChart(new SingleLineChart("servers", () -> 1));
    }
}
