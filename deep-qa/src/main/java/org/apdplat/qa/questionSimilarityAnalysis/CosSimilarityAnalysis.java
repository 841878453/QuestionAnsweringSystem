package org.apdplat.qa.questionSimilarityAnalysis;

import org.apdplat.qa.datasource.LocalDataSource;
import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CosSimilarityAnalysis implements SimilarityAnalysis{
    private static final Logger LOG = LoggerFactory.getLogger(CosSimilarityAnalysis.class);

    public CosSimilarityAnalysis() {
    }

    @Override
    public Question analysis(String questionStr,List<Question> questionList) {
        List<Evidence> evidences = new ArrayList<>();
        for(Question q:questionList){
            evidences.addAll(q.getEvidences());
        }
        Question question = new Question();
        question.setQuestion(questionStr);
        Map<String,Integer> questionFre = Tools.getFrequent(questionStr);
        for(Evidence e:evidences){
            Map<String,Integer> evidenceFre = Tools.getFrequent(e.getTitle());
            HashSet<String> total = new HashSet<>(questionFre.keySet());
            total.addAll(evidenceFre.keySet());
            int cosup = 0;
            double cosdown = 0;
            double costemp = 0;
            for(String s:total){
               int qf =  questionFre.containsKey(s)?questionFre.get(s):0;
               int ef =  evidenceFre.containsKey(s)?evidenceFre.get(s):0;
               cosup += qf * ef;
            }
            for(String s:questionFre.keySet()){
                int fre = questionFre.get(s);
                costemp += fre * fre;
            }
            cosdown+=Math.sqrt(costemp);
            costemp = 0;
            for(String s:evidenceFre.keySet()){
                int fre = evidenceFre.get(s);
                costemp += fre * fre;
            }
            cosdown*=Math.sqrt(costemp);
            e.setCosSimilarity(cosup/cosdown);
            question.addEvidence(e);

        }

        return question;
    }

    public static void main (String args[]){
        List<Question> questions  = new LocalDataSource().getQuestions();
        CosSimilarityAnalysis cosA = new CosSimilarityAnalysis();
        SimilarityWeight sw = new SimilarityWeight();
        Question question =  cosA.analysis("我想问问天翼4G套餐",questions);
        LOG.info(question.toString());
    }
}
