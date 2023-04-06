package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.commons.constant.UserConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.exception.InternalException;
import com.example.pizza_order_demo.mapper.UserMapper;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.RoleService;
import com.example.pizza_order_demo.service.UserRoleService;
import com.example.pizza_order_demo.service.UserService;
import com.example.pizza_order_demo.utils.MailUtil;
import com.example.pizza_order_demo.utils.UserUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



import java.util.concurrent.ConcurrentHashMap;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    private ConcurrentHashMap<String,String> codeMap = new ConcurrentHashMap<>();
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final long duration = 1*1000*60*5;
    private static final String MAIL_POSTFIX_REGISTER = "register";
    private static final String MAIL_POSTFIX_RESET = "reset";



    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Object userRegister(User user,String code){
        Result result = UserUtil.checkRegisterField(user);
        if (result!=null){return result;}
        if (StringUtils.isBlank(code)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_MISSING_PARAM,null);
        }
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(user.getUsername());
        User userInDb = userService.selectFirstByExample(userExample);
        if (!ObjectUtils.isEmpty(userInDb)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.USER_REGISTER_USERNAME_EXIST,null);
        }

//        String mailCode = codeMap.get(user.getMail()+MAIL_POSTFIX_REGISTER);
//        if (StringUtils.isBlank(mailCode)){
//            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.CODE_WRONG,null);
//        }
//        String[] mailCodeAndTime = mailCode.split(",");
//        long start = Long.parseLong(mailCodeAndTime[1]);
//        if (System.currentTimeMillis()-start>duration){
//            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.CODE_EXPIRE,null);
//        }
//        if (!code.equals(mailCodeAndTime[0])){
//            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.CODE_WRONG,null);
//        }
//        codeMap.remove(user.getMail()+MAIL_POSTFIX_REGISTER);
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user.setGender(user.getGender()==null?0:user.getGender());
        user.setStatus(1);
        int res = userService.insert(user);
        if (res<1){throw new CURDException();}
        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(UserConstant.ROLE_CUSTOMER);
        Role role = roleService.selectFirstByExample(roleExample);
        if (ObjectUtils.isEmpty(role)){
            throw new InternalException("role cannot find");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        res = userRoleService.insert(userRole);
        if(res<1){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }


    @GetMapping("/register/validateMail")
    @ResponseBody
    public Object createRegisterCode(String mail) {
        if(StringUtils.isBlank(mail)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.MAIL_EMPTY,null);
        }
        if (!MailUtil.isValidateMail(mail)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.MAIL_ILLEGAL,null);
        }
        String code = MailUtil.createValidationCode(4);
        System.out.println(code);
//        boolean success = MailUtil.sendCode(code,mail);
//        if (!success){
//            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.CODE_SEND_ERROR,null);
//        }
        codeMap.put(mail+MAIL_POSTFIX_REGISTER,code+","+System.currentTimeMillis());
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }


    @GetMapping("/resetPwd/ValidateMail")
    public Object createResetCode(Integer uid){
        return null;
    }






}
