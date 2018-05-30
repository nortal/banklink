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
package com.nortal.banklink.link.base;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.link.Bank;
import com.nortal.banklink.link.BankLink;
import com.nortal.banklink.link.BankLinkInfo;
import com.nortal.banklink.link.BankLinkManager;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 *
 */
public class BankLinkManagerImpl<INFO extends BankLinkInfo, LINK extends BankLink<INFO>> implements BankLinkManager<INFO, LINK> {
    /** The banklinks. */
    private Map<Bank, LINK> banklinks = new HashMap<>();

    /**
     * Sets the banklinks.
     * 
     * @param banklinks
     *            the new banklinks
     */
    public void setBanklinks(LINK[] banklinks) {
        for (LINK banklink : banklinks)
            this.banklinks.put(banklink.getBank(), banklink);
    }

    public LINK getBankLink(Bank bank) {
        return banklinks.get(bank);
    }

    public INFO getPacketInfo(HttpServletRequest req) {
        return getBankLink(identifyBank(req)).getPacketInfo(req);
    }

    public Packet getBanklinkPacket(HttpServletRequest req) {
        Bank bank = identifyBank(req);
        Packet packet = getBankLink(bank).getPacket(req);
        packet.verify();
        return packet;
    }

    public Bank identifyBank(HttpServletRequest req) {
        for (BankLink<?> bl : banklinks.values())
            if (bl.supportsMessage(req))
                return bl.getBank();
        throw new BanklinkException("Unknown banklink message format");
    }

}