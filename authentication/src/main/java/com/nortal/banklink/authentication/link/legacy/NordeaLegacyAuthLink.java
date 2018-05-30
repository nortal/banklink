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
import com.nortal.banklink.authentication.link.legacy.parser.NordeaLegacyInfoParser;
import com.nortal.banklink.core.algorithm.AlgorithmNordea;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.nordea.NordeaPacketFactory;
import com.nortal.banklink.link.Bank;
import com.nortal.banklink.link.BankLinkConfig.NordeaConfig;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class NordeaAuthLink.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Deprecated
public class NordeaLegacyAuthLink extends AuthLinkBase<NordeaConfig> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.AuthLink#supportsMessage(javax.servlet
     * .http.HttpServletRequest)
     */
    @Override
    public boolean supportsMessage(HttpServletRequest req) {
        return req.getParameter("B02K_MAC") != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.link.AuthLinkBase#getParser()
     */
    private final NordeaLegacyInfoParser parser = new NordeaLegacyInfoParser();

    protected NordeaLegacyInfoParser getParser() {
        return parser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.AuthLink#getPacket(javax.servlet.http
     * .HttpServletRequest)
     */
    @Override
    public Packet getPacket(HttpServletRequest req) {
        AlgorithmNordea alg = new AlgorithmNordea();
        alg.initVerify(getConfig().getMac());

        Packet packet = NordeaPacketFactory.getPacket("AuthResponse0002", alg);
        packet.init(req);
        return packet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.AuthLink#createOutgoingPacket()
     */
    @Override
    public Packet createOutgoingPacket(Map<String, String> parameters) {
        AlgorithmNordea alg = new AlgorithmNordea();
        alg.initSign(getConfig().getMac());

        Packet packet = NordeaPacketFactory.getPacket("AuthRequest0002", alg);
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
        packet.setParameter("A01Y_ACTION_ID", "701");
        packet.setParameter("A01Y_VERS", "0002");
        packet.setParameter("A01Y_RCVID", getConfig().getSenderId());
        packet.setParameter("A01Y_LANGCODE", "ET");
        packet.setParameter("A01Y_STAMP", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        packet.setParameter("A01Y_IDTYPE", "02");

        String url = getReturnUrl();
        packet.setParameter("A01Y_RETLINK", url);
        packet.setParameter("A01Y_CANLINK", url);
        packet.setParameter("A01Y_REJLINK", url);

        packet.setParameter("A01Y_KEYVERS", "0001");
        packet.setParameter("A01Y_ALG", "01");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.AuthLink#getBank()
     */
    @Override
    public Bank getBank() {
        return Bank.NORDEA;
    }
}
