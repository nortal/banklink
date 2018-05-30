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
package com.nortal.banklink.authentication.link.standard;

import com.nortal.banklink.link.Bank;

import com.nortal.banklink.authentication.AuthLinkInfo;
import com.nortal.banklink.authentication.link.AuthLinkInfoParser;
import com.nortal.banklink.core.packet.Packet;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Standardized ipizza authentication info parser ()
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
public class IPizzaStandardAuthInfoParser implements AuthLinkInfoParser {
    @Override
    public AuthLinkInfo parse(Bank bank, Packet packet) {
        AuthLinkInfo ali = new AuthLinkInfo();
        ali.setBank(bank);
        ali.setCode(packet.getParameterValue("VK_USER_ID"));
        ali.setCountry(packet.getParameterValue("VK_COUNTRY"));

        String[] names = normalizeName(packet.getParameterValue("VK_USER_NAME"));
        ali.setName(names[0] + " " + names[1]);
        ali.setFirstName(names[0]);
        ali.setLastName(names[1]);

        // TODO: format for these? probably in VK_OTHER?
        // Also, if needed for other countries apart from EST the keys are likely not going to be in Estonian.
        Map<String, String> vko = vkOtherToMap(packet.getParameterValue("VK_OTHER"));
        ali.setAccountNumber(vko.get("KONTO"));
        ali.setCompanyCode(vko.get("EVNIMI"));
        ali.setCompanyName(vko.get("REG"));
        return ali;
    }

    protected final Map<String, String> vkOtherToMap(String vkInfo) {
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
     * Normalize name.
     * 
     * @param name
     *            the name
     * @return the string[]
     */
    protected String[] normalizeName(String name) {
        String[] parts = name.split("[ |,|/]+");
        return new String[] { WordUtils.capitalizeFully(parts[1]), WordUtils.capitalizeFully(parts[0]) };
    }
}
