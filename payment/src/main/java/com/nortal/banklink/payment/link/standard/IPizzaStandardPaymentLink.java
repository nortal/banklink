/**
 * Copyright 2014 Nortal AS
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nortal.banklink.payment.link.standard;

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
import com.nortal.banklink.payment.link.PaymentLinkBase;
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
import org.apache.commons.lang3.StringUtils;

/**
 * IPizza spec version 1.4 based PaymentLink base class.
 *
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@RequiredArgsConstructor
public class IPizzaStandardPaymentLink extends PaymentLinkBase<IPizzaConfig> {
    @Getter
    private final Bank bank;

    @Setter
    private String[] tryReEncodes = {};

    @Getter
    private final IPizzaStandardPaymentInfoParser parser = new IPizzaStandardPaymentInfoParser();

    @Getter
    private final NonceManager nonce = new DefaultNonceManager();

    @Override
    public boolean supportsMessage(HttpServletRequest req) {
        String vkSndId = req.getParameter("VK_SND_ID");
        String bankId = getConfig().getReceiverId();
        if (vkSndId == null || bankId == null)
            return false;
        return vkSndId.equals(bankId);
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

        Packet packet = createPacket("1012", alg);
        setOutgoingParameters(packet, parameters);
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
     * @param parameters
     */
    private void setOutgoingParameters(Packet packet, Map<String, String> parameters) {
        packet.setParameter("VK_SERVICE", "1012");
        packet.setParameter("VK_VERSION", "008");
        packet.setParameter("VK_SND_ID", getConfig().getSenderId());

        packet.setParameter("VK_STAMP", requireParameter(parameters, "VK_STAMP"));
        packet.setParameter("VK_AMOUNT", requireParameter(parameters, "VK_AMOUNT"));
        packet.setParameter("VK_CURR", requireParameter(parameters, "VK_CURR"));
        packet.setParameter("VK_REF", requireParameter(parameters, "VK_REF"));
        packet.setParameter("VK_MSG", requireParameter(parameters, "VK_MSG"));

        packet.setParameter("VK_RETURN", StringUtils.defaultIfBlank(parameters.get("VK_RETURN"), getReturnUrl()));
        packet.setParameter("VK_CANCEL", StringUtils.defaultIfBlank(parameters.get("VK_CANCEL"), getReturnUrl()));
        packet.setParameter("VK_DATETIME", new SimpleDateFormat(PacketDateTimeParameter.DATETIME_FORMAT).format(new Date()));

        // not part of mac
        packet.setParameter("VK_ENCODING", "UTF-8");
        if (parameters.get("VK_LANG") != null)
            packet.setParameter("VK_LANG", parameters.get("VK_LANG"));
    }

    private String requireParameter(Map<String, String> parameters, String key) {
        String value = parameters.get(key);
        if (value == null)
            throw new IllegalArgumentException("Payment packet parameter: " + key + ", is required");
        return value;
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
