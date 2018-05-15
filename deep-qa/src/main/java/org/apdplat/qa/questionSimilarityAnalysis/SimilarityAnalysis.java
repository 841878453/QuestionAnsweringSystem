package org.apdplat.qa.questionSimilarityAnalysis;

import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;

import java.util.List;

public interface SimilarityAnalysis {
    public Question analysis(String questionStr,List<Question> questionList);
}
