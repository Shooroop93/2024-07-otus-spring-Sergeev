package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

public class Application {
    public static void main(String[] args) {

        //Прописать бины в spring-context.xml и создать контекст на его основе
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
//        var testRunnerService = context.getBean(TestRunnerService.class);
//        testRunnerService.run();

        CsvQuestionDao csvQuestionDao = new CsvQuestionDao((AppProperties) context.getBean("appProperties"));
        List<Question> all = csvQuestionDao.findAll();
        System.out.println();

    }
}