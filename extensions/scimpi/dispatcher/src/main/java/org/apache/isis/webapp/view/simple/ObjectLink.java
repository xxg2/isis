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

import org.apache.isis.metamodel.adapter.ObjectAdapter;
import org.apache.isis.metamodel.authentication.AuthenticationSession;
import org.apache.isis.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.metamodel.spec.feature.ObjectAssociationFilters;
import org.apache.isis.runtime.context.IsisContext;
import org.apache.isis.webapp.Dispatcher;
import org.apache.isis.webapp.processor.Request;


public class ObjectLink extends AbstractLink {

    protected boolean valid(Request request, ObjectAdapter object) {
        AuthenticationSession session = IsisContext.getAuthenticationSession();
        ObjectAssociation[] visibleFields = object.getSpecification().getAssociations(ObjectAssociationFilters.dynamicallyVisible(session, object));
        return visibleFields.length > 0;
    }

    protected String linkLabel(String name, ObjectAdapter object) {
        if (name == null) {
            return object.titleString();
        } else {
            return name;
        }
    }
    
    protected String defaultView() {
        return Dispatcher.GENERIC + "." + Dispatcher.EXTENSION;
    }

    public String getName() {
        return "object-link";
    }

}
