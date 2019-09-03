package com.yfny.utilscommon.basemvc.producer;

import com.yfny.utilscommon.basemvc.common.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 微服务基础Component
 * Author jisongZhou
 * Date  2019-05-09
 */
public abstract class BaseComponent<T extends BaseEntity> extends AbstractComponent<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    public BaseMapper<T> getBaseMapper() {
        return this.baseMapper;
    }

    public void setBaseMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    public ArrayList<AbstractComponent> list = new ArrayList<>();

    public T param;

    public void add(AbstractComponent component) {
        list.add(component);
    }

    public void remove(AbstractComponent component) {
        list.remove(component);
    }

    public void clear() {
        list.clear();
    }

    public AbstractComponent getChild(int i) {
        return list.get(i);
    }

    public List<AbstractComponent> allList() {
        return list;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public int insert() {
        return getBaseMapper().insert(getParam());
    }

    public int insertSelective() {
        return getBaseMapper().insertSelective(getParam());
    }

    public int update() {
        return getBaseMapper().updateByPrimaryKey(getParam());
    }

    public int updateSelective() {
        return getBaseMapper().updateByPrimaryKeySelective(getParam());
    }

    public int delete() {
        return getBaseMapper().delete(getParam());
    }

}
