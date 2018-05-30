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

import org.apache.commons.lang3.text.WordUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SwedbankInfoParser.
 * 
 * @author <a href="mailto:toomas.parna@nortal.com">Toomas Pärna</a>
 */
@Deprecated
public class SwedbankLegacyInfoParser extends IPizzaLegacyInfoParser {
    // swedbank has reverse order of first and last name NAME element compared to
    // other banks.
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.authentication.parser.IPizzaInfoParser#normalizeName
     * (java.lang.String)
     */
    protected String[] normalizeName(String name) {
        String[] parts = name.split("( |,|/)");
        return new String[] { WordUtils.capitalizeFully(parts[0]), WordUtils.capitalizeFully(parts[1]) };
    }
}
