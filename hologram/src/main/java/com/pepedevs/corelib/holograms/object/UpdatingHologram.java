package com.pepedevs.corelib.holograms.object;

import com.pepedevs.corelib.holograms.HologramManager;
import com.pepedevs.corelib.task.CancellableWorkload;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public class UpdatingHologram extends Hologram {

    private CancellableWorkload task;

    protected UpdatingHologram(String id, Location location) {
        super(id, location);
        this.startUpdate();
    }

    @Override
    public void destroy() {
        this.stopUpdate();
        super.destroy();
    }

    public void startUpdate() {
        if (this.task != null && !this.task.isCancelled())
            throw new IllegalStateException("Update already running!");

        this.task = new CancellableWorkload() {
            long lastExec = System.currentTimeMillis();

            @Override
            public void compute() {
                lastExec = System.currentTimeMillis();
                UpdatingHologram.this.update(UpdatingHologram.this.viewers.stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .toArray(Player[]::new));
            }

            @Override
            public boolean shouldExecute() {
                return !this.isCancelled() && System.currentTimeMillis() - this.lastExec >= UpdatingHologram.this.getSettings().getUpdateInterval();
            }
        };
        HologramManager.get().getThread().submit(task);
    }

    public void stopUpdate() {
        this.task.setCancelled(true);
    }

}
