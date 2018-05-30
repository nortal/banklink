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
package com.nortal.banklink.core.packet.verify;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.core.packet.param.PacketDateTimeParameter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * Verifier for the new DATETIME format introduced in Oct. 2014 banklink specification.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class PacketDateTimeVerifier implements PacketVerifier {
    @Override
    public boolean verify(Packet packet) throws BanklinkException {
        if (!packet.hasParameter("VK_DATETIME"))
            return true;

        String datetimeStr = packet.getParameterValue("VK_DATETIME");
        datetimeStr = StringUtils.trimToNull(datetimeStr);
        if (datetimeStr == null)
            return false;

        try {
            Date datetime = new SimpleDateFormat(PacketDateTimeParameter.DATETIME_FORMAT).parse(datetimeStr);
            Calendar start = Calendar.getInstance();
            start.add(Calendar.MINUTE, -5);

            Calendar end = Calendar.getInstance();
            end.add(Calendar.MINUTE, 5);

            return !datetime.before(start.getTime()) && !datetime.after(end.getTime());
        } catch (ParseException e) {
            // invalid
            return false;
        }
    }
}
