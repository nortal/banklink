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
import javax.servlet.http.HttpServletRequest;

/**
 * Base banklink manager, holds one type of banklinks.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 *
 */
public interface BankLinkManager<INFO extends BankLinkInfo, LINK extends BankLink<INFO>> {
    /**
     * Gets the auth link.
     * 
     * @param bank
     *            the bank
     * @return the auth link
     */
    LINK getBankLink(Bank bank);

    /**
     * Gets the packet info.
     * 
     * @param req
     *            the req
     * @return the packet info
     */
    INFO getPacketInfo(HttpServletRequest req);

    /**
     * Gets the banklink packet.
     * 
     * @param req
     *            the req
     * @return the banklink packet
     */
    Packet getBanklinkPacket(HttpServletRequest req);

    /**
     * Identify bank.
     * 
     * @param req
     *            the req
     * @return the bank
     */
    Bank identifyBank(HttpServletRequest req);
}