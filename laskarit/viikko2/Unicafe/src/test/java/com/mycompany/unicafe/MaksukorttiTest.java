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
    public void senttienTulostusOikein() {
        Maksukortti k1 = new Maksukortti(3);
        assertEquals("saldo: 0.03", k1.toString());
        Maksukortti k2 = new Maksukortti(30);
        assertEquals("saldo: 0.30", k2.toString());
    }
    
    @Test
    public void tulostusJaSaldoYhtenevaiset() {
        assertEquals("saldo: 10.00", kortti.toString());
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void saldoAlussaOikeinTest() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikeinTest() {
        kortti.lataaRahaa(200);
        assertEquals(1200, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(200);
        assertEquals(800, kortti.saldo());
    }

    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(2000);
        assertEquals(1000, kortti.saldo());
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
