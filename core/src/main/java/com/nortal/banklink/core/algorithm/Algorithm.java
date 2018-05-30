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
import java.util.Enumeration;
import com.nortal.banklink.core.Version;

// TODO: Auto-generated Javadoc
/**
 * Abstract base class for packet parameter encryption algorithm. Special
 * algorithm must derive from this class in order to create specific
 * functionality.
 * 
 * @param <SIGKEY>
 *            the generic type
 * @param <VERKEY>
 *            the generic type
 */
public abstract class Algorithm<SIGKEY, VERKEY> {
    /** Private key. */
    protected SIGKEY m_privateSecret;
    /** Public key. */
    protected VERKEY m_publicSecret;

    /** The charset. */
    private String charset;

    /** Constructor. */
    protected Algorithm() {
    }

    /**
     * Return string which must be verified or signed. This depends on algorithm
     * used.
     * 
     * @param parameters
     *            enumeration of packet parameters that must be signed or verified
     * @return values of all packet parameters that must be in MAC and their
     *         length (see for example 008 spec.)
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public abstract String getMacString(Enumeration<PacketParameter> parameters) throws AlgorithmException;

    /**
     * Signature of specified packet parameters.
     * 
     * @param parameters
     *            enumeration of packet parameters to sign
     * @return signature of packet parameters
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public abstract String sign(Enumeration<PacketParameter> parameters) throws AlgorithmException;

    /**
     * Check if the signature if correct or not.
     * 
     * @param parameters
     *            enumeration of packet parameters that will be used for verifying
     *            signature
     * @param MAC
     *            the mac
     * @return whether the signature is valid
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public abstract boolean verify(Enumeration<PacketParameter> parameters, String MAC) throws AlgorithmException;

    /**
     * 
     * Return the version of algorithm.
     * 
     * @return the version of Algorithm
     */
    public abstract String getVersion();

    /**
     * Set private key.
     * 
     * @param privateSecret
     *            the private secret
     */
    public void initSign(SIGKEY privateSecret) {
        m_privateSecret = privateSecret;
    }

    /**
     * Set public key.
     * 
     * @param publicSecret
     *            the public secret
     */
    public void initVerify(VERKEY publicSecret) {
        m_publicSecret = publicSecret;
    }

    /**
     * Gets the single instance of Algorithm.
     * 
     * @param <SIGKEY>
     *            the generic type
     * @param <VERKEY>
     *            the generic type
     * @param id
     *            the id
     * @return single instance of Algorithm
     * @throws AlgorithmException
     *             the algorithm exception
     */
    public static <SIGKEY, VERKEY> Algorithm<SIGKEY, VERKEY> getInstance(String id) throws AlgorithmException {
        return getInstance(id, false);
    }

    /**
     * Return algorithm specified by ID.
     * 
     * @param <SIGKEY>
     *            the generic type
     * @param <VERKEY>
     *            the generic type
     * @param id
     *            algorithm to return
     * @param lengthInBytes
     *            the length in bytes
     * @return algorithm specified by ID.
     * @throws AlgorithmException
     *             the algorithm exception
     */
    @SuppressWarnings("unchecked")
    public static <SIGKEY, VERKEY> Algorithm<SIGKEY, VERKEY> getInstance(String id, boolean lengthInBytes)
            throws AlgorithmException {
        try {
            if (lengthInBytes) {
                id = id + "_1";
            }

            Class<?> cClass = Class.forName("com.nortal.banklink.core.algorithm.Algorithm" + id);
            return (Algorithm<SIGKEY, VERKEY>) cClass.newInstance();
        } catch (Exception ex) {
            throw new AlgorithmException("Error creating algorithm \"" + id + "\". Banklink " + Version.version + "cause: "
                    + ex.getMessage());
        }
    }

    /**
     * Returns the character set.
     * 
     * @return the charset
     * @see {@link Algorithm#setCharset(String)}
     */
    public String getCharset() {
        if (charset == null) {
            return Charset.defaultCharset().name();
        }
        return charset;
    }

    /**
     * Sets the character set which is used when converting Strings to byte
     * arrays. If not set, platform environment default character set is used.
     * 
     * @author Ilhan Nisamedtinov (ilhan@webmedia.ee)
     * @param charset
     *            the new charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
