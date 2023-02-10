package ru.strict.controltime.domain.entity.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TimeManager {
    Map<String, Task> tasks;

    public static TimeManager newInst(Instant startedAt) {
        throw new UnsupportedOperationException("implement me");
    }

    public void addTask(Task task) {
        throw new UnsupportedOperationException("implement me");
    }

    public List<TaskId> getReadyTaskIds() {
        throw new UnsupportedOperationException("implement me");
    }

    public Message getTaskMessage(TaskId taskId) {
        throw new UnsupportedOperationException("implement me");
    }

    public Message markTaskAsProcessed(TaskId taskId) {
        throw new UnsupportedOperationException("implement me");
    }
}
