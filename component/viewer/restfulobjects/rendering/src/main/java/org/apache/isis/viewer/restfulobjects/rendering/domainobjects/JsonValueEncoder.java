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
package org.apache.isis.viewer.restfulobjects.rendering.domainobjects;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facets.object.encodeable.EncodableFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;

/**
 * Similar to Isis' value encoding, but with additional support for JSON
 * primitives.
 */
public final class JsonValueEncoder {

    public static class ExpectedStringRepresentingValueException extends IllegalArgumentException {
        private static final long serialVersionUID = 1L;
    }

    public ObjectAdapter asAdapter(final ObjectSpecification objectSpec, final JsonRepresentation argRepr) {
        if (objectSpec == null) {
            String reason = "ObjectSpec is null, cannot validate";
            argRepr.mapPut("invalidReason", reason);
            throw new IllegalArgumentException(reason);
        }
        final EncodableFacet encodableFacet = objectSpec.getFacet(EncodableFacet.class);
        if (encodableFacet == null) {
            String reason = "ObjectSpec expected to have an EncodableFacet";
            argRepr.mapPut("invalidReason", reason);
            throw new IllegalArgumentException(reason);
        }
        if(!argRepr.mapHas("value")) {
            String reason = "No 'value' key";
            argRepr.mapPut("invalidReason", reason);
            throw new IllegalArgumentException(reason);
        }
        final JsonRepresentation argValueRepr = argRepr.getRepresentation("value");
        if(argValueRepr == null) {
            return null;
        }
        if (!argValueRepr.isValue()) {
            String reason = "Representation must be of a value";
            argRepr.mapPut("invalidReason", reason);
            throw new IllegalArgumentException(reason);
        }

        // special case handling for JSON built-ins
        if (isBoolean(objectSpec)) {
            if (!argValueRepr.isBoolean()) {
                throwIncompatibleException(objectSpec, argRepr);
            }
            final String argStr = "" + argValueRepr.asBoolean();
            return encodableFacet.fromEncodedString(argStr);
        }

        if (isInteger(objectSpec)) {
            if (argValueRepr.isInt()) {
                final String argStr = "" + argValueRepr.asInt();
                return encodableFacet.fromEncodedString(argStr);
            }
            // best effort
            if (argValueRepr.isString()) {
                final String argStr = argValueRepr.asString();
                return encodableFacet.fromEncodedString(argStr);
            }
            // give up
            throwIncompatibleException(objectSpec, argRepr);
        }

        if (isLong(objectSpec)) {
            if (!argValueRepr.isLong()) {
                throwIncompatibleException(objectSpec, argRepr);
            }
            final String argStr = "" + argValueRepr.asLong();
            return encodableFacet.fromEncodedString(argStr);
        }

        if (isBigInteger(objectSpec)) {
            if (argValueRepr.isBigInteger()) {
                final String argStr = "" + argValueRepr.asBigInteger();
                return encodableFacet.fromEncodedString(argStr);
            }
            // best effort
            if (argValueRepr.isLong()) {
                final String argStr = "" + argValueRepr.asLong();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isInt()) {
                final String argStr = "" + argValueRepr.asInt();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isString()) {
                final String argStr = argValueRepr.asString();
                return encodableFacet.fromEncodedString(argStr);
            }
            // give up
            throwIncompatibleException(objectSpec, argRepr);
        }

        if (isBigDecimal(objectSpec)) {
            if (argValueRepr.isBigDecimal()) {
                final String argStr = "" + argValueRepr.asBigDecimal();
                return encodableFacet.fromEncodedString(argStr);
            }
            // best effort
            if (argValueRepr.isBigInteger()) {
                final String argStr = "" + argValueRepr.asBigInteger();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isDouble()) {
                final String argStr = "" + argValueRepr.asDouble();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isLong()) {
                final String argStr = "" + argValueRepr.asLong();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isInt()) {
                final String argStr = "" + argValueRepr.asInt();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isString()) {
                final String argStr = argValueRepr.asString();
                return encodableFacet.fromEncodedString(argStr);
            }
            // give up
            throwIncompatibleException(objectSpec, argRepr);
        }

        if (isDouble(objectSpec)) {
            if (argValueRepr.isDouble()) {
                final String argStr = "" + argValueRepr.asDouble();
                return encodableFacet.fromEncodedString(argStr);
            }
            // best effort
            if (argValueRepr.isLong()) {
                final String argStr = "" + argValueRepr.asLong();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isInt()) {
                final String argStr = "" + argValueRepr.asInt();
                return encodableFacet.fromEncodedString(argStr);
            }
            if (argValueRepr.isString()) {
                final String argStr = argValueRepr.asString();
                return encodableFacet.fromEncodedString(argStr);
            }
            // give up
            throwIncompatibleException(objectSpec, argRepr);
        }

        if (argValueRepr.isString()) {
            final String argStr = argValueRepr.asString();
            return encodableFacet.fromEncodedString(argStr);
        }
        
        final String reason = "Unable to parse value";
        argRepr.mapPut("invalidReason", reason);
        throw new IllegalArgumentException(reason);
    }

    Object asObject(final ObjectAdapter objectAdapter) {
        if (objectAdapter == null) {
            throw new IllegalArgumentException("objectAdapter cannot be null");
        }
        final ObjectSpecification objectSpec = objectAdapter.getSpecification();

        // special case handling for JSON built-ins (at least so far as json.org
        // defines them).
        if (isBoolean(objectSpec) || isInteger(objectSpec) || isLong(objectSpec) || isBigInteger(objectSpec) || isDouble(objectSpec) || isBigDecimal(objectSpec)) {
            // simply return
            return objectAdapter.getObject();
        }

        final EncodableFacet encodableFacet = objectSpec.getFacet(EncodableFacet.class);
        if (encodableFacet == null) {
            throw new IllegalArgumentException("objectSpec expected to have EncodableFacet");
        }
        return encodableFacet.toEncodedString(objectAdapter);
    }

    private boolean isBoolean(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, boolean.class, Boolean.class);
    }

    private boolean isInteger(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, int.class, Integer.class);
    }

    private boolean isLong(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, long.class, Long.class);
    }

    private boolean isBigInteger(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, BigInteger.class);
    }

    private boolean isDouble(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, double.class, Double.class);
    }

    private boolean isBigDecimal(final ObjectSpecification objectSpec) {
        return hasCorrespondingClass(objectSpec, BigDecimal.class);
    }

    private boolean hasCorrespondingClass(final ObjectSpecification objectSpec, final Class<?>... candidates) {
        final Class<?> specClass = objectSpec.getCorrespondingClass();
        for (final Class<?> candidate : candidates) {
            if (specClass == candidate) {
                return true;
            }
        }
        return false;
    }

    private void throwIncompatibleException(final ObjectSpecification objectSpec, final JsonRepresentation argRepr) {
        String reason = String.format("representation '%s' incompatible with objectSpec '%s'", argRepr.getMap("value").toString(), objectSpec.getCorrespondingClass().getName());
        argRepr.mapPut("invalidReason", reason);
        throw new IllegalArgumentException(reason);
    }

}
