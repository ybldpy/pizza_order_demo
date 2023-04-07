

function login(){

    let form = $("#loginForm");

    $.ajax(
        {
            type:"post",
            url:basePath+"/login",
            data:form.serialize(),
            beforeSend:function (){
                let username = $("#username").val();
                let pwd = $("#password").val();
                if (!checkUserNameAndPwd(username,pwd)){
                    return false;
                }
                return true;
            },
            success:function (result){
                if (result == undefined||result==null){alert("server error");}
                else if (result.code!=1){
                    alert(result.message);
                }
                else {
                    window.location.href = result.data;
                }
            }
        }
    )
}