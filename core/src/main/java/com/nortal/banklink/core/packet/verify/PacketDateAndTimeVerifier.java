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
package com.nortal.banklink.core.packet.verify;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * Verifier that enforces rules on VK_DATE and VK_TIME values. If values are
 * missing in packet parameters, Verifier returns true. Date + time is accepted
 * when it is not longer then 1 hour in the past
 * 
 * @author Alrik Peets
 */
public class PacketDateAndTimeVerifier implements PacketVerifier {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.packet.verify.PacketVerifier#verify(com.nortal * .banklink.core.packet.Packet)
     */
    public boolean verify(Packet packet) throws BanklinkException {
        if (!isVerifyRequired(packet)) {
            return true;
        }

        String dateStr = packet.getParameterValue("VK_DATE");
        String timeStr = packet.getParameterValue("VK_TIME");

        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(timeStr)) {
            return false;
        }

        Date vkDate = parseDate(dateStr, timeStr);
        return verifyPacketDate(vkDate);

    }

    private boolean verifyPacketDate(Date vkDate) {
        Calendar packetDate = Calendar.getInstance();
        packetDate.setTime(vkDate);
        Calendar targetDate = Calendar.getInstance();
        targetDate.add(Calendar.HOUR_OF_DAY, -1);
        return packetDate.after(targetDate);
    }

    private Date parseDate(String dateStr, String timeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return dateFormat.parse(dateStr + " " + timeStr);
        } catch (ParseException e) {
            throw new BanklinkException("Couldnt parse incoming dates", e);
        }
    }

    private boolean isVerifyRequired(Packet packet) {
        return packet.hasParameter("VK_DATE") && packet.hasParameter("VK_TIME");
    }

}
