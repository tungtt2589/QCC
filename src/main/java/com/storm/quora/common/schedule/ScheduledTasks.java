package com.storm.quora.common.schedule;

import com.storm.quora.service.QuestionService;
import com.storm.quora.util.RedisUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    QuestionService questionService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//        log.info("backup from redis to mysql");
        try {
            JSONArray listQuestion = RedisUtil.getAllInfoQuestion();
            if (listQuestion != null) {
                questionService.updateVoteQuestion(listQuestion);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
