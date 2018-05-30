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

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Packet config is a data holder for message packet parameters which are static
 * eg. packet code, algorithm code, language, encoding or bank client id. These
 * predefined values can be added to bank configuration on initialization and
 * reused for messages processing
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
@Data
public class PacketConfig {
    private Map<String, Map<String, String>> configs = new HashMap<>();

    /**
     * Adds parameter values for packet
     * 
     * @param packetId
     * @param config
     */
    public void addPacketConfig(String packetId, Map<String, String> config) {
        configs.put(packetId, config);
    }

    /**
     * Returns parameter values by packet id
     * 
     * @param packetId
     * @return
     */
    public Map<String, String> getPacketConfig(String packetId) {
        return configs.get(packetId);
    }
}
