package org.apdplat.qa.questionSimilarityAnalysis;

import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CombinationSimilarityAnalysis implements SimilarityAnalysis{
    private static final Logger LOG = LoggerFactory.getLogger(CombinationSimilarityAnalysis.class);
    private final List<SimilarityAnalysis> similarityAnalysisList = new ArrayList<>();
    private SimilarityWeight similarityWeight = new SimilarityWeight();

    public CombinationSimilarityAnalysis() {
    }

    public List<SimilarityAnalysis> getSimilarityAnalysisList() {
        return similarityAnalysisList;
    }

    public void addSimilarityAnalysis(SimilarityAnalysis similarityAnalysis){
        this.similarityAnalysisList.add(similarityAnalysis);
    }

    public SimilarityWeight getSimilarityWeight() {
        return similarityWeight;
    }

    public void setSimilarityWeight(SimilarityWeight similarityWeight) {
        this.similarityWeight = similarityWeight;
    }

    @Override
    public Question analysis(String questionStr, List<Question> questionList) {
        List<Question> questions = questionList;
        for (SimilarityAnalysis similarityAnalysis:similarityAnalysisList){
            Question question = similarityAnalysis.analysis(questionStr,questions);
            questions = new ArrayList<>();
            questions.add(question);
        }
        List<Evidence> evidenceList = questions.get(0).getEvidences();
        List<Evidence> newEvidenceList = new ArrayList<>();
        for(Evidence e:evidenceList){
            LOG.info("Cos:"+e.getCosSimilarity());
            LOG.info("Jaccard:"+e.getJaccardSimilarity());
            LOG.info("Euclidean:"+e.getEuclideanSimilarity());
            if(e.getCosSimilarity()>=similarityWeight.getCosSimilarity()&&e.getJaccardSimilarity()>=similarityWeight.getJaccardSimilarity()&&e.getEuclideanSimilarity()>=similarityWeight.getEuclideanSimilarity()){
                newEvidenceList.add(e);
            }
        }
        questions.get(0).setEvidences(newEvidenceList);

        return questions.get(0);
    }
}
