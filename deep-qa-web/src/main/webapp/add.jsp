<%@ page import="org.apdplat.qa.util.MySQLUtils" %>
<%@ page import="org.apdplat.qa.model.Question" %>
<%@ page import="org.apdplat.qa.model.Evidence" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/16
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String questionStr = request.getParameter("question");
    String answerStr = request.getParameter("answer");
    System.out.println(questionStr+answerStr);
%>
<html>
<head>
    <title>增加标准问题</title>
</head>


<style type="text/css">
   .answerText{
       width: 40%;
       height:20%;
   }
</style>
<body>
<%
    if(questionStr==null||answerStr==null||"".equals(questionStr.trim())||"".equals(answerStr.trim())){
        %>
<font color="red">内容为空！</font>
<%
    }else{
        Question q = new Question();
        q.setQuestion(questionStr);
        Evidence e = new Evidence();
        e.setTitle(questionStr);
        e.setSnippet(answerStr);
        q.addEvidence(e);
        MySQLUtils.saveQuestionToDatabase("",q);
        %>
<font color="red">保存成功！</font>
<%
    }
%>



<form action="" method="get">
    <font color="red">输入问题：</font><input id="question" name="question" size="50" maxlength="50"/><br/><br/>
    <font color="red">输入答案：</font><textarea id="answer" name="answer"  maxlength="200" class="answerText"></textarea><br/><br/>
    <button type="submit" style="margin-left:10%;">提交</button>
    <button type="reset">重置</button>
</form>
<br/><br/><br/><br/>
<h2><a href="index.jsp">返回首页</a></h2><br/>
</body>
</html>
