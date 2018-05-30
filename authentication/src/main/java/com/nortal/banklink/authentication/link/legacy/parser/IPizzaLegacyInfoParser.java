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
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Legacy ipizza authentication info parser (VK_INFO based)
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Deprecated
public class IPizzaLegacyInfoParser implements AuthLinkInfoParser {

    @Override
    public AuthLinkInfo parse(Bank bank, Packet packet) {
        return vkInfoMapToBankLinkInfo(bank, vkInfoToMap(packet.getParameterValue("VK_INFO")));
    }

    /**
     * Vk info to map.
     * 
     * @param vkInfo
     *            the vk info
     * @return the map
     */
    protected final Map<String, String> vkInfoToMap(String vkInfo) {
        Map<String, String> res = new HashMap<>();

        String[] keyValPairs = vkInfo.split(";");
        for (String pair : keyValPairs) {
            if (StringUtils.isBlank(pair))
                continue;
            String[] keyVal = pair.split(":");
            if (keyVal.length != 2)
                continue;
            res.put(keyVal[0].toUpperCase(), keyVal[1]);
        }

        return res;
    }

    /**
     * Vk info map to bank link info.
     * 
     * @param bank
     *            the bank
     * @param vki
     *            the vki
     * @return the auth link info
     */
    protected final AuthLinkInfo vkInfoMapToBankLinkInfo(Bank bank, Map<String, String> vki) {
        AuthLinkInfo bli = new AuthLinkInfo();

        bli.setBank(bank);
        bli.setCode(vki.get("ISIK"));

        String[] names = normalizeName(vki.get("NIMI"));
        bli.setName(names[0] + " " + names[1]);
        bli.setFirstName(names[0]);
        bli.setLastName(names[1]);

        bli.setCompanyCode(vki.get("REG"));
        bli.setCompanyName(vki.get("EVNIMI"));
        bli.setAccountNumber(vki.get("KONTO"));

        return bli;
    }

    /**
     * Normalize name.
     * 
     * @param name
     *            the name
     * @return the string[]
     */
    protected String[] normalizeName(String name) {
        String[] parts = name.split("( |,|/)");
        return new String[] { WordUtils.capitalizeFully(parts[1]), WordUtils.capitalizeFully(parts[0]) };
    }
}
