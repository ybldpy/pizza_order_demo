


function register(){
    // let result = checkField();
    // if (!result){return;}
    let form = $("#registerForm");
    $.ajax({
        type:"post",
        url:basePath+"/register",
        data:form.serialize(),
        beforeSend:function (){
            let canSend = true;
            let username = $("#username");
            let pwd = $("#pwd");
            let rPwd = $("#rpwd");
            let mail = $("#mail");
            let code = $("#verificationCode");
            let rep = /^[A-Za-z0-9]+$/;
            if (!checkUsername(username.val(),rep)) {
                showFeedback(username, username[0].id, illegalUsername);
                canSend = false;
            }
            if (!checkPassword(pwd.val(),rep)){
                showFeedback(pwd,pwd[0].id,illegalPassword);
                canSend = false;
            }
            if (rPwd.val()!==pwd.val()){
                showFeedback(rPwd,rPwd[0].id,"repeat password must equals to password");
                canSend = false;
            }
            if (!isValidateMail(mail.val())){
                showFeedback(mail,mail[0].id,"illegal mail");
                canSend = false;
            }
            let codeStr = code.val();
            if (codeStr==undefined||codeStr==null||codeStr==""){
                showFeedback(code,code[0].id,"you must input code");
                canSend = false;
            }
            if (canSend){
                enableLoadingVideo($(".submitBtn"));
            }
            return canSend;
        },
        success:function (result){

            disableLoadingVideo($(".submitBtn"),"Register");
            if (result == null||result==undefined){
                alertUtil.message("server error","danger",$("#messageContainer"));
            }
            else {
                if (result.code==0){
                    alertUtil.message("server error","danger",$("#messageContainer"));
                }
                else {
                    window.location.href = basePath+"/registerSuccess.html";
                }
            }
        },
        error:function (){
            disableLoadingVideo($(".submitBtn"),"Register");
            alertUtil.message("server error","danger",$("#messageContainer"));
        }
    })
}

function sendVerificationCode(btn){
    // 检查邮箱是否为空和是否有效

    let mail = $("#mail");
    let btnJ = $(btn);
    $.ajax({
        type:"get",
        url:"register/validateMail?mail="+mail.val(),
        beforeSend:function (){
            let val = mail.val();
            if(val==null||val==undefined){
                showFeedback(mail,mail[0].id,"illegal mail");
                return false;
            }
            if(!isValidateMail(val)){
                showFeedback(mail,mail[0].id,"illegal mail");
                return false;
            }
            enableLoadingVideo(btnJ);
            return true;
        },
        success:function (result){
            disableLoadingVideo(btnJ,"Send Verification Code");
            if (result==undefined||result==null||result.code!=1){
                alertUtil.message("Send failed","danger",$("#messageContainer"));
            }
            else {
                alertUtil.message("Send Success","info",$("#messageContainer"));
                btnJ[0].disabled = true;
                let count = 60*1000;
                let func = setInterval(function (){
                    btnJ[0].innerText = "retry after "+count+"seconds";
                    count = count-1000;
                },1000);
                setTimeout(function(){
                    clearInterval(func);
                    btnJ[0].disabled = false;
                },60*1000)
            }
        },
        error:function (){
            disableLoadingVideo(btnJ,"Send Verification Code");
            alertUtil.message("Send failed","danger",$("#messageContainer"));
        }
    })

}

