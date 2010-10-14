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


package org.apache.isis.metamodel.adapter.version;

import java.io.IOException;
import java.util.Date;

import org.apache.isis.metamodel.encoding.DataInputExtended;
import org.apache.isis.metamodel.encoding.DataOutputExtended;



public abstract class VersionUserAndTimeAbstract extends VersionUserAbstract {
    private final Date time;

    public VersionUserAndTimeAbstract(final String user, final Date time) {
    	super(user);
        this.time = time;
        initialized();
    }

    public VersionUserAndTimeAbstract(DataInputExtended input) throws IOException {
    	super(input);
    	this.time = new Date(input.readLong());
    	initialized();
	}

    @Override
    public void encode(final DataOutputExtended output) throws IOException {
        super.encode(output);
        output.writeLong(time.getTime());
    }

	private void initialized() {
		// nothing to do
	}

    /////////////////////////////////////////////////////////
    //
    /////////////////////////////////////////////////////////


	public Date getTime() {
        return time;
    }


}