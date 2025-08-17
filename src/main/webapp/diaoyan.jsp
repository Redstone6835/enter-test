<%--
  Created by IntelliJ IDEA.
  User: zhangrunze
  Date: 2025/8/17
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 防止未登录就进入网站 -->
<% if (session.getAttribute("name") == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>调研</title>
    <style>
        . {
            padding: 0;
            margin: 0;
        }
        #main{
            position: absolute;
            margin-left: 6%;
            margin-right: 10%;
            padding: 4%;
            width: 80%;
            background: rgba(255, 255, 255, 0.7);
            -webkit-backdrop-filter: blur(10px);
            backdrop-filter: blur(5px);
            border-radius: 20px;
        }
        @media screen and (min-width: 576px) {
            #main{
                position: absolute;
                padding: 2%;
                margin-left: 14%;
                margin-right: 14%;
                width: 70%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
            }
        }
        @media screen and (min-width: 769px){
            #main{
                padding: 2%;
                position: absolute;
                margin-left: 14%;
                margin-right: 14%;
                width: 70%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
            }
        }
        @media screen and (min-width: 992px){
            #main{
                position: absolute;
                padding: 2%;
                margin-left: 19%;
                margin-right: 19%;
                width: 60%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
            }
        }
        @media screen and (min-width: 1200px){
            #main{
                position: absolute;
                padding: 2%;
                margin-left: 24%;
                margin-right: 24%;
                width: 50%;
                background: rgba(255, 255, 255, 0.7);
                -webkit-backdrop-filter: blur(10px);
                backdrop-filter: blur(5px);
            }
        }
        #survey {
            float: left;
            width: 100%;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.4);
            padding: 2px;
        }
        h1 {
            font-weight: normal;
            font-size: 1.1em;
            margin: 0;
            padding: 0;
        }
        #gameid,#age{
            position: relative;
            outline: none;
            border: none;
            border-radius: 2px;
            width: 30%;
            height: 25px;
            padding: 0;
            font-size: 1.2em;
        }
        body {
            background-attachment: fixed;
            background-color: rgb(0, 245, 255);
        }
        #submit{
            position: relative;
            border: none;
            border-radius: 5px;
            height: 40px;
            width: 60%;
            margin-left: 22%;
            font-size: 1.3em;
            cursor: pointer;
            background-color: #a0c4ff;
        }
        #submit:hover {
            position: relative;
            border: none;
            border-radius: 5px;
            height: 40px;
            width: 60%;
            margin-left: 22%;
            font-size: 1.3em;
            cursor: pointer;
            background-color: #d1e5ff;
        }
    </style>
</head>
<body>
<div id="main">
    <form id="survey-form" action="${pageContext.request.contextPath}/survey" method="post">
        <div id="survey">
            <h1>请输入您的游戏ID：</h1>
            <input type="text" name="id" id="gameid">
            <br>
            <h1>请输入您的年龄：</h1>
            <input type="text" name="age" id="age">
            <br>
            <h1>请选择您的特长：</h1>
            <input type="radio" name="techang" id="techang1" value="SURVIVAL">
            <label for="techang">生电</label><br>
            <input type="radio" name="techang" id="techang2" value="DIGITAL">
            <label for="techang">数电</label><br>
            <input type="radio" name="techang" id="techang3" value="MECHANICAL">
            <label for="techang">械电</label><br>
            <input type="radio" name="techang" id="techang4" value="BUILDING">
            <label for="techang">建筑</label><br>
            <input type="radio" name="techang" id="techang5" value="CODING">
            <label for="techang">编程</label><br>
            <input type="radio" name="techang" id="techang6" value="NONE">
            <label for="techang">无</label><br>
            <button type="submit" id="submit">提交</button>
        </div>
    </form>
</div>

<script type="text/javascript">
    document.getElementById('survey-form').addEventListener('submit',async (e) =>{
        e.preventDefault();

        const sf = new FormData(e.target);
        const sfObj = Object.fromEntries(sf.entries());
        //保留数据
        const localData = {
            gameid: sfObj.id,
            age: sfObj.age
        };
        //发送后端的数据

        const backendData = {
            type: sfObj.techang
        };

        try {
            localStorage.setItem('survey-result',JSON.stringify({
                data: localData,
                timestamp: new Date().getTime()
            }));
            console.log(JSON.stringify(backendData))
            // 这里写后端路径
            const response = await fetch('${pageContext.request.contextPath}/survey', {
                method: 'POST',
                body: JSON.stringify(backendData)
            });
            const data = await response.json();

            console.log(data)

            localStorage.setItem('problem', JSON.stringify({
                data,
                timestamp: Date.now()
            }));

            //跳转到结果页面
            window.location.href = '${pageContext.request.contextPath}/dati.jsp';
        } catch (error) {
            console.error('提交失败',error);
            alert('提交失败，请重试');
        }
    })
</script>

</body>
</html>