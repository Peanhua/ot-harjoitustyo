package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassaTest() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikeinTest() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikeinTest() {
        kortti.lataaRahaa(200);
        assertEquals("saldo: 12.0", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(200);
        assertEquals("saldo: 8.0", kortti.toString());
    }

    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(2000);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void oikeaPaluuarvoJosRahaaOnTarpeeksi() {
        assertTrue(kortti.otaRahaa(200));
    }

    @Test
    public void oikeaPaluuarvoJosRahaaEiOleTarpeeksi() {
        assertFalse(kortti.otaRahaa(2000));
    }
}
