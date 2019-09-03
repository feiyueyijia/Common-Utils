package ${BasePackageName}exception;

import com.yfny.utilscommon.util.InvokeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理机制
 * Author ${Author}
 * Date  ${Date}
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public InvokeResult handException(HttpServletRequest req, Exception e) throws Exception {
        logger.error("报错信息:" + e.getMessage(), e);
        return InvokeResult.exception("10005", e.getMessage());
    }
}
