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

import com.nortal.banklink.link.Bank;

import java.security.PrivateKey;
import java.security.PublicKey;
import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.lhv.LHVPacketFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class LHVAuthLink.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 11.02.2014
 */
@Deprecated
public class LHVLegacyAuthLink extends IPizzaLegacyAuthLink {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.link.IPizzaAuthLink#createPacket(java
     * .lang.String, com.nortal.banklink.core.algorithm.Algorithm)
     */
    @Override
    protected Packet createPacket(String id, Algorithm<PrivateKey, PublicKey> alg) {
        return LHVPacketFactory.getPacket(id, alg, getNonce());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.AuthLink#getBank()
     */
    @Override
    public Bank getBank() {
        return Bank.LHV;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.authentication.link.AuthLinkBase#getEncoding()
     */
    @Override
    protected String getEncoding() {
        return "UTF-8";
    }
}
