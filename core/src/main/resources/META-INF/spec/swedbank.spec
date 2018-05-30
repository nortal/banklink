Swedbank'i pangalingi spetsifikatsioon
Uuendatud: 03.02.14
Versioon: 1.2

Maksefunktsioonid

PACKET 1001
1	VK_SERVICE	4	INTEGER	Teenuse number (1001)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	10	STRING	Päringu koostaja ID (Kaupluse ID)
4	VK_STAMP	20	STRING	Päringu ID
5	VK_AMOUNT	17	DOUBLE	Maksmisele kuuluv summa
6	VK_CURR		3	STRING	Valuuta lühend: EEK (Alates 01.01.2011 EUR)
7	VK_ACC		20	ACC_NR	Saaja konto number
8	VK_NAME		30	STRING	Saaja nimi
9	VK_REF		20	REF_NR	Maksekorralduse viitenumber
10	VK_MSG		70	STRING	Maksekorralduse seletus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_RETURN	60	STRING	URL, kuhu vastatakse edukal tehingu sooritamisel
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	12	STRING	Sõnumi kodeering. ISO-8859-1 (vaikeväärtus) või UTF-8

PACKET 1002
1	VK_SERVICE	4	INTEGER	Teenuse number (1002)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	10	STRING	Päringu koostaja ID (Kaupluse ID)
4	VK_STAMP	20	STRING	Päringu ID
5	VK_AMOUNT	17	DOUBLE	Maksmisele kuuluv summa
6	VK_CURR		3	STRING	Valuuta lühend: EEK (Alates 01.01.2011 EUR)
7	VK_REF		20	REF_NR	Maksekorralduse viitenumber
8	VK_MSG		300	STRING	Maksekorralduse seletus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_RETURN	150	STRING	URL, kuhu vastatakse edukal tehingu sooritamisel
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	20	STRING	Sõnumi kodeering. ISO- 8859-1 (vaikeväärtus) või UTF-8

PACKET 1101
1	VK_SERVICE	4	INTEGER	Teenuse number (1101)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm 008
3	VK_SND_ID	10	STRING	Päringu koostaja ID (Panga ID)
4	VK_REC_ID	10	STRING	Päringu vastuvõtja ID (Kaupluse ID)
5	VK_STAMP	20	STRING	Päringu ID
6	VK_T_NO		5	STRING	Maksekorralduse number
7	VK_AMOUNT	17	DOUBLE	Makstud summa
8	VK_CURR		3	STRING	Valuuta lühend: EEK (Alates 01.01.2011 EUR)
9	VK_REC_ACC	34	ACC_NR	Saaja konto number
10	VK_REC_NAME	70	STRING	Saaja nimi
11	VK_SND_ACC	34	ACC_NR	Maksja konto number
12	VK_SND_NAME	40	STRING	Maksja nimi
13	VK_REF		20	REF_NR	Maksekorralduse viitenumber
14	VK_MSG		300	STRING	Maksekorralduse selgitus
15	VK_T_DATE	10	DATE	Maksekorralduse kuupäev
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_AUTO		1	STRING	Näitab seda, kas pakett oli saadetud automaatselt (`Y`) või mitte (`N`)
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8

PACKET 1901
1	VK_SERVICE	4	INTEGER	Teenuse number (1901)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	10	STRING	Päringu koostaja ID (Panga ID)
4	VK_REC_ID	10	STRING	Päringu vastuvõtja ID (Kaupluse ID)
5	VK_STAMP	20	STRING	Päringu ID
6	VK_REF		20	STRING	Maksekorralduse viitenumber
7	VK_MSG		300	STRING	Maksekorralduse selgitus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_AUTO		1	STRING	Näitab seda, kas pakett oli saadetud automaatselt (`Y`) või mitte (`N`)
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8

Autentimise funktsioonid

PACKET 3002
1	VK_SERVICE	4	INTEGER	Teenuse number (3002)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_USER		16	STRING	Kokkuleppeline kasutaja indifikaator
4	VK_DATE		10	DATE	Sõnumi genereerimise kuupäev
5	VK_TIME		8	TIME	Sõnumi genereerimise kellaaeg
6	VK_SND_ID	10	STRING	Sõnumi koostaja ID (Panga ID)
7	VK_INFO		300	STRING	Kokkuleppel standardiseeritav kasutaja isikuandmeid sisaldav väli
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8

PACKET 4001
1	VK_SERVICE	4	INTEGER	Info esitatud päringust
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_SND_ID	10	STRING	Sõnumi koostaja (partneri) ID
4	VK_REPLY	4	INTEGER	Oodatava vastuspaketi kood (3002)
5	VK_RETURN	60	STRING	Kaupmehe URL, kuhu vastatakse
6	VK_DATE		10	DATE	Sõnumi genereerimise kuupäev
7	VK_TIME		8	TIME	Sõnumi genereerimise kellaaeg
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8

PACKET 4002
1	VK_SERVICE	4	INTEGER	Teenuse number (4002)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_SND_ID	10	STRING	Sõnumi koostaja (partneri) ID
4	VK_REC_ID	10	STRING	Sõnumi saaja (panga) ID
5	VK_NONCE	50	STRING	Päringu saatja poolt genereeritud juhuslik nonss (kasutatakse värskuse tagamiseks)
6	VK_RETURN	150	STRING	URL, kuhu suunatakse autenditud kasutaja.
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8

PACKET 3003
1	VK_SERVICE	4	INTEGER	Teenuse number (3003)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_SND_ID	10	STRING	Sõnumi koostaja ID (Panga ID)
4	VK_REC_ID	10	STRING	Sõnumi saaja (partneri) ID
5	VK_NONCE	50	STRING	Päringus olnud nonsi koopia
6	VK_INFO		300	STRING	Kokkuleppel standardiseeritav autenditava inimese isikuandmeid sisaldav väli
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_ENCODING	12	STRING	Sõnumi kodeering. Vaikeväärtus ISO-8859-1 või UTF-8


-end of spec-