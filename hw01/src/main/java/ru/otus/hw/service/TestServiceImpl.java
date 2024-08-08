package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questionDaoList = csvQuestionDao.findAll();
        for (Question question : questionDaoList) {
            ioService.printLine(question.text());
            int countQuestion = 0;
            for (int i = 0; i < question.answers().size(); i++) {
                ioService.printLine(++countQuestion + ": " + question.answers().get(i).text());
            }
        }
    }
}