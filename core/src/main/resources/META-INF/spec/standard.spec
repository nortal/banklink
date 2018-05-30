Fallback after looking up packet specs from bank specific file and not finding it there.
New packets to be used (at least in Estonia) from 01.01.2016
New payment packets

PACKET 1011
1	VK_SERVICE	4	INTEGER		Teenuse number (1011)
2	VK_VERSION	3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING		Päringu koostaja ID (Kaupluse ID)
4	VK_STAMP	20	STRING		Päringu ID
5	VK_AMOUNT	12	DOUBLE		Maksmisele kuuluv summa
6	VK_CURR		3	STRING		Valuuta nimi: EUR
7	VK_ACC		34	ACC_NR		Saaja konto number
8	VK_NAME		70	STRING		Saaja nimi
9	VK_REF		35	REF_NR		Maksekorralduse viitenumber
10	VK_MSG		95	STRING		Maksekorralduse selgitus
11	VK_RETURN	255	STRING		URL, kuhu vastatakse edukal tehingu sooritamisel
12	VK_CANCEL	255	STRING		URL, kuhu vastatakse ebaõnnestunud tehingu puhul
13	VK_DATETIME	24	DATETIME	Päringu algatamise kuupäev ja kellaaeg DATETIME formaadis
-	VK_MAC		700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING	12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG		3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)

PACKET 1012
1	VK_SERVICE	4	INTEGER		Teenuse number (1012)
2	VK_VERSION	3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING		Päringu koostaja ID (Kaupluse ID)
4	VK_STAMP	20	STRING		Päringu ID
5	VK_AMOUNT	12	DOUBLE		Maksmisele kuuluv summa
6	VK_CURR		3	STRING		Valuuta nimi: EUR
7	VK_REF		35	REF_NR		Maksekorralduse viitenumber
8	VK_MSG		95	STRING		Maksekorralduse selgitus
9	VK_RETURN	255	STRING		URL, kuhu vastatakse edukal tehingu sooritamisel
10	VK_CANCEL	255	STRING		URL, kuhu vastatakse ebaõnnestunud tehingu puhul
11	VK_DATETIME	24	DATETIME	Päringu algatamise kuupäev ja kellaaeg DATETIME formaadis
-	VK_MAC		700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING	12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG		3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)

PACKET 1111
1	VK_SERVICE		4	INTEGER		Teenuse number (1111)
2	VK_VERSION		3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID		15	STRING		Päringu koostaja ID (Panga ID)
4	VK_REC_ID		15	STRING		Päringu vastuvõtja ID (Kaupluse ID)
5	VK_STAMP		20	STRING		Päringu ID
6	VK_T_NO			20	STRING		Maksekorralduse number
7	VK_AMOUNT		12	DOUBLE		Makstud summa
8	VK_CURR			3	STRING		Valuuta nimi: EUR
9	VK_REC_ACC		34	ACC_NR		Saaja konto number
10	VK_REC_NAME		70	STRING		Saaja nimi
11	VK_SND_ACC		34	ACC_NR		Maksja konto number
12	VK_SND_NAME		70	STRING		Maksja nimi
13	VK_REF			35	REF_NR		Maksekorralduse viitenumber
14	VK_MSG			95	STRING		Maksekorralduse selgitus
15	VK_T_DATETIME	24	DATETIME	Maksekorralduse kuupäev ja kellaaeg DATETIME formaadis
-	VK_MAC			700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING		12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG			3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)
-	VK_AUTO			1	STRING		Y = panga poolt automaatselt saadetud vastus, N = vastus kliendi liikumisega kaupmehe lehele

PACKET 1911
1	VK_SERVICE	4	INTEGER		Teenuse number (1911)
2	VK_VERSION	3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING		Päringu koostaja ID (Panga ID)
4	VK_REC_ID	15	STRING		Päringu vastuvõtja ID (Kaupluse ID)
5	VK_STAMP	20	STRING		Päringu ID
6	VK_REF		35	REF_NR		Maksekorralduse viitenumber
7	VK_MSG		95	STRING		Maksekorralduse selgitus
-	VK_MAC		700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING	12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG		3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)
-	VK_AUTO		1	STRING		N = vastus kliendi liikumisega kaupmehe lehele

New authentication packets

