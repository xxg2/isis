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


package org.apache.isis.extensions.dnd.view.option;

import org.apache.isis.commons.lang.ToString;
import org.apache.isis.metamodel.consent.Allow;
import org.apache.isis.metamodel.consent.Consent;
import org.apache.isis.metamodel.spec.feature.ObjectActionType;
import org.apache.isis.extensions.dnd.drawing.Location;
import org.apache.isis.extensions.dnd.view.UserAction;
import org.apache.isis.extensions.dnd.view.View;
import org.apache.isis.extensions.dnd.view.Workspace;


/**
 * Each option that a user is shown in an objects popup menu a MenuOption. A MenuOption details: the name of
 * an option (in the users language);
 * <ul>
 * the type of object that might result when requesting this option
 * </ul>; a way to determine whether a user can select this option on the current object.
 */
public abstract class UserActionAbstract implements UserAction {
    private String description;
    private String name;
    private final ObjectActionType type;

    public UserActionAbstract(final String name) {
        this(name, ObjectActionType.USER);
    }

    public UserActionAbstract(final String name, final ObjectActionType type) {
        this.name = name;
        this.type = type;
    }

    public Consent disabled(final View view) {
        return Allow.DEFAULT;
    }

    public abstract void execute(final Workspace workspace, final View view, final Location at);

    public String getDescription(final View view) {
        return description;
    }

    public String getHelp(final View view) {
        return "No help available for user action";
    }

    /**
     * Returns the stored name of the menu option.
     */
    public String getName(final View view) {
        return name;
    }

    public ObjectActionType getType() {
        return type;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final ToString str = new ToString(this);
        str.append("name", name);
        str.append("type", type);
        return str.toString();
    }
}