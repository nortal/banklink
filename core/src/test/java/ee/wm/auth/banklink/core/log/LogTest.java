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
package ee.wm.auth.banklink.core.log;

import com.nortal.banklink.core.log.PacketForwardLog;
import com.nortal.banklink.core.log.PacketLog;
import com.nortal.banklink.core.log.PacketSignLog;
import com.nortal.banklink.core.log.PacketVerifyLog;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * The Class LogTest.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 07.02.2014
 */
public class LogTest {

    /**
     * Log test1.
     */
    @Test
    public void logTest1() {
        Map<String, String> params = new LinkedHashMap<>();
        PacketSignLog pl1 = new PacketSignLog();
        PacketVerifyLog pl2 = new PacketVerifyLog();
        PacketForwardLog pl3 = new PacketForwardLog();

        for (int i = 0; i < 10; i++) {
            String key = "param" + i;
            pl1.setParameter(key, UUID.randomUUID().toString());
            pl2.setParameter(key, pl1.getParameter(key));
            pl3.setParameter(key, pl1.getParameter(key));
            params.put(key, pl1.getParameter(key));
        }

        String expectedLog = StringUtils.join(params.values(), "\t");
        String log = getLog(pl1);
        Assert.assertTrue(MessageFormat.format("Expected sign log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));

        log = getLog(pl2);
        Assert.assertTrue(MessageFormat.format("Expected veify log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));

        log = getLog(pl3);
        Assert.assertTrue(MessageFormat.format("Expected veify log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));
    }

    /**
     * Log test2.
     */
    @Test
    public void logTest2() {
        Map<String, String> params = new LinkedHashMap<>();
        // \t should be replaced with space
        params.put("param1", "1\t2");
        // \r should be replaced with space
        params.put("param2", "3\r4");
        // \n should be replaced with space
        params.put("param3", "5\n6");
        // String null should be replaced with "null"
        params.put("param4", "null");

        PacketSignLog pl1 = new PacketSignLog();
        PacketVerifyLog pl2 = new PacketVerifyLog();
        PacketForwardLog pl3 = new PacketForwardLog();
        for (String key : params.keySet()) {
            pl1.setParameter(key, params.get(key));
            pl2.setParameter(key, params.get(key));
            pl3.setParameter(key, params.get(key));
        }

        String expectedLog = "1 2\t3 4\t5 6\t\"null\"";

        String log = getLog(pl1);
        Assert.assertTrue(MessageFormat.format("Expected sign log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));

        log = getLog(pl2);
        Assert.assertTrue(MessageFormat.format("Expected veify log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));

        log = getLog(pl3);
        Assert.assertTrue(MessageFormat.format("Expected veify log={0} was log={1}", expectedLog, log),
                log.equals(expectedLog));
    }

    /**
     * Gets the log.
     * 
     * @param pl
     *            the pl
     * @return the log
     */
    private String getLog(PacketLog pl) {
        // Packet log starts with current date so we return substring after first \t
        // for comparing
        return StringUtils.substringAfter(pl.toString(), "\t");
    }
}
