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
package com.nortal.banklink.link;

import com.nortal.banklink.core.packet.Packet;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 *
 */
public interface BankLink<INFO extends BankLinkInfo> {

    /**
     * @return the bank associated with this link
     */
    Bank getBank();

    /**
     * @return true, if given request contains a valid banklink message for this authlink instance.
     */
    boolean supportsMessage(HttpServletRequest req);

    /**
     * Reads the banklink info from incoming banklink request.
     * 
     * @return the banklink info
     */
    INFO getPacketInfo(HttpServletRequest req);

    /**
     * @return the raw banklink packet.
     */
    Packet getPacket(HttpServletRequest req);

    /**
     * Creates the outgoing packet to be submitted to a banklink endpoint on bank side.
     */
    Packet createOutgoingPacket();

    Packet createOutgoingPacket(Map<String, String> parameters);

    /**
     * @return the banklink endpoint url on the bank side.
     */
    String getUrl();

}