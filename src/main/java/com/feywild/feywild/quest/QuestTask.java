package com.feywild.feywild.quest;

import com.feywild.feywild.quest.task.TaskType;
import com.google.gson.JsonObject;

public class QuestTask {
    
    // Careful with this
    private final TaskType<Object> task;
    private final Object element;

    private QuestTask(TaskType<Object> task, Object element) {
        this.task = task;
        this.element = element;
    }

    public static QuestTask fromJson(JsonObject json) {
        return null;
    }
}
