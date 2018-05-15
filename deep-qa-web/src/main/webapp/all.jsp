<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apdplat.qa.util.MySQLUtils"%>
<%@page import="org.apdplat.qa.model.Question"%>
<%@page import="org.apdplat.qa.model.Evidence"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    StringBuilder str = new StringBuilder();
    List<Question> questions = MySQLUtils.getQuestionsFromDatabase();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>自动问答系统</title>
    </head>
    <body>
                <%
                    int i = 1;
                    for (Question question : questions) {
                %>
                <font color="red">Question <%=(i++)%> : <%=question.getQuestion()%></font><br/>
                    <%
                        int j = 1;
                        for (Evidence evidence : question.getEvidences()) {
                    %>
                <font color="red"> Title <%=j%> : </font> <%=evidence.getTitle()%><br/>
                <font color="red"> Snippet <%=j%> : </font> <%=evidence.getSnippet()%><br/>
                    <%
                            j++;
                        }
                    %>
                <br/>
                <br/>
                <%
                    }
                    if(questions.isEmpty()){
                %>
                MySQL中没有缓存任何数据或MySQL未启动
                <%
                    }
                %>
    </body>
</html>