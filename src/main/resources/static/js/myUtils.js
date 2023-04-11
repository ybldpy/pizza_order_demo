
const illegalUsername = "The length of user name is 5-30 and only letters and numbers are allowed to appear in username";
const illegalPassword = "The length of user name is 5-16 and only letters and numbers are allowed to appear in password";
const repeatPasswordNotEqual = "Repeat password must equals to password";

function redirect(url){
    window.location.href = url;
}

const alertUtil = {
    /**
     * 弹出消息框
     * @param msg 消息内容
     * @param type 消息框类型（参考bootstrap的alert）
     */
    alert: function(msg, type,msgContainer){
        if(typeof(type) =="undefined") { // 未传入type则默认为success类型的消息框
            type = "success";
        }
        // 创建bootstrap的alert元素
        let divElement = $("<div></div>").addClass('alert').addClass('alert-'+type).addClass('alert-dismissible');
        divElement.css(
            {
                "font-size":20
            }
        )
        divElement.text(msg); // 设置消息框的内容
        // 消息框添加可以关闭按钮
        let closeBtn = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
        $(divElement).append(closeBtn);
        // 消息框放入到页面中
        $(msgContainer).append(divElement);
        return divElement;
    },

    /**
     * 短暂显示后上浮消失的消息框
     * @param msg 消息内容
     * @param type 消息框类型
     */
    message: function(msg, type,msgContainer) {
        var divElement = alertUtil.alert(msg, type,msgContainer); // 生成Alert消息框
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
            alert(nowTop)
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
        }, 1000*5);
    }
}

function isValidateMail(mail){
    if (typeof mail!="string"){return false;}
    let reg = /^[\w-_\.+]*[\w-_\.]\@([\w]+\.)+[\w]+[\w]$/;
    return reg.test(mail);
}

function checkUserNameAndPwd(username,password){

    let rep = /^[A-Za-z0-9]+$/;
    return checkUsername(username,rep)&&checkPassword(password);
}

function showFeedback(e,eId,msg){
    let target = e.siblings("#"+eId+"Feedback");
    target[0].innerText = msg;
    target.show();
}

function checkUsername(username,rep){
    if (typeof username!="string"){
        return false;
    }
    if (username==undefined||username==null||username.length<5||username.length>30){

        return false;
    }
    if (!rep.test(username)){
        return false;
    }
    return true;
}

function checkPassword(password,rep){
    if (typeof rep=="undefined"){
        rep = /^[A-Za-z0-9]+$/;
    }
    if (typeof password!="string"){
        // alert("illegal password");
        return false;
    }
    if (password==null||password==undefined||password.length<5||password.length>20){
        // alert("length of password is between 5 and 20");
        return false;
    }
    if (!rep.test(password)){
        // alert("Only letters and digital are allowed in password");
        return false;
    }

    return true;
}


function enableLoadingVideo(btn){
    if (btn==undefined||btn==null){return;}
    if (btn[0]==undefined||btn[0]==null){return;}
    btn[0].disabled = true;
    btn[0].innerHTML = '<div class="spinner-border" role="status"><span class="sr-only">Loading...</span></div>'
}
function disableLoadingVideo(btn,msg){
    if (btn==undefined||btn==null){return;}
    if (btn[0]==undefined||btn[0]==null){return;}
    btn[0].disabled = false;
    btn[0].innerHTML = msg;
}

