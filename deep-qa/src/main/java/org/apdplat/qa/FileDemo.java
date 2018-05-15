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

package org.apdplat.qa;

import org.apdplat.qa.system.CommonQuestionAnsweringSystem;
import org.apdplat.qa.system.QuestionAnsweringSystem;

/**
 * 从配置文件中读取问题及证据 然后计算候选答案
 *
 * @author 叶铱雷
 */
public class FileDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        QuestionAnsweringSystem questionAnsweringSystem = new CommonQuestionAnsweringSystem();
        questionAnsweringSystem.answerQuestions();
        questionAnsweringSystem.showPerfectQuestions();
        questionAnsweringSystem.showNotPerfectQuestions();
        questionAnsweringSystem.showWrongQuestions();
        questionAnsweringSystem.showUnknownTypeQuestions();
    }
}