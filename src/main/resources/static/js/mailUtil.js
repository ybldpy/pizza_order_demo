function isValidateMail(mail){
    if (typeof mail!="string"){return false;}
    let reg = /^[\w-_\.+]*[\w-_\.]\@([\w]+\.)+[\w]+[\w]$/;
    return reg.test(mail);
}

