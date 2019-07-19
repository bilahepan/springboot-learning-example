package org.spring.springboot.utils.copy;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanCopyUtil.class);

    /**
     * Bean拷贝工具
     *
     * @param source
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> T copy(S source, Class<T> targetClazz) {
        if(source == null) {
            return null;
        }
        try {
            T target = targetClazz.newInstance();
            BeanCopier copier = BeanCopier.create(source.getClass(), targetClazz, false);
            copier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            logger.error("Bean copy {} to {} failed!", source.getClass(), targetClazz);
            logger.error("exception=", e);
        }
        return null;
    }

    /**
     * Bean拷贝工具
     *
     * @param source
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> T copy(S source, Class<T> targetClazz, Converter converter) {
        if(converter == null) {
            return copy(source, targetClazz);
        }

        if(source == null) {
            return null;
        }
        try {
            T target = targetClazz.newInstance();
            BeanCopier copier = BeanCopier.create(source.getClass(), targetClazz, true);
            copier.copy(source, target, converter);
            return target;
        } catch (Exception e) {
            logger.error("Bean convert copy {} to {} failed!", source.getClass(), targetClazz);
            logger.error("exception=", e);
        }
        return null;
    }

    /**
     *
     * List拷贝工具
     *
     * @param sourceList
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> List<T> copyList(List<S> sourceList, Class<T> targetClazz) {
        List<T> targetList = new ArrayList<T>();
        if(CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }

        try {
            for (S source : sourceList) {
                T target = copy(source, targetClazz);
                targetList.add(target);
            }

            return targetList;
        } catch (Exception e) {
            logger.error("Bean list copy {} to {} failed!", sourceList, targetClazz);
            logger.error("exception=", e);
        }
        return targetList;
    }

    /**
     *
     * List拷贝工具
     *
     * @param sourceList
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> List<T> copyList(List<S> sourceList, Class<T> targetClazz, Converter converter) {
        List<T> targetList = new ArrayList<T>();
        if(CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        try {
            for (S source : sourceList) {
                T target = copy(source, targetClazz, converter);
                targetList.add(target);
            }

            return targetList;
        } catch (Exception e) {
            logger.error("Bean convert list copy {} to {} failed!", sourceList, targetClazz);
            logger.error("exception=", e);
        }
        return targetList;
    }
}
