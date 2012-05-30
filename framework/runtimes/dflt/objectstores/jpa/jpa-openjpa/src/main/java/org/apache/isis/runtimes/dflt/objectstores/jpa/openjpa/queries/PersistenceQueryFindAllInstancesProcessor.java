package org.apache.isis.runtimes.dflt.objectstores.jpa.openjpa.queries;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.runtimes.dflt.runtime.persistence.query.PersistenceQueryFindAllInstances;
import org.apache.isis.runtimes.dflt.runtime.system.persistence.AdapterManager;

public class PersistenceQueryFindAllInstancesProcessor extends PersistenceQueryProcessorAbstract<PersistenceQueryFindAllInstances> {

    private static final Logger LOG = Logger.getLogger(PersistenceQueryFindAllInstancesProcessor.class);

    public PersistenceQueryFindAllInstancesProcessor(final AdapterManager adapterManager, final EntityManager entityManager) {
        super(adapterManager, entityManager);
    }

    public List<ObjectAdapter> process(final PersistenceQueryFindAllInstances persistenceQuery) {

        final ObjectSpecification specification = persistenceQuery.getSpecification();
        if (LOG.isDebugEnabled()) {
            LOG.debug("getInstances: class=" + specification.getFullIdentifier());
        }
        final Query query = QueryUtil.createQuery(getEntityManager(), "o", null, specification, null);
        final List<?> pojos = query.getResultList();
        return loadAdapters(specification, pojos);
    }
}

// Copyright (c) Naked Objects Group Ltd.
