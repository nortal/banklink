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
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

/**
 * Used for store account number parameter.
 * 
 * @author Vladimir Tsastsin
 * @author Siim Viiklaid
 */
@ToString
public final class PacketAccountNumberParameter extends PacketParameter {

    /** The Constant ESTONIA_ISO_CODE. */
    private static final String ESTONIA_ISO_CODE = "EE";

    /** The Constant LATVIA_ISO_CODE. */
    private static final String LATVIA_ISO_CODE = "LV";

    /**
     * Instantiates a new packet account number parameter.
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
    public PacketAccountNumberParameter(int orderNr, String name, boolean isMac, boolean canChange) {
        super(orderNr, name, isMac, canChange);
    }

    /**
     * Instantiates a new packet account number parameter.
     * 
     * @param orderNr
     *            the order nr
     * @param name
     *            the name
     */
    public PacketAccountNumberParameter(int orderNr, String name) {
        super(orderNr, name);
    }

    /**
     * * Set value. Before set, check the correct format of data. * * @param value
     * * value to set * @throws InvalidParameterException * if the value is
     * incorrect
     * 
     * @param value
     *            the new value
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    @Override
    public void setValue(String value) throws InvalidParameterException {
        String accountString = StringUtils.trimToEmpty(value);
        while (accountString.indexOf(" ") > -1) {
            int k = accountString.indexOf(" ");
            accountString = accountString.substring(0, k) + accountString.substring(k + 1, accountString.length());
        }

        if ((accountString.length() > 20) && (accountString.length() < 5)) {
            throw new InvalidParameterException("Field \"" + m_name + "\" has incorrect length. Maximum length allowed is "
                    + m_length + ". Value: \" value \"");
        }

        if (!isValidEstonianIBANBankAccountNr(accountString) && !isValidEstonianInternalBankAccountNr(accountString)) {
            throw new InvalidParameterException("Account number field \"" + m_name + "\" is not valid. Value: \"" + value
                    + "\".");
        }

        m_value = accountString;

    }

    /**
     * Checks if is valid estonian iban bank account nr.
     * 
     * @param bankAccToCheck
     *            the bank acc to check
     * @return true, if is valid estonian iban bank account nr
     */
    private static boolean isValidEstonianIBANBankAccountNr(String bankAccToCheck) {
        return isValidIBANBankAccountNr(bankAccToCheck, ESTONIA_ISO_CODE);
    }

    /**
     * Checks if is valid iban bank account nr.
     * 
     * @param bankAccToCheck
     *            the bank acc to check
     * @param countryCode
     *            the country code
     * @return true, if is valid iban bank account nr
     */
    private static boolean isValidIBANBankAccountNr(String bankAccToCheck, String countryCode) {
        int length = LATVIA_ISO_CODE.equals(countryCode) ? 21 : 20;
        if (bankAccToCheck.length() != length)
            return false;
        if (!bankAccToCheck.startsWith(countryCode))
            return false;
        return new IBANCheckDigit().isValid(bankAccToCheck);
    }

    /**
     * Checks if is valid estonian internal bank account nr.
     * 
     * @param bankAccToCheck
     *            the bank acc to check
     * @return true, if is valid estonian internal bank account nr
     */
    private static boolean isValidEstonianInternalBankAccountNr(String bankAccToCheck) {
        if (!bankAccToCheck.matches("[\\d]{5,16}"))
            return false;
        if (getBankChecksumBy731Method(bankAccToCheck) != bankAccToCheck.charAt(bankAccToCheck.length() - 1))
            return false;
        return true;
    }

    // Calculate checksum by 7-3-1 weights.
    /**
     * Gets the bank checksum by731 method.
     * 
     * @param sourceOfChecksum
     *            the source of checksum
     * @return the bank checksum by731 method
     */
    private static char getBankChecksumBy731Method(String sourceOfChecksum) {
        char checkSumSymbol;
        final int[] weights = new int[] { 7, 3, 1 };
        int weight = 0;
        int indx = 0;
        for (int i = (sourceOfChecksum.length() - 2); i >= 0; i--) {
            int nrAtPos = Integer.parseInt(String.valueOf(sourceOfChecksum.charAt(i)));
            weight = weight + (nrAtPos * (weights[indx % 3]));
            indx++;
        }
        int modWeight = weight % 10;
        if (modWeight == 0) {
            checkSumSymbol = '0';
        } else {
            checkSumSymbol = String.valueOf(10 - modWeight).charAt(0);
        }
        return checkSumSymbol;
    }

}
