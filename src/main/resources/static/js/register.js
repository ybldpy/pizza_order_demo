


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
                alert("server error");
            }
            else {
                if (result.code==0){
                    alert(result.message);
                }
                else {
                    window.location.href = basePath+"/registerSuccess.html";
                }
            }
        },
        error:function (){
            alert("server error");
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

    if (username==undefined||username==null||username.length<5||username.length>30){
        alert("length of user name is between 5 and 30");
        return false;
    }
    let rep = /^[A-Za-z0-9]+$/;
    if (!rep.test(username)){
        alert("Only letters and digital are allowed in user name");
        return false;
    }
    if (password==null||password==undefined||password.length<5||password.length>20){
        alert("length of password is between 5 and 20");
        return false;
    }
    if (!rep.test(password)){
        alert("Only letters and digital are allowed in password");
        return false;
    }
    if (repeatPasswowrd!==password){
        alert("password must be equal to repeat password");
        return false;
    }
    if (!isValidateMail(mail)){
        alert("illegal mail");
        return false;
    }
    if (code==undefined||code==null){
        alert("you must input verification code");
        return false;
    }
    return true;


}


function sendVerificationCode(btn){
    // 检查邮箱是否为空和是否有效

    let val = $("#mail").val();
    alert(val)
    if(val==null||val==undefined){
        alert("mail cannot be empty");
        return;
    }


    if(!isValidateMail(val)){
        alert("illegal mail");
        return;
    }

    $.get(basePath+"/register/validateMail?mail="+val,function (data,status){


        if (data==undefined||data==null){
            alert("internal error");
            return;
        }

        if (data.code!=1){
            alert("server error");
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

