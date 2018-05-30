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
package com.nortal.banklink.core.packet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Simple default implementation of NONCEs. Generates NONCEs are kept in a
 * private Set. Verifying NONCE removes it from the Set.
 * 
 * @author Alrik Peets
 */
public class DefaultNonceManager implements NonceManager {

    /** The generated nonces. */
    private Map<String, Date> generatedNonces = new HashMap<>();

    /*
     * (non-Javadoc)
     * 
     * @see com.nortal.banklink.core.packet.NonceManager#generateNonce()
     */
    public String generateNonce() {
        UUID uuid = UUID.randomUUID();
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.HOUR_OF_DAY, 1);
        synchronized (generatedNonces) {
            generatedNonces.put(uuid.toString(), expireDate.getTime());
        }
        return uuid.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nortal.banklink.core.packet.NonceManager#verifyNonce(java.lang.String)
     */
    public boolean verifyNonce(String nonce) {
        Date curDate = new Date();
        synchronized (generatedNonces) {
            // First clear expired and unused nonces
            for (Iterator<Map.Entry<String, Date>> i = generatedNonces.entrySet().iterator(); i.hasNext();) {
                Map.Entry<String, Date> entry = i.next();
                if (entry.getValue().before(curDate)) {
                    i.remove();
                }
            }
            return generatedNonces.remove(nonce) != null;
        }
    }

}
