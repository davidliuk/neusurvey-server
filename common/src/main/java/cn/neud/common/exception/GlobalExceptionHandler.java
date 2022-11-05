package cn.neud.common.exception;

import cn.neud.common.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //aop
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局异常.....");
        e.printStackTrace();
        return new Result().error(200, "执行全局异常处理");
    }

    //特定异常处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return new Result().error(200,"执行ArithmeticException异常处理");
    }

    //NEUException
    @ExceptionHandler(NEUException.class)
    @ResponseBody
    public Result error(NEUException e) {
        e.printStackTrace();
        return new Result().error(e.getCode(), e.getMsg());
    }

}
