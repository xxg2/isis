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

package org.apache.isis.runtimes.embedded;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.testsupport.jmock.JUnitRuleMockery2;
import org.apache.isis.core.testsupport.jmock.JUnitRuleMockery2.Mode;
import org.apache.isis.progmodel.wrapper.applib.DisabledException;
import org.apache.isis.progmodel.wrapper.applib.WrapperFactory;
import org.apache.isis.runtimes.embedded.dom.claim.ClaimRepository;
import org.apache.isis.runtimes.embedded.dom.claim.ClaimRepositoryImpl;
import org.apache.isis.runtimes.embedded.dom.employee.Employee;
import org.apache.isis.runtimes.embedded.dom.employee.EmployeeRepository;
import org.apache.isis.runtimes.embedded.dom.employee.EmployeeRepositoryImpl;
import org.apache.isis.runtimes.embedded.internal.PersistenceState;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class WrappedFactoryDefaultTest_wrappedObject_transient {

    @Rule
    public final JUnitRuleMockery2 mockery = JUnitRuleMockery2.createFor(Mode.INTERFACES_ONLY);

    @Mock
    private EmbeddedContext mockContext;
    @Mock
    private AuthenticationSession mockAuthenticationSession;

    private EmployeeRepository employeeRepository;
    private ClaimRepository claimRepository;
    
    private Employee employeeDO;
    private Employee employeeWO;

    private IsisMetaModel metaModel;
    private WrapperFactory wrapperFactory;

    
    @Before
    public void setUp() {

        employeeRepository = new EmployeeRepositoryImpl();
        claimRepository = new ClaimRepositoryImpl();

        employeeDO = new Employee();
        employeeDO.setName("Smith");
        employeeDO.setEmployeeRepository(employeeRepository); // would be done by the EmbeddedContext impl
    }


    @Test(expected = DisabledException.class)
    public void shouldNotBeAbleToModifyPropertyIfPersistent() {
        
        // given
        initializedMetaModelWhereEmployeeIs(PersistenceState.PERSISTENT);
        
        // when
        employeeWO.setPassword("12345678");
        // then should throw exception
    }


    @Test
    public void canModifyPropertyIfTransient() {
        // given
        initializedMetaModelWhereEmployeeIs(PersistenceState.TRANSIENT);
        
        // when
        employeeWO.setPassword("12345678");
        // then be allowed
        assertThat(employeeWO.getPassword(), is("12345678"));
    }

    
    private void initializedMetaModelWhereEmployeeIs(final PersistenceState persistentState) {
        mockery.checking(new Expectations() {
            {
                allowing(mockContext).getPersistenceState(with(any(Employee.class)));
                will(returnValue(persistentState));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockContext).getPersistenceState(with(any(String.class)));
                will(returnValue(PersistenceState.STANDALONE));

                allowing(mockContext).getAuthenticationSession();
                will(returnValue(mockAuthenticationSession));
            }
        });
        metaModel = new IsisMetaModel(mockContext, employeeRepository, claimRepository);
        metaModel.init();

        wrapperFactory = metaModel.getWrapperFactory();

        employeeWO = wrapperFactory.wrap(employeeDO);
    }


}