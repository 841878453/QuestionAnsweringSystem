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
    <style type="text/css">
        .a_button{
            width: 100px;
            background: #42929d;
            color: #fff;
            border-radius: 5px;
            border: 0;
            height: 40px;
            cursor: pointer;
        }
    </style>

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
                    <tr><td><font color="red"><%=(i++)%> 、 <%=question.getQuestion()%></font></td><td><button class="a_button"  onclick="goToView('<%=question.getQuestion().replaceAll("\"","").replaceAll("\'","")%>')">查看详细回答</button></td></tr>
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
        <div><button class="a_button" onclick="goToIndex()">返回首页</button></div>
        <script type="text/javascript">
            function goToIndex(){
                location.href="chat.html";
            }

            function goToView(questionStr){
                location.href="view.jsp?q="+questionStr;
            }
        </script>
    </body>
</html>