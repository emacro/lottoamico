
inserire il file delle estrazioni nella directory '/lotto/WebContent/WEB-INF/extractions'
(il file e' scaricabile all'indirizzo 'http://www.estrazionidellotto.com/estrazionilotto.htm' come file .txt)

formato estrazione:
[data] [numero_estrazione] [estratto] [estratto] [estratto] ...

l'ordine delle ruote e' quello standard

1   BARI
2	CAGLIARI
3	FIRENZE
4	GENOVA
5	MILANO
6	NAPOLI
7	PALERMO
8	ROMA
9	TORINO
10	VENEZIA
11	NAZIONALE

esempio:
02/08/2007 92 55 40 67 12 71 37 42 86 85 20 59 32 16 75 66 86 37 69 85 66 66 56 64 52 ....

il 92 e' il numero dell'estrazione, i numeri 55 40 67 12 71 fanno riferimento agli estratti sulla ruota di Bari e così via  



fare partire con: run_extractor nome_file.txt 