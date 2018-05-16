package org.apdplat.qa.questionSimilarityAnalysis;

import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.util.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class EuclideanSimilarityAnalysis implements SimilarityAnalysis{
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
            total.addAll(evidenceFre.keySet());
            double temp = 0;
            for (String s:total){
                int qf =  questionFre.containsKey(s)?questionFre.get(s):0;
                int ef =  evidenceFre.containsKey(s)?evidenceFre.get(s):0;
                temp+=(qf-ef)*(qf-ef);
            }
            e.setEuclideanSimilarity(1/(Math.sqrt(temp)+1));
            question.addEvidence(e);

        }



        return question;
    }
}
