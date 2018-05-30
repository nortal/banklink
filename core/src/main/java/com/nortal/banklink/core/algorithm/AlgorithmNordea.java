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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmNordea.
 */
public final class AlgorithmNordea extends Algorithm<String, String> {

    /** The m_secret. */
    private String m_secret;

    /**
     * Instantiates a new algorithm nordea.
     */
    public AlgorithmNordea() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.algorithm.Algorithm#getMacString(java.util.Enumeration
     * )
     */
    @Override
    public String getMacString(Enumeration<PacketParameter> parameters) throws AlgorithmException {
        StringBuffer sToSign = new StringBuffer();
        while (parameters.hasMoreElements()) {
            PacketParameter param = parameters.nextElement();
            if (param.isMac()) {
                if (param.getValue() == null || "".equals(param.getValue())) {
                    /*
                     * 12.01.2006 - bug fix.
                     * 
                     * @author Ilhan Nisamedtinov (ilhan@webmedia.ee) I replaced double
                     * '&' symbols which is not correct. One should only be added in case
                     * of empty fields. Code before replace was: sToSign.append("&&");
                     */
                    sToSign.append("&");
                } else {
                    sToSign.append(param.getValue());
                    sToSign.append("&");
                }
            }
        }
        sToSign.append(m_secret);
        sToSign.append("&");
        return sToSign.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.algorithm.Algorithm#sign(java.util.Enumeration)
     */
    @Override
    public String sign(Enumeration<PacketParameter> parameters) throws AlgorithmException {
        try {
            return MD5encrypt(getMacString(parameters));
        } catch (Exception ex_a) {
            throw new AlgorithmException("Can not sign packet parameters.", ex_a);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.algorithm.Algorithm#verify(java.util.Enumeration,
     * java.lang.String)
     */
    @Override
    public boolean verify(Enumeration<PacketParameter> parameters, String MAC) throws AlgorithmException {
        try {
            String paramMAC = MD5encrypt(getMacString(parameters));
            return paramMAC.equals(MAC);
        } catch (Exception ex_a) {
            throw new AlgorithmException("Can not verify packet parameters.", ex_a);
        }
    }

    /**
     * Set private key.
     * 
     * @param secret
     *            the secret
     */
    @Override
    public void initSign(String secret) {
        m_secret = secret != null ? secret.toString() : null;
    }

    /**
     * Set public key.
     * 
     * @param secret
     *            the secret
     */
    @Override
    public void initVerify(String secret) {
        m_secret = secret != null ? secret.toString() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.core.algorithm.Algorithm#getVersion()
     */
    @Override
    public String getVersion() {
        return "Nordea";
    }

    /**
     * M d5encrypt.
     * 
     * @param what
     *            the what
     * @return the string
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     */
    private String MD5encrypt(String what) throws NoSuchAlgorithmException {
        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        if (this.getCharset() == null) {
            md.update(what.getBytes());
        } else {
            try {
                md.update(what.getBytes(this.getCharset()));
            } catch (UnsupportedEncodingException uee) {
                throw new NoSuchAlgorithmException("Unsupported encoding.");
            }
        }
        byte[] dBytes = md.digest();
        return toHex(dBytes);
    }

    /**
     * To hex.
     * 
     * @param dBytes
     *            the d bytes
     * @return the string
     */
    private static String toHex(byte[] dBytes) {
        char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        String back = "";
        for (byte dByte : dBytes) {
            int d = dByte;
            if (d < 0) {
                d = 256 + d;
            }
            int a = (d) / 16;
            int c = (d) % 16;
            back += hex[a] + "" + hex[c];
        }
        return back;
    }

}