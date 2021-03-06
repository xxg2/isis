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

package org.apache.isis.viewer.wicket.viewer.settings;

import com.google.inject.Singleton;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.isis.WicketViewerSettings;

@Singleton
public class WicketViewerSettingsDefault implements WicketViewerSettings {

    IsisConfiguration getConfiguration() {
        return IsisContext.getConfiguration();
    }

    @Override
    public int getTitleLengthInStandaloneTables() {
        return getConfiguration().getInteger("isis.viewer.wicket.maxTitleLengthInStandaloneTables", getMaxTitleLengthInTables());
    }

    @Override
    public int getTitleLengthInParentedTables() {
        return getConfiguration().getInteger("isis.viewer.wicket.maxTitleLengthInParentedTables", getMaxTitleLengthInTables());
    }
    
    /**
     * fallback...
     */
    private int getMaxTitleLengthInTables() {
        return getConfiguration().getInteger("isis.viewer.wicket.maxTitleLengthInTables", 12);
    }


}
