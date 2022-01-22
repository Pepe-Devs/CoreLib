package com.pepedevs.radium.nms.v1_8_R3.objects;

import com.pepedevs.radium.nms.objects.WrappedDataWatcher;
import net.minecraft.server.v1_8_R3.DataWatcher;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class WrappedDataWatcherImpl extends DataWatcher implements WrappedDataWatcher {

    public WrappedDataWatcherImpl(Entity entity) {
        super(((CraftEntity) entity).getHandle());
    }

    public WrappedDataWatcherImpl() {
        super(null);
    }

    @Override
    public <T> void add(int value, T object) {
        super.a(value, object);
    }

    @Override
    public void add(int value, DataTypeId dataType) {
        super.add(value, dataType.getId());
    }

    @Override
    public void add0(int value, int dataType) {
        super.add(value, dataType);
    }

    @Override
    public <T> void watch0(int typeId, T object) {
        super.watch(typeId, object);
    }

    public static class WrappedWatchableObjectImpl extends WatchableObject implements WrappedWatchableObject {

        public WrappedWatchableObjectImpl(Object watchableObject) {
            super(((WatchableObject) watchableObject).c(), ((WatchableObject) watchableObject).a(), ((WatchableObject) watchableObject).b());
        }

        public WrappedWatchableObjectImpl(int i, int j, Object object) {
            super(i, j, object);
        }

        @Override
        public int getType() {
            return super.c();
        }

        @Override
        public int getIndex() {
            return super.a();
        }

        @Override
        public Object getValue() {
            return super.b();
        }

        @Override
        public void setValue(Object object) {
            super.a(object);
        }

    }

}
