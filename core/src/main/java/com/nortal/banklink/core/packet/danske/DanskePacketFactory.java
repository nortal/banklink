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
/*
 * PacketHP.java
 *
 * Created on April 22, 2002, 12:18 PM
 */

package com.nortal.banklink.core.packet.danske;

import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.packet.NonceManager;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.PacketFactory;

/**
 * A factory for creating SampoPacket objects.
 * 
 * @author ago
 * @version
 */
public class DanskePacketFactory {

    /**
     * Gets the packet.
     * 
     * @param id
     *            the id
     * @param algorithm
     *            the algorithm
     * @return the packet
     */
    @SuppressWarnings("rawtypes")
    public static Packet getPacket(String id, Algorithm algorithm) {
        return PacketFactory.getPacket("danske", id, algorithm);
    }

    /**
     * Gets the packet.
     * 
     * @param id
     *            the id
     * @param algorithm
     *            the algorithm
     * @param nonceManager
     *            the nonce manager
     * @return the packet
     */
    @SuppressWarnings("rawtypes")
    public static Packet getPacket(String id, Algorithm algorithm, NonceManager nonceManager) {
        return PacketFactory.getPacket("danske", id, algorithm, nonceManager);
    }
}