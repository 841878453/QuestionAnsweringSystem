package org.apdplat.qa.system;

import org.apdplat.qa.datasource.LocalDataSource;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.questionSimilarityAnalysis.*;
import org.apdplat.qa.score.evidence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimilarityQuestionAnsweringSystem extends QuestionAnsweringSystemImpl{
    private static final Logger LOG = LoggerFactory.getLogger(SimilarityQuestionAnsweringSystem.class);


    public SimilarityQuestionAnsweringSystem() {
        LOG.info("开始构建问答系统");
        //1、默认评分组件权重
        ScoreWeight scoreWeight = new ScoreWeight();

        //2.设置证据评分组件
        //***********************
        //2.1、TermMatch评分组件
        EvidenceScore termMatchEvidenceScore = new TermMatchEvidenceScore();
        termMatchEvidenceScore.setScoreWeight(scoreWeight);
        //2.2、二元模型评分组件
        EvidenceScore bigramEvidenceScore = new BigramEvidenceScore();
        bigramEvidenceScore.setScoreWeight(scoreWeight);
        //2.3、跳跃二元模型评分组件
        EvidenceScore skipBigramEvidenceScore = new SkipBigramEvidenceScore();
        skipBigramEvidenceScore.setScoreWeight(scoreWeight);
        //2.4、组合证据评分组件
        CombinationEvidenceScore combinationEvidenceScore = new CombinationEvidenceScore();
        combinationEvidenceScore.addEvidenceScore(termMatchEvidenceScore);
        combinationEvidenceScore.addEvidenceScore(bigramEvidenceScore);
        combinationEvidenceScore.addEvidenceScore(skipBigramEvidenceScore);

        super.setEvidenceScore(combinationEvidenceScore);

        SimilarityWeight similarityWeight = new SimilarityWeight();

        SimilarityAnalysis cosSimilarityAnalysis = new CosSimilarityAnalysis();
        SimilarityAnalysis jaccardSimilarityAnalysis = new JaccardSimilarityAnalysis();
        CombinationSimilarityAnalysis CombiSimilarityAnalysis = new CombinationSimilarityAnalysis();
        CombiSimilarityAnalysis.addSimilarityAnalysis(cosSimilarityAnalysis);
        CombiSimilarityAnalysis.addSimilarityAnalysis(jaccardSimilarityAnalysis);
        CombiSimilarityAnalysis.setSimilarityWeight(similarityWeight);

        super.setSimilarityAnalysis(CombiSimilarityAnalysis);

        LOG.info("问答系统构造完成");
    }

    public static void main(String[] args) {
        QuestionAnsweringSystem questionAnsweringSystem = new SimilarityQuestionAnsweringSystem();
        questionAnsweringSystem.setDataSource(new LocalDataSource());
        Question q = questionAnsweringSystem.answerQuestion("我想问问天翼4G套餐");
        LOG.info(q.toString());
    }
}
