

function checkUserNameAndPwd(username,password){

    if (typeof username!="string"||typeof password!="string"){
        alert("illegal user name or password");
        return false;
    }
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

    return true;
}