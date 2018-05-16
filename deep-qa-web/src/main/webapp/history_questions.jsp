<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apdplat.qa.util.MySQLUtils"%>
<%@page import="org.apdplat.qa.model.Question"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    List<Question> questions = MySQLUtils.getHistoryQuestionsFromDatabase();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>自动问答系统</title>
    </head>
    <body>
        <h2>其他用户曾经问过的问题（<%=questions.size() %>）：</h2>
                <table>
                <%
                    int i = 1;
                    for (Question question : questions) {
                        if(question.getQuestion()==null || question.getQuestion().trim().equals("")){
                            continue;
                        }
                        if(question.getQuestion().length()>50){
                            continue;
                        }
                        if(question.getQuestion().contains("傻逼")){
                            continue;
                        }
                %>
                    <tr><td><font color="red"><%=(i++)%> 、 <%=question.getQuestion()%></font></td><td><a target="_blank" href="index.jsp?q=<%=question.getQuestion().replaceAll("\"","").replaceAll("\'","")%>">答案</a></td></tr>
                <%
                    }
                %>
                </table>
                <%
                    if(questions.isEmpty()){
                %>
                还没有人问过问题
                <%
                    }
                %>
                <br/>
        <h2><a href="index.jsp">返回主页</a></h2>
    </body>
</html>