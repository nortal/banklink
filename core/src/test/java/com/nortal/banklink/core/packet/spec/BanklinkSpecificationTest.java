/**
 *   Copyright 2015 Nortal AS
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

import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.algorithm.Algorithm008;
import com.nortal.banklink.core.algorithm.AlgorithmNordea;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.PacketFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class BanklinkSpecificationTest {
    private static final String[] IPIZZA_SPECS = {
            "danske",
            "krediidipank",
            "lhv",
            "seb",
            "swedbank"
    };

    private static final String[] IPIZZA_SERVICE_IDS = {
            // authentication
            "4001", "3002",
            "4002", /* "3003", */
            "4012", "3013",
            // payments
            /* "1001", */"1002", "1101", "1901",
            "1011", "1012", "1111", "1911"
    };

    @Test
    public void testIpizzaSpecs() {
        Algorithm008 alg = new Algorithm008();
        for (String spec : IPIZZA_SPECS)
            for (String sid : IPIZZA_SERVICE_IDS)
                Assert.assertEquals(sid, getPacket(spec, sid, alg).getPacketId());
    }

    private static final String NORDEA_SPEC = "nordea";
    private static final String[] NORDEA_SERVICE_IDS = {
            "AuthRequest0001", "AuthRequest0002", "AuthResponse0001", "AuthResponse0002",
            "PmntRequest0003", "PmntResponse0003"
    };

    @Test
    public void testNordeaSpecs() {
        AlgorithmNordea alg = new AlgorithmNordea();
        for (String sid : NORDEA_SERVICE_IDS)
            Assert.assertEquals(sid, getPacket(NORDEA_SPEC, sid, alg).getPacketId());
    }

    private Packet getPacket(String spec, String sid, Algorithm<?, ?> alg) {
        try {
            return PacketFactory.getPacket(spec, sid, alg);
        } catch (Exception e) {
            throw new RuntimeException("Failure in spec+sid: " + spec + "+" + sid, e);
        }
    }
}
