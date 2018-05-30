/**
 *   Copyright 2014 Nortal AS
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.nortal.banklink.core.packet.param;


/**
 * Factory class for creating new packet parameters.
 * 
 * @author Maido Käära
 */
public final class PacketParameterFactory {

    /**
     * The Enum PacketParameterType.
     */
    public enum PacketParameterType {

        /** The integer. */
        INTEGER,
        /** The string. */
        STRING,
        /** The double. */
        DOUBLE,
        /** The acc nr. */
        ACC_NR,
        /** The ref nr. */
        REF_NR,
        /** The date. */
        DATE,
        /** The time. */
        TIME,
        DATETIME;
    }

    /**
     * Instantiates a new packet parameter factory.
     */
    private PacketParameterFactory() {

    }

    /**
     * Gets the packet parameter.
     * 
     * @param type
     *            the type
     * @param name
     *            the name
     * @param length
     *            the length
     * @param isMac
     *            the is mac
     * @param orderNr
     *            the order nr
     * @return the packet parameter
     */
    public static PacketParameter getPacketParameter(PacketParameterType type, String name, int length, boolean isMac,
            int orderNr) {
        return getPacketParameter(type, name, length, isMac, orderNr, null);
    }

    /**
     * Gets the packet parameter.
     * 
     * @param type
     *            the type
     * @param name
     *            the name
     * @param length
     *            the length
     * @param isMac
     *            the is mac
     * @param orderNr
     *            the order nr
     * @param formats
     *            the formats
     * @return the packet parameter
     */
    public static PacketParameter getPacketParameter(PacketParameterType type, String name, int length, boolean isMac,
            int orderNr, String[] formats) {
        switch (type) {
        case INTEGER:
            return new PacketIntegerParameter(orderNr, name, length, isMac, false);
        case STRING:
            return new PacketStringParameter(orderNr, name, length, isMac, false);
        case DOUBLE:
            return new PacketDoubleParameter(orderNr, name, length, isMac, false);
        case ACC_NR:
            return new PacketAccountNumberParameter(orderNr, name, isMac, false);
        case REF_NR:
            return new PacketReferenceNumberParameter(orderNr, name, isMac, false);
        case DATE:
            if (formats == null) {
                return new PacketDateParameter(orderNr, name, isMac, false);
            }
            return new PacketDateParameter(orderNr, name, isMac, false, formats);
        case TIME:
            return new PacketTimeParameter(orderNr, name, isMac, false);
        case DATETIME:
            return new PacketDateTimeParameter(orderNr, name, isMac, false);
        default:
            throw new IllegalArgumentException("No such packet parameter type!");
        }
    }

}
