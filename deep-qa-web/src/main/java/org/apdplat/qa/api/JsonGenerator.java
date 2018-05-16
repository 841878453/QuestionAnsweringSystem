/**
 *
 * APDPlat - Application Product Development Platform Copyright (c) 2013, 叶铱雷,
 * yang-shangchuan@qq.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.apdplat.qa.api;

import java.util.ArrayList;
import java.util.List;
import org.apdplat.qa.datasource.BaiduDataSource;
import org.apdplat.qa.datasource.LocalDataSource;
import org.apdplat.qa.model.CandidateAnswer;
import org.apdplat.qa.model.Evidence;
import org.apdplat.qa.model.Question;
import org.apdplat.qa.system.CommonQuestionAnsweringSystem;
import org.apdplat.qa.system.QuestionAnsweringSystem;
import org.apdplat.qa.system.SimilarityQuestionAnsweringSystem;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将候选答案生成为json格式
 * @author 叶铱雷
 */
public class JsonGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JsonGenerator.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String generate(CandidateAnswer candidateAnswer) {
        try {
            return MAPPER.writeValueAsString(candidateAnswer);
        } catch (Exception e) {
            LOG.error("生成候选答案的json表示出错", e);
        }
        return "{}";
    }

    public static String generate(List<Evidence> evidenceAnswers) {
        return generate(evidenceAnswers, -1);
    }
    public static String generate(List<Evidence> evidenceAnswers, int topN) {
        if(evidenceAnswers==null){
            return "[]";
        }
        try {
            return MAPPER.writeValueAsString(evidenceAnswers);
        } catch (Exception e) {
            LOG.error("生成候选答案的json表示出错", e);
        }
        return "[]";
    }

    public static void main(String[] args) {
        QuestionAnsweringSystem questionAnsweringSystem = new SimilarityQuestionAnsweringSystem();
        questionAnsweringSystem.setDataSource(new LocalDataSource());
        String questionStr = "办理宽带";
        Question question = questionAnsweringSystem.answerQuestion(questionStr);
        if (question != null) {
            List<Evidence> evidenceAnswers = question.getEvidences();
            System.out.println(JsonGenerator.generate(evidenceAnswers));
        }
    }
}
