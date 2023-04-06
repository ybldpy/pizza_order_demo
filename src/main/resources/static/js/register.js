




function sendVerificationCode(btn){
    // 检查邮箱是否为空和是否有效

    let val = $("#mail").val();
    alert(val)
    if(val==null||val==undefined){
        alert("mail cannot be empty");
        return;
    }

    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    if(!reg.test(val)){
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

