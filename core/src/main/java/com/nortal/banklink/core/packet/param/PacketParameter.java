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

import com.nortal.banklink.core.packet.InvalidParameterException;

// TODO: Auto-generated Javadoc
/**
 * Abstact class for all packet parameters. Used for store packet parameters.
 * 
 * @author Vladimir Tsastsin
 */
public abstract class PacketParameter {

    /** Name of PacketParameter. */

    protected String m_name;

    /** Value of PacketParameter. */
    protected String m_value = "";

    /** * If it used in making MAC. */
    protected boolean m_isMac;

    /** * Order Number in Packet. */
    protected int m_orderNr;

    /**
     * Can be change latter by user.
     */
    protected boolean m_canChange;

    /**
     * Length of the value.
     */
    protected int m_length;

    /**
     * Instantiates a new packet parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    protected PacketParameter(int orderNr, String name) {
        this(orderNr, name, true, false);
    }

    /**
     * Create PacketParameter with specified order number, name, length, if in
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param isMac
     *            if in MAC
     * @param canChange
     *            if it can be changed latter
     */
    protected PacketParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        m_orderNr = orderNr;
        m_name = name;
        m_isMac = isMac;
        m_canChange = canChange;
    }

    /**
     * Instantiates a new packet parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param length
     *            the length
     */
    protected PacketParameter(int orderNr, String name, int length) {
        this(orderNr, name, length, true, false);
    }

    /**
     * Create PacketParameter with specified order number, name, length, if in
     * MAC, if it can be changed latter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param length
     *            length of the value of the PacketParameter
     * @param isMac
     *            if in MAC
     * @param canChange
     *            if it can be changed latter
     */
    protected PacketParameter(int orderNr, String name, int length, boolean isMac, boolean canChange) {
        m_orderNr = orderNr;
        m_name = name;
        m_isMac = isMac;
        m_canChange = canChange;
        m_length = length;
    }

    /**
     * Set the value of the PacketParameter.
     * 
     * @param value
     *            value of the PacketParameter.
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public abstract void setValue(String value) throws InvalidParameterException;

    /**
     * Return value of PacketParameter as String.
     * 
     * @return String value of PacketParameter
     */
    public String getValue() {
        return m_value;
    }

    /**
     * Reset value.
     */
    public void resetValue() {
        m_value = "";
    }

    /**
     * Return the order number of PacketParemeter in Packet
     * 
     * @return int order number of PacketParemeter in Packet.
     */
    public int getOrderNr() {
        return m_orderNr;
    }

    /**
     * If it used in MAC
     * 
     * @return boolean ff it used in MAC.
     * 
     */
    public boolean isMac() {
        return m_isMac;
    }

    /**
     * Return name of PacketParameter.
     * 
     * @return String name of PacketParameter.
     */
    public String getName() {
        return m_name;
    }

}
