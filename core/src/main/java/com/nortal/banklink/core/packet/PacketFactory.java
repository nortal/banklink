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
package com.nortal.banklink.core.packet;

import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.packet.spec.BanklinkSpecification;
import java.io.IOException;

/**
 * Helper for creating different packets for different banks in the same place.
 * 
 * @author Maido Käära
 * @author Alrik Peets
 */
public abstract class PacketFactory {
    /** The Constant defaultNonceManager. */
    private static final NonceManager defaultNonceManager = new DefaultNonceManager();

    /**
     * Gets the packet.
     * 
     * @param bank
     *            the bank
     * @param packetId
     *            the packet id
     * @param algorithm
     *            the algorithm
     * @return the packet
     */
    @SuppressWarnings("rawtypes")
    public static Packet getPacket(String bank, String packetId, Algorithm algorithm) {
        return getPacket(bank, packetId, algorithm, defaultNonceManager);
    }

    /**
     * Gets the packet.
     * 
     * @param bank
     *            the bank
     * @param packetId
     *            the packet id
     * @param algorithm
     *            the algorithm
     * @param nonceManager
     *            the nonce manager
     * @return the packet
     */
    @SuppressWarnings("rawtypes")
    public static Packet getPacket(String bank, String packetId, Algorithm algorithm, NonceManager nonceManager) {
        Packet packet = getPacketInstance(packetId, algorithm, nonceManager);
        try {
            BanklinkSpecification.populatePacketFromSpec(packet, bank, packetId);
        } catch (IOException e) {
            throw new RuntimeException("Problem when reading package from spec file", e);
        }
        return packet;
    }

    /**
     * Gets the packet instance.
     * 
     * @param packetId
     *            the packet id
     * @param algorithm
     *            the algorithm
     * @param nonceManager
     *            the nonce manager
     * @return the packet instance
     */
    @SuppressWarnings("rawtypes")
    private static Packet getPacketInstance(String packetId, Algorithm algorithm, NonceManager nonceManager) {
        return new Packet(packetId, algorithm, nonceManager);
    }
}
