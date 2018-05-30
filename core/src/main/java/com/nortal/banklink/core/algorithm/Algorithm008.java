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
package com.nortal.banklink.core.algorithm;

import com.nortal.banklink.core.packet.param.PacketParameter;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Enumeration;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 * Algorithm 008 works like the following: value is calculated using public key
 * algorithm RSA .
 * 
 * The length of empty fields is also counted � �000�.
 * 
 * 008(x1,x2,�,xn) := RSA( SHA-1(p(x1 )|| x1|| p(x2 )|| x2 || � ||p( xn
 * )||xn),d,n)
 * 
 * where:
 * <P/>
 * 
 * <UL>
 * 
 * <LI>
 * 
 * <PRE>
 * ||
 * </PRE>
 * 
 * string concatenation</LI>
 * 
 * <LI>
 * 
 * <PRE>
 * x1, x2, �, xn
 * </PRE>
 * 
 * query parameters</LI>
 * 
 * <LI>
 * 
 * <PRE>
 * p
 * </PRE>
 * 
 * is a function from parameter length</LI>
 * 
 * <LI>
 * 
 * <PRE>
 * d
 * </PRE>
 * 
 * is RSA secret exponent</LI>
 * 
 * <LI>
 * 
 * <PRE>
 * n
 * </PRE>
 * 
 * is RSA modulus</LI>
 */
public final class Algorithm008 extends Algorithm<PrivateKey, PublicKey> {

    /** The length in bytes. */
    private boolean lengthInBytes;
    private String hashAlgorithm = "SHA1";

    /**
     * Sets the length in bytes.
     * 
     * @param lengthInBytes
     *            the new length in bytes
     */
    public void setLengthInBytes(boolean lengthInBytes) {
        this.lengthInBytes = lengthInBytes;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    /**
     * Return string that must be verified or signed. The string has the following
     * form:
     * 
     * <PRE>
     * p(x1 )|| x1|| p(x2 )|| x2 || � ||p( xn )||xn
     * </PRE>
     * 
     * , where
     * 
     * <UL>
     * 
     * <LI>
     * 
     * <PRE>
     * x1, x2, ..., xn
     * </PRE>
     * 
     * are packet parameters</LI>
     * 
     * <LI>
     * 
     * <PRE>
     * p
     * </PRE>
     * 
     * is a function from parameter length</LI>
     * 
     * </UL>
     * 
     * @param parameters
     *            enumeration of packet parameters that must be signed or verified.
     * @return values of all PacketParameters which musy be in MAC and their
     *         length (see 008 spec.)
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public String getMacString(Enumeration<PacketParameter> parameters) throws AlgorithmException {
        StringBuffer sToSign = new StringBuffer();
        // gather all what we need to sign
        for (Enumeration<PacketParameter> e = parameters; e.hasMoreElements();) {
            PacketParameter param = e.nextElement();
            if (param.isMac()) {
                // Ago - is it better to use buffer here ?
                String length;
                String value = param.getValue();

                if (!lengthInBytes) {
                    length = Integer.toString(value.length());
                } else {
                    byte[] bytes;
                    if (getCharset() != null) {
                        bytes = value.getBytes(Charset.forName(getCharset()));
                    } else {
                        bytes = value.getBytes();
                    }
                    length = Integer.toString(bytes.length);
                }

                if (length.length() == 1)
                    length = "00" + length;
                if (length.length() == 2)
                    length = "0" + length;
                sToSign.append(length);
                sToSign.append(value);
            }
        }
        return sToSign.toString();
    }

    /**
     * Signature of specified packet parameters. Algorithm version 008 uses SHA1
     * digest algorithm in order to create message hash and RSA encryption
     * algorithm to encrypt this hash.
     * 
     * @param parameters
     *            enumeration of packet parameters to sign
     * @return signature of packet parameters
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public String sign(Enumeration<PacketParameter> parameters) throws AlgorithmException {
        try {

            Signature rsa = Signature.getInstance(hashAlgorithm + "withRSA");
            rsa.initSign((java.security.PrivateKey) m_privateSecret);
            String macString = getMacString(parameters);
            if (this.getCharset() == null) {
                rsa.update(macString.getBytes());
            } else {
                rsa.update(macString.getBytes(this.getCharset()));
            }
            byte[] s_MAC = rsa.sign();
            String encMAC = Base64.encodeBase64String(s_MAC);
            StringBuffer sb = new StringBuffer();
            int length = encMAC.length();
            for (int i = 0; i < length; i++) {
                if (encMAC.charAt(i) != '\n' && encMAC.charAt(i) != '\r') {
                    sb.append(encMAC.charAt(i));
                }
            }
            return sb.toString();
        } catch (Exception ex_a) {
            throw new AlgorithmException("Can not sign packet parameters.", ex_a);
        }
    }

    /**
     * Check if the signature if correct or not.
     * 
     * @param parameters
     *            enumeration of packet parameters that will be used for
     * 
     *            verifying signature
     * @param MAC
     *            the mac
     * @return whether the signature is valid
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public boolean verify(java.util.Enumeration<PacketParameter> parameters, String MAC) throws AlgorithmException {
        try {
            byte[] decodedMAC = Base64.decodeBase64(MAC);
            Signature rsa = Signature.getInstance(hashAlgorithm + "withRSA");
            rsa.initVerify((java.security.PublicKey) m_publicSecret);
            if (this.getCharset() == null) {
                rsa.update(getMacString(parameters).getBytes());
            } else {
                rsa.update(getMacString(parameters).getBytes(this.getCharset()));
            }
            return rsa.verify(decodedMAC);
        } catch (Exception ex_a) {
            throw new AlgorithmException("Can not verfiy MAC.", ex_a);
        }
    }

    /**
     * Return the version of algorithm - in this case it is 008.
     * 
     * @return the version of this algorithm - 008
     */
    public String getVersion() {
        return "008";
    }
}
