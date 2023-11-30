package er.er;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class TilaustenKäsittelyTest {

    private TilaustenKäsittely tilaustenKäsittely;
    private IHinnoittelija hinnoittelijaMock;

    @Before
    public void setUp() {
        tilaustenKäsittely = new TilaustenKäsittely();
        hinnoittelijaMock = mock(IHinnoittelija.class);
        tilaustenKäsittely.setHinnoittelija(hinnoittelijaMock);
    }

    @Test
    public void testKäsittelyHintaAlle100() {
        Asiakas asiakas = new Asiakas(150);
        Tuote tuote = new Tuote("Testituote", 80);
        Tilaus tilaus = new Tilaus(asiakas, tuote);

        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(10f);

        tilaustenKäsittely.käsittele(tilaus);

        assertEquals(150 - (80 * 0.9), asiakas.getSaldo(), 0.001);
    }

    @Test
    public void testKäsittelyHintaYliTaiYhtäSuuriKuin100() {
        Asiakas asiakas = new Asiakas(200);
        Tuote tuote = new Tuote("Testituote", 120);
        Tilaus tilaus = new Tilaus(asiakas, tuote);

        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(15f);

        tilaustenKäsittely.käsittele(tilaus);

        assertEquals(200 - (120 * 0.85), asiakas.getSaldo(), 0.001);
    }
}

