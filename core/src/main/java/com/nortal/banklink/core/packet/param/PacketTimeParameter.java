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

/**
 * 
 * Used for store time parammeter.
 * 
 * @author Vladimir Tsastsin
 */
@ToString
public final class PacketTimeParameter extends PacketParameter {

    /**
     * Instantiates a new packet time parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param isMac
     *            the is mac
     * @param canChange
     *            the can change
     */
    public PacketTimeParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        super(orderNr, name, isMac, canChange);
    }

    /**
     * Instantiates a new packet time parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    public PacketTimeParameter(int orderNr, String name) {
        super(orderNr, name);
    }

    /**
     * s * Set value. Before set, check the correct format of data.
     * 
     * @param value
     *            value to set
     * @throws InvalidParameterException
     *             if the value is incorrect
     */
    @Override
    public void setValue(String value) throws InvalidParameterException {
        int hour;
        int minute;
        int second;

        String timeString = StringUtils.trimToEmpty(value);
        if (timeString.length() > 8) {
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Maximum length allowed is "
                    + m_length + ". Value: \"" + value + "\"");
        }

        if (timeString.indexOf(":") < 0) {
            throw new InvalidParameterException("Time field \"" + m_name + "\" has incorrent format. Value: \"" + value
                    + "\".");
        }
        int fIn = timeString.indexOf(":");

        if (timeString.indexOf(":", timeString.indexOf(":") + 1) < 0) {
            throw new InvalidParameterException("Time field \"" + m_name + "\" has incorrent format. Value: \"" + value
                    + "\".");
        }
        int sIn = timeString.indexOf(":", timeString.indexOf(":") + 1);

        try {
            hour = Integer.parseInt(timeString.substring(0, fIn));
            minute = Integer.parseInt(timeString.substring(fIn + 1, sIn));
            second = Integer.parseInt(timeString.substring(sIn + 1, timeString.length()));
        } catch (Exception e) {
            throw new InvalidParameterException("Time field \"" + m_name + "\" has incorrent format. Value: \"" + value
                    + "\".", e);
        }

        if ((minute > 59) || (hour > 23) || (second > 59)) {
            throw new InvalidParameterException("Time field \"" + m_name + "\" has incorrent format. Value: \"" + value
                    + "\".");
        }
        m_value = timeString;
    }
}
