<%--
  Created by IntelliJ IDEA.
  User: zhangrunze
  Date: 2025/8/14
  Time: 21:13
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
    <title>答题</title>
    <style>
        . {
            padding: 0;
        }
        body {
            background-attachment: fixed;
            background-color: rgb(0, 245, 255);
        }
        p {
            margin: 0;
            flex-basis: 70%;

        }
        .img {
            display: inline;
            width: 20%;
            float: right;

        }
        img {
            width: 100%;
            height: 100%;
            object-fit: contain;

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
        .problem{
            float: left;
            width: 100%;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.4);
            padding: 2px;
        }
        .problem-title{
            position: relative;
            display: flex;
            justify-content: space-between;
            align-content: space-between;
            overflow: hidden;
            word-break: break-all;
            width: 100%;
        }
        textarea {
            position: relative;
            border: none;
            margin-top: 2%;
            width: 97.2%;
            background-color: rgba(250, 250, 250, 0.4);
            padding: 5px;
            font-size: 150%;
            outline: none;
            padding: 2px;
            resize: none;
            border-radius: 10px;
            box-shadow: inset -5px -5px 5px rgba(0, 0, 0, 0.3),inset 5px 5px 5px rgba(255, 255, 255, 0.7);
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

        .problem-title p {
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<div id="main">
    <form id="form" action="${pageContext.request.contextPath}/" method="get">

    </form></div>
<script>
    class Problem {
        constructor(description,id,isImg,imgUrl){
            this.description = description;
            this.id = id;
            this.isImg = isImg;
            this.imgUrl = imgUrl;
        }
        static fromJson(json) {
            return new Problem(
                json.description,
                json.id,
                json.isImg,
                json.imgUrl
            );
        }
    }
    //跨页面接受localstorage数据
    const response = localStorage.getItem('problem');
    const survey_result_n = localStorage.getItem('survey-result');

    //生成答题容器函数
    function createProblem1(n){
        //问题容器
        const problemDiv = document.createElement('div');
        problemDiv.className = 'problem';
        problemDiv.id = `problem` + n.toString();
        document.getElementById('form').appendChild(problemDiv);
        //题目容器
        const titleDiv = document.createElement('div');
        titleDiv.id = `pt` + n.toString();
        titleDiv.className = 'problem-title';
        document.getElementById(`problem` + n.toString()).appendChild(titleDiv);
        //p标签
        const p = document.createElement('p');
        document.getElementById(`pt` + n.toString()).appendChild(p);
        //输入框
        const input = document.createElement('textarea');
        input.id = n.toString();
        input.className = "answer";
        input.type = 'text';
        input.name = `answer`+n.toString();
        input.placeholder = '请输入答案';
        input.rows = '7';
        input.cols = '30';
        document.getElementById(`problem` + n.toString()).appendChild(input);
    }
    function createProblem2(n,url,id){
        //问题容器
        const problemDiv = document.createElement('div');
        problemDiv.className = 'problem';
        problemDiv.id = `problem` + n.toString();
        document.getElementById('form').appendChild(problemDiv);
        //题目容器
        const titleDiv = document.createElement('div');
        titleDiv.id = `pt` + n.toString();
        titleDiv.className = 'problem-title';
        document.getElementById(`problem` + n.toString()).appendChild(titleDiv);
        //p标签
        const p = document.createElement('p');
        document.getElementById(`pt` + n.toString()).appendChild(p);
        //图片
        const imgDiv = document.createElement('div');
        imgDiv.className = "img";
        imgDiv.id = "img"+id.toString();
        document.getElementById(`pt` + n.toString()).appendChild(imgDiv);
        const img = document.createElement('img');
        img.id = id;
        img.src = url;
        document.getElementById("img"+id.toString()).appendChild(img);

        //输入框
        const input = document.createElement('textarea');
        input.id = n.toString();
        input.className = "answer";
        input.type = 'text';
        input.name = `answer`+n.toString();
        input.placeholder = '请输入答案';
        input.rows = '7';
        input.cols = '30';
        document.getElementById(`problem` + n.toString()).appendChild(input);
    }
    // 构建链表结构
    function buildLinkedList(items) {
        if (items.length === 0) return null;

        let head = {
            data: items[0],
            next: null
        };

        let current = head;
        for (let i = 1; i < items.length; i++) {
            current.next = {
                data: items[i],
                next: null
            };
            current = current.next;
        }

        return head;
    }

    if (response) {
        const { data, timestamp1 } = JSON.parse(response);
        var i = 0;
        for(i;i<22;i+=1){
            const problem = Problem.fromJson(data[i]);
            if(problem.isImg === true){
                createProblem2(i+1,problem.imgUrl,problem.id);
                var title = document.getElementById(`pt` + (i+1));
                var p=title.getElementsByTagName("p");
                p.innerHTML=problem.description;
            }else{
                createProblem1(i+1);
                var title = document.getElementById(`pt` + (i+1));
                var p=title.getElementsByTagName("p")[0];
                p.innerHTML=problem.description;
            }
        }
        //生成提交按钮
        const submit = document.createElement('input');
        submit.id = 'submit'
        submit.type = 'submit';
        submit.value = '提交';
        document.getElementById('form').appendChild(submit);

    }

    document.getElementById('form').addEventListener('submit',async (e) =>{
        e.preventDefault();
        const { survey_data, timestamp2 } = JSON.parse(survey_result_n);
        const sf = new FormData(e.target);
        const sfObj = Object.fromEntries(sf.entries());
        const result_Data = [];
        for(var j = 0;j<10;j++){
            const answer = "answer"+(j+1).toString();
            result_Data[j] = {id: j+1,result: sfObj[answer]};
        }
        const linked_result = buildLinkedList(result_Data);
        const linked_result_data = JSON.stringify({result : linked_result,survey_result: survey_data})
        try {
            const response = await fetch('${pageContext.request.contextPath}/survey', {
                method: 'POST',
                body: linked_result_data
            });

            //跳转到结果页面
            window.location.href = '${pageContext.request.contextPath}/index.jsp';
        } catch (error) {
            console.error('提交失败',error);
            alrt('提交失败，请重试');
        }
        //清除localstorage数据
        localStorage.removeItem('problem');
        localStorage.removeItem('survey-result')
    })
</script>

</body>
</html>