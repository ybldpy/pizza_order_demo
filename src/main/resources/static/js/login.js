

function login(){

    let form = $("#loginForm");

    $.ajax(
        {
            type:"post",
            url:"login",
            data:form.serialize(),
            beforeSend:function (){
                let username = $("#username");
                let pwd = $("#password");
                let canSend = true;
                if (!checkPassword(pwd.val())){
                    let feedbackDiv = pwd.siblings(".invalid-feedback");
                    feedbackDiv[0].innerText = illegalPassword;
                    feedbackDiv.show();
                    canSend = false;
                }
                if (!checkPassword(username.val())){
                    let feedBackDiv = username.siblings(".invalid-feedback");
                    feedBackDiv[0].innerText = illegalUsername;
                    feedBackDiv.show();
                    canSend = false;
                }
                if (canSend){
                    enableLoadingVideo($(".submitBtn"));
                }
                return canSend;
            },
            success:function (result){
                disableLoadingVideo($(".submitBtn"),"Log in");
                if (result == undefined||result==null){alertUtil.message("server error","danger",$("#messageContainer"));}
                else if (result.code!=1){
                    let message = result.message;
                    if (message==null){
                        alertUtil.message("Register failed",dangerType,document.getElementById("messageContainer"));
                        return;
                    }
                    else {
                        alertUtil.message(result.message,dangerType,document.getElementById("messageContainer"));
                    }

                }
                else {
                    let manager = document.getElementById("manager").checked;
                    if (manager){
                        window.location.href = "admin/index"
                    }
                    else {
                        window.location.href = "home";
                    }
                }
            },
            error:function (){
                disableLoadingVideo($(".submitBtn"),"Log in");
                alertUtil.message("server error","danger",$("#messageContainer"));
            }
        }
    )
}