//秒杀交互js
var seckill = {
    //封装秒杀相关ajax的url
    url: {
        //获取服务器当前时间
        nowTime: function () {
            return '/seckill/time/now';
        },
        //获取秒杀地址
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        // 执行秒杀地址
        excuteUrl: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/excution';
        }
    },
    //用户是否使用手机登录验证
    validatePhone: function (killPhone) {
        if (killPhone && killPhone.length == 11 && !isNaN(killPhone)) {
            return true;
        } else {
            return false;
        }
    },
    //执行秒杀逻辑
    handleSeckill: function (seckillId, node) {
        $('#seckill-box').html('<button id="startKill" class="btn btn-default">开始秒杀</button>');
        //秒杀已开始
        //获取秒杀地址
        $("#startKill").one('click', function () {
            $("#startKill").addClass("disabled");
            $.post(seckill.url.exposer(seckillId), {}, function (result) {
                console.log(result);
                if (result && result.success) {
                    var md5 = result.data.md5;
                    console.log(md5);
                    $.post(seckill.url.excuteUrl(seckillId, md5), {}, function (result) {
                        if (result) {
                            console.log(result);
                            $('#seckill-box').html('<button class="btn btn-success disabled">' + result.data.stateInfo + '</button>');
                        }
                    }, 'json');
                }
            }, 'json');
        });

    },
    //秒杀计时
    countdown: function (nowTime, seckillId, startTime, endTime) {
        if (nowTime > endTime) {
            //秒杀结束
            $("#seckill-box").html('秒杀结束！');
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime);
            //秒杀未开始，显示倒计时
            $("#seckill-box").countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                $("#seckill-box").html(format);
            }).on('finish.countdown', function () {
                //执行秒杀逻辑
                seckill.handleSeckill(seckillId, $('#seckill-box'));
            });
        } else {
            //执行秒杀逻辑
            seckill.handleSeckill(seckillId, $('#seckill-box'));
        }
    },
    //详情页秒杀逻辑
    detail: {
        init: function (param) {
            //获取cookie中的用户手机号码
            var killPhone = $.cookie("killPhone");

            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //如果验证未通过
                //操作模态框
                $("#seckillPhoneModal").modal({
                    show: true,//显示模态框
                    backdrop: 'static',//当用户点击模态框外部时不会关闭模态框。
                    keyboard: false//当按下 escape 键时关闭模态框，设置为 false 时则按键无效。
                });

                //绑定提交按钮点击事件
                $("#killPhoneButton").click(function () {
                    //获取手机号码输入框内容
                    var phone = $("#killPhone").val();
                    //验证手机号码是否合法
                    if (seckill.validatePhone(phone)) {
                        //如果合法
                        //将手机号码写入cookie
                        $.cookie('killPhone', phone, {expirse: 7, path: '/seckill'});//有效期7天
                        //刷新当前页
                        window.location.reload();
                    } else {
                        //验证未通过
                        $("#killPhoneMessage").hide().html("<lable class='label label-danger'>手机号码错误！</lable>").show(300);
                    }
                });
            }

            //用户已登录
            var seckillId = param['seckillId'];
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            $.get(seckill.url.nowTime(), {}, function (data) {
                if (data && data.success) {
                    var nowTime = data.data;
                    seckill.countdown(nowTime, seckillId, startTime, endTime);
                }
            }, 'json');
        }
    }
};