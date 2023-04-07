const commonUtil = {
    /**
     * 弹出消息框
     * @param msg 消息内容
     * @param type 消息框类型（参考bootstrap的alert）
     */
    alert: function(msg, type){
        if(typeof(type) =="undefined") { // 未传入type则默认为success类型的消息框
            type = "success";
        }
        // 创建bootstrap的alert元素
        var divElement = $("<div></div>").addClass('alert').addClass('alert-'+type).addClass('alert-dismissible').addClass('col-md-4').addClass('col-md-offset-4');
        divElement.css({ // 消息框的定位样式
            "position": "absolute",
            "top": "40%",
            "left":"50%",
            "width":"auto",
            "font-size":"20px"
        });
        divElement.text(msg); // 设置消息框的内容
        // 消息框添加可以关闭按钮
        var closeBtn = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
        $(divElement).append(closeBtn);
        // 消息框放入到页面中
        $('body').append(divElement);
        return divElement;
    },

    /**
     * 短暂显示后上浮消失的消息框
     * @param msg 消息内容
     * @param type 消息框类型
     */
    message: function(msg, type) {
        var divElement = commonUtil.alert(msg, type); // 生成Alert消息框
        var isIn = false; // 鼠标是否在消息框中

        divElement.on({ // 在setTimeout执行之前先判定鼠标是否在消息框中
            mouseover : function(){isIn = true;},
            mouseout  : function(){isIn = false;}
        });

        // 短暂延时后上浮消失
        setTimeout(function() {
            var IntervalMS = 20; // 每次上浮的间隔毫秒
            var floatSpace = 60; // 上浮的空间(px)
            var nowTop = divElement.offset().top; // 获取元素当前的top值
            var stopTop = nowTop - floatSpace;    // 上浮停止时的top值
            divElement.fadeOut(IntervalMS * floatSpace); // 设置元素淡出

            var upFloat = setInterval(function(){ // 开始上浮
                if (nowTop >= stopTop) { // 判断当前消息框top是否还在可上升的范围内
                    divElement.css({"top": nowTop--}); // 消息框的top上升1px
                } else {
                    clearInterval(upFloat); // 关闭上浮
                    divElement.remove();    // 移除元素
                }
            }, IntervalMS);

            if (isIn) { // 如果鼠标在setTimeout之前已经放在的消息框中，则停止上浮
                clearInterval(upFloat);
                divElement.stop();
            }

            divElement.hover(function() { // 鼠标悬浮时停止上浮和淡出效果，过后恢复
                clearInterval(upFloat);
                divElement.stop();
            },function() {
                divElement.fadeOut(IntervalMS * (nowTop - stopTop)); // 这里设置元素淡出的时间应该为：间隔毫秒*剩余可以上浮空间
                upFloat = setInterval(function(){ // 继续上浮
                    if (nowTop >= stopTop) {
                        divElement.css({"top": nowTop--});
                    } else {
                        clearInterval(upFloat); // 关闭上浮
                        divElement.remove();    // 移除元素
                    }
                }, IntervalMS);
            });
        }, 1500);
    }
}

function register(){
    // let result = checkField();
    // if (!result){return;}
    let form = $("#registerForm");
    $.ajax({
        type:"post",
        url:basePath+"/register",
        data:form.serialize(),
        beforeSend:checkField,
        success:function (result){
            if (result == null||result==undefined){
                commonUtil.message("Server Error");
            }
            else {
                if (result.code==0){
                    commonUtil.message(result.message);
                }
                else {
                    window.location.href = basePath+"/registerSuccess.html";
                }
            }
        },
        error:function (){
            commonUtil.message("Server Error");
        }
    })
}

function checkField(){
    //检查名字和密码
    let username = $("#username").val();
    let password = $("#pwd").val();
    let repeatPasswowrd = $("#rpwd").val();
    let mail = $("#mail").val();
    let code = $("#verificationCode").val();

    if (!checkUserNameAndPwd(username,password)){
        return false;
    }

    if (repeatPasswowrd!==password){
        commonUtil.message("Password must be equal to repeat password");
        return false;
    }

    if (!isValidateMail(mail)){
        commonUtil.message("Illegal Mail");
        return false;
    }
    if (code==undefined||code==null){
        commonUtil.message("You must input verification code");
        return false;
    }
    return true;


}


function sendVerificationCode(btn){
    // 检查邮箱是否为空和是否有效

    let val = $("#mail").val();
    commonUtil.message(val)
    if(val==null||val==undefined){
        commonUtil.message("Mail cannot be empty");
        return;
    }


    if(!isValidateMail(val)){
        commonUtil.message("Illegal Mail");
        // alert("illegal mail");
        return;
    }

    $.get(basePath+"/register/validateMail?mail="+val,function (data,status){


        if (data==undefined||data==null){
            commonUtil.message("Internal Error");
            return;
        }

        if (data.code!=1){
            commonUtil.message("Server Error");
            return;
        }
        alert("success");
        btn.disabled = true;
        let time = 60*1000;
        let countFunc = setInterval(function (){
            btn.innerText = "retry after "+time/1000+" seconds";
            time-=1000;
        },1000);
        setTimeout(function (){
            clearInterval(countFunc);
            btn.innerText = "send verification code"
            btn.disabled = false;
        },60*1000);

    })
}



