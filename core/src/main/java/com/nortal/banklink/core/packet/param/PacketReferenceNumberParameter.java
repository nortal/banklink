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

import java.math.BigInteger;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * 
 * Used for store reference number parameter.
 * 
 * @author Vladimir Tsastsin
 */
@ToString
public final class PacketReferenceNumberParameter extends PacketParameter {

    /**
     * Instantiates a new packet reference number parameter.
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
    public PacketReferenceNumberParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        super(orderNr, name, isMac, canChange);
    }

    /**
     * Instantiates a new packet reference number parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    public PacketReferenceNumberParameter(int orderNr, String name) {
        super(orderNr, name);
    }

    /**
     * Check if reference number is correct using 7-3-1 method.
     * 
     * @param referenceNumber
     *            the reference number
     * @return true, if successful
     */

    private boolean testControl(String referenceNumber) {
        int summa = 0;
        int array[] = new int[] { 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7 };
        int controlNumber = Integer.parseInt(referenceNumber.substring(referenceNumber.length() - 1,
                referenceNumber.length()));

        for (int x = referenceNumber.length() - 2; x >= 0; x--) {
            int oneDig = Integer.parseInt(referenceNumber.substring(x, x + 1));
            summa += oneDig * array[referenceNumber.length() - x - 2];
        }
        summa = (10 - summa % 10) % 10;

        if (controlNumber != summa) {
            return false;
        }
        return true;
    }

    /**
     * Set value. Before set, check the correct format of data.
     * 
     * @param value
     *            value to set
     * @throws InvalidParameterException
     *             if the value is incorrect
     */
    @Override
    public void setValue(String value) throws InvalidParameterException {
        String refString = StringUtils.trimToEmpty(value);

        while (refString.indexOf(" ") > -1) {
            int k = refString.indexOf(" ");
            refString = refString.substring(0, k) + refString.substring(k + 1, refString.length());
        }

        if (refString.length() > 20) {
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Maximum length allowed is "
                    + m_length + ". Value: \"" + value + "\"");
        }

        if (refString.length() == 1) {
            throw new InvalidParameterException("Field \"" + m_name + "\" can't have length 1. Value: \"" + value + "\"");
        }

        if (refString.length() != 0) {
            try {
                new BigInteger(refString);
            } catch (Exception ex) {
                throw new InvalidParameterException("Reference number field \"" + m_name + "\" has incorrent format. Value: \""
                        + value + "\".", ex);
            }

            if (!testControl(refString)) {
                throw new InvalidParameterException("Reference number field \"" + m_name
                        + "\" has incorrent control number. Value: \"" + value + "\".");
            }
        }
        m_value = refString;
    }
}
