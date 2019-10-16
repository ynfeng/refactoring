package chapter4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author fynwin@gmail.com
 */
public class ProvinceTest {

    private Province province;

    @Before
    public void setup(){
        province = new Province(Province.sampleProvinceData());
    }

    @Test
    public void should_shortfall(){
        assertThat(province.shortfall(), is(5));
    }
    
    @Test
    public void should_get_230_profit(){
        assertThat(province.profit(), is(230));
    }
    
    @Test
    public void should_change_production(){
        province.producers().get(0).production(20);
        assertThat(province.shortfall(), is(-6));
        assertThat(province.profit(), is(292));
    }
}
