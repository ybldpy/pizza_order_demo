

function checkUserNameAndPwd(username,password){

    let rep = /^[A-Za-z0-9]+$/;
    return checkUsername(username,rep)&&checkPassword(password,rep);
}

function checkUsername(username,rep){
    if (typeof username!="string"){
        alert("illegal user name");
        return false;
    }
    if (username==undefined||username==null||username.length<5||username.length>30){
        alert("length of user name is between 5 and 30");
        return false;
    }
    if (!rep.test(username)){
        alert("Only letters and digital are allowed in user name");
        return false;
    }
    return true;
}

function checkPassword(password,rep){
    if (typeof password!="string"){
        return false;
    }
    if (password==null||password==undefined||password.length<5||password.length>20){
        return false;
    }
    if (!rep.test(password)){
        return false;
    }

    return true;
}

