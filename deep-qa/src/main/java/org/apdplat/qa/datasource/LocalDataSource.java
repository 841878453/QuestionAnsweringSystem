package org.apdplat.qa.datasource;


import org.apdplat.qa.files.FilesConfig;
import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.system.QuestionAnsweringSystem;
import org.apdplat.qa.util.MySQLUtils;
import org.apdplat.qa.util.Tools;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * 只从本地数据库中获取信息，否则直接返回空
 */
public class LocalDataSource implements DataSource{

    private static final Logger LOG = LoggerFactory.getLogger(LocalDataSource.class);
    private Set<String> special = new HashSet<>();


    public LocalDataSource() {
        BufferedReader reader = null;
        try {
            LOG.info("读取专有词库");
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(FilesConfig.special), "utf-8"));
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().equals("")) {
                    //读下一行
                    line = reader.readLine();
                    continue;
                }

                special.add(line.trim());
                //读下一行
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            LOG.error("文件找不到", e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码错误", e);
        } catch (IOException e) {
            LOG.error("IO错误", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("关闭文件错误", e);
                }
            }
        }
    }

    @Override
    public List<Question> getQuestions() {
        return null;
    }

    public List<Question> getQuestions(String questionStr) {
        return getAndAnswerQuestions(questionStr);
    }

    @Override
    public Question getQuestion(String questionStr) {
        return getAndAnswerQuestion(questionStr,null);
    }

    @Override
    public List<Question> getAndAnswerQuestions(QuestionAnsweringSystem questionAnsweringSystem) {

        return null;
    }

    public List<Question> getAndAnswerQuestions(String questionStr) {
        //从本地缓存里面找
        LOG.info("从数据库中获取信息");
        List<String> list = new ArrayList<>();
        List<Word> wordList = Tools.getWords(questionStr);
        for(Word w:wordList){
            if(special.contains(w.getText())){
                list.add(w.getText());
            }
        }
        List<Question> questions = MySQLUtils.getQuestionsFromDatabaseLike(list);

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
