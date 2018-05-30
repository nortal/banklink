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
package com.nortal.banklink.authentication.link.legacy.parser;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.authentication.link.AuthLinkInfoParser;
import com.nortal.banklink.authentication.AuthLinkInfo;
import com.nortal.banklink.core.packet.Packet;
import org.apache.commons.lang3.text.WordUtils;

/**
 * The Class NordeaInfoParser.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Deprecated
public class NordeaLegacyInfoParser implements AuthLinkInfoParser {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.parser.AuthLinkInfoParser#parse(com.
     * nortal.banklink.authentication.Bank,
     * com.nortal.banklink.core.packet.Packet)
     */
    @Override
    public AuthLinkInfo parse(Bank bank, Packet packet) {
        AuthLinkInfo bli = new AuthLinkInfo();
        bli.setBank(bank);

        // name and person code are just about all the info we can get from nordea.
        // though its enough for physical person authentication.
        bli.setCode(packet.getParameterValue("B02K_CUSTID"));
        String[] names = normalizeName(packet.getParameterValue("B02K_CUSTNAME"));

        bli.setName(names[0] + " " + names[1]);
        bli.setFirstName(names[0]);
        bli.setLastName(names[1]);

        return bli;
    }

    /**
     * Normalize name.
     * 
     * @param name
     *            the name
     * @return the string[]
     */
    private String[] normalizeName(String name) {
        String[] parts = name.split("( |,)");
        return new String[] { WordUtils.capitalizeFully(parts[0]), WordUtils.capitalizeFully(parts[1]) };
    }
}
