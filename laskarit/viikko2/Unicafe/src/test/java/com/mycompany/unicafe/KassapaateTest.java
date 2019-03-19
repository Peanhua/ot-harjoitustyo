/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class KassapaateTest {
    
    private Kassapaate  paate;
    private Maksukortti kortti;
    private final int   alkukassa = 100000;
    private final int   edullinen = 240;
    private final int   maukas    = 400;
    
    @Before
    public void setUp() {
        this.paate     = new Kassapaate();
        this.kortti    = new Maksukortti(0);
    }
    
    @Test
    public void onkoRahamaaraOikeinAlussaTest() {
        assertEquals(alkukassa, paate.kassassaRahaa());
    }

    @Test
    public void onkoMyytyjenMaukkaidenLounaidenMaaraOikeinAlussaTest() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void onkoMyytyjenEdullistenLounaidenMaaraOikeinAlussaTest() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKateisostoKasvattaaKassaa() {
        paate.syoEdullisesti(500);
        assertEquals(alkukassa + edullinen, paate.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoKasvattaaKassaa() {
        paate.syoMaukkaasti(1500);
        assertEquals(alkukassa + maukas, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKateisostoTasarahallaKasvattaaKassaa() {
        paate.syoEdullisesti(240);
        assertEquals(alkukassa + edullinen, paate.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoTasarahallaKasvattaaKassaa() {
        paate.syoMaukkaasti(400);
        assertEquals(alkukassa + maukas, paate.kassassaRahaa());
    }
    
    @Test
    public void edullisestaKateisostostaOikeaVaihtoraha() {
        assertEquals(500 - edullinen, paate.syoEdullisesti(500));
    }

    @Test
    public void maukkaastaKateisostostaOikeaVaihtoraha() {
        assertEquals(1500 - maukas, paate.syoMaukkaasti(1500));
    }

    @Test
    public void edullinenKateisostoKasvattaaMyytyjaLounaita() {
        paate.syoEdullisesti(500);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasKateisostoKasvattaaMyytyjaLounaita() {
        paate.syoMaukkaasti(1500);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKateisostoLiianVahallaRahallaEiMuutaSaldoja() {
        paate.syoEdullisesti(123);
        assertEquals(alkukassa, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasKateisostoLiianVahallaRahallaEiMuutaSaldoja() {
        paate.syoMaukkaasti(123);
        assertEquals(alkukassa, paate.kassassaRahaa());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenKateisostoLiianVahallaRahallaOikeaVaihtoraha() {
        assertEquals(123, paate.syoEdullisesti(123));
    }

    @Test
    public void maukasKateisostoLiianVahallaRahallaOikeaVaihtoraha() {
        assertEquals(123, paate.syoMaukkaasti(123));
    }
    
    /* Ostokset maksukortilla: */
    @Test
    public void edullinenKorttiostoPalauttaaOikein() {
        assertFalse(paate.syoEdullisesti(kortti));
        kortti.lataaRahaa(10000);
        assertTrue(paate.syoEdullisesti(kortti));
    }

    @Test
    public void maukasKorttiostoPalauttaaOikein() {
        assertFalse(paate.syoMaukkaasti(kortti));
        kortti.lataaRahaa(10000);
        assertTrue(paate.syoMaukkaasti(kortti));
    }

    @Test
    public void edullinenKorttiostoVeloittaaOikein() {
        kortti.lataaRahaa(alkukassa);
        paate.syoEdullisesti(kortti);
        assertEquals(alkukassa - edullinen, kortti.saldo());
    }

    @Test
    public void maukasKorttiostoVeloittaaOikein() {
        kortti.lataaRahaa(alkukassa);
        paate.syoMaukkaasti(kortti);
        assertEquals(alkukassa - maukas, kortti.saldo());
    }

    @Test
    public void edullinenKorttiostoKasvattaaMyytyjaLounaita() {
        kortti.lataaRahaa(alkukassa);
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasKorttiostoKasvattaaMyytyjaLounaita() {
        kortti.lataaRahaa(alkukassa);
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenKorttiostoEiMuutaSaldoa() {
        kortti.lataaRahaa(alkukassa);
        paate.syoEdullisesti(kortti);
        assertEquals(alkukassa, paate.kassassaRahaa());
    }

    @Test
    public void maukasKorttiostoEiMuutaSaldoa() {
        kortti.lataaRahaa(alkukassa);
        paate.syoMaukkaasti(kortti);
        assertEquals(alkukassa, paate.kassassaRahaa());
    }
    
    /* Kortin lataus: */
    @Test
    public void kortilleLatausToimii() {
        paate.lataaRahaaKortille(kortti, 123);
        assertEquals(123, kortti.saldo());
        assertEquals(alkukassa + 123, paate.kassassaRahaa());
    }
    
    @Test
    public void kortilleLatausNegatiivisellaMaarallaEiMuutaSaldoja() {
        paate.lataaRahaaKortille(kortti, -123);
        assertEquals(0, kortti.saldo());
        assertEquals(alkukassa, paate.kassassaRahaa());
    }
}
