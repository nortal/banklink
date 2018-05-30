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
package com.nortal.banklink.core.log;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * An abstract representation of information to be logged.
 * 
 * @author Imre Lumiste
 */

public abstract class PacketLog implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** is inserted into log file instead of null string. */
    private static final String NULL_VALUE = "null";

    /**
     * Keeps the names of parameters.
     * 
     * @since 02.08.2001
     */
    private List<String> m_keys;

    /**
     * Keeps the values of parameters.
     * 
     * @since 02.08.2001
     */
    private List<String> m_values;

    /**
     * The sole constructor. Initializes required fields.
     * 
     * @since 20.07.2001
     * @roseuid 3B616EE5034E
     */
    protected PacketLog() {
        init();
    }

    /**
     * * Gets the parameter with given name. * * @param key * parameter's name * @throws
     * LogParameterException * if parameter with given name has not been added * @return
     * String value according to specified key * @since 20.07.2001 * @roseuid
     * 3B61695401A0
     * 
     * @param key
     *            the key
     * @return the parameter
     * @throws LogParameterException
     *             the log parameter exception
     */
    public String getParameter(String key) throws LogParameterException {
        if (m_keys.contains(key)) {
            return (String) m_values.get(m_keys.indexOf(key));
        } else {
            throw new LogParameterException("Packet log parameter \"" + key + "\" was not found.");
        }
    }

    /**
     * * Adds or replaces given parameter's value. If the value is replaced, the *
     * parameter stays * * in the same position in list where it was before. * * @param
     * key * name of parameter to set * @param value * new value * @since
     * 20.07.2001 * @roseuid 3B6169650141
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void setParameter(String key, String value) {
        if (m_keys.contains(key)) {
            m_values.set(m_keys.indexOf(key), new String(value));
        } else {
            m_keys.add(new String(key));
            m_values.add(new String(value));
        }
    }

    /**
     * Gets name of the operation. This method is intended to be returning a
     * fixed string perclass.
     * 
     * @return String
     * @since 20.07.2001
     * @roseuid 3B6169E20118
     * 
     * @return the operation name
     */
    public abstract String getOperationName();

    /**
     * Gets names of all parameters in the order they were added.
     * 
     * @return java.util.List (the elements are java.lang.String)
     * @since 02.08.2001
     * @roseuid 3B616B9800C8
     * 
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public List<String> keys() {
        return (List<String>) ((Vector<String>) m_keys).clone();
    }

    /**
     * Gets values of all parameters in the order they were added.
     * 
     * @return java.util.List (the elements are java.lang.String)
     * @since 02.08.2001
     * 
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public List<String> values() {
        return (List<String>) ((Vector<String>) m_values).clone();
    }

    /**
     * Resets the parameter data. Also sets parameter &quot;TIME&quot; to current
     * date and time.
     * 
     * @since 20.07.2001
     * @roseuid 3B616EF4029B
     */
    public void init() {
        m_keys = new Vector<>();
        m_values = new Vector<>();
        setParameter("TIME", (new Date()).toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        List<String> values = values();
        StringBuffer logRow = new StringBuffer();
        String logParameter = null;
        boolean firstParameter = true;

        Iterator<String> it = values.iterator();
        while (it.hasNext()) {
            logParameter = (String) it.next();
            logRow.append(firstParameter ? "" : "\t");
            if (logParameter != null) {
                logParameter = logParameter.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ');
                if (logParameter.equals(NULL_VALUE)) {
                    logRow.append("\"").append(logParameter).append("\"");
                } else {
                    logRow.append(logParameter);
                }
            } else {
                logRow.append(NULL_VALUE);
            }
            firstParameter = false;
        }
        return logRow.toString();
    }
}