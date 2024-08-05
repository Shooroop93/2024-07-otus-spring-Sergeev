package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        List<Question> result = new ArrayList<>();

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(resourceAsStream)))) {

            List<QuestionDto> listQuestionDto = new CsvToBeanBuilder(reader)
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .build()
                    .parse();

            for (QuestionDto questionDto : listQuestionDto) {
                result.add(questionDto.toDomainObject());
            }

        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage());
        }

        return result;
    }
}
