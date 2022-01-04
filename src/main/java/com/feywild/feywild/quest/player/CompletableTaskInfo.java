package com.feywild.feywild.quest.player;

import com.feywild.feywild.quest.task.TaskType;

import java.util.function.Consumer;

// When it is not possible to iterate through all current elements but rather
// required to iterate through all possibly completable elements
// Provides an element and a method to call check complete for this quest.
public class CompletableTaskInfo<T, X> {

    private final TaskType<T, X> taskTpe;
    private final T value;
    private final Consumer<X> checkComplete;

    public CompletableTaskInfo(TaskType<T, X> taskTpe, T value, Consumer<X> checkComplete) {
        this.taskTpe = taskTpe;
        this.value = value;
        this.checkComplete = checkComplete;
    }

    public TaskType<T, ?> getTaskTpe() {
        return taskTpe;
    }

    public T getValue() {
        return value;
    }

    // Checks completion on the whole quest, not just the single task
    // The task type still MUST define a checkCompleted method that really checks for completion
    public void checkComplete(X value) {
        checkComplete.accept(value);
    }
}
