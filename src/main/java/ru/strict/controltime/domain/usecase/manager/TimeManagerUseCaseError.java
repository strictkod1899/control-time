package ru.strict.controltime.domain.usecase.manager;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerUseCaseError {
    public final String createTaskParamsListIsRequiredErrorCode = "c0962dd2-001";
    public final String taskManagerAlreadyInitializedErrorCode = "c0962dd2-002";
    public final String taskManagerIsNotInitializedErrorCode = "c0962dd2-003";

    public CodeableException errCreateTaskParamsListIsRequired() {
        return new CodeableException(createTaskParamsListIsRequiredErrorCode, "CreateTaskParamsList is required");
    }

    public CodeableException errTaskManagerAlreadyInitialized() {
        return new CodeableException(taskManagerAlreadyInitializedErrorCode, "TaskManager already initialized");
    }

    public CodeableException errTaskManagerIsNotInitialized() {
        return new CodeableException(taskManagerIsNotInitializedErrorCode, "TaskManager is not initialized");
    }
}
