package ru.strict.controltime.task.adapter.repository.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.exception.Errors;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.util.StringUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskJsonRepositoryBuilder {
    JacksonObjectMapper jacksonObjectMapper;
    String filePath;

    Errors errors;

    public TaskJsonRepositoryBuilder() {
        errors = new Errors();
    }

    public TaskJsonRepositoryBuilder jacksonObjectMapper(JacksonObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
        return this;
    }

    public TaskJsonRepositoryBuilder filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public TaskJsonRepository build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (jacksonObjectMapper == null) {
            errors.addError(TaskJsonRepositoryError.errJacksonObjectMapperIsRequired());
        }
        if (StringUtil.isNullOrEmpty(filePath)) {
            errors.addError(TaskJsonRepositoryError.errFilePathIsRequired());
        }
    }

    private TaskJsonRepository createFromBuilder() {
        var taskJsonRepository = new TaskJsonRepository();
        taskJsonRepository.jacksonObjectMapper = this.jacksonObjectMapper;
        taskJsonRepository.filePath = this.filePath;

        return taskJsonRepository;
    }
}
