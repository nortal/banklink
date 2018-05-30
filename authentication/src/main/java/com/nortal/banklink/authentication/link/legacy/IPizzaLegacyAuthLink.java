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
package com.nortal.banklink.authentication.link.legacy;

import com.nortal.banklink.authentication.link.AuthLinkBase;
import com.nortal.banklink.authentication.link.legacy.parser.IPizzaLegacyInfoParser;
import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.algorithm.Algorithm008;
import com.nortal.banklink.core.packet.DefaultNonceManager;
import com.nortal.banklink.core.packet.NonceManager;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.link.BankLinkConfig.IPizzaConfig;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Base class for legacy ipizza based authentication links.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Deprecated
public abstract class IPizzaLegacyAuthLink extends AuthLinkBase<IPizzaConfig> {

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.link.AuthLinkBase#getParser()
     */
    private final IPizzaLegacyInfoParser parser = new IPizzaLegacyInfoParser();

    protected IPizzaLegacyInfoParser getParser() {
        return parser;
    }

    /**
     * Gets the nonce.
     * 
     * @return the nonce
     */
    private final NonceManager nonce = new DefaultNonceManager();

    protected NonceManager getNonce() {
        return nonce;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.AuthLink#supportsMessage(javax.servlet
     * .http.HttpServletRequest)
     */
    @Override
    public boolean supportsMessage(HttpServletRequest req) {
        String vkSndId = req.getParameter("VK_SND_ID");
        String vkRecId = req.getParameter("VK_REC_ID");
        String bankId = getConfig().getReceiverId();
        String contractId = getConfig().getSenderId();
        if (vkSndId == null || vkRecId == null || bankId == null || contractId == null)
            return false;
        return vkSndId.equals(bankId) && contractId.equals(vkRecId);
    }

    @Override
    public Packet getPacket(HttpServletRequest req) {
        Algorithm008 alg = new Algorithm008();
        alg.setCharset(getEncoding());
        alg.setLengthInBytes(isUseByteLengthInMac());
        alg.initVerify(getConfig().getBankPub());
        Packet packet = createPacket(req.getParameter("VK_SERVICE"), alg);
        packet.init(req);
        return packet;
    }

    @Override
    public Packet createOutgoingPacket(Map<String, String> parameters) {
        Algorithm008 alg = new Algorithm008();
        alg.setCharset(getEncoding());
        alg.initSign(getConfig().getClientPriv());

        Packet packet = createPacket("4002", alg);
        setOutgoingParameters(packet);
        packet.sign();
        return packet;
    }

    /**
     * Sets the outgoing parameters.
     * 
     * @param packet
     *            the new outgoing parameters
     */
    private void setOutgoingParameters(Packet packet) {
        packet.setParameter("VK_SERVICE", "4002");
        packet.setParameter("VK_VERSION", "008");
        packet.setParameter("VK_SND_ID", getConfig().getSenderId());
        packet.setParameter("VK_REC_ID", getConfig().getReceiverId());
        packet.setParameter("VK_NONCE", packet.generateNonce());
        packet.setParameter("VK_RETURN", getReturnUrl());
        setEncoding(packet);
    }

    /**
     * Sets the encoding.
     * 
     * @param packet
     *            the new encoding
     */
    protected void setEncoding(Packet packet) {
        packet.setParameter("VK_ENCODING", getEncoding());
    }

    protected String getEncoding() {
        return "ISO-8859-1";
    }

    /**
     * Creates the packet.
     * 
     * @param id
     *            the id
     * @param alg
     *            the alg
     * @return the packet
     */
    protected abstract Packet createPacket(String id, Algorithm<PrivateKey, PublicKey> alg);

}
