package com.yfny.utilscommon.generator.invoker.base;

/**
 * 代码生成器抽象构造器
 * Created by jisongZhou on 2019/3/5.
 **/
public abstract class AbstractBuilder {

    public abstract Invoker build();

    public boolean isParamtersValid() {
        try {
            checkBeforeBuild();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public abstract void checkBeforeBuild() throws Exception;

}
