Swedpanga pangalingi spetsifikatsioon
Uuendatud: 11.02.2014
Versioon: 1.2
VK_ENCODING on ära jäetud kuna ei ole kohustuslik.

Maksefunktsioonid

PACKET 1001
1	VK_SERVICE	4	INTEGER	Teenuse number (1001)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	15	STRING	Päringu koostaja ID (kaupmehe ID)
4	VK_STAMP	20	STRING	Päringu ID
5	VK_AMOUNT	17	DOUBLE	Maksmisele kuuluv summa. Komakohad ja sendid eristatud punktiga ".". Tuhandete eraldajat ei kasutata.
6	VK_CURR		3	STRING	Valuuta lühend (EUR)
7	VK_ACC		20	ACC_NR	Saaja arve number
8	VK_NAME		30	STRING	Saaja nimi
9	VK_REF		20	REF_NR	Maksekorralduse viitenumber
10	VK_MSG		70	STRING	Maksekorralduse selgitus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_RETURN	60	STRING	URL, kuhu vastatakse edukal tehingu sooritamisel
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

PACKET 1002
1	VK_SERVICE	4	INTEGER	Teenuse number (1002)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	15	STRING	Päringu koostaja ID (kaupmehe ID)
4	VK_STAMP	20	STRING	Päringu ID
5	VK_AMOUNT	17	DOUBLE	Maksmisele kuuluv summa. Komakohad ja sendid eristatud punktiga ".". Tuhandete eraldajat ei kasutata.
6	VK_CURR		3	STRING	Valuuta lühend (EUR)
7	VK_REF		20	REF_NR	Maksekorralduse viitenumber
8	VK_MSG		210	STRING	Maksekorralduse selgitus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_RETURN	200	STRING	URL, kuhu saadetakse tehingu vastuse päring
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

PACKET 1101
1	VK_SERVICE	4	INTEGER	Teenuse number (1101)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm 008
3	VK_SND_ID	15	STRING	Päringu koostaja ID (Panga ID)
4	VK_REC_ID	15	STRING	Päringu vastuvõtja ID (Kaupmehe ID)
5	VK_STAMP	20	STRING	Päringu ID
6	VK_T_NO		20	STRING	Maksekorralduse number
7	VK_AMOUNT	17	DOUBLE	Makstud summa
8	VK_CURR		3	STRING	Valuuta lühend (EUR)
9	VK_REC_ACC	20	ACC_NR	Saaja konto number
10	VK_REC_NAME	100	STRING	Saaja nimi
11	VK_SND_ACC	20	ACC_NR	Maksja konto number
12	VK_SND_NAME	100	STRING	Maksja nimi
13	VK_REF		20	REF_NR	Maksekorralduse viitenumber
14	VK_MSG		210	STRING	Maksekorralduse selgitus
15	VK_T_DATE	10	DATE	Maksekorralduse kuupäev (DD.MM.YYYY)
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257
-	VK_AUTO		1	STRING	Y = panga poolt automaatselt saadetud vastus.

PACKET 1901
1	VK_SERVICE	4	INTEGER	Teenuse number (1901)
2	VK_VERSION	3	STRING	Kasutatav krüptoalgoritm (008)
3	VK_SND_ID	15	STRING	Päringu koostaja ID (Panga ID)
4	VK_REC_ID	15	STRING	Päringu vastuvõtja ID (Kaupmehe ID)
5	VK_STAMP	20	STRING	Päringu ID
6	VK_REF		20	STRING	Maksekorralduse viitenumber
7	VK_MSG		255	STRING	Maksekorralduse selgitus
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_LANG		3	STRING	Soovitav suhtluskeel
-	VK_AUTO		1	STRING	Y = panga poolt automaatselt saadetud vastus.

Autentimise funktsioonid

PACKET 3001
1	VK_SERVICE	4	INTEGER	Teenuse number (3001)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_USER		16	STRING	Ei ole kasutusel
4	VK_DATE		10	DATE	Paketi genereerimise kuupäev (DD.MM.YYYY)
5	VK_TIME		8	TIME	Paketi genereerimise kellaaeg (HH24:MM:SS)
6	VK_SND_ID	15	STRING	Päringu koostaja ID (Panga ID)
-	VK_MAC		700	STRING	Kontrollkood e.allkiri
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

PACKET 3002
1	VK_SERVICE	4	INTEGER	Teenuse number (3002)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_USER		16	STRING	Ei ole kasutusel
4	VK_DATE		10	DATE	Paketi genereerimise kuupäev (DD.MM.YYYY)
5	VK_TIME		8	TIME	Paketi genereerimise kellaaeg (HH24:MM:SS)
6	VK_SND_ID	15	STRING	Sõnumi koostaja ID (Panga ID)
7	VK_INFO		300	STRING	Kasutaja isikuandmeid sisaldav väli
-	VK_MAC		350	STRING	Kontrollkood e. allkiri
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

PACKET 4001
1	VK_SERVICE	4	INTEGER	Teenuse number (4001)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING	Päringu koostaja ID (kaupmeheID)
4	VK_REPLY	4	INTEGER	Oodatava vastuspaketi kood (3001, 3002)
5	VK_RETURN	200	STRING	URL, kuhu saadetakse tehingu vastuse päring
6	VK_DATE		10	DATE	Paketi genereerimise kuupäev (DD.MM.YYYY)
7	VK_TIME		8	TIME	Paketi genereerimise kellaaeg (HH24:MM:SS)
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

PACKET 4002
1	VK_SERVICE	4	INTEGER	Teenuse number (4002)
2	VK_VERSION	3	STRING	Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING	Päringu koostaja ID (kaupmeheID)
4	VK_REC_ID	15	STRING	Päringu koostaja ID (Panga ID)
5	VK_NONCE	50	STRING	Päringu saatja poolt genereeritud juhuslik nonss
6	VK_RETURN	60	STRING	UURL, kuhu saadetakse tehingu vastuse päring
-	VK_MAC		700	STRING	Kontrollkood e. allkiri
-	VK_CHARSET	12	STRING	ISO-8859-1 (vaikeväärtus), UTF-8 või WINDOWS-1257

-end of spec-