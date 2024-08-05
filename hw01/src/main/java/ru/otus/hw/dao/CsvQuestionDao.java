package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        List<QuestionDto> questionDtoList = getQuestionDtoList(fileNameProvider.getTestFileName());
        return getAListOfQuestion(questionDtoList);
    }

    private List<Question> getAListOfQuestion(List<QuestionDto> questionDtoList) {
        List<Question> result = new ArrayList<>();
        questionDtoList.forEach(i -> result.add(i.toDomainObject()));
        return result;
    }

    private List<QuestionDto> getQuestionDtoList(String fileNameProvider) {
        try (InputStream file = getClass().getClassLoader().getResourceAsStream(fileNameProvider);
             BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            List<QuestionDto> parse = new CsvToBeanBuilder(reader)
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .build()
                    .parse();
            return parse;

        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage());
        }
    }
}