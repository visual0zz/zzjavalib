package com.zz.lib.idealogy.core;

/**
 * 情绪向量
 */
public interface IdoMood {
    IdoMood add(IdoMood idoMood);
    IdoMood sub(IdoMood idoMood);
    IdoMood reverse();
}
