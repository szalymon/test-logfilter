# Logfilter feladat

## Leírás

A megoldást egy ssd-n teszteltem.
SSD-n picivel jobb eredményt lehetett elérni ha párhuzamosan több fájlt olvasunk egyszerre.
HDD-n valószínűleg érdemesebb egy fájl buffert olvasni több szállal.
Így hdd esetében a szálak leosztása máshogy kellene, hogy kinézzen HDD esetében.

Például így:

```Java
}).forEach(reader -> {
    threadPool.submit(new LogProcessor(reader, logPatternWriters));
    threadPool.submit(new LogProcessor(reader, logPatternWriters));
    threadPool.submit(new LogProcessor(reader, logPatternWriters));
    threadPool.submit(new LogProcessor(reader, logPatternWriters));
});
```

A hasznos szálak lehetséges számát a hasznos Buffer feldolgozás korlátozza.


## Futtatás

A program három paramétert vár el. Forrás könyvtárt, cél könvtárt és a konfigurációs fájl helyét.