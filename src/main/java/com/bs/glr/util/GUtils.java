package com.bs.glr.util;

import com.bs.glr.bean.ResultBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author gonglqb
 * @name
 * @description
 * @create
 **/
@Slf4j
@Data
public  class GUtils {
    private static final Logger logger = LoggerFactory.getLogger(GUtils.class);


    public static Boolean isNotEmpty(Object  ...arg){
        if(arg == null){
            return false;
        }
        for (Object s : arg) {

            if(s instanceof String){
                if(StringUtils.isEmpty((String) s)){
                    return false;
                }
            }else {
                if(s == null){
                    return false;
                }
            }

        }
        return true;
    }

    public static VChechVo vCheck(Object o ) {
        if(o == null){
            return  new VChechVo(false,"对象为空");
        }
        Class<?> aClass = o.getClass();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            V annotation = field.getAnnotation(V.class);
            //判空校验
            if(GUtils.isNotEmpty(annotation) && annotation.notNull()){
//                System.out.println(field.getName());
                String fieldName = field.getName();
                String	methodName="get"+
                        fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                try {
                    Method method = aClass.getMethod(methodName);
                    Object object = method.invoke(o);
//                    System.out.println(object);

                    if(!GUtils.isNotEmpty(object)){
//                        System.out.println("字段["+fieldName+"]不能为空");
                        logger.error("字段["+fieldName+"]不能为空");
                        return new VChechVo(false,"字段["+fieldName+"]不能为空");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //固定长度校验
            if(GUtils.isNotEmpty(annotation) && annotation.len()!=-1){
//                System.out.println(field.getName());
                String fieldName = field.getName();
                String	methodName="get"+
                        fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                try {
                    Method method = aClass.getMethod(methodName);
                    Object object = method.invoke(o);
//                    System.out.println(object);
                    if(object instanceof  Integer){
//                        Integer a = (Integer) object;
                        String str = String.valueOf((Integer) object);
                        if(str.length() != annotation.len()){
                            return new VChechVo(false,"字段["+fieldName+"]的长度不为["+annotation.len()+"]");
                        }
                    }else if (object instanceof String){
                        String s = (String) object;
                        if(s.length() != annotation.len()){
                            return new VChechVo(false,"字段["+fieldName+"]的长度不为["+annotation.len()+"]");

                        }
                    }else{
                        return new VChechVo(false,"非int、string字段不支持校验");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return new VChechVo(true,"校验通过");
    }

    public static ResultBean vCheckResult(Object o ){
        VChechVo vChechVo = vCheck(o);
        if(vChechVo.getStatus()){
            return ResultBean.returnOk();
        }else {
            return ResultBean.returnError(vChechVo.getMsg());
        }

    }
}
