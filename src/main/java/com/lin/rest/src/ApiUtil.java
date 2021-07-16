package com.lin.rest.src;

import com.crew.infrastructure.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.mvc.method.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * @Author linjing
 * @create 2021/7/15 10:48
 */
@Component
public class ApiUtil {
    @Autowired
    WebApplicationContext applicationContext;

    public List<ApiDo> getParam() {

        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 拿到Handler适配器中的全部方法
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<ApiDo> apis = new ArrayList<>();
        for (RequestMappingInfo info : methodMap.keySet()) {
            HandlerMethod handlerMethod = methodMap.get(info);
            Set<String> urlSet = info.getPatternsCondition().getPatterns();
            // 获取全部请求方式
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            for (String url : urlSet) {
                ApiDo apiDo = ApiDo.builder().api(url).type(methods.toString()).build();
                //获取请求的方法
                Method method = handlerMethod.getMethod();
                //判断方法存在@ApiDesc，获取注释
                if (method.isAnnotationPresent(ApiDesc.class)) {
                    apiDo.setDesc(method.getAnnotation(ApiDesc.class).desc());
                }
                //获取方法上的参数
                Parameter[] parameters = method.getParameters();
                if (parameters.length != 0) {
                    List<ParamDo> params = new ArrayList<>();
                    for (Parameter parameter : parameters) {
                        Class<?> clazz = parameter.getType();
                        //判断非自定义类型
                        if (isJavaClass(clazz)) {
                            ParamDo paramDo = ParamDo.builder()
                                    .name(parameter.getName())
                                    .type(parameter.getType().getName())
                                    .build();
                            params.add(paramDo);
                        } else {
                            //自定义类型
                            Field[] fields = clazz.getDeclaredFields();
                            for (Field field : fields) {
                                ParamDesc paramDesc = field.getAnnotation(ParamDesc.class);
                                ParamDo paramDo = ParamDo.builder()
                                        .name(field.getName())
                                        .type(field.getType()
                                                .getName())
                                        .build();
                                if (paramDesc != null) {
                                    paramDo.setDesc(paramDesc.desc());
                                    paramDo.setIsRequire(paramDesc.require());
                                }
                                params.add(paramDo);
                            }
                        }
                    }
                    apiDo.setParams(params);
                }
                apis.add(apiDo);
            }
        }
        return apis;
    }

    public boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
