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

import java.text.SimpleDateFormat;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Used for store date parameter.
 * 
 * @author Vladimir Tsastsin
 */
@ToString
public final class PacketDateParameter extends PacketParameter {

    /** The Constant DEFAULT_DATE_FORMATS. */
    private static final String[] DEFAULT_DATE_FORMATS = { "dd.MM.yyyy" };

    /** The m_formats. */
    private final String[] m_formats;

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
    public PacketDateParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        this(orderNr, name, isMac, canChange, DEFAULT_DATE_FORMATS);
    }

    /**
     * Instantiates a new packet date parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    public PacketDateParameter(int orderNr, String name) {
        super(orderNr, name);
        m_formats = DEFAULT_DATE_FORMATS;
    }

    /**
     * Instantiates a new packet date parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     * @param formats
     *            the formats
     */
    public PacketDateParameter(int orderNr, String name, String[] formats) {
        super(orderNr, name);
        m_formats = formats;
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
     * @param formats
     *            the formats
     */
    public PacketDateParameter(int orderNr, String name, boolean isMac, boolean canChange, String[] formats) {
        super(orderNr, name, isMac, canChange);
        m_formats = formats;
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
        if (dateString.length() > 10) {
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Value: \"" + value + "\".");
        }

        SimpleDateFormat f = null;
        int idx = 0;
        for (String m_format : m_formats) {
            f = new SimpleDateFormat(m_format);
            try {
                f.parse(dateString);
                break;
            } catch (Exception e) {
            }
            idx = idx + 1;
        }

        if (idx == m_formats.length) {
            throw new InvalidParameterException("Date field \"" + m_name + "\" has incorrect format. Value: \"" + value + "\".");
        }
        m_value = dateString;
    }
}
