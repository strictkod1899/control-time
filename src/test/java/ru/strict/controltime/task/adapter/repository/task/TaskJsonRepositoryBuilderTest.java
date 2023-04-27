package ru.strict.controltime.task.adapter.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.exception.ErrorsException;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.test.AssertUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskJsonRepositoryBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskJsonRepositoryError.jacksonObjectMapperIsRequiredErrorCode,
                TaskJsonRepositoryError.filePathIsRequiredErrorCode
        );

        var actualEx = assertThrows(ErrorsException.class, () -> TaskJsonRepository.builder().build());

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
    }

    @Test
    void testBuild_AllValidParams_ReturnRepository() {
        var taskRepository = TaskJsonRepository.builder().
                jacksonObjectMapper(new JacksonObjectMapper()).
                filePath("json-file-path").
                build();

        AssertUtil.assertFieldsNotNull(taskRepository);
    }
}
