package org.nakedobjects.distribution.command;

import org.nakedobjects.distribution.Distribution;
import org.nakedobjects.distribution.ObjectData;
import org.nakedobjects.distribution.ReferenceData;
import org.nakedobjects.object.Session;
import org.nakedobjects.utility.ToString;


public class ExecuteClientAction extends AbstractRequest {

    private final ObjectData[] persisted;
    private final ObjectData[] changed;
    private final ReferenceData[] deleted;

    public ExecuteClientAction(Session session, ObjectData[] persisted, ObjectData[] changed, ReferenceData[] deleted) {
        super(session);
        this.persisted = persisted;
        this.changed = changed;
        this.deleted = deleted;
    }

    public void execute(Distribution distribution) {
        setResponse(distribution.executeClientAction(session, persisted, changed, deleted));
    }

    public ObjectData[] getActionResult() {
        return (ObjectData[]) getResponse();
    }

    public String toString() {
        ToString str = new ToString(this);
        str.append("persisted", persisted.length);
        str.append("changed", changed.length);
        str.append("deleted", deleted.length);
        return str.toString();
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business objects directly to the
 * user. Copyright (C) 2000 - 2005 Naked Objects Group Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address of Naked Objects
 * Group is Kingsway House, 123 Goldworth Road, Woking GU21 1NR, UK).
 */