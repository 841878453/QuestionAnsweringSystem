package org.apdplat.qa.questionSimilarityAnalysis;

import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.util.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class JaccardSimilarityAnalysis implements SimilarityAnalysis {
    @Override
    public Question analysis(String questionStr, List<Question> questionList) {
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
            int common = 0;//两个文档共有的词
            total.addAll(evidenceFre.keySet());//两个文档所有的词
            for(String s:total){
                if(questionFre.containsKey(s) && evidenceFre.containsKey(s)){
                    common++;
                }
            }
            e.setJaccardSimilarity((double)common/total.size());
            question.addEvidence(e);
        }

        return question;
    }
}
