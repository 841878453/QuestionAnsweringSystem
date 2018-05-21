<%@ page import="org.apdplat.qa.model.Question" %>
<%@ page import="org.apdplat.qa.model.Evidence" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="org.apdplat.qa.SharedQuestionAnsweringSystem" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    request.setCharacterEncoding("UTF-8");
    String questionStr = request.getParameter("q");
    Question question = null;
    List<Evidence> evidenceList = null;
    DecimalFormat df = new DecimalFormat("0.000");
    if (questionStr != null && !"".equals(questionStr.trim())) {
        question = SharedQuestionAnsweringSystem.getInstance().answerQuestion(questionStr);
        if (question != null) {
            evidenceList = question.getEvidences();
        }
    }
%>
<style type="text/css">
    .table1{
        margin: 50px 0 0 30px;
    }

    .table1 td{
        padding: 10px 20px;
        text-align: center;
    }

    .a_button{
        margin-left: 30px;
        margin-top: 30px;
        width: 100px;
        background: #42929d;
        color: #fff;
        border-radius: 5px;
        border: 0;
        height: 40px;
        cursor: pointer;
    }
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>详情</title>
    <script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">

        function goToIndex(){
            location.href="chat.html";
        }

    </script>
</head>
<body>
<div>
    <p style="font-size: 25px;margin-left:30px;"><span><strong>用户问题：</strong></span><span><%=questionStr%></span></p>
</div>

<div class="table1">
    <p><strong>用户问题与标准问题相似度列表：</strong></p>
    <table>
        <tr><th width="8%">序号</th><th width="50%">标准问题</th><th width="12%">cos相似度</th><th width="12%">Jaccard相似度</th><th width="12%">Euclidean相似度</th></tr>
        <%
            int i = 0;
            for (Evidence evidence : evidenceList) {
                if ((++i) == 1) {
        %>
        <tr><td><font color="red"><%=i%></font></td><td><font color="red"><%=evidence.getTitle()%></font></td><td><font color="red"><%=df.format(evidence.getCosSimilarity())%></font></td><td><font color="red"><%=df.format(evidence.getJaccardSimilarity())%></font></td><td><font color="red"><%=df.format(evidence.getEuclideanSimilarity())%></font></td></tr>
        <%
        } else {
        %>
        <tr><td><%=i%></td><td><%=evidence.getTitle()%></td><td><%=df.format(evidence.getCosSimilarity())%></td><td><%=df.format(evidence.getJaccardSimilarity())%></td><td><%=df.format(evidence.getEuclideanSimilarity())%></td></tr>
        <%
                }
            }
        %>
    </table>
</div>

<div class="table1">
    <p><strong>具体回答内容和评分：</strong></p>
    <table>
        <tr><th width="8%">序号</th><th width="30%">标准问题</th><th width="40%">回答内容</th><th width="8%">评分</th></tr>
        <%
            i = 0;
            for (Evidence evidence : evidenceList) {
                if ((++i) == 1) {
        %>
        <tr><td><font color="red"><%=i%></font></td><td><font color="red"><%=evidence.getTitle()%></font></td><td><font color="red"><%=evidence.getSnippet()%></font></td><td><font color="red"><%=df.format(evidence.getScore())%></font></td></tr>
        <%
        } else {
        %>
        <tr><td><%=i%></td><td><%=evidence.getTitle()%></td><td><%=evidence.getSnippet()%></td><td><%=df.format(evidence.getScore())%></td></tr>
        <%
                }
            }
        %>
    </table>
</div>


<div class="table1">
    <p><strong>标准问题分词结果：</strong></p>
    <table>
        <tr><th width="8%">序号</th><th width="30%">标准问题分词</th><th width="50%">回答内容分词</th></tr>
        <%
            i = 0;
            for (Evidence evidence : evidenceList) {
                if ((++i) == 1) {
        %>
        <tr><td><font color="red"><%=i%></font></td><td><font color="red"><%=evidence.getTitleWords()%></font></td><td><font color="red"><%=evidence.getSnippetWords()%></font></td></tr>
        <%
        } else {
        %>
        <tr><td><%=i%></td><td><%=evidence.getTitleWords()%></td><td><%=evidence.getSnippetWords()%></td></tr>
        <%
                }
            }
        %>
    </table>
</div>
<div><button class="a_button" onclick="goToIndex()">返回首页</button></div>

</body>
</html>