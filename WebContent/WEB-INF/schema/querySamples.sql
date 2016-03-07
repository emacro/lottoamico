-- some query examples...


-- numeri di una determinata estrazione, ordinati per ruota e posizione in determinata data

select ex.date as data, r.name as ruota, 
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=1)  as n1,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=2)  as n2,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=3)  as n3,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=4)  as n4,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=5)  as n5
from extractions ex, extracts e, ruote r
where e.extraction=ex.id
and e.ruota=r.id
and ex.date='2007-07-07'
order by data
------------------------------------------------------------------------------------------------------
-- numeri di una determinata estrazione, ordinati per ruota e posizione dell'ultima estrazione 
-- presente in db

select ex.date as data, r.name as ruota, 
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=1)  as n1,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=2)  as n2,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=3)  as n3,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=4)  as n4,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=5)  as n5
from extractions ex, extracts e, ruote r
where e.extraction=ex.id
and e.ruota=r.id
and ex.date=(select max(date) from extractions)
order by r.id
----------------------------------------------------------------------------------------------------

-- numeri delle estrazioni di un determinato anno, ordinati per ruota e posizione

select ex.date as data, r.name as ruota, 
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=1)  as n1,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=2)  as n2,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=3)  as n3,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=4)  as n4,
(select n.number from numbers n where n.extract=e.id and e.extraction=ex.id and n.position=5)  as n5
from extractions ex, extracts e, ruote r
where e.extraction=ex.id
and e.ruota=r.id
and ex.date>'2007-01-01'
order by data, r.id
-------------------------------------------------------------------------------------------------------

select ex.date as data, r.name as ruota, n.number 
from EXTRACTS e, extractions ex, ruote r, numbers n
where e.ruota=r.id 
and e.extraction=ex.id
and n.extract=e.id
order by data, ruota, n.position

select ex.date as data, r.name as ruota, n.number 
from EXTRACTS e, extractions ex, ruote r, numbers n
where e.ruota=r.id 
and e.extraction=ex.id
and n.extract=e.id
and n.number = 85;

select ex.date as data, r.name as ruota, n.number 
from EXTRACTS e, extractions ex, ruote r, numbers n
where e.ruota=r.id 
and e.extraction=ex.id
and n.extract=e.id
order by n.number;

select n.number, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id
group by n.number
order by presenze desc;

select n.number, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id
group by n.number
having count(n.number) > 1
order by presenze desc;

select group_concat((select r.name from ruote r, EXTRACTS ext where r.id = ext.ruota and n.extract=ext.id)) as ruote, n.number, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
group by n.number
having count(n.number) > 1
order by presenze desc;

-------------------------------------------------------------------------------
-- presenze numeri in tutte le estrazioni

select n.number as Numero, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
group by n.number
order by presenze desc;
------------------------------------------------------------------------------
-- presenze numeri nelle estrazioni da una determinata data ad oggi

select n.number as Numero, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
and ex.date>'2007-01-01' 
group by n.number
order by presenze desc;
-----------------------------------------------------------------------------
-- presenze numeri nelle estrazioni in un determinato lasso di tempo

select n.number as Numero, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
and ex.date between '2007-01-01' and '2007-05-01'
group by n.number
order by presenze desc;
----------------------------------------------------------------------------
-- presenze numeri nelle singole ruote in un determinato lasso di tempo

select r.name,n.number as Numero, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex, ruote r
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
and ex.date between '2007-01-01' and '2007-05-17'
and e.ruota=r.id
group by n.number,r.name
order by presenze desc;

------------------------------------------------------------------------------

select r.name,n.number as Numero, count(n.number) as presenze
from numbers n, EXTRACTS e, extractions ex, ruote r
where e.extraction=ex.id
and n.extract=e.id and n.number > 0
and ex.date between '2007-01-01' and '2007-06-09'
and e.ruota=r.id
and (r.name = 'TORINO' or r.name = 'VENEZIA')
group by n.number,r.name
order by numero desc;
