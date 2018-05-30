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
package com.nortal.banklink.example.core;

import com.nortal.banklink.example.core.model.PacketConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.springframework.core.io.ClassPathResource;

/**
 * Utility for reading and parsing bank packet mappings from core module and
 * packet configurations from classpath.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public class TransactionUtil {

    /**
     * Reads and parses packet configurations according to path.
     * 
     * @param path
     *            target packet configurations path
     * @return {@link PacketConfig}
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static PacketConfig readPacketConfig(String path) throws IOException {
        PacketConfig result = new PacketConfig();

        // Read spec
        InputStream is = getResourceStream(path);
        Iterator<String> specLines = IOUtils.readLines(is).iterator();
        is.close();

        // Parse spec packet configs
        while (specLines.hasNext()) {
            String specline = specLines.next();
            if (specline.startsWith("PACKET")) {
                String packetKey = specline.split("\\s+")[1];
                Map<String, String> packetConfig = new HashMap<>();
                String packetLine;
                do {
                    if (!specLines.hasNext()) {
                        break;
                    }
                    packetLine = specLines.next();
                    if (StringUtils.isNotBlank(packetLine)) {
                        String[] lineVals = packetLine.split("\\t+");
                        packetConfig.put(lineVals[0], lineVals[1]);
                    }
                } while (StringUtils.isNotBlank(packetLine));

                result.addPacketConfig(packetKey, packetConfig);
            }
        }
        return result;
    }

    /**
     * Gets the pem object.
     * 
     * @param path
     *            the path
     * @return the pem object
     */
    public static Object getPemObject(String path) {
        try {
            if (Security.getProperty("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }

            InputStreamReader reader = new InputStreamReader(getResourceStream(path));
            PEMReader pemParser = new PEMReader(reader);
            Object pemObject = pemParser.readObject();
            pemParser.close();

            return pemObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the resource stream.
     * 
     * @param path
     *            the path
     * @return the resource stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static InputStream getResourceStream(String path) throws IOException {
        return new ClassPathResource(path).getInputStream();
    }
}
