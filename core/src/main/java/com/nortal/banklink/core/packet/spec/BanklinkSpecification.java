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
package com.nortal.banklink.core.packet.spec;

import com.nortal.banklink.core.packet.param.PacketParameterFactory;
import com.nortal.banklink.core.packet.param.PacketParameterFactory.PacketParameterType;

import com.nortal.banklink.core.packet.param.PacketParameter;
import java.util.ArrayList;
import com.nortal.banklink.core.packet.Packet;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

/**
 * Helper class for reading packet parameters info from .spec files and adding
 * this info to packets.
 * 
 * @author Maido Käära
 */
public class BanklinkSpecification {

    /** The specs. */
    private static Map<String, List<String>> specs = new ConcurrentHashMap<>();

    /**
     * Finds packet parameters info from spec and adds it to the packet.
     * 
     * @param packet
     *            Packet to be modified with packet parameters
     * @param bank
     *            Bank id for current packet
     * @param packetId
     *            Packet id for current packet
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void populatePacketFromSpec(Packet packet, String bank, String packetId) throws IOException {
        List<String> bankSpec = getSpec(bank);
        List<String> standardSpec = getSpec("standard");

        List<String> spec = new ArrayList<>(bankSpec);
        spec.addAll(standardSpec);

        Iterator<String> it = spec.iterator();
        while (it.hasNext()) {
            if (it.next().equals("PACKET " + packetId)) {
                PacketParameter packetParameter = null;
                while (it.hasNext()) {
                    String line = it.next();
                    if (StringUtils.isNotBlank(line)) {
                        int lastOrderNr = packetParameter != null ? packetParameter.getOrderNr() : 0;
                        packetParameter = getPacketParameter(line, lastOrderNr);
                        addPacketParameter(packet, packetParameter);
                    } else {
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Bank " + bank + " spec or packet " + packetId + " not found!");
    }

    /**
     * Parses packet parameter line from spec and creates a PacketParameter object
     * accordingly.
     * 
     * @param line
     *            Line from spec
     * @param lastOrderNr
     *            Previous PacketParameter order number
     * @return new PacketParameter instance
     */
    private static PacketParameter getPacketParameter(String line, int lastOrderNr) {
        String[] split = line.split("\\t+");
        PacketParameterType type = PacketParameterType.valueOf(split[3]);
        int length = Integer.parseInt(split[2]);
        String orderNrString = split[0];
        int orderNr = ++lastOrderNr;
        boolean isMac = true;
        if ("-".equals(orderNrString)) {
            isMac = false;
        } else {
            orderNr = Integer.parseInt(orderNrString);
        }
        if (PacketParameterType.DATE.equals(type) && split.length == 6) {
            String[] formats = split[4].split(";");
            return PacketParameterFactory.getPacketParameter(type, split[1], length, isMac, orderNr, formats);
        }
        return PacketParameterFactory.getPacketParameter(type, split[1], length, isMac, orderNr);
    }

    /**
     * Adds the packet parameter.
     * 
     * @param packet
     *            the packet
     * @param packetParameter
     *            the packet parameter
     */
    private static void addPacketParameter(Packet packet, PacketParameter packetParameter) {
        try {
            Method addPacketParameter = Packet.class.getDeclaredMethod("addPacketParameter", PacketParameter.class);
            addPacketParameter.setAccessible(true);
            addPacketParameter.invoke(packet, packetParameter);
        } catch (Exception e) {
            throw new RuntimeException("Problem when trying to call addPacketParameter on Packet", e);
        }
    }

    /**
     * Finds spec from file or if already found then searches cache map.
     * 
     * @param bank
     *            the bank
     * @return the spec
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static List<String> getSpec(String bank) throws IOException {
        List<String> spec = specs.get(bank);
        if (spec == null) {
            spec = readSpecFromFile(bank);
            specs.put(bank, spec);
        }
        return spec;
    }

    /**
     * Reads spec from file and returns it as list of strings.
     * 
     * @param bank
     *            the bank
     * @return the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static List<String> readSpecFromFile(String bank) throws IOException {
        try (Scanner scanner = new Scanner(BanklinkSpecification.class.getResourceAsStream("/META-INF/spec/" + bank + ".spec"), "UTF-8")) {
            String file = scanner.useDelimiter("\\A").next();
            return Arrays.asList(file.split("\\r?\\n"));
        }
    }
}
