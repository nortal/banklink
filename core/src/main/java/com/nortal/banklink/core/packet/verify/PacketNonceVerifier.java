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
package com.nortal.banklink.core.packet.verify;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;
import org.apache.commons.lang3.StringUtils;

/**
 * Verifier that enforces rules on VK_NONCE value. If packet does not have
 * VK_NONCE parameter, this verifier returns true. This verifier requires that
 * Packet has configured with NonceManager
 * 
 * @author Alrik Peets
 */
public class PacketNonceVerifier implements PacketVerifier {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.packet.verify.PacketVerifier#verify(com.nortal
     * .banklink.core.packet.Packet)
     */
    public boolean verify(Packet packet) throws BanklinkException {
        if (!packet.hasParameter("VK_NONCE")) {
            return true;
        }
        String packetNonce = packet.getParameterValue("VK_NONCE");
        if (StringUtils.isEmpty(packetNonce))
            return false;
        return packet.verifyNonce(packetNonce);
    }
}
