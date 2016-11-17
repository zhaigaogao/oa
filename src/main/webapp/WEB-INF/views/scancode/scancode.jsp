<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href="${contextPath}/assets/src/css/scancode.css">
</head>
<body>
    <form id="scan_form">
        <h1>请扫描二维码</h1>
        <input id="qrcode" class="scan_input" type="text" tabindex="0" name="qrcode" autofocus="true" autocomplete="off">
        <div class="scan_note">如扫描后没有自动提交,请点击【提交】按钮</div>
        <input id="scan_button" class="button button-grey" type="submit" value="提交">
        <input id="clear_button" class="button button-grey" type="button" value="清空">
    </form>
    <div id="emp_info">
        <table class="emp_info_table">
            <caption>来访人员信息</caption>
            <tbody></tbody>
        </table>
        <input id="current_id" type="hidden">
        <input id="in_button" class="button button-blue" type="button" value="进入">
        <input id="out_button" class="button button-grey" type="button" value="离开">
        <hr>
        <input id="reply_button" class="button button-grey" type="button" value="再次扫描">
    </div>

    <script id="tmpl-emp-info" type="text/html">
        <# if (model.visitStatus != '0') { #>
        <tr>
            <th>来访人员</th>
            <td>{{model.userName}}</td>
            <th>来访单位</th>
            <td>{{model.unit}}</td>
        </tr>
        <tr>
            <th>来访人员手机</th>
            <td>{{model.mobile}}</td>
            <th></th>
            <td></td>
        </tr>
        <tr>
            <th>预约人员</th>
            <td>{{model.createUserName}}</td>
            <th>预约人员部门</th>
            <td>{{model.createUserAllOrgName}}</td>
        </tr>
        <tr>
            <th>预约人员手机</th>
            <td>{{model.createUserMobile}}</td>
            <th></th>
            <td></td>
        </tr>
        <tr>
            <th>预约生效时间</th>
            <td>{{formatTime(model.startTime)}}</td>
            <th>预约失效时间</th>
            <td>{{formatTime(model.endTime)}}</td>
        </tr>
        <tr>
            <th>进入时间</th>
            <td>{{formatTime(model.entryTime)}}</td>
            <th>离开时间</th>
            <td>{{formatTime(model.outTime)}}</td>
        </tr>
        <tr>
            <th>来访状态</th>
            <td><# if (model.userStatus == 0) { #>
                    已预约
                <# } else if (model.userStatus == 1) { #>
                    已进入
                <# } else { #>
                    已离开
                <# } #>
            </td>
            <th></th>
            <td></td>
        </tr>
        <# } #>
        <# if (model.visitStatus != '1') { #>
            <tr><td class="warning" colspan="4">
                <# if (model.visitStatus == '0') { #>
                    查无此人
                <# } else if (model.visitStatus == '2') { #>
                    已超出预约时间
                <# } else if (model.visitStatus == '3') { #>
                    尚未到达预约时间
                <# } else if (model.visitStatus == '4') { #>
                    该预约已被使用
                <# } else { #>
                    预约状态不正确
                <# } #>
            </td></tr>
        <# } #>
    </script>
    <script src="${contextPath}/assets/src/page/scancode/scancode.js"></script>
</body>
</html>
