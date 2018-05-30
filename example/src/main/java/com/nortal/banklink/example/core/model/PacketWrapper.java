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
package com.nortal.banklink.example.core.model;

import com.nortal.banklink.core.packet.param.PacketParameter;

import com.nortal.banklink.core.packet.Packet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Provides addition functionality to ease initialization and data retrieval
 * from packet.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 06.03.2014
 */
@Data
public class PacketWrapper {
    private static final Logger LOG = Logger.getLogger(PacketWrapper.class);

    @Setter(AccessLevel.NONE)
    private Packet packet;
    private List<String> packetKeys = new ArrayList<>();

    public PacketWrapper(Packet packet) {
        this.packet = packet;

        Enumeration<PacketParameter> params = packet.parameters();
        while (params.hasMoreElements()) {
            PacketParameter param = params.nextElement();
            packetKeys.add(param.getName());
        }
    }

    /**
     * Returns wrapped packet id
     * 
     * @return
     */
    public String getPacktId() {
        return packet.getPacketId();
    }

    /**
     * Appends input parameters into wrapped packet. Parameters are included only
     * if they are described in specification and target key value in packet is
     * undefined - is not initialized by {@link PacketConfig}
     * 
     * @param params
     */
    public void appendParams(Map<String, String> params) {
        for (String key : params.keySet()) {
            if (packetKeys.contains(key)) {
                if (StringUtils.isBlank(packet.getParameterValue(key))) {
                    packet.setParameter(key, params.get(key));
                }
            } else {
                LOG.warn(MessageFormat.format("PacketWrapper.appendParams: param not supported id={0}, key={1}, value={2}",
                        packet.getPacketId(), key, params.get(key)));
            }
        }
    }

    /**
     * Returns wrapped packet parameters
     * 
     * @return
     */
    public Map<String, String> getParams() {
        Map<String, String> result = new HashMap<>();

        for (String key : packetKeys) {
            result.put(key, packet.getParameterValue(key));
        }
        return result;
    }
}
