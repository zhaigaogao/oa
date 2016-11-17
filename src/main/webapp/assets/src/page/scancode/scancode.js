+ function () {
    var options = {
        evaluate: /<#([\s\S]+?)#>/g,
        interpolate: /\{\{\{([\s\S]+?)\}\}\}/g,
        escape: /\{\{([^\}]+?)\}\}(?!\})/g
    };
    var testInfo = {
        unit: 'aaa',
        userName: 'aaa',
        mobile: '11111111111',
        orderName: 'bbb',
        orderDept: 'bbb',
        orderMobile: '22222222222',
        startTime: 12345678,
        endTime: 12345678,
        entryTime: 12345678,
        outTime: 12345678,
        userStatus: 1
    };

    var urls = {
        queryVisitInfo: CONTEXT_PATH + '/visitingInfo/queryVisitInfo.do',
        addComVisitInfo: CONTEXT_PATH + '/visitingInfo/addComVisitInfo.do',
        addOutVisitInfo: CONTEXT_PATH + '/visitingInfo/addOutVisitInfo.do',
    };
    var formatDate = function (paramDate, format) {
        var o = {
            'M+': paramDate.getMonth() + 1, //month
            'd+': paramDate.getDate(), //day
            'h+': paramDate.getHours() % 12, //hour
            'H+': paramDate.getHours(),
            'm+': paramDate.getMinutes(), //minute
            's+': paramDate.getSeconds(), //second
            'q+': Math.floor((paramDate.getMonth() + 3) / 3), //quarter
            'S': paramDate.getMilliseconds() //millisecond
        };
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (paramDate.getFullYear() + '').substr(4 - RegExp.$1.length));
        for (var k in o) {
            if (new RegExp('(' + k + ')').test(format)) {
                format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] :
                    ('00' + o[k]).substr(('' + o[k]).length));
            }
        }

        return format;
    };
    var formatTime = function (timestamp) {
        if (timestamp) {
            var d = new Date(timestamp);
            return formatDate(d, 'yyyy-MM-dd HH:mm');
        } else {
            return  '';
        }
    };

    var run = function () {
        var $qrcode = $('#qrcode'),
            $currentId = $('#current_id'),
            $scanForm = $('#scan_form'),
            $empBox = $('#emp_info'),
            $empTable = $('.emp_info_table');
        var $replyBtn = $('#reply_button'),
            $inBtn = $('#in_button'),
            $outBtn = $('#out_button');
        var infoRender = _.template($('#tmpl-emp-info').html(), options);

        $scanForm.on('submit', function (e) {
            e.preventDefault();
            //var data = $(this).serialize();
            var qrurl = $qrcode.val();
            var qrParamters = qrurl.split('?')[1];

            $.ajax({
                url: urls.queryVisitInfo,
                data: qrParamters,
                type: 'POST',
                dataType: 'json'
            }).done(function (res) {
                if (res.success) {
                    $empTable.find('tbody').html(infoRender({
                        model: res.model,
                        formatTime: formatTime
                    }));
                    $currentId.val(res.model.id);
                    $scanForm.hide();
                    $empBox.show();

                    if (res.model.userStatus == 2 || res.model.visitStatus == 0) {
                        $inBtn.hide();
                        $outBtn.hide();
                    } else {
                        $inBtn.show();
                        $outBtn.show();
                    }
                }
                else {
                    alert(res.message);
                }
            }).fail(function () {
                alert('发生错误！');
            });
        });

        var reply = function () {
            $scanForm[0].reset();
            $currentId.val('');
            $scanForm.show();
            $empBox.hide();
            $qrcode.focus();
        };
        $('#clear_button').on('click', reply);
        $replyBtn.on('click', reply);

        $inBtn.on('click', function () {
            $.ajax({
                url: urls.addComVisitInfo,
                data: { id: $currentId.val() },
                type: 'POST',
                dataType: 'json'
            }).done(function (res) {
                if (res.success) {
                    alert('操作成功!');
                    reply();
                } else {
                    alert(res.message);
                }
            }).fail(function () {
                alert('发生错误！');
            });
        });
        $outBtn.on('click', function () {
            $.ajax({
                url: urls.addOutVisitInfo,
                data: { id: $currentId.val() },
                type: 'POST',
                dataType: 'json'
            }).done(function (res) {
                if (res.success) {
                    alert('操作成功!');
                    reply();
                } else {
                    alert(res.message);
                }
            }).fail(function () {
                alert('发生错误！');
            });
        });

        $qrcode.focus();
    };

    $(run);
}();
