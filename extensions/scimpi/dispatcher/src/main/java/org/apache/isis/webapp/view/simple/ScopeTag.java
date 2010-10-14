/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */


package org.apache.isis.webapp.view.simple;

import org.apache.isis.webapp.AbstractElementProcessor;
import org.apache.isis.webapp.context.RequestContext;
import org.apache.isis.webapp.context.RequestContext.Scope;
import org.apache.isis.webapp.processor.Request;


public class ScopeTag extends AbstractElementProcessor {

    public void process(Request request) {
        String name = request.getRequiredProperty(NAME);
        String scopeName = request.getRequiredProperty(SCOPE);
        Scope scope = RequestContext.scope(scopeName);
        changeScope(request, name, scope);
    }

    protected void changeScope(Request request, String name, Scope scope) {
        request.processUtilCloseTag();
        request.getContext().changeScope(name, scope);
        Object value = request.getContext().getVariable(name);
        request.getContext().addVariable(name, value, scope);
    }

    public String getName() {
        return "scope";
    }

}
