package com.pepedevs.radium.task;

public interface Task {

    int getID();

    TaskQueueHandler getHandler();

    void submit(Workload workload);

    void cancel();

    boolean isCancelled();

    boolean isRunning();
}
