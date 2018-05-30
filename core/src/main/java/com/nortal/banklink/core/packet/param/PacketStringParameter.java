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

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * 
 * Used for store string parameter.
 * 
 * @author Vladimir Tsastsin
 */
@ToString
public final class PacketStringParameter extends PacketParameter {

    /**
     * Instantiates a new packet string parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param length
     *            the length
     * @param isMac
     *            the is mac
     * @param canChange
     *            the can change
     */
    public PacketStringParameter(int orderNr, String name, int length, boolean isMac, boolean canChange) {
        super(orderNr, name, length, isMac, canChange);
    }

    /**
     * Instantiates a new packet string parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param length
     *            the length
     */
    public PacketStringParameter(int orderNr, String name, int length) {
        super(orderNr, name, length);
    }

    /**
     * Set value. Before set, check the correct format of data.
     * 
     * @param value
     *            value to set.
     * @throws InvalidParameterException
     *             if the value is incorrect
     */

    @Override
    public void setValue(String value) throws InvalidParameterException {
        String local = StringUtils.trimToEmpty(value);

        if (local.length() > m_length) {
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Maximum length allowed is "
                    + m_length + ". Value: \"" + value + "\".");
        }
        m_value = local;
    }
}
