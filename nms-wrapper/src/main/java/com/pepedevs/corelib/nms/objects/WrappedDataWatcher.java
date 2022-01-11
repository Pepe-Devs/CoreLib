package com.pepedevs.corelib.nms.objects;

public interface WrappedDataWatcher {

    <T> void add(int value, T object);

    void add(int value, DataTypeId dataType);

    void add0(int value, int dataType);

    <T> void watch0(int typeId, T object);

    interface WrappedWatchableObject {

        Object getValue();

        void setValue(Object object);

    }

    enum DataTypeId {
        BYTE(0),
        SHORT(1),
        INT(2),
        FLOAT(3),
        STRING(4),
        ITEM_STACK(5),
        BLOCK_POSITION(6),
        VECTOR(7);

        private final int id;

        DataTypeId(final int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

}
