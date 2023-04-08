package com.example.pizza_order_demo.utils;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;

import com.example.pizza_order_demo.model.User;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class UserUtil {


    /**
     *
     * @param str 用户名或密码
     * @param minLen 最小长度
     * @param maxLen 最大长度
     * @return
     */
    public static boolean isValidateField(String str,int minLen,int maxLen){
        if (StringUtils.isBlank(str)){return false;}
        int len = str.length();
        return len>=minLen&&len<=maxLen;
    }



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

    public static boolean isLegalUsernameOrPwd(String str){

        return str.matches("^[A-Za-z0-9]+$");

    }


}
