/**
 *
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 叶铱雷, 841878453@qq.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.apdplat.qa.score.evidence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.system.ScoreWeight;
import org.apdplat.qa.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对证据进行评分 【TermMatch评分组件】 不管语法关系或词序，直接对问题和证据的词进行匹配 对于问题中的词，在title中出现一次记2/idf分
 * 对于问题中的词，在snippet中出现一次记1/idf分
 *
 * @author 叶铱雷
 */
public class TermMatchEvidenceScore implements EvidenceScore {

    private static final Logger LOG = LoggerFactory.getLogger(TermMatchEvidenceScore.class);
    private ScoreWeight scoreWeight = new ScoreWeight();

    @Override
    public void setScoreWeight(ScoreWeight scoreWeight) {
        this.scoreWeight = scoreWeight;
    }

    @Override
    public void score(Question question, Evidence evidence) {
        LOG.debug("*************************");
        LOG.debug("Evidence TermMatch评分开始");
        //1、对问题进行分词
        List<String> questionTerms = question.getWords();
        LOG.debug("questionTerms:" + questionTerms);
        //2、对证据进行分词
        List<String> titleTerms = evidence.getTitleWords();
        List<String> snippetTerms = evidence.getSnippetWords();
        LOG.debug("titleTerms:" + titleTerms);
        LOG.debug("snippetTerms:" + snippetTerms);

        Map<String, Integer> wordsMap = new HashMap<>();
        for(String word:titleTerms){
            Integer fre = wordsMap.get(word);
            if(fre == null){
                wordsMap.put(word,1);
            }else{
                wordsMap.put(word,++fre);
            }
        }
        for(String word:snippetTerms){
            Integer fre = wordsMap.get(word);
            if(fre == null){
                wordsMap.put(word,1);
            }else{
                wordsMap.put(word,++fre);
            }
        }
        //3、不管语法关系或词序，直接对问题和证据的词进行匹配
        //对于问题中的词，在evidence中出现一次记一分
        double score = 0;
        for (String questionTerm : questionTerms) {
            //忽略问题中长度为1的词
            if (questionTerm.length() < 2) {
                LOG.debug("忽略问题中长度为1的词:" + questionTerm);
                continue;
            }
            Integer fre = wordsMap.get(questionTerm);
            double idf = 0;
            if (fre != null) {
                idf = 1 / (double)fre;
            } else {
                continue;
            }
            LOG.debug(questionTerm+"  "+idf);
            for (String titleTerm : titleTerms) {
                if (questionTerm.equals(titleTerm)) {
                    LOG.debug("title match: " + questionTerm + " " + titleTerm);
                    score += idf * 2;
                    break;
                }
            }
            for (String snippetTerm : snippetTerms) {
                if (questionTerm.equals(snippetTerm)) {
                    LOG.debug("snippet match: " + questionTerm + " " + snippetTerm);
                    score += idf;
                    break;
                }
            }
        }
        score *= scoreWeight.getTermMatchEvidenceScoreWeight();
        LOG.debug("Evidence TermMatch评分:" + score);
        evidence.addScore(score);
        LOG.debug("Evidence TermMatch评分结束");
        LOG.debug("*************************");
    }
}