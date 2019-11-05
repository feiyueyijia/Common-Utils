package ${BasePackageName}.${AspectPackageName};

import com.yfny.utilscommon.basemvc.producer.BeforeBaseServiceImpl;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 业务通用切面处理
 * Author ${Author}
 * Date  ${Date}
 */
@Aspect
@Component
public class BeforeServiceImpl extends BeforeBaseServiceImpl {

    @Override
    public String getPackageName() {
        return "${BasePackageName}";
    }

    @Override
    public Class<?> getClazz(String className) {
    try {
        Class clazz = Class.forName(className);
        return clazz;
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }

}