PACKET 4011
1	VK_SERVICE	4	INTEGER		Teenuse number (4011)
2	VK_VERSION	3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING		Sõnumi koostaja (partneri) ID
4	VK_REPLY	4	INTEGER		Oodatava vastuspaketi kood (3012)
5	VK_RETURN	255	STRING		Kaupmehe URL, kuhu vastatakse
6	VK_DATETIME	24	DATETIME	Sõnumi genereerimise aeg DATETIME formaadis
7	VK_RID		30	STRING		Sessiooniga seotud identifikaator; Swedbanki puhul tühi väärtus
-	VK_MAC		700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING	12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG		3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)

PACKET 3012
1	VK_SERVICE		4	INTEGER		Teenuse number (3012)
2	VK_VERSION		3	STRING		Kasutatav krüptologaritm (008)
3	VK_USER			16	STRING		Kokkuleppeline kasutaja identifikaator
4	VK_DATETIME		24	DATETIME	Sõnumi genereerimise aeg DATETIME formaadis
5	VK_SND_ID		15	STRING		Sõnumi koostaja ID (Panga ID)
6	VK_REC_ID		15	STRING		Sõnumi saaja (partneri) ID
7	VK_USER_NAME	140	STRING		Kasutaja nimi
8	VK_USER_ID		20	STRING		Kasutaja isikukood
9	VK_COUNTRY		2	STRING		Isikukoodi riik (kahetäheline kood vastavalt ISO 3166-1 standardile)
10	VK_OTHER		150	STRING		Muu info kasutaja kohta
11	VK_TOKEN		2	INTEGER		Autentimisvahendi identifikaatori kood: (1-id-kaart, 2-mID, 5-ühekordsed koodid, 6-pin kalk., 7-korduvkasutusega kaart)
12	VK_RID			30	STRING		Sessiooniga seotud identifikaator; Swedbanki puhul tühi väärtus
-	VK_MAC			700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING		12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG			3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)

PACKET 4012
1	VK_SERVICE	4	INTEGER		Teenuse number (4012)
2	VK_VERSION	3	STRING		Kasutatav krüptologaritm (008)
3	VK_SND_ID	15	STRING		Sõnumi koostaja (partneri) ID 
4	VK_REC_ID	15	STRING		Sõnumi saaja (panga) ID
5	VK_NONCE	50	STRING		Päringu koostaja poolt genereeritud juhuslik nonss
6	VK_RETURN	255	STRING		Kaupmehe URL, kuhu vastatakse
7	VK_DATETIME	24	DATETIME	Sõnumi genereerimise aeg DATETIME formaadis
8	VK_RID		30	STRING		Sessiooniga seotud identifikaator; Swedbanki puhul tühi väärtus
-	VK_MAC		700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING	12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG		3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)

PACKET 3013
1	VK_SERVICE		4	INTEGER		Teenuse number (3013)
2	VK_VERSION		3	STRING		Kasutatav krüptologaritm (008)
3	VK_DATETIME		24	DATETIME	Sõnumi genereerimise aeg DATETIME formaadis
4	VK_SND_ID		15	STRING		Sõnumi koostaja ID (Panga ID)
5	VK_REC_ID		15	STRING		Sõnumi saaja (partneri) ID
6	VK_NONCE		50	STRING		Päringus olnud nonssi koopia
7	VK_USER_NAME	140	STRING		Kasutaja nimi
8	VK_USER_ID		20	STRING		Kasutaja isikukood
9	VK_COUNTRY		2	STRING		Isikukoodi riik (kahetäheline kood vastavalt ISO 3166-1 standardile)
10	VK_OTHER		150	STRING		Muu info kasutaja kohta
11	VK_TOKEN		2	INTEGER		Autentimisvahendi identifikaatori kood: (1-id-kaart, 2-mID, 5-ühekordsed koodid, 6-pin kalk., 7-korduvkasutusega kaart)
12	VK_RID			30	STRING		Sessiooniga seotud identifikaator
-	VK_MAC			700	STRING		Kontrollkood e. allkiri
-	VK_ENCODING		12	STRING		Sõnumi kodeering. UTF-8 (vaikeväärtus), ISO-8859-1 või WINDOWS-1257
-	VK_LANG			3	STRING		Soovitav suhtluskeel (EST, ENG või RUS | LIT, LAT)


-end of spec-