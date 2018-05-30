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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * New DATETIME format introduced with the new banklink specification released in October 2014.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@ToString
public final class PacketDateTimeParameter extends PacketParameter {
    /**
     * Supported date-time formats .
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Instantiates a new packet date parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    public PacketDateTimeParameter(int orderNr, String name) {
        super(orderNr, name);
    }

    /**
     * Instantiates a new packet date parameter.
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
    public PacketDateTimeParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        super(orderNr, name, isMac, canChange);
    }

    /**
     * 
     * Set value. Before set, check the correct format of data.
     * 
     * @param value
     *            the new value
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    @Override
    public void setValue(String value) throws InvalidParameterException {
        String dateString = StringUtils.trimToEmpty(value);
        if (StringUtils.isBlank(dateString))
            throw new InvalidParameterException("Required field \"" + m_name + "\" is empty.");

        if (dateString.length() > 24)
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Value: \"" + value + "\".");

        parseDateTimeValue(dateString);
        m_value = dateString;
    }

    private void parseDateTimeValue(String dateStr) {
        try {
            new SimpleDateFormat(DATETIME_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidParameterException("Date field \"" + m_name + "\" has incorrect format. Value: \"" + dateStr + "\".");
        }
    }
}
