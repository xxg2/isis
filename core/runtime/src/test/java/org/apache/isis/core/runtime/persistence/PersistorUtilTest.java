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


package org.apache.isis.core.runtime.persistence;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.ResolveState;
import org.apache.isis.core.metamodel.adapter.oid.Oid;
import org.apache.isis.core.runtime.testsystem.ProxyJunit3TestCase;
import org.apache.isis.core.runtime.testsystem.TestPojo;
import org.apache.isis.core.runtime.testsystem.TestProxyOid;


public class PersistorUtilTest extends ProxyJunit3TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testRecreateTransientAdapter() {
        final Oid oid = new TestProxyOid(13, false);
        final Object object = new TestPojo();
        final ObjectAdapter adapter = getAdapterManagerTestSupport().testCreateTransient(object, oid);
        assertEquals(oid, adapter.getOid());
        assertEquals(object, adapter.getObject());
        assertEquals(ResolveState.TRANSIENT, adapter.getResolveState());
        assertEquals(null, adapter.getVersion());
    }

    public void testRecreatePersistentAdapter() {
        final Oid oid = new TestProxyOid(15, true);
        final Object object = new TestPojo();
        final ObjectAdapter adapter = getAdapterManagerPersist().recreateRootAdapter(oid, object);
        assertEquals(oid, adapter.getOid());
        assertEquals(object, adapter.getObject());
        assertEquals(ResolveState.GHOST, adapter.getResolveState());

        assertEquals(null, adapter.getVersion());
    }

}
