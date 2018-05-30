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

/**
 * Interface for generating and validating VK_NONCE parameter.
 * 
 * @author Alrik Peets
 */
public interface NonceManager {

    /**
     * Generate nonce.
     * 
     * @return the string
     */
    public String generateNonce();

    /**
     * Verify nonce.
     * 
     * @param nonce
     *            the nonce
     * @return true, if successful
     */
    public boolean verifyNonce(String nonce);
}
