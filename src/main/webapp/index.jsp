<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    // 1) 优先处理重定向：必须在任何 HTML/JS 输出之前调用 sendRedirect 或 forward
    Boolean logged = (Boolean) session.getAttribute("login");
    if (logged != null && logged) {
        response.sendRedirect(request.getContextPath() + "/dati.jsp");
        return; // 一定要 return，防止后续输出
    }

// 2) 读取一次性 session 消息（来自 Servlet 的跨请求消息）
    String msg = (String) session.getAttribute("msg");
// 不在这里输出 alert，等页面主体输出前再输出脚本并移除 session
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>答题系统</title>
    <style>
        . {
            padding: 0;
        }
        body{
            padding: 0;
            background-color: rgb(0,245,255);
            background-size: cover;
            background-repeat:no-repeat;
            margin: 0;
        }
        #denglu{
            position: absolute;
            padding: 0;
            height: 50%;
            width: 70%;
            background: rgba(255, 255, 255, 0.7);
            -webkit-backdrop-filter: blur(10px);
            backdrop-filter: blur(5px);
            border-radius: 25px;
            margin-left: 15%;
            margin-right: 15%;
            margin-top: 40%;

        }
        @media screen and (min-width: 576px){
            #denglu{
                position: absolute;
                padding: 0;
                height: 50%;
                width: 60%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
                border-radius: 25px;
                margin-left: 20%;
                margin-right: 20%;
                margin-top: 30%;

            }
        }
        @media screen and (min-width: 769px) {
            #denglu{
                position: absolute;
                padding: 0;
                height: 50%;
                width: 50%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
                border-radius: 25px;
                margin-left: 25%;
                margin-right: 25%;
                margin-top: 25%;

            }
        }
        @media screen and (min-width: 992px) {
            #denglu{
                position: absolute;
                padding: 0;
                height: 50%;
                width: 40%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
                border-radius: 25px;
                margin-left: 30%;
                margin-right: 30%;
                margin-top: 20%;

            }
        }
        @media screen and (min-width: 1200px){
            #denglu{
                position: absolute;
                padding: 0;
                height: 50%;
                width: 30%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
                border-radius: 25px;
                margin-left: 35%;
                margin-right: 35%;
                margin-top: 10%;

            }
        }
        @media screen and (min-width: 1400px) {
            #denglu{
                position: absolute;
                padding: 0;
                height: 50%;
                width: 30%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
                border-radius: 25px;
                margin-left: 35%;
                margin-right: 35%;
                margin-top: 10%;

            }
        }
        #title{
            position: relative;
            height: 100px;
            width: 90%;
            margin-left: 5%;
            margin-right: 5%;
            padding: 0;
        }
        #font{
            position: relative;
            width: 70%;
            margin: auto;
        }
        #title-text{
            text-align: center;
            font-size: 300%;
            margin: 0;
            font-weight: 700;
        }
        #form{
            position: relative;
            width: 90%;
            margin-left: 5%;
            margin-right: 5%;
        }
        #name{
            margin-right: 10%;
            margin-left: 10%;
            margin-bottom: 10px;
            float: left;
            width: 80%;
            height: 30px;
            border: none;
            border-bottom: 2px solid black;
            background-color: rgba(255, 255, 255, 0.4);
            outline: none;
            font-size: 110%;
        }
        #password{
            margin-left: 10%;
            margin-right: 10%;
            margin-bottom: 10px;
            float: left;
            width: 80%;
            height: 30px;
            border: none;
            border-bottom: 2px solid black;
            background-color: rgba(255, 255, 255, 0.4);
            outline: none;
            font-size: 110%;
        }
        #submit {
            float: left;
            margin-top: 5%;
            width: 80%;
            height: 40px;
            margin-left: 10%;
            margin-right:10%;
            border: none;
            border-bottom: 1px solid black;
            border-radius: 10px;
            background-color: rgb(250,240,230);
            font-size: 120%;
        }
        #submit:hover {
            float: left;
            margin-top: 5%;
            width: 80%;
            height: 40px;
            margin-left: 10%;
            margin-right: 10%;
            border: none;
            border-bottom: 1px solid black;
            border-radius: 10px;
            background-color: rgb(250, 235, 215);
            font-size: 120%;
        }
    </style>
</head>
<body>

<%
    // 3) 如果有跨请求的 session 消息（通常来自 sendRedirect 情况），在页面加载时弹出并移除
    if (msg != null) {
%>
<script>
    alert("<%= msg.replace("\"","\\\"").replace("\n","\\n") %>");
</script>
<%
        session.removeAttribute("msg");
    }

// 4) 继续显示 request 范围内的错误信息（如果 Servlet 使用 forward 返回时）
    String error = (String) request.getAttribute("errorMessage");
    if (error != null) {
%>
<script>
    // 也可以用 alert 或把错误渲染在 DOM 中
    alert("<%= error.replace("\"","\\\"").replace("\n","\\n") %>");
</script>
<%
    }
%>

<div id="main">
    <div id="denglu">
        <div id="biaodan">
            <div id="title">
                <div id="font">
                    <p id="title-text">剑殇</p>
                </div>
            </div>
            <form id="form" action="${pageContext.request.contextPath}/login" method="post">
                <label for="name"></label><input type="text" name="name" placeholder="请输入您的用户名" id="name" >
                <label for="password"></label><input type="password" name="password" placeholder="请输入答题序列号" id="password" >
                <input id="submit" type="submit" value="登录">
            </form>
        </div>
    </div>
</div>
</body>
</html>