# Logfilter feladat

## Leírás

A megoldást egy ssd-n teszteltem.
SSD-n picivel jobb eredményt lehetett elérni ha párhuzamosan több fájlt olvasunk egyszerre.
HDD-n valószínűleg érdemesebb egy fájl buffert olvasni több szállal.
Így hdd esetében a szálak leosztása máshogy kellene, hogy kinézzen.

Például így:

```Java
}).forEach(reader -> {
    for(int i = 0; i < availableProcessorUnit; i++) {
        threadPool.submit(new LogProcessor(reader, logPatternWriters));        
    }
});
```

A hasznos szálak lehetséges számát a Buffer feldolgozás korlátozza.


## Futtatás
A program három paramétert vár el. A Forrás könyvtárat, a cél könvtárat és a konfigurációs fájl helyét.