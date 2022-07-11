/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.security.mgt;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;

/**
 *
 *
 * @author howiefh
 */
public class StatelessSessionStorageEvaluator implements SessionStorageEvaluator {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.shiro.mgt.SessionStorageEvaluator#isSessionStorageEnabled(
     * org.apache.shiro.subject.Subject)
     */
    @Override
    public boolean isSessionStorageEnabled(Subject subject) {
        return false;
    }

}
