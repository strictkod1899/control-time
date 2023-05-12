package ru.strict.controltime.timemanager.domain.entity.manager;

import lombok.EqualsAndHashCode;
import ru.strict.domainprimitive.id.EntityId;

@EqualsAndHashCode(callSuper = true)
public class TimeManagerId extends EntityId {

    private TimeManagerId(){
        super();
    }

    private TimeManagerId(String id){
        super(id);
    }

    public static TimeManagerId init() {
        return new TimeManagerId();
    }

    public static TimeManagerId from(String id) {
        return new TimeManagerId(id);
    }
}
