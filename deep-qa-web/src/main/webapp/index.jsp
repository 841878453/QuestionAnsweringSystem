<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apdplat.qa.model.Question"%>
<%@page import="org.apdplat.qa.model.Evidence"%>
<%@page import="org.apdplat.qa.model.CandidateAnswer"%>
<%@page import="org.apdplat.qa.model.QuestionType"%>
<%@page import="org.apdplat.qa.SharedQuestionAnsweringSystem"%>
<%@page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>自动问答系统</title>
        <script type="text/javascript">
            function answer(){
                var q = document.getElementById("q").value;
                if(q === ""||q.trim()===""){
                    console.log(1);
                    alert("输入问题为空！");
                    return;
                }
                location.href = "index.jsp?q="+q;
            }
        </script>
    </head>
    <body>
                <%
                    if (questionStr == null || "".equals(questionStr)) {
                %>
        <font color="red">请输入问题</font>
            <%
            } else if (evidenceList == null || evidenceList.size() == 0) {
            %>
        <font color="red">回答问题失败：<%=questionStr%></font><br/>
            <%
                }
                if (evidenceList != null && evidenceList.size() > 0) {
            %>
        <font color="red">问题：</font><%=questionStr%><br/>
        <font color="red">答案：</font>
        <table>
            <tr><th>序号</th><th>相似问法</th><th>回答内容</th><th>内容评分</th><th>cos相似度</th><th>Jaccard相似度</th><th>最终相似度</th></tr>
                    <%
                        int i = 0;
                        for (Evidence evidence : evidenceList) {
                            if ((++i) == 1) {
                    %>
            <tr><td><font color="red"><%=i%></font></td><td><font color="red"><%=evidence.getTitle()%></font></td><td><font color="red"><%=evidence.getSnippet()%></font></td><td><font color="red"><%=df.format(evidence.getScore())%></font></td><td><font color="red"><%=df.format(evidence.getCosSimilarity())%></font></td><td><font color="red"><%=df.format(evidence.getJaccardSimilarity())%></font></td><td><font color="red"><%=df.format(evidence.getSimilarity())%></font></td></tr>
                        <%
                        } else {
                        %>
            <tr><td><%=i%></td><td><%=evidence.getTitle()%></td><td><%=evidence.getSnippet()%></td><td><%=df.format(evidence.getScore())%></td><td><%=df.format(evidence.getCosSimilarity())%></td><td><%=df.format(evidence.getJaccardSimilarity())%></td><td><%=df.format(evidence.getSimilarity())%></td></tr>
            <%
                    }
                }
            %>        
        </table>
        <h2><a href="index.jsp">返回主页</a></h2>
        <%
        } else {
        %>
        <p>
            <b>可以像如下提问：</b><br/>
            1、<a href="index.jsp?q=天翼4G套餐">天翼4G套餐</a><br/>
            2、<a href="index.jsp?q=拆机服务">拆机服务</a><br/>
        <font color="red">输入问题：</font><input id="q" name="q" size="50" maxlength="50">
        <p></p>
        <h2><a href="#" onclick="answer();">查看答案</a></h2>
        <%
            }
        %>  	
        <br/>
        <h2><a href="history_questions.jsp">其他用户曾经问过的问题</a></h2>
    </body>
</html>