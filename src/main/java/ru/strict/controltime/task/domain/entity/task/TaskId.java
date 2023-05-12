package ru.strict.controltime.task.domain.entity.task;

import lombok.EqualsAndHashCode;
import ru.strict.domainprimitive.id.EntityId;

@EqualsAndHashCode(callSuper = true)
public class TaskId extends EntityId {

    private TaskId(){
        super();
    }

    private TaskId(String id){
        super(id);
    }

    public static TaskId init() {
        return new TaskId();
    }

    public static TaskId from(String id) {
        return new TaskId(id);
    }
}
