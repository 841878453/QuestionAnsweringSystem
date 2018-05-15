<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="org.apdplat.qa.model.Question" %>
<%@page import="org.apdplat.qa.model.Evidence" %>
<%@page import="org.apdplat.qa.model.CandidateAnswer" %>
<%@page import="org.apdplat.qa.model.QuestionType" %>
<%@page import="org.apdplat.qa.SharedQuestionAnsweringSystem" %>
<%@page import="org.apdplat.qa.parser.WordParser" %>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    request.setCharacterEncoding("UTF-8");
    String questionStr = request.getParameter("q");
    Question question = null;
    List<CandidateAnswer> candidateAnswers = null;
    if (!"".equals(questionStr) && questionStr != null && questionStr.trim().length() > 3) {
        question = SharedQuestionAnsweringSystem.getInstance().answerQuestion(questionStr);
        if (question != null) {
            candidateAnswers = question.getAllCandidateAnswer();
        }
    } else {
%>
<div class="alert">问题为空，<a href="index.jsp">返回主页</a></div>
<%
    }
%>
<style type="text/css">
    .alert {
        color: red;
        font-size: 1.5em;
    }
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>自动问答系统</title>
    <script type="text/javascript">
        function answer() {
            var q = document.getElementById("q").value;
            if (q === "") {
                return;
            }
            location.href = "view.jsp?q=" + q;
        }
    </script>
</head>
<body>
<h2><a href="index.jsp?q=<%=questionStr%>">忽略细节</a></h2>
<%
    if (questionStr == null || questionStr.trim().length() <= 3) {
%>
<font color="red">请输入问题且长度大于3</font>
<%
} else if (candidateAnswers == null || candidateAnswers.size() == 0) {
%>
<font color="red">回答问题失败：<%=questionStr%>
</font><br/>
<%
    }
    if (question != null) {
%>
<font color="red">Question : <%=question.getQuestion()%> 问题类型：<%=question.getQuestionType().getDes()%>
    /<%=question.getQuestionType().getPos()%>
</font><br/><br/>
<font color="red">Question Words
    : </font> <%=WordParser.parse(question.getQuestion().replace("?", "").replace("？", ""))%><br/><br/>
<%
    int j = 1;
    for (Evidence evidence : question.getEvidences()) {
%>
<font color="red"> Title <%=j%> : </font> <%=evidence.getTitle()%><br/>
<font color="red"> Title Words <%=j%> : </font> <%=WordParser.parse(evidence.getTitle())%><br/>
<font color="red"> Snippet <%=j%> : </font> <%=evidence.getSnippet()%><br/>
<font color="red"> Snippet Words <%=j%> : </font> <%=WordParser.parse(evidence.getSnippet())%><br/>
<%
        j++;
    }
    if (candidateAnswers != null && candidateAnswers.size() > 0) {
%>
<p><font color="red">答案：</font></p>
<table>
    <tr>
        <th>序号</th>
        <th>候选答案</th>
        <th>答案评分</th>
    </tr>
    <%
        int i = 0;
        for (CandidateAnswer candidateAnswer : candidateAnswers) {
            if ((++i) == 1) {
    %>
    <tr>
        <td><font color="red"><%=i%>
        </font></td>
        <td><font color="red"><%=candidateAnswer.getAnswer()%>
        </font></td>
        <td><font color="red"><%=candidateAnswer.getScore()%>
        </font></td>
    </tr>
    <%
    } else {
    %>
    <tr>
        <td><%=i%>
        </td>
        <td><%=candidateAnswer.getAnswer()%>
        </td>
        <td><%=candidateAnswer.getScore()%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
    }
%>
<%
} else {
%>
<p>
    <b>可以像如下提问：</b><br/>
    1、<a href="view.jsp?q=开源项目APDPlat应用级产品开发平台的作者是谁？">开源项目APDPlat应用级产品开发平台的作者是谁？</a><br/>
    2、<a href="view.jsp?q=APDPlat开源项目的发起人是谁？">APDPlat开源项目的发起人是谁？</a><br/>
    3、<a href="view.jsp?q=谁死后布了七十二疑冢？">谁死后布了七十二疑冢？</a><br/>
    4、<a href="view.jsp?q=谁是资深Nutch搜索引擎专家？">谁是资深Nutch搜索引擎专家？</a><br/>
    5、<a href="view.jsp?q=BMW是哪个汽车公司制造的？">BMW是哪个汽车公司制造的？</a><br/>
</p>
<font color="red">输入问题：</font><input id="q" name="q" size="50" maxlength="50">
<p></p>
<h2><a href="#" onclick="answer();">查看证据及答案</a></h2>
<%
    }
%>
<br/>
<h2><a href="<%=request.getContextPath()%>/view.jsp">返回主页</a></h2>
<h2><a href="history_questions.jsp">其他用户曾经问过的问题</a></h2>
</body>
</html>