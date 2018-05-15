package org.apdplat.qa.datasource;


import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.system.QuestionAnsweringSystem;
import org.apdplat.qa.util.MySQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 只从本地数据库中获取信息，否则直接返回空
 */
public class LocalDataSource implements DataSource{

    private static final Logger LOG = LoggerFactory.getLogger(LocalDataSource.class);


    public LocalDataSource() {
    }

    @Override
    public List<Question> getQuestions() {
        return getAndAnswerQuestions(null);
    }

    @Override
    public Question getQuestion(String questionStr) {
        return getAndAnswerQuestion(questionStr,null);
    }

    @Override
    public List<Question> getAndAnswerQuestions(QuestionAnsweringSystem questionAnsweringSystem) {
        //从本地缓存里面找
        LOG.info("从数据库中获取信息");
        List<Question> questions = MySQLUtils.getQuestionsFromDatabase();

        if (!questions.isEmpty()) {
            //数据库中存在问题
            List<Evidence> evidences = new ArrayList<>();
            for (Question q:questions){
                evidences.addAll(q.getEvidences());
            }
            Question question = new Question();
            question.addEvidences(evidences);
            List<Question> questionList = new ArrayList<Question>();
            questionList.add(question);
            return questionList;
        }
        return null;
    }

    @Override
    public Question getAndAnswerQuestion(String questionStr, QuestionAnsweringSystem questionAnsweringSystem) {
        //从本地缓存里面找
        Question question = MySQLUtils.getQuestionFromDatabase("", questionStr);
        if (question != null) {
            //数据库中存在
            return question;
        }
        return null;
    }

    public static void main(String args[]) {

    }
}
