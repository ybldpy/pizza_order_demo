package com.example.pizza_order_demo.utils;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;

import com.example.pizza_order_demo.model.User;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class UserUtil {


    public static Result checkRegisterField(User user){
        if (StringUtils.isAllBlank(user.getUsername())){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.USER_REGISTER_MISSING_PARAM,null);
        }
        else if (false&&StringUtils.isAnyBlank(user.getSafeQuestion(),user.getAnswer())){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_MISSING_PARAM,null);}
        else if (user.getUsername().length()>30||user.getUsername().length()<5){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_USERNAME_LENGTH,null);}
        else if(!isLegalUsernameOrPwd(user.getPwd())){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_USERNAME_ILLEGAL_CHARACTER,null);}
        else if (ObjectUtils.isEmpty(user.getPwd())){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_MISSING_PARAM,null);}
        else if (user.getPwd().length()>16||user.getPwd().length()<5){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_PASSWORD_LENGTH,null);}
        else if(!isLegalUsernameOrPwd(user.getPwd())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_PASSWORD_ILLEGAL_CHARACTER,null);
        }
        else if(ObjectUtils.isEmpty(user.getMail())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.MAIL_EMPTY,null);
        }
        else if (!MailUtil.isValidateMail(user.getMail())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.MAIL_ILLEGAL,null);
        }
        return null;
    }

    private static boolean isLegalUsernameOrPwd(String str){
        char c=str.charAt(0);
        for(int i=0;i<str.length();i++){
            c = str.charAt(i);
            if (!Character.isLetter(c)&&!Character.isDigit(c)){return false;}
        }
        return true;

    }


}
