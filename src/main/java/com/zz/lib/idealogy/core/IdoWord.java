package com.zz.lib.idealogy.core;


public interface IdoWord extends IdoExpression {
    IdoExpression applyTo(IdoExpression exp);
    IdoExpression with(IdoExpression exp);
}
