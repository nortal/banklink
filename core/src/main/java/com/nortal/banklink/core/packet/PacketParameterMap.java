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
package com.nortal.banklink.core.packet;

import com.nortal.banklink.core.packet.param.PacketParameter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class PacketParameterMap.
 */
public final class PacketParameterMap extends HashMap<String, PacketParameter> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Inits the.
     * 
     * @param request
     *            the request
     * @param macName
     *            the mac name
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public void init(HttpServletRequest request, String macName) throws InvalidParameterException {
        init(request, macName, "UTF-8");
    }

    public void init(HttpServletRequest request, String macName, String reencodeFrom) throws InvalidParameterException {
        for (String parameter : keySet()) {
            if (macName.equals(parameter)) {
                PacketParameter pParameter = get(parameter);
                setParameter(parameter, request.getParameter(pParameter.getName()));
            } else {
                setParameter(parameter, getRequestParameter(request, parameter, reencodeFrom));
            }
        }
    }

    private String getRequestParameter(HttpServletRequest request, String parameter, String reencodeFrom) {
        String reqValue = request.getParameter(parameter);
        if (reqValue == null)
            return null;
        return new String(reqValue.getBytes(Charset.forName(reencodeFrom)), Charset.forName("UTF-8"));
    }

    /**
     * Put.
     * 
     * @param packetParameter
     *            the packet parameter
     */
    public void put(PacketParameter packetParameter) {
        put(packetParameter.getName(), packetParameter);
    }

    /**
     * Sets the parameter.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public void setParameter(String key, String value) throws InvalidParameterException {
        try {
            PacketParameter p = get(key);
            if (p == null) {
                throw new InvalidParameterException("No such packet parameter '" + key + "'.");
            }
            p.setValue(value);
        } catch (Exception ex) {
            throw new InvalidParameterException(ex.toString(), ex);
        }
    }

    /**
     * Gets the parameter value.
     * 
     * @param key
     *            the key
     * @return the parameter value
     */
    public String getParameterValue(String key) {
        if (key == null) {
            return null;
        }
        PacketParameter packetParameter = get(key);
        return packetParameter != null ? packetParameter.getValue() : null;
    }

    /**
     * Parameters.
     * 
     * @return the enumeration
     */
    public Enumeration<PacketParameter> parameters() {
        Vector<PacketParameter> v = new Vector<>();
        for (int i = 0; i < size(); i++) {
            v.add(getPacketParameter(i + 1));
        }

        return v.elements();
    }

    /**
     * Reset.
     */
    public void reset() {
        for (PacketParameter parameter : values()) {
            parameter.resetValue();
        }
    }

    /**
     * Gets the packet parameter.
     * 
     * @param orderNr
     *            the order nr
     * @return the packet parameter
     */
    private PacketParameter getPacketParameter(int orderNr) {
        for (PacketParameter p : values()) {
            if (p.getOrderNr() == orderNr) {
                return p;
            }
        }
        return null;
    }
}
