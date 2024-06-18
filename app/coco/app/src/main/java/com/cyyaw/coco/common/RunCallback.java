package com.cyyaw.coco.common;


/**
 * 回调
 */
public interface RunCallback<T> {
    default void run(T t) {
    }
}
