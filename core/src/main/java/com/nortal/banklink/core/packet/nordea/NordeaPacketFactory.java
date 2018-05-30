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

package com.nortal.banklink.core.packet.nordea;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.PacketFactory;
import com.nortal.banklink.core.packet.spec.BanklinkSpecification;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating NordeaPacket objects.
 * 
 * @author ago
 * @version
 */
public class NordeaPacketFactory {

    /** The Constant paymentMacNames. */
    private static final Map<String, String> paymentMacNames = new ConcurrentHashMap<>();

    /** The Constant authMacNames. */
    private static final Map<String, String> authMacNames = new ConcurrentHashMap<>();

    static {
        paymentMacNames.put("PmntRequest0003", "SOLOPMT_MAC");
        paymentMacNames.put("PmntResponse0003", "SOLOPMT_RETURN_MAC");
        authMacNames.put("AuthRequest0001", "A01Y_MAC");
        authMacNames.put("AuthRequest0002", "A01Y_MAC");
        authMacNames.put("AuthResponse0001", "B02K_MAC");
        authMacNames.put("AuthResponse0002", "B02K_MAC");
    }

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
        if (paymentMacNames.containsKey(id)) {
            return getNordeaPaymentPacket("nordea", id, paymentMacNames.get(id), algorithm);
        }
        return getNordeaAuthPacket("nordea", id, algorithm);

    }

    /**
     * Gets the nordea auth packet.
     * 
     * @param bank
     *            the bank
     * @param packetId
     *            the packet id
     * @param algorithm
     *            the algorithm
     * @return the nordea auth packet
     */
    @SuppressWarnings("rawtypes")
    private static Packet getNordeaAuthPacket(String bank, String packetId, Algorithm algorithm) {
        Packet packet = PacketFactory.getPacket(bank, packetId, algorithm);
        packet.setMacName(authMacNames.get(packetId));
        return packet;
    }

    /**
     * Gets the nordea payment packet.
     * 
     * @param bank
     *            the bank
     * @param packetId
     *            the packet id
     * @param macName
     *            the mac name
     * @param algorithm
     *            the algorithm
     * @return the nordea payment packet
     */
    @SuppressWarnings("rawtypes")
    private static Packet getNordeaPaymentPacket(String bank, String packetId, String macName, Algorithm algorithm) {
        NordeaPaymentPacket packet = getNordeaPaymentPacketInstance(packetId, algorithm);
        packet.setMacName(macName);
        try {
            BanklinkSpecification.populatePacketFromSpec(packet, bank, packetId);
        } catch (IOException e) {
            throw new RuntimeException("Problem when reading package from spec file", e);
        }
        return packet;
    }

    /**
     * Gets the nordea payment packet instance.
     * 
     * @param packetId
     *            the packet id
     * @param algorithm
     *            the algorithm
     * @return the nordea payment packet instance
     */
    @SuppressWarnings("rawtypes")
    private static NordeaPaymentPacket getNordeaPaymentPacketInstance(String packetId, Algorithm algorithm) {
        try {
            Class<?> classes[] = new Class[] { String.class, Algorithm.class };
            Constructor<?> constructor = NordeaPaymentPacket.class.getDeclaredConstructor(classes);
            Object obj[] = new Object[] { packetId, algorithm };
            return (NordeaPaymentPacket) constructor.newInstance(obj);
        } catch (Exception ex) {
            return null;
        }
    }

}