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
package com.nortal.banklink.authentication.link.standard;

import com.nortal.banklink.authentication.link.AuthLinkBase;
import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.algorithm.Algorithm008;
import com.nortal.banklink.core.packet.DefaultNonceManager;
import com.nortal.banklink.core.packet.NonceManager;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.PacketFactory;
import com.nortal.banklink.core.packet.param.PacketDateTimeParameter;
import com.nortal.banklink.link.Bank;
import com.nortal.banklink.link.BankLinkConfig.IPizzaConfig;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

/**
 * IPizza spec version 1.4 based AuthLink implementation, starting from january 2016, this should be the only authentication link
 * implementation used for Estonian banks and some external banks.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@RequiredArgsConstructor
public class IPizzaStandardAuthLink extends AuthLinkBase<IPizzaConfig> {
    @Getter
    private final Bank bank;

    @Setter
    private String[] tryReEncodes = {};

    @Getter
    private final IPizzaStandardAuthInfoParser parser = new IPizzaStandardAuthInfoParser();

    @Getter
    private final NonceManager nonce = new DefaultNonceManager();

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
        Algorithm008 alg = createAlgorithm();
        alg.initVerify(getConfig().getBankPub());

        Packet packet = createPacket(req.getParameter("VK_SERVICE"), alg);

        try {
            readAndVerifyPacket(req, packet, "UTF-8");
            return packet;
        } catch (BanklinkException e) {
            if (ArrayUtils.isEmpty(tryReEncodes))
                throw e;

            // Some nasty hacking for banks that haven't heard of POST method or UTF-8 or content-type charset parameter...

            BanklinkException reencodeEx = e;
            for (String encoding : tryReEncodes) {
                try {
                    readAndVerifyPacket(req, packet, encoding);
                    return packet;
                } catch (BanklinkException ree) {
                    reencodeEx = ree;
                }
            }
            throw reencodeEx;
        }
    }

    private void readAndVerifyPacket(HttpServletRequest req, Packet packet, String encoding) {
        packet.init(req, encoding);
        if (!packet.verify())
            throw new BanklinkException("Invalid banklink message");
    }

    @Override
    public Packet createOutgoingPacket(Map<String, String> parameters) {
        Algorithm008 alg = createAlgorithm();
        alg.initSign(getConfig().getClientPriv());

        Packet packet = createPacket("4012", alg);
        setOutgoingParameters(packet);
        packet.sign();
        return packet;
    }

    private Algorithm008 createAlgorithm() {
        Algorithm008 alg = new Algorithm008();
        alg.setCharset("UTF-8");
        alg.setLengthInBytes(isUseByteLengthInMac());
        return alg;
    }

    /**
     * Sets the outgoing parameters.
     * 
     * @param packet
     *            the new outgoing parameters
     */
    private void setOutgoingParameters(Packet packet) {
        packet.setParameter("VK_SERVICE", "4012");
        packet.setParameter("VK_VERSION", "008");
        packet.setParameter("VK_SND_ID", getConfig().getSenderId());
        packet.setParameter("VK_REC_ID", getConfig().getReceiverId());
        packet.setParameter("VK_NONCE", packet.generateNonce());
        packet.setParameter("VK_RETURN", getReturnUrl());
        packet.setParameter("VK_DATETIME", new SimpleDateFormat(PacketDateTimeParameter.DATETIME_FORMAT).format(new Date()));
        packet.setParameter("VK_RID", "");// blank
        packet.setParameter("VK_ENCODING", "UTF-8");
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
    protected Packet createPacket(String id, Algorithm<PrivateKey, PublicKey> alg) {
        return PacketFactory.getPacket(getBank().getSpec(), id, alg, getNonce());
    }
}
